package novoline.ui.InfosUtils


import tomk.utils.BlurBuffer
import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager
import net.ccbluex.liquidbounce.utils.render.EaseUtils2.easeOutQuart

import net.ccbluex.liquidbounce.utils.render.RenderUtils
import net.ccbluex.liquidbounce.utils.render.RoundedUtil

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.*
import novoline.font.Fonts
import org.lwjgl.opengl.GL11
import java.awt.Color
import java.io.IOException

/**
 *@author xiatian233
 *@Date 2022/12/14
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

        RenderUtils.drawCircle2(sr.scaledWidth.toFloat() - 10F - 15F - 1.5F + Fonts.NovolineIcon.NovolineIcon45.NovolineIcon45.stringWidth("G") / 2,8F + Fonts.NovolineIcon.NovolineIcon45.NovolineIcon45.height / 2 ,13F,Color(24,24,24).rgb)

        //主界面

        net.ccbluex.liquidbounce.ui.font.Fonts.font35.drawCenteredString(
            "TomkClient 1.12.2",
            sr.scaledWidth / 2f,
            sr.scaledHeight - 5F - net.ccbluex.liquidbounce.ui.font.Fonts.font35.fontHeight,
            Color(200,200,200).rgb,true
        )
        progress = if (progress >= 1f) 1f else (System.currentTimeMillis() - lastMS).toFloat() / 2000F

        val trueAnim = easeOutQuart(progress.toDouble())

        GL11.glTranslated(0.0, (1 - trueAnim) * -sr!!.scaledHeight, 0.0)
        net.ccbluex.liquidbounce.ui.font.Fonts.tenacity100.drawCenteredString("TomkClient", sr.scaledWidth / 2f, sr.scaledHeight / 2f - 55 - 20 -Fonts.NovolineIcon.NovolineIcon75.NovolineIcon75.height,
            Color(110,183,225).rgb,true
        )
        BlurBuffer.CustomBlurRoundArea(sr!!.scaledWidth / 2f - 12 - 45 - 10, sr!!.scaledHeight / 2f - 67,(24 + 90 + 10 + 5).toFloat(), (20 * 4 + 7 * 5).toFloat(),12f,40f)
      /*  RoundedUtil.drawRound(sr!!.scaledWidth / 2f - 12 - 45 - 10, sr!!.scaledHeight / 2f - 67,(24 + 90 + 10 + 5).toFloat(), (20 * 4 + 7 * 5).toFloat(),6f,Color(24,24,24))

        RoundedUtil.drawRound(sr!!.scaledWidth / 2f - 12 - 45 - 5, sr!!.scaledHeight / 2f - 60, (24 + 95).toFloat(), 20f, 7f,
            Color(20,50,80)
        )

       */
        var Color =  Color(39,120,186).rgb
        if (RenderUtils.isHovered(sr!!.scaledWidth / 2f - 12 - 45 - 5, sr!!.scaledHeight / 2f - 60, (24 + 95).toFloat(), 20f,mouseX, mouseY)){
           Color = Color(63,186,213).rgb
        }


        net.ccbluex.liquidbounce.ui.font.Fonts.font35.drawCenteredString("SinglePlayer", sr.scaledWidth / 2f - 2.5F, sr.scaledHeight / 2f - 55,
            Color,true
        )
      //  RoundedUtil.drawRound(sr!!.scaledWidth / 2f - 12 - 45 - 5, sr!!.scaledHeight / 2f - 33, (24 + 90 + 5).toFloat(), 20f, 7f, Color(20,50,80))
        var Color2 =  Color(39,120,186).rgb
        if (RenderUtils.isHovered(  sr!!.scaledWidth / 2f - 12 - 45 - 5, sr!!.scaledHeight / 2f - 33, (24 + 90 + 5).toFloat(), 20f,mouseX, mouseY)){
            Color2 = Color(63,186,213).rgb
        }


        net.ccbluex.liquidbounce.ui.font.Fonts.font35.drawCenteredString("MultiPlayer", sr.scaledWidth / 2f - 2.5F, sr.scaledHeight / 2f - 28,
            Color2,true
        )
      //  RoundedUtil.drawRound(sr!!.scaledWidth / 2f - 12 - 45 - 5, sr!!.scaledHeight / 2f - 6, (24 + 90 + 5 ).toFloat(), 20f, 7f,
     //       Color(20,50,80)
     //   )
        var Color4 =  Color(39,120,186).rgb
        if (RenderUtils.isHovered(    sr!!.scaledWidth / 2f - 12 - 45 - 5, sr!!.scaledHeight / 2f - 6, (24 + 90 + 5 ).toFloat(), 20f,mouseX, mouseY)){
            Color4 = Color(63,186,213).rgb
        }


        net.ccbluex.liquidbounce.ui.font.Fonts.font35.drawCenteredString("Alts", sr.scaledWidth / 2f - 2.5F, sr.scaledHeight / 2f - 1,
            Color4,true
        )
    //    RoundedUtil.drawRound(sr!!.scaledWidth / 2f - 12 - 45 - 5, sr!!.scaledHeight / 2f + 21, (24 + 90 + 5).toFloat(), 20f, 7f, Color(20,50,80))
        var Color5 =  Color(39,120,186).rgb
        if (RenderUtils.isHovered(   sr!!.scaledWidth / 2f - 12 - 45 - 5, sr!!.scaledHeight / 2f + 21, (24 + 90 + 5).toFloat(), 20f,mouseX, mouseY)){
            Color5= Color(63,186,213).rgb
        }
        net.ccbluex.liquidbounce.ui.font.Fonts.font35.drawCenteredString("Shutdown", sr.scaledWidth / 2f - 2.5F, sr.scaledHeight / 2f + 26,
            Color5,true
        )
        //Outlined
        RoundedUtil.drawRoundOutline(sr!!.scaledWidth / 2f - 12 - 45 - 10, sr!!.scaledHeight / 2f - 67,(24 + 90 + 10 + 5).toFloat(), (20 * 4 + 7 * 5).toFloat(),12f,0.3f,Color(240,240,240,0),Color(17,34,54))
    }

    @Throws(IOException::class)
    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        progress = if (progress >= 1f) 1f else (System.currentTimeMillis() - lastMS).toFloat() / 2000F

        val trueAnim = easeOutQuart(progress.toDouble())

        GL11.glTranslated(0.0, (1 - trueAnim) * -height, 0.0)
        if (RenderUtils.isHovered(
                sr!!.scaledWidth / 2f - 12 - 45 - 5,
                sr!!.scaledHeight / 2f - 60,
                (24 + 95).toFloat(),
                20f,
                mouseX,
                mouseY
            )
        ) {
            Minecraft.getMinecraft().displayGuiScreen(GuiWorldSelection(this))
        }
        if (RenderUtils.isHovered(
                sr!!.scaledWidth / 2f - 12 - 45 - 5,
                sr!!.scaledHeight / 2f - 33,
                (24 + 90 + 5).toFloat(),
                20f,
                mouseX,
                mouseY
            )
        ) {
            Minecraft.getMinecraft().displayGuiScreen(GuiMultiplayer(this))
        }
    /*    if (RenderUtils.isHovered(sr!!.scaledWidth.toFloat() - 10F - 15F,5F,
                Fonts.NovolineIcon.NovolineIcon45.NovolineIcon45.stringWidth("G").toFloat(),
                Fonts.NovolineIcon.NovolineIcon45.NovolineIcon45.height.toFloat(),mouseX, mouseY)){
            LiquidBounce.wrapper.minecraft.displayGuiScreen(LiquidBounce.wrapper.classProvider.wrapGuiScreen(GuiAltManager())
            )
            Alt = !Alt
        }

     */
        if (RenderUtils.isHovered(
                sr!!.scaledWidth / 2f - 12 - 45 - 5,
                sr!!.scaledHeight / 2f - 6,
                (24 + 90 + 5 ).toFloat(),
                20f,
                mouseX,
                mouseY
            )
        ) {
            LiquidBounce.wrapper.minecraft.displayGuiScreen(LiquidBounce.wrapper.classProvider.wrapGuiScreen(GuiAltManager())
            )
            Alt = !Alt
        }
        if (RenderUtils.isHovered(  sr!!.scaledWidth / 2f - 12 - 45 - 5,
                sr!!.scaledHeight / 2f + 21,
                (24 + 90 + 5).toFloat(),
                20f, mouseX, mouseY)) {
            Minecraft.getMinecraft().shutdown()
        }
    }
}