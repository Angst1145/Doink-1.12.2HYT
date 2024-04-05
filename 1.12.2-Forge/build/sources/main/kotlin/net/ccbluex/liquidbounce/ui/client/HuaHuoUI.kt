/*
 * Glacier+ Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/WYSI-Foundation/LiquidBouncePlus/
 */
package net.ccbluex.liquidbounce.ui.client;



import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager
import net.ccbluex.liquidbounce.ui.font.FontLoaders
import net.ccbluex.liquidbounce.ui.font.Fonts
import net.ccbluex.liquidbounce.utils.MinecraftInstance
import net.ccbluex.liquidbounce.utils.render.RenderUtils
import net.ccbluex.liquidbounce.utils.render.tenacity.ColorUtil.interpolateColorsBackAndForth
import net.minecraft.client.gui.*
import net.minecraft.util.ResourceLocation
import org.lwjgl.input.Mouse
import java.awt.Color


//import java.util.List

class HuaHuoUI : GuiScreen() {


    private var currentX = 0f
    private var currentY = 0f

    var lastAnimTick: Long = 0L
    var alrUpdate = false
    var drag = false
    var huaHuo4s: MutableList<HuaHuo4> = ArrayList<HuaHuo4>()


    override fun initGui() {
        val sr = ScaledResolution(this.mc)
        val w = sr.scaledWidth/2
        val h = sr.scaledHeight/2
        huaHuo4s.clear()

        this.buttonList.add( HuaHuo2(ResourceLocation("zork/m.png"),2,w-130, h-41, 260, 58,"MultiPlayers"))
        this.buttonList.add( HuaHuo2(ResourceLocation("zork/s.png"),1,w-130, h-77+100, 127, 35,"SinglePlayer"))
        this.buttonList.add( HuaHuo2(ResourceLocation("zork/a.png"),5,w+3, h-77+100, 127, 35,"AltManager"))
        this.buttonList.add( HuaHuo2(ResourceLocation("zork/o.png"),3,w-220+340, h-62, 15, 15,"Options"))

        super.initGui()

    }
    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        val color =
            interpolateColorsBackAndForth(15, 1, Color(198, 215, 219, 200), Color(140, 194, 105, 200), false)
        val color2 = color.rgb
        val color3 = interpolateColorsBackAndForth(15, 1, Color(239, 124, 130), Color(123, 90, 163), false)
        val fontcolor = color3.rgb
        if (!alrUpdate) {
            lastAnimTick = System.currentTimeMillis()
            alrUpdate = true
        }
        val sr = ScaledResolution(this.mc)
        val res = ScaledResolution(this.mc)
        val h: Int = this.height
        val w: Int = this.width
        val a: Int = this.height/ 2
        val b: Int = this.width / 2
        drag = Mouse.isButtonDown(0)
        val xDiff: Float = (mouseX - h / 2 - currentX) / res.scaleFactor.toFloat()
        val yDiff: Float = (mouseY - w / 2 - currentY) / res.scaleFactor.toFloat()
        currentX += xDiff * 0.3f
        currentY += yDiff * 0.3f


        val scaledResolution = ScaledResolution(mc)
        val width2 = scaledResolution.scaledWidth
        val height2 = scaledResolution.scaledHeight
        drawBackground(1)



        //info
        Fonts.NewBold20.drawCenteredString("@Donking_Team", w/2f, h-10f, Color(255, 255, 255, 220).rgb,true)
        Fonts.font50.drawStringWithShadow(LiquidBounce.CLIENT_NAME, w / 2 - 125, h/2 - 78 , Color(255, 255, 255, 220).rgb)



        FontLoaders.F16.drawString(" ", 20.0f, (this.height - 53 - 22 + 7 + 30 + 5).toFloat(), fontcolor)
        super.drawScreen(mouseX, mouseY, partialTicks)
        if (!LiquidBounce.mainMenuPrep) {
            val animProgress = ((System.currentTimeMillis() - lastAnimTick).toFloat() / 2000F).coerceIn(0F, 1F)
            RenderUtils.drawRect(0F, 0F, width.toFloat(), height.toFloat(), Color(0F, 0F, 0F, 1F - animProgress))
            if (animProgress >= 1F)
                LiquidBounce.mainMenuPrep = true

        }



        if (huaHuo4s.size > 0) {
            val huaHuo4Iterator: MutableIterator<HuaHuo4> = huaHuo4s.iterator()
            while (huaHuo4Iterator.hasNext()) {
                val huaHuo4: HuaHuo4 = huaHuo4Iterator.next()
                huaHuo4.draw()
                if (huaHuo4.canRemove()) huaHuo4Iterator.remove()
            }
        }

        //Button Texts
        Fonts.NewBold20.drawCenteredString("MultiPlayer", w/2f, h/2 - 20.5f, Color(255, 255, 255, 220).rgb,false)
        Fonts.NewBold20.drawCenteredString("SinglePlayer", w/2f-63.5f, h/2 + 32.5f, Color(255, 255, 255, 220).rgb,false)
        Fonts.NewBold20.drawCenteredString("AltManager", w/2f+63.5f, h/2 + 32.5f, Color(255, 255, 255, 220).rgb,false)

    }
    override fun actionPerformed(button: GuiButton) {
        when (button.id) {
            1-> mc.displayGuiScreen(GuiWorldSelection(this))
            2-> mc.displayGuiScreen(GuiMultiplayer(this))
            3-> mc.displayGuiScreen(GuiOptions(this, this.mc.gameSettings))

            6-> mc.displayGuiScreen(GuiLanguage(this, this.mc.gameSettings, this.mc.languageManager))

            7-> mc.shutdown()
            5-> MinecraftInstance.mc.displayGuiScreen(MinecraftInstance.classProvider.wrapGuiScreen(GuiAltManager()))

        }
    }





}






