/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.liquidbounce.features.module.modules.render;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.ShaderEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.BloomUtil;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.blur.InGameBlurUtil;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.shader.Framebuffer;


@ModuleInfo(name = "BlurSettings", description = "Shader effect.", category = ModuleCategory.RENDER)
public class BlurSettings extends Module {
    public static final ListValue modeValues = new ListValue("BlurMode", new String[]{ "No", "New"}, "No");
    private final ListValue modeValuese = new ListValue("ShadowMode", new String[]{ "No", "New"}, "No");


    public  static final IntegerValue radius =  new IntegerValue("Blur Radius", 5, 1, 50 );

    private final IntegerValue shadowRadius=  new IntegerValue("shadowRadius", 6, 1, 20);
    private final IntegerValue  shadowOffset =  new IntegerValue("shadowOffset", 2, 1, 10);

    private Framebuffer bloomFramebuffer = new Framebuffer(1, 1, true);

    @Expose
    @SerializedName("toggled")
    protected boolean toggled;
    public void toggleSilent() {
        this.toggled = !this.toggled;
        if (this.toggled) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }

    public BlurSettings() {
        if (!toggled) this.toggleSilent();
    }

    private String currentMode;

    @Override
    public void onEnable() {

        super.onEnable();
    }

    public static void stuffToBlur(boolean bloom) {
    }
    public static void stuffToBlur2(boolean bloom) {

    }
    public void blurScreen() {
        if (!toggled) return;
        switch (modeValues.get()) {
            case "New":
                InGameBlurUtil.toBlurBuffer.bindFramebuffer(false);
                InGameBlurUtil.setupBuffers();
                //InGameBlurUtil.renderGaussianBlur((float) ((NumberSetting) Objects.requireNonNull(Rise.INSTANCE.getModuleManager().getSetting("Blur", "Radius"))).getValue(), (float) ((NumberSetting) Objects.requireNonNull(Rise.INSTANCE.getModuleManager().getSetting("Blur", "Compression"))).getValue(), true, false);
                InGameBlurUtil.renderGaussianBlur(radius.getValue().floatValue(), 2, true, false);
                mc.getFramebuffer().bindFramebuffer(false);
                break;
        }

        switch (modeValuese.get()) {
            case "New":
                bloomFramebuffer = RenderUtils.createFrameBuffer(bloomFramebuffer);
                bloomFramebuffer.framebufferClear();
                bloomFramebuffer.bindFramebuffer(true);
                LiquidBounce.eventManager.callEvent(new ShaderEvent());
                bloomFramebuffer.unbindFramebuffer();
                BloomUtil.renderBlur(bloomFramebuffer.framebufferTexture, shadowRadius.get(), shadowOffset.get());
                break;

        }
    }
}
