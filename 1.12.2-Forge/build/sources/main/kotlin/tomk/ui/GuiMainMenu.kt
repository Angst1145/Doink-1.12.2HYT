package tomk.ui

import tomk.utils.BlurBuffer
import tomk.utils.ShadowUtils
import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager
import net.ccbluex.liquidbounce.ui.font.Fonts
import net.ccbluex.liquidbounce.utils.render.EaseUtils.easeOutQuart
import net.ccbluex.liquidbounce.utils.render.RenderUtils
import net.ccbluex.liquidbounce.utils.render.RoundedUtil
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.*
import net.minecraft.client.renderer.GlStateManager
import org.lwjgl.opengl.GL11
import java.awt.Color
import java.io.IOException

/**
 *@author 7ad
 *@Date 2023/03/08
 */

class GuiMainMenu : GuiScreen() {
    var mc: Minecraft = Minecraft.getMinecraft()
    var sr: ScaledResolution? = null
    private var progress = 0f
    private var progress2 = 0f
    private var Alt = false
    private var lastMS = 0L
    private var lastMS2 = 0L
    override fun initGui() {
        sr = ScaledResolution(Minecraft.getMinecraft())
        lastMS = System.currentTimeMillis()
        progress = 0f
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawBackground(0)
        val sr = ScaledResolution(Minecraft.getMinecraft())
        var Color6 =  Color(255,255,255).rgb
        var Color7 =  Color(255,255,255).rgb
  Fonts.rise35.drawCenteredString(
            "DonkingClient 1.12.2",
            sr.scaledWidth / 2f,
            sr.scaledHeight - 5F - net.ccbluex.liquidbounce.ui.font.Fonts.rise35.fontHeight,
            Color(200,200,200).rgb,true
        )
        progress = if (progress >= 1f) 1f else (System.currentTimeMillis() - lastMS).toFloat() / 2000F
        val trueAnim = easeOutQuart(progress.toDouble())

        ShadowUtils.shadow(8f,{
            GL11.glPushMatrix()
            RoundedUtil.drawRound(
                sr!!.scaledWidth / 2f - 12 - 45 - 42,
                sr!!.scaledHeight / 2f - 59.5.toInt(),
                (101 + 95).toFloat(),
                26f,
                12f,
                Color(0,0,0)
            )
            GL11.glPopMatrix()

        },{
            GL11.glPushMatrix()
            GlStateManager.enableBlend()
            GlStateManager.disableTexture2D()
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
            RoundedUtil.drawRound(
                sr!!.scaledWidth / 2f - 12 - 45 - 42,
                sr!!.scaledHeight / 2f - 59.5.toInt(),
                (101 + 95).toFloat(),
                26f,
                12f,
                Color(0,0,0)
            )
            GlStateManager.enableTexture2D()
            GlStateManager.disableBlend()
            GL11.glPopMatrix()
        })

        RoundedUtil.drawRound(
            sr!!.scaledWidth / 2f - 12 - 45 - 42,
            sr!!.scaledHeight / 2f - 59.5.toInt(),
            (101 + 95).toFloat(),
            26f,
            7f,
            Color(7,26,40,150)
        )
        BlurBuffer.CustomBlurRoundArea(sr!!.scaledWidth / 2f - 12 - 45 - 42,
            sr!!.scaledHeight / 2f - 59.5.toInt(),
            (101 + 95).toFloat(),
            26f,7f,60f)
        var Color = Color(12,30,47,60)
        if (RenderUtils.isHovered(sr!!.scaledWidth / 2f - 12 - 45 - 43.5.toInt(),
                sr!!.scaledHeight / 2f - 60,
                (103 + 95).toFloat(),
                28f,mouseX, mouseY)){
            Color = Color(21,40,53,60)
        }
        RoundedUtil.drawRound(
            sr!!.scaledWidth / 2f - 12 - 45 - 43.5.toInt(),
            sr!!.scaledHeight / 2f - 60,
            (103 + 95).toFloat(),
            28f,
            7f,
            Color
        )
        Fonts.tenacitybold50.drawCenteredString(
            "Singleplayer",
            sr.scaledWidth / 2f - 2.5F,
            sr.scaledHeight / 2f - 52,
            Color(59,72,107).rgb,false
        )


        // Multi
        ShadowUtils.shadow(8f,{
            GL11.glPushMatrix()
            RoundedUtil.drawRound(
                sr!!.scaledWidth / 2f - 12 - 45 - 42,
                sr!!.scaledHeight / 2f - 24.5.toInt(),
                (101 + 95).toFloat(),
                26f,
                12f,
                Color(0,0,0)
            )
            GL11.glPopMatrix()

        },{
            GL11.glPushMatrix()
            GlStateManager.enableBlend()
            GlStateManager.disableTexture2D()
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
            RoundedUtil.drawRound(
                sr!!.scaledWidth / 2f - 12 - 45 - 42,
                sr!!.scaledHeight / 2f - 24.5.toInt(),
                (101 + 95).toFloat(),
                26f,
                12f,
                Color(0,0,0)
            )
            GlStateManager.enableTexture2D()
            GlStateManager.disableBlend()
            GL11.glPopMatrix()
        })

        RoundedUtil.drawRound(
            sr!!.scaledWidth / 2f - 12 - 45 - 42,
            sr!!.scaledHeight / 2f - 24.5.toInt(),
            (101 + 95).toFloat(),
            26f,
            7f,
            Color(7,26,40,150)
        )
        BlurBuffer.CustomBlurRoundArea(sr!!.scaledWidth / 2f - 12 - 45 - 42,
            sr!!.scaledHeight / 2f - 24.5.toInt(),
            (101 + 95).toFloat(),
            26f,7f,60f)
        var Color2 = Color(12,30,47,60)
        if (RenderUtils.isHovered(sr!!.scaledWidth / 2f - 12 - 45 - 43.5.toInt(),
                sr!!.scaledHeight / 2f - 25,
                (103 + 95).toFloat(),
                28f,mouseX, mouseY)){
            Color2 = Color(21,40,53,60)
        }
        RoundedUtil.drawRound(
            sr!!.scaledWidth / 2f - 12 - 45 - 43.5.toInt(),
            sr!!.scaledHeight / 2f - 25,
            (103 + 95).toFloat(),
            28f,
            7f,
            Color2
        )
        Fonts.tenacitybold50.drawCenteredString(
            "Multiplayer",
            sr.scaledWidth / 2f - 2.5F,
            sr.scaledHeight / 2f - 17,
            Color(59,72,107).rgb,false
        )
        // Alts
        ShadowUtils.shadow(8f,{
            GL11.glPushMatrix()
            RoundedUtil.drawRound(
                sr!!.scaledWidth / 2f - 12 - 45 - 42,
                sr!!.scaledHeight / 2f + 10.5.toInt(),
                (101 + 95).toFloat(),
                26f,
                12f,
                Color(0,0,0)
            )
            GL11.glPopMatrix()

        },{
            GL11.glPushMatrix()
            GlStateManager.enableBlend()
            GlStateManager.disableTexture2D()
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
            RoundedUtil.drawRound(
                sr!!.scaledWidth / 2f - 12 - 45 - 42,
                sr!!.scaledHeight / 2f + 10.5.toInt(),
                (101 + 95).toFloat(),
                26f,
                12f,
                Color(0,0,0)
            )
            GlStateManager.enableTexture2D()
            GlStateManager.disableBlend()
            GL11.glPopMatrix()
        })


        RoundedUtil.drawRound(
            sr!!.scaledWidth / 2f - 12 - 45 - 42,
            sr!!.scaledHeight / 2f + 10.5.toInt(),
            (101 + 95).toFloat(),
            26f,
            7f,
            Color(7,26,40,150)
        )

        BlurBuffer.CustomBlurRoundArea(sr!!.scaledWidth / 2f - 12 - 45 - 42,
            sr!!.scaledHeight / 2f + 10.5.toInt(),
            (101 + 95).toFloat(),
            26f,7f,60f)
        var Color3 = Color(12,30,47,60)
        if (RenderUtils.isHovered(sr!!.scaledWidth / 2f - 12 - 45 - 43.5.toInt(),
                sr!!.scaledHeight / 2f + 10,
                (103 + 95).toFloat(),
                28f,mouseX, mouseY)){
            Color3 = Color(21,40,53,60)
        }

        RoundedUtil.drawRound(
            sr!!.scaledWidth / 2f - 12 - 45 - 43.5.toInt(),
            sr!!.scaledHeight / 2f + 10,
            (103 + 95).toFloat(),
            28f,
            7f,
            Color3
        )
        Fonts.tenacitybold50.drawCenteredString(
            "Alts",
            sr.scaledWidth / 2f - 2.5F,
            sr.scaledHeight / 2f + 18,
            Color(59,72,107).rgb,false
        )
//        GL11.glTranslated(0.0, (1 - trueAnim) * -sr!!.scaledHeight, 1.5)
        // title
        Fonts.tenacity100.drawCenteredString(
            "DonkingClient",
            sr.scaledWidth / 2f,
            sr.scaledHeight /2f -Fonts.rise100.fontHeight - 107f,
            Color(235,235,235,200).rgb,false
        )
    }
    @Throws(IOException::class)
    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        progress = if (progress >= 1f) 1f else (System.currentTimeMillis() - lastMS).toFloat() / 2000F

        val trueAnim = easeOutQuart(progress.toDouble())

        GL11.glTranslated(0.0, (1 - trueAnim) * -height, 0.0)
        if (RenderUtils.isHovered(
                sr!!.scaledWidth / 2f - 12 - 45 - 43.5.toInt(),
                sr!!.scaledHeight / 2f - 60,
                (103 + 95).toFloat(),
                28f,
                mouseX,
                mouseY
            )
        ) {
            Minecraft.getMinecraft().displayGuiScreen(GuiWorldSelection(this))
        }
        // Multi
        if (RenderUtils.isHovered(
                sr!!.scaledWidth / 2f - 12 - 45 - 43.5.toInt(),
                sr!!.scaledHeight / 2f - 25,
                (103 + 95).toFloat(),
                28f,mouseX, mouseY
            )
        ) {
            Minecraft.getMinecraft().displayGuiScreen(GuiMultiplayer(this))
        }
        // Alt
        if (RenderUtils.isHovered(sr!!.scaledWidth / 2f - 12 - 45 - 43.5.toInt(),
                sr!!.scaledHeight / 2f + 10,
                (103 + 95).toFloat(),
                28f,mouseX, mouseY)){
            LiquidBounce.wrapper.minecraft.displayGuiScreen(
                LiquidBounce.wrapper.classProvider.wrapGuiScreen(
                    GuiAltManager()
                )
            )
            Alt = !Alt
        }
//        if (RenderUtils.isHovered(
//                sr!!.scaledWidth / 2f - 12 - 45 - 5,
//                sr!!.scaledHeight / 2f - 6,
//                (24 + 90 + 5 ).toFloat(),
//                20f,
//                mouseX,
//                mouseY
//            )
//        ) {
//            Minecraft.getMinecraft().displayGuiScreen(GuiOptions(this, Minecraft.getMinecraft().gameSettings))
//        }
//        if (RenderUtils.isHovered(  sr!!.scaledWidth / 2f - 12 - 45 - 5,
//                sr!!.scaledHeight / 2f + 21,
//                (24 + 90 + 5).toFloat(),
//                20f, mouseX, mouseY)) {
//            Minecraft.getMinecraft().shutdown()
    }
}