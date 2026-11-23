import java.nio.file.*;
import java.util.*;
import java.io.*;
import com.google.gson.JsonParser;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

/**
 * Static porting aid, not a Mixin runtime verifier. Requires ASM/Gson from compileClasspath.
 * Run after compileJava, with neoforge/build/classes/java/main and the game's compileClasspath:
 *   MixinAudit common/src/main/resources/roundabout.mixins.json
 *              forge/src/main/resources/roundabout.forge.mixins.json
 *
 * Prints TSV candidates for missing shadows/declared injection methods and callback signatures.
 * Inherited/injected members, bridge methods, coercion and overloads require manual review.
 * This does not validate @At instructions, locals, redirects, side-only loading, or game behavior.
 * An empty report is NOT evidence of a successful mod startup. No source/config is modified.
 */
public class MixinAudit {
    static final Map<String,ClassNode> cache = new HashMap<>();
    static ClassNode read(String name) {
        if(cache.containsKey(name)) return cache.get(name);
        try(InputStream in = MixinAudit.class.getClassLoader().getResourceAsStream(name+".class")) {
            ClassNode c=null;
            if(in!=null) {c=new ClassNode();new ClassReader(in).accept(c,ClassReader.SKIP_DEBUG|ClassReader.SKIP_FRAMES);}
            cache.put(name,c); return c;
        } catch(IOException e) {throw new RuntimeException(e);}
    }
    static List<AnnotationNode> annotations(List<AnnotationNode> a, List<AnnotationNode>b) {
        var n=new ArrayList<AnnotationNode>();if(a!=null)n.addAll(a);if(b!=null)n.addAll(b);return n;
    }
    static Object value(AnnotationNode a,String key,Object fallback) {
        if(a.values!=null)for(int i=0;i<a.values.size();i+=2)if(key.equals(a.values.get(i)))return a.values.get(i+1);
        return fallback;
    }
    static boolean member(String owner,String name,String desc,boolean field,boolean inherit) {
        ClassNode c=read(owner);if(c==null)return false;
        if(field) {for(FieldNode f:c.fields)if(f.name.equals(name)&&f.desc.equals(desc))return true;}
        else {for(MethodNode m:c.methods)if((name.endsWith("*") ? m.name.startsWith(name.substring(0, name.length()-1)) : m.name.equals(name))&&(desc.isEmpty()||m.desc.equals(desc)))return true;}
        if(!inherit)return false;
        if(c.superName!=null&&member(c.superName,name,desc,field,true))return true;
        for(String it:c.interfaces)if(member(it,name,desc,field,true))return true;
        return false;
    }
    static void missing(String kind,String mixin,String target,String name,String desc,String detail) {
        System.out.println(kind+"\t"+mixin+"\t"+target+"\t"+name+desc+"\t"+(detail.isEmpty() ? "-" : detail));
    }
    public static void main(String[] args)throws Exception {
        List<String> mixins = new ArrayList<>();
        for (String path : args) {
            var config = JsonParser.parseString(Files.readString(Path.of(path))).getAsJsonObject();
            String prefix = config.get("package").getAsString() + ".";
            for (String section : List.of("mixins", "client", "server")) {
                if (config.has(section)) for (var name : config.getAsJsonArray(section)) {
                    mixins.add((prefix + name.getAsString()).replace('.', '/'));
                }
            }
        }
        for(String mixin:mixins) {
            ClassNode c=read(mixin);if(c==null)throw new IllegalArgumentException(mixin);
            var targets=new ArrayList<String>();
            for(var a:annotations(c.visibleAnnotations,c.invisibleAnnotations))if(a.desc.endsWith("/Mixin;")) {
                for(Object o:(List<?>)value(a,"value",List.of()))targets.add(((Type)o).getInternalName());
                for(Object o:(List<?>)value(a,"targets",List.of()))targets.add(o.toString().replace('.','/'));
            }
            for(String target:targets) {
                if(read(target)==null) {missing("TARGET",mixin,target,"","","");continue;}
                for(FieldNode f:c.fields)for(var a:annotations(f.visibleAnnotations,f.invisibleAnnotations))if(a.desc.endsWith("/Shadow;")) {
                    if(!member(target,f.name,f.desc,true,true))missing("SHADOW_FIELD",mixin,target,f.name,f.desc,"");
                }
                for(MethodNode m:c.methods)for(var a:annotations(m.visibleAnnotations,m.invisibleAnnotations)) {
                    if(a.desc.endsWith("/Shadow;")) {
                        String n=m.name.replaceFirst("^shadow\\$","");
                        if(!member(target,n,m.desc,false,true))missing("SHADOW_METHOD",mixin,target,n,m.desc,"");
                    }
                    Object methods=value(a,"method",null);
                    if(methods instanceof List<?> list)for(Object o:list) {
                        String spec=o.toString(); int paren=spec.indexOf('(');
                        String n=paren<0?spec:spec.substring(0,paren), d=paren<0?"":spec.substring(paren);
                        if(!member(target,n,d,false,false))missing("INJECT_METHOD",mixin,target,n,d,"require="+value(a,"require","default"));
                        if(a.desc.equals("Lorg/spongepowered/asm/mixin/injection/Inject;")) {
                            Type[] parameters=Type.getArgumentTypes(m.desc); int ci=-1;
                            for(int k=0;k<parameters.length;k++)if(parameters[k].getDescriptor().startsWith("Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo")){ci=k;break;}
                            if(ci>=0)for(MethodNode tm:read(target).methods) {
                                if(!(n.endsWith("*") ? tm.name.startsWith(n.substring(0,n.length()-1)) : tm.name.equals(n))||(!d.isEmpty()&&!d.equals(tm.desc)))continue;
                                Type[] expected=Type.getArgumentTypes(tm.desc);
                                boolean mismatch=ci>0&&(ci!=expected.length||!Arrays.equals(Arrays.copyOf(parameters,ci),expected));
                                boolean voidReturn=Type.getReturnType(tm.desc).equals(Type.VOID_TYPE);
                                boolean plainInfo=parameters[ci].getDescriptor().endsWith("/CallbackInfo;");
                                if(mismatch||voidReturn!=plainInfo)missing("CALLBACK_SIGNATURE",mixin,target,tm.name,tm.desc,m.name+m.desc);
                            }
                        }
                    }
                }
            }
        }
    }
}
