package net.hydra.jojomod.mixin.metallica;

import net.hydra.jojomod.client.ClientUtil;
import net.minecraft.client.renderer.GameRenderer;
import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class MetallicaGameRenderer {

    @Inject(method = "renderItemInHand(Lnet/minecraft/client/Camera;FLorg/joml/Matrix4f;)V", at = @At(value = "TAIL"))
    private void metallica$resetTransparency(CallbackInfo ci)
    {
        ClientUtil.setThrowFadeToTheEther(1);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}