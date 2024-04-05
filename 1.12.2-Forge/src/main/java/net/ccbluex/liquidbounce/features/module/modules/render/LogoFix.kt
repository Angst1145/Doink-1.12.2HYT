package net.ccbluex.liquidbounce.features.module.modules.render

import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.Render2DEvent
import net.ccbluex.liquidbounce.event.ShaderEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.ui.client.font1.CFontRenderer
import net.ccbluex.liquidbounce.ui.client.font1.FontLoaders
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Text
import net.ccbluex.liquidbounce.ui.client.newdropdown.utils.render.DrRenderUtils
import net.ccbluex.liquidbounce.ui.client.newdropdown.utils.render.GradientUtil
import net.ccbluex.liquidbounce.ui.font.Fonts
import net.ccbluex.liquidbounce.utils.ServerUtils
import net.ccbluex.liquidbounce.utils.VisualUtils
import net.ccbluex.liquidbounce.utils.misc.MathUtils
import net.ccbluex.liquidbounce.utils.render.ColorUtils
import net.ccbluex.liquidbounce.utils.render.RenderUtils
import net.ccbluex.liquidbounce.utils.render.RoundedUtil
import net.ccbluex.liquidbounce.utils.render.tenacity.BloomUtil
import net.ccbluex.liquidbounce.utils.render.tenacity.ColorUtil
import net.ccbluex.liquidbounce.value.*
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.util.ResourceLocation
import org.lwjgl.input.Keyboard
import java.awt.Color
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.hypot
import kotlin.math.roundToLong

@ModuleInfo(name = "LogoFix", description = ":)", category = ModuleCategory.RENDER, keyBind = Keyboard.KEY_G)
class LogoFix : Module() {
    val logoValue = ListValue("Logo", arrayOf("New","Neverlose","Fluger","Novoline","Tuesday","Jello"), "Novoline")
    private val colorMode = ListValue("ColorMode", arrayOf("Rainbow", "Light Rainbow", "Gident"), "Gident")
    private val colorModeValue = ListValue("Color", arrayOf("Gident"), "Gident")
    val gradientDistanceValue = IntegerValue("GradientDistance", 50, 1, 200)
    val gradientLoopValue = IntegerValue("GradientLoop", 4, 1, 40)
    val gidentspeed = IntegerValue("GidentSpeed", 100, 1, 1000)
    var r = IntegerValue("r", 160, 0, 255)
    var g = IntegerValue("b", 50, 0, 255)
    var b = IntegerValue("g", 255, 0, 255)
    private val clientName = TextValue("ClientName", "DonkingClient")
    private val sColors = BoolValue("Colors", true)
    val hueInterpolation = BoolValue("hueInterpolation", false)
    val infoValue = BoolValue("Info", true)
    private val bottomLeftText: MutableMap<String, String> = LinkedHashMap()
    private var gradientColor1 = Color.WHITE
    private  var gradientColor2:java.awt.Color? = java.awt.Color.WHITE
    private  var gradientColor3:java.awt.Color? = java.awt.Color.WHITE
    private  var gradientColor4:java.awt.Color? = java.awt.Color.WHITE
    private fun drawInfo(clientColors: Array<Color>) {

        val sr = ScaledResolution(Minecraft.getMinecraft())
        bottomLeftText["XYZ:"] = mc.thePlayer!!.posX.roundToLong().toString() + " " + mc.thePlayer!!.posY.roundToLong() + " " + mc.thePlayer!!.posZ.roundToLong()
        bottomLeftText["Speed:"] = calculateBPS().toString()
        bottomLeftText["FPS:"] = Minecraft.getDebugFPS().toString()
        val yOffset = 0.toFloat()
        val height = ((FontLoaders.SF18.height + 2) * bottomLeftText.keys.size).toFloat()
        val width = FontLoaders.SF18.getStringWidth("XYZ:").toFloat()
        GradientUtil.applyGradientVertical(2f, sr.scaledHeight - (height + yOffset), width, height, 1f, clientColors[0], clientColors[1]) {
            var boldFontMovement = FontLoaders.SF18.height + 2 + yOffset
            for ((key, value) in bottomLeftText) { BloomUtil.drawAndBloom {
                FontLoaders.SF18.drawString(key + value, 2f, sr.scaledHeight - boldFontMovement, -1)
            }
                boldFontMovement += (FontLoaders.SF18.height + 2).toFloat()
            }
        }

    }


    private fun calculateBPS(): Double {
        val bps = hypot(mc.thePlayer!!.posX - mc.thePlayer!!.prevPosX, mc.thePlayer!!.posZ - mc.thePlayer!!.prevPosZ) * mc.timer.timerSpeed * 20
        return (bps * 100.0).roundToLong() / 100.0
    }
    @EventTarget
    fun onShader(event: ShaderEvent?) {
        when(logoValue.get().toLowerCase()) {
        "neverlose" -> {
            var  username: String? = mc.session.username
            var  servername: String? = ServerUtils.getRemoteIp()
            var  fps: String? = Minecraft.getDebugFPS().toString() + "fps"
            val times = SimpleDateFormat("HH:mm").format(Calendar.getInstance().time)
            RenderUtils.drawRoundedRect2(6F, 0.toFloat()+5F,
                net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SFBOLD.SFBOLD_18.SFBOLD_18.stringWidth("TomkClient")
                        + net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SF.SF_16.SF_16.stringWidth(" | $username | $servername | $times | $fps")
                        + 3F + 5F,
                12F, 2F, -1)
        }
        "novoline" -> {
            var  username: String? = mc.session.username
            var  servername: String? = ServerUtils.getRemoteIp()
            var  fps: String? = Minecraft.getDebugFPS().toString() + "fps"
            val times = SimpleDateFormat("HH:mm").format(Calendar.getInstance().time)
            val x1 =net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SF.SF_16.SF_16.stringWidth("| ")+1
            val x2 =net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SF.SF_16.SF_16.stringWidth("$username")+3
            val x3 =net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SF.SF_16.SF_16.stringWidth("|")+3
            val x4 =net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SF.SF_16.SF_16.stringWidth("$servername")-1
            val x5 =net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SF.SF_16.SF_16.stringWidth("|")+3
            val x6 = net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SF.SF_16.SF_16.stringWidth("$times")+6
            val x7 =net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SF.SF_16.SF_16.stringWidth("|")+2
            val x8 =   net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SF.SF_16.SF_16.stringWidth("$fps")+3
            RenderUtils.drawRoundedRect2(6F, 0.toFloat()+5F, net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SFBOLD.SFBOLD_18.SFBOLD_18.stringWidth("OnLooker Reborn")
                    +x1+x2+x3+x4+x5+x6+x7+x8-18f, 12F, 0F, -1)
         }
       }
    }
    @EventTarget
    fun onRender2D(event: Render2DEvent?) {
        val sigmaY = 4
        val sigmaX = 8
        // PlayTime
        val clientColors = clientColors
        when (logoValue.get().toLowerCase()) {
            "fluger" -> {
                var  animationA: Int = 0
                var  animate: Boolean = false
                var  xA: Float = 10.0f
                var  yA: Float = 10.0f
                var  heightA: Float = 10.0f
                var  timeB: String? = SimpleDateFormat("HH:mm").format(Calendar.getInstance().time)
                var  user: String? =  mc.session.username
                var  textA: String? = "TomkClient | Time: $timeB | User: $user"
                var  textWidth: Int = net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SFBOLD.SFBOLD_18.SFBOLD_18.stringWidth(textA)
                var  widthA: Float = (textWidth + 11).toFloat()
                if (animationA >= 255){
                    animate = true
                }

                if (animationA <= 0) {
                    animate = false
                }
                if (animate) {
                    --animationA
                }
                if (!animate) {
                    ++animationA
                }
                val hud = LiquidBounce.moduleManager.getModule(HUD::class.java) as HUD
                gradientColor1 = RenderUtils.interpolateColorsBackAndForth(
                    15,
                    0,
                    getClientColor(),
                    getAlternateClientColor(),
                    hud.hueInterpolation.get()
                )
                gradientColor2 = RenderUtils.interpolateColorsBackAndForth(
                    15,
                    90,
                    getClientColor(),
                    getAlternateClientColor(),
                    hud.hueInterpolation.get()
                )
                gradientColor3 = RenderUtils.interpolateColorsBackAndForth(
                    15,
                    180,
                    getClientColor(),
                    getAlternateClientColor(),
                    hud.hueInterpolation.get()
                )
                gradientColor4 = RenderUtils.interpolateColorsBackAndForth(
                    15,
                    270,
                    getClientColor(),
                    getAlternateClientColor(),
                    hud.hueInterpolation.get()
                )
                animationA = MathUtils.clamp_int(animationA, 0, 255)
                RoundedUtil.drawGradientRound(
                    xA,
                    yA,
                    widthA,
                    heightA,
                    4.0f,
                    DrRenderUtils.applyOpacity(gradientColor4, .85f), gradientColor1, gradientColor3, gradientColor2
                )

                net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SFBOLD.SFBOLD_18.SFBOLD_18.drawString(textA, xA + 5.0f, yA + 2.5f, -1)
            }
            "neverlose" -> {
                var  username: String? = mc.session.username
                var  servername: String? = ServerUtils.getRemoteIp()
                var  fps: String? = Minecraft.getDebugFPS().toString() + "fps"
                val times = SimpleDateFormat("HH:mm").format(Calendar.getInstance().time)
                RoundedUtil.drawRound(6F, 5F, net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SFBOLD.SFBOLD_18.SFBOLD_18.stringWidth("TomkClient") + net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SF.SF_16.SF_16.stringWidth(" | $username | $servername | $times | $fps") + 3F + 5F, 12F, 1F,
                    Color(0, 0, 0, 100)
                )
                net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SFBOLD.SFBOLD_18.SFBOLD_18.drawString(
                    "TomkClient",
                    9,
                    7,
                    Color(24, 114, 165).rgb
                )
                net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SFBOLD.SFBOLD_18.SFBOLD_18.drawString("TomkClient", 8, 7, -1)
                net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SF.SF_16.SF_16.drawString(
                    " | $username | $servername | $times | $fps",
                    net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SFBOLD.SFBOLD_18.SFBOLD_18.stringWidth("TomkClient") + 11,
                    8,
                    -1
                )
            }
            "new" -> {
                GradientUtil.applyGradientHorizontal(5f, 5f,
                    FontLoaders.T40.getStringWidth(clientName.get()).toFloat(), 20f, 1f, clientColors[0], clientColors[1]
                ) {
                    RenderUtils.setAlphaLimit(0f)
                    BloomUtil.drawAndBloom {
                        FontLoaders.T40.drawStringWithShadow(clientName.get(), 5.0, 5.0, Color(0, 0, 0, 0).rgb
                        )
                    }
                }
                GlStateManager.resetColor()
                FontLoaders.T18.drawString(LiquidBounce.CLIENT_VERSIO, (FontLoaders.T40.getStringWidth(clientName.get()) + 4).toFloat(), 5f,
                    clientColors[1].rgb
                )

            }
            "tuesday" -> {
                val df = SimpleDateFormat("HH:mm")
                val text =
                    LiquidBounce.CLIENT_NAME + " " + Text.DATE_FORMAT.format(System.currentTimeMillis()) + "(" + df.format(System.currentTimeMillis()) + ")" + " - DEV:" + LiquidBounce.CLIENT_CREATOR + " - User:"+ mc2.getSession().username
                val width = Math.max(100, Fonts.font40.getStringWidth(text) + 6)
                VisualUtils.drawRect(2.0, 5.0, 2.0 + width, 22.0, Color(15, 15, 15, 220).rgb)
                //     VisualUtils.drawRect(2.0, 5.0, 2.0 + width, 6.5,
                //      Color(r.get(), g.get(), b.get(), 255).rgb
                for (i in 0..(gradientLoopValue.get() - 1)) {
                    val barStart = i.toDouble() / gradientLoopValue.get().toDouble()*163
                    val barEnd = (i + 1).toDouble() / gradientLoopValue.get().toDouble()*163
                    RenderUtils.drawGradientSideways(2.0, 5.0, 2.0 + width, 6.5, getColorAtIndex(i), getColorAtIndex(i + 1))
                }

                Fonts.font40.drawCenteredString(
                    text,
                    (width / 2 + 2).toFloat(),
                    (((22 + 6.5) / 2 - Fonts.font40.fontHeight / 2).toFloat()),
                    Color(255, 255, 255).rgb,
                    true
                )
            }
            "jello" ->{
                val fr: CFontRenderer = FontLoaders.jellolight18

                RenderUtils.drawShadowImage(
                    sigmaX - 12 - fr.getStringWidth("TomkClient") / 2 - 8,
                    sigmaY + 1,
                    125,
                    50,
                    ResourceLocation("tomk/shadow/arraylistshadow.png")
                )

                FontLoaders.jellolightBig.drawString("TomkClient", 8.0F, 4.0F + 1f, Color(255, 255, 255, 130).rgb)
                FontLoaders.jellolight18.drawCenteredString(
                    "Jello",
                    8.0 + 10,
                    4.0 + 28,
                    Color(255, 255, 255, 170).rgb
                )
            }
            "novoline" -> {
                val counter = intArrayOf(0)
                var  username: String? = mc.session.username
                var  servername: String? = ServerUtils.getRemoteIp()
                var  fps: String? = Minecraft.getDebugFPS().toString() + "fps"
                val times = SimpleDateFormat("HH:mm").format(Calendar.getInstance().time)
                val x1 =net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SF.SF_16.SF_16.stringWidth("| ")+1
                val x2 =net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SF.SF_16.SF_16.stringWidth("$username")+3
                val x3 =net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SF.SF_16.SF_16.stringWidth("|")+3
                val x4 =net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SF.SF_16.SF_16.stringWidth("$servername")-1
                val x5 =net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SF.SF_16.SF_16.stringWidth("|")+3
                val x6 = net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SF.SF_16.SF_16.stringWidth("$times")+6
                val x7 =net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SF.SF_16.SF_16.stringWidth("|")+2
                val x8 =   net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SF.SF_16.SF_16.stringWidth("$fps")+3
                RenderUtils.drawRoundedRect2(6F, 0.toFloat()+5F, net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SFBOLD.SFBOLD_18.SFBOLD_18.stringWidth("OnLooker Reborn")
                        +x1+x2+x3+x4+x5+x6+x7+x8-18f, 12F, 0F, Color(0, 0, 0, 100).rgb)
                val startX = 6
                for (i in 0..(AColorPalette.gradientLoopValue.get() - 1)) {
                    val barStart = startX+i.toDouble() / AColorPalette.gradientLoopValue.get().toDouble()*(net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SFBOLD.SFBOLD_18.SFBOLD_18.stringWidth("OnLooker Reborn")+x1+x2+x3+x4+x5+x6+x7+x8-18f)
                    val barEnd = startX+(i + 1).toDouble() / AColorPalette.gradientLoopValue.get().toDouble()*(net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SFBOLD.SFBOLD_18.SFBOLD_18.stringWidth("OnLooker Reborn")+x1+x2+x3+x4+x5+x6+x7+x8-18f)
                    RenderUtils.drawGradientSideways( barStart, 4.toDouble(), 0 + barEnd, 5.0, getColorAtIndex(i), getColorAtIndex(i + 1))
                }
                GradientUtil.applyGradientHorizontal(9f, 9f,
                    FontLoaders.SF16.getStringWidth(clientName.get()).toFloat(), 20f, 1f, clientColors[1], clientColors[0]) {
                    RenderUtils.setAlphaLimit(0f)
                    BloomUtil.drawAndBloom {
                        FontLoaders.SF16.drawStringWithShadow(clientName.get(), 9.0,
                            9.0, Color(0, 0, 0, 0).rgb
                        )
                    }
                }
                GlStateManager.resetColor()


                net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SF.SF_16.SF_16.drawString(
                    "|",
                    FontLoaders.SF16.getStringWidth(clientName.get())+11,
                    9,Color(100,100,100).rgb
                )

                net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SF.SF_16.SF_16.drawString(
                    "$username",
                    FontLoaders.SF16.getStringWidth(clientName.get())+x1+11,
                    9,
                    -1
                )

                net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SF.SF_16.SF_16.drawString(
                    "|",
                    FontLoaders.SF16.getStringWidth(clientName.get())+x1+x2+11,
                    9,
                    Color(100,100,100).rgb
                )

                net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SF.SF_16.SF_16.drawString(
                    "$servername",
                    FontLoaders.SF16.getStringWidth(clientName.get())+x1+x2+x3+11,
                    9,
                    -1
                )

                net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SF.SF_16.SF_16.drawString(
                    "|",
                    FontLoaders.SF16.getStringWidth(clientName.get())+x1+x2+x3+x4+15,
                    9,
                    Color(100,100,100).rgb
                )

                net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SF.SF_16.SF_16.drawString(
                    "$times",
                    Fonts.tenacitybold35.getStringWidth(clientName.get())+x1+x2+x3+x4+x5+11,
                    9,
                    -1
                )

                net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SF.SF_16.SF_16.drawString(
                    "|",
                    FontLoaders.SF16.getStringWidth(clientName.get())+x1+x2+x3+x4+x5+x6+11,
                    9,
                    Color(100,100,100).rgb
                )

                net.ccbluex.liquidbounce.ui.client.fonts.impl.Fonts.SF.SF_16.SF_16.drawString(
                    "$fps",
                    FontLoaders.SF16.getStringWidth(clientName.get())+x1+x2+x3+x4+x5+x6+x7+11,
                    9,
                    -1
                )


            }
        }
        if (infoValue.get()) {
            drawInfo(clientColors)
        }
    }
    private fun mixColors(color1: Color, color2: Color): Color {
        return if (sColors.get()) {
            ColorUtil.interpolateColorsBackAndForth(
                15,
                1,
                color1,
                color2,
                hueInterpolation.get()
            )
        } else {
            ColorUtil.interpolateColorC(color1, color2, 0F)
        }
    }

    fun getClientColor(): Color {
        return Color(AColorPalette.r.get(), AColorPalette.g.get(), AColorPalette.b.get())
    }

    fun getAlternateClientColor(): Color {
        return Color(AColorPalette.r2.get(), AColorPalette.g2.get(), AColorPalette.b2.get())
    }
    private val clientColors: Array<Color> get() {
        val firstColor: Color
        val secondColor: Color
        when (colorMode.get().toLowerCase()) {
            "light rainbow" -> {
                firstColor = ColorUtil.rainbow(15, 1, .6f, 1f, 1f)
                secondColor = ColorUtil.rainbow(15, 40, .6f, 1f, 1f)
            }

            "rainbow" -> {
                firstColor = ColorUtil.rainbow(15, 1, 1f, 1f, 1f)
                secondColor = ColorUtil.rainbow(15, 40, 1f, 1f, 1f)
            }
            "gident" -> {
                firstColor =
                    mixColors(getClientColor(), getAlternateClientColor())
                secondColor =
                    mixColors(getAlternateClientColor(), getClientColor())
            }
            else -> {
                firstColor = Color(-1)
                secondColor = Color(-1)
            }
        }
        return arrayOf(firstColor, secondColor)
    }
    fun getFadeProgress() = 0f
    fun getColor(color: Color) = ColorUtils.reAlpha(color, color.alpha / 255F * (1F - getFadeProgress()))
    fun getColor(color: Int) = getColor(Color(color))
    private fun getColorAtIndex(i: Int): Int {
        return getColor(when (AColorPalette.colorModeValue.get()) {
            "Rainbow" -> RenderUtils.getRainbowOpaque(AColorPalette.waveSecondValue.get(), AColorPalette.saturationValue.get(), AColorPalette.brightnessValue.get(), i * AColorPalette.gradientDistanceValue.get())
            "Sky" -> RenderUtils.SkyRainbow(i * AColorPalette.gradientDistanceValue.get(), AColorPalette.saturationValue.get(), AColorPalette.brightnessValue.get())
            "Slowly" -> ColorUtils.LiquidSlowly(System.nanoTime(), i * AColorPalette.gradientDistanceValue.get(), AColorPalette.saturationValue.get(), AColorPalette.brightnessValue.get())!!.rgb
            "Fade" -> ColorUtils.fade(Color(AColorPalette.r.get(), AColorPalette.g.get(), AColorPalette.b.get()), i * AColorPalette.gradientDistanceValue.get(), 100).rgb
            "Gident" -> RenderUtils.getGradientOffset(Color(AColorPalette.r.get(), AColorPalette.g.get(), AColorPalette.b.get()), Color(AColorPalette.r2.get(), AColorPalette.g2.get(),
                AColorPalette.b2.get(),1), (Math.abs(System.currentTimeMillis() /
                    AColorPalette.gradientSpeed.get().toDouble() + i * AColorPalette.gradientDistanceValue.get()) / 10)).rgb
            else -> -1
        }).rgb
    }


    override val tag: String?
        get() = logoValue.get()
}
