package net.hydra.jojomod.mixin.access;

import net.hydra.jojomod.access.IAreaOfEffectCloud;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.item.alchemy.Potion;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

@Mixin(AreaEffectCloud.class)
public class AccessAreaOfEffectCloud implements IAreaOfEffectCloud {

    /**There is no reason for these to be private or protected, we should be able to tap into them.*/
    @Unique
    @Override
    public List<MobEffectInstance> roundabout$getEffects(){
        return this.potionContents.customEffects();
    }
    @Unique
    @Override
    public Potion roundabout$getPotions(){
        return this.potionContents.potion().map(net.minecraft.core.Holder::value).orElse(null);
    }

    /**Shadows, ignore
     * -------------------------------------------------------------------------------------------------------------
     * */


    @Shadow private net.minecraft.world.item.alchemy.PotionContents potionContents;
}
