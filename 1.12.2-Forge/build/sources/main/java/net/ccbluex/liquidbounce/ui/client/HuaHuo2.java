package net.ccbluex.liquidbounce.ui.client;

import java.awt.Color;

import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.tenacity.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class HuaHuo2 extends GuiButton {
    public ResourceLocation img;

    private int alpha;

    public HuaHuo2(ResourceLocation res, int buttonId, int x, int y, int widthIn, int hightIn, String buttonText) {
        super(buttonId, x, y, widthIn, hightIn, buttonText);
        this.img = res;
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        int color = ColorUtil.interpolateColorsBackAndForth(15, 1, new Color(198, 215, 219), new Color(140, 194, 105), false).getRGB();
        if (this.enabled) {
            this.hovered = (mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y+ this.height);
            int delta = RenderUtils.deltaTime;

            RenderUtils.drawImage4(this.img, this.x, this.y, this.width, this.height);
            RenderUtils.drawBorderedRect(this.x, this.y, (this.x + this.width), (this.y+ this.height), 1.5F, (new Color(82, 82, 82, 0)).getRGB(), (new Color(20, 20, 20, this.alpha)).getRGB());
            if (this.visible && this.hovered) {

                this.alpha = (int)(this.alpha + 0.3F * delta);
                if (this.alpha >= 135)
                    this.alpha = 135;
            } else {
                this.alpha = (int)(this.alpha - 0.3F * delta);
                if (this.alpha <= 10)
                    this.alpha = 10;
            }
            Minecraft.getMinecraft().getTextureManager();
            mouseDragged(mc, mouseX, mouseY);
            GlStateManager.resetColor();
        }
    }
}
