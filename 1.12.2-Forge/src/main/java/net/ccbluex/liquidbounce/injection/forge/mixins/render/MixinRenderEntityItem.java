package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.Chams;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.entity.item.EntityItem;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderEntityItem.class)
public class MixinRenderEntityItem {

    @Inject(method = "doRender", at = @At("HEAD"))
    private void injectChamsPre(CallbackInfo callbackInfo) {
        final Chams chams = (Chams) LiquidBounce.moduleManager.getModule(Chams.class);
        if (chams.getState() && chams.getItemsValue().get()) {
            GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
            GL11.glPolygonOffset(1.0F, -1000000F);
        }
    }

    @Inject(method = "doRender", at = @At("RETURN"))
    private void injectChamsPost(CallbackInfo callbackInfo) {
        final Chams chams = (Chams) LiquidBounce.moduleManager.getModule(Chams.class);
        if (chams.getState() && chams.getItemsValue().get()) {
            GL11.glPolygonOffset(1.0F, 1000000F);
            GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
        }
    }
    @Inject(method = "doRender(Lnet/minecraft/entity/item/EntityItem;DDDFF)V", at = @At("HEAD"), cancellable = true)
    public void doRender(EntityItem f1, double f2, double f3, double f4, float f5, float j, CallbackInfo ci) {

    }
}
