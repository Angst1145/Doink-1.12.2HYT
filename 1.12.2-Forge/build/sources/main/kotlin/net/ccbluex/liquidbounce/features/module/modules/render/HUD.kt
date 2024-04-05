/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.liquidbounce.features.module.modules.render


import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
import net.ccbluex.liquidbounce.api.minecraft.potion.PotionType
import net.ccbluex.liquidbounce.event.*
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.ui.font.FontLoaders
import net.ccbluex.liquidbounce.ui.font.Fonts
import net.ccbluex.liquidbounce.utils.render.RenderUtils
import net.ccbluex.liquidbounce.utils.render.RoundedUtil
import net.ccbluex.liquidbounce.utils.render.tenacity.ColorUtil
import net.ccbluex.liquidbounce.value.*
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import java.awt.Color
import java.text.DecimalFormat
import kotlin.math.pow

@ModuleInfo(name = "HUD", description = "Toggles visibility of the HUD.", category = ModuleCategory.RENDER, array = false)
class HUD : Module() {
    private val toggleMessageValue = BoolValue("DisplayToggleMessage", true)
    private val sColors = BoolValue("Colors", true)
    private val toggleSoundValue = ListValue("ToggleSound", arrayOf("None", "Default", "Custom"), "Custom")
    private val colorRedValue = IntegerValue("R", 255, 0, 255)
    private val colorGreenValue = IntegerValue("G", 255, 0, 255)
    private val colorBlueValue = IntegerValue("B", 255, 0, 255)
    val domainValue = TextValue("Scoreboard-Domain", "TomkClient@2023")
    private val infoValue = BoolValue("Info", false)
    val fontChatValue = BoolValue("FontChat", false)
    val chatRect = BoolValue("ChatRect", false)
    val chatAnimValue = BoolValue("ChatAnimation", true)
    val blackHotbarValue = BoolValue("BlackHotbar", false)
    @JvmField
    val hotbar = BoolValue("Hotbar", false)
    val ColorItem = BoolValue("HotbarRect", false)
    val hotbarEaseValue = BoolValue("HotbarEase", false)
    val hueInterpolation = BoolValue("HueInterpolate", false)
    val inventoryParticle = BoolValue("InventoryParticle", false)
    private val inventoryrender = BoolValue("Inventory-render",false)
    val blurValue = BoolValue("Blur", false)
    val BlurStrength = FloatValue("BlurStrength", 15F, 0f, 30F)//这是模糊°
    val Radius = IntegerValue("BlurRadius", 10 , 1 , 50 )
    val ChineseScore = BoolValue("ChineseScore", true)

    companion object {
        val clolormode = ListValue(
            "ColorMode", arrayOf(
                "Rainbow",
                "Light Rainbow",
                "Static",
                 "Gident",
                "Double Color",
                "Default"
            ), "Light Rainbow"
        )
    }
    private var easingHealth = 0f
    private val decimalFormat:DecimalFormat = DecimalFormat()
    private var easingarmor: Float = 0F
    private var easingxp: Float = 0F
    private var easingfood: Float = 0F
    private var easingValue = 0



    fun getInventoryrender():BoolValue{
        return inventoryrender
    }

    @EventTarget
    fun onTick(event: TickEvent) {
        LiquidBounce.moduleManager.shouldNotify = toggleMessageValue.get()
        LiquidBounce.moduleManager.toggleSoundMode = toggleSoundValue.values.indexOf(toggleSoundValue.get())
    }
    val sr = ScaledResolution(mc2)
    val left: Int = sr.getScaledWidth() / 2 + 91
    val top: Int = sr.getScaledHeight() - 100
    val x = 380

    @EventTarget
    fun onRender2D(event: Render2DEvent?) {
        if (classProvider.isGuiHudDesigner(mc.currentScreen))
            return
        val sr = ScaledResolution(mc2)
        val left = sr.scaledWidth / 2 + 91
        val top= sr.scaledHeight - 50
        val x = left - 1 * 8 - 180
        val scaledResolution = ScaledResolution(mc2)
        if (infoValue.get()) {
            val text = String.format(
                "VERSION:" + LiquidBounce.CLIENT_VERSION
            )
            val bps = Math.hypot(
                mc.thePlayer!!.posX - mc.thePlayer!!.prevPosX,
                mc.thePlayer!!.posZ - mc.thePlayer!!.prevPosZ
            ) * mc.timer.timerSpeed * 20
            val XYZ = String.format(
                "XYZ: " + mc2.player.posX.toInt() + ", " + mc2.player.posY.toInt() + "," + mc2.player.posZ.toInt()
            )
            val FPS = String.format(

                "FPS: " + Minecraft.getDebugFPS()

            )
            val BPS = String.format(
                "BPS: " + Math.round(bps * 100.0) / 100.0
            )
            Fonts.rubik40.drawString(
                text,
                (classProvider.createScaledResolution(mc).scaledWidth - Fonts.rubik40.getStringWidth(text) - 2).toFloat(),
                ((classProvider.createScaledResolution(mc).scaledHeight - Fonts.rubik40.fontHeight - 1).toDouble()
                    .toFloat()),
                Color(210, 210, 210).rgb,
                true
            )
            Fonts.rubik40.drawString(
                XYZ,
                2f,
                (scaledResolution.scaledHeight - 10).toFloat(),
                Color(210, 210, 210).rgb,
                true
            )
            Fonts.rubik40.drawString(
                BPS,
                2f,
                (scaledResolution.scaledHeight - 10- Fonts.rubik40.fontHeight).toFloat(),
                Color(210, 210, 210).rgb,
                true
            )
            Fonts.rubik40.drawString(
                FPS,
                2f,
                (scaledResolution.scaledHeight - 10- Fonts.rubik40.fontHeight*2).toFloat(),
                Color(210, 210, 210).rgb,
                true
            )
        }
        if (hotbar.get() && mc.thePlayer != null && mc.theWorld != null ) {
            var color2 = Color(212 ,48 ,48).rgb
            if (easingHealth <= 0f ){
                easingHealth  = 0F
            }
            if (easingHealth >= mc.thePlayer!!.maxHealth ){
                easingHealth  = mc.thePlayer!!.maxHealth
            }
            if (easingarmor <= 0){
                easingarmor = 0F
            }
            if (easingarmor >= 20f){
                easingarmor = 20F
            }
            if (easingfood <= 0){
                easingfood = 0F
            }
            if (easingfood >= 20f){
                easingfood = 20F
            }
            if (mc.thePlayer!!.isPotionActive(classProvider.getPotionEnum(PotionType.REGENERATION))){ color2 = Color(200, 90, 90).rgb }
            RoundedUtil.drawRound(x.toFloat(), top.toFloat()-8, 100F, 5f, 1f, Color(126,11,11))
            RoundedUtil.drawRound(x.toFloat(), top.toFloat()-8, (easingHealth / mc.thePlayer!!.maxHealth) * 100F, 5f, 1f, Color(color2))
            RoundedUtil.drawRound(x.toFloat(), top.toFloat()+10f, 210f, 5f, 1f, Color(37,94,37))
            RoundedUtil.drawRound(x.toFloat(), top.toFloat()+10f, (easingxp / 20F) * 40, 5f, 1f,Color(65, 205, 125))
            RoundedUtil.drawRound(x.toFloat(), top.toFloat()-18, 100f, 5f, 1f, Color(35,105,136))
            RoundedUtil.drawRound(x.toFloat(), top.toFloat()-18, (easingarmor / 20F) * 100F, 5f, 1f, Color(73,173,203))
            RoundedUtil.drawRound(x.toFloat() + 110F, top.toFloat()-8, 100f, 5f, 1f, Color(100,76,37))
            RoundedUtil.drawRound(x.toFloat() + 110F, top.toFloat()-8, (easingfood / 20F) * 100F, 5f, 1f, Color(255 ,140 ,25))
            Fonts.posterama30.drawString("Armo/" +decimalFormat.format((easingarmor / 20f) * 100f) + "%", x.toFloat() +2, ((top+3  - Fonts.posterama30.fontHeight / 2).toFloat()) - 3f - 15f, Color(255,255,255).rgb)
            var reasingHealth = Math.round(easingHealth / mc.thePlayer!!.maxHealth * 100f).toFloat()
            var s = StringBuilder().append(DecimalFormat().format(java.lang.Float.valueOf(reasingHealth))).append("%").toString()
            Fonts.posterama30.drawString("HP/" +s, x.toFloat() +2, ((top - 5 - Fonts.posterama30.fontHeight / 2).toFloat()), Color(255,255,255).rgb)
            Fonts.posterama30.drawString("Level/" + mc2.player.experienceLevel.toString(), x.toFloat()+95, ((top.toFloat()+6f- Fonts.posterama30.fontHeight / 2)), Color(255,255,255).rgb)
            Fonts.posterama30.drawString("Starvation/" + decimalFormat.format((easingfood / 20F) * 100F) + "%", x.toFloat()+110F +2, ((top - 5 - Fonts.posterama30.fontHeight / 2).toFloat()), Color(255,255,255).rgb)
            easingfood += (mc2.player.foodStats.foodLevel - easingfood) / 2.0F.pow(10.0F - 5F) * RenderUtils.deltaTime
            easingxp += ((mc2.player.experience * 100F) - easingxp) / 2.0F.pow(10.0F - 5F) * RenderUtils.deltaTime
            easingHealth += ((mc.thePlayer!!.health - easingHealth) / 2.0F.pow(10.0F - 5F)) * RenderUtils.deltaTime
            easingarmor += ((mc2.player.totalArmorValue - easingarmor) / 2.0F.pow(10.0F - 5F)) * RenderUtils.deltaTime
        }
        LiquidBounce.hud.render(false)
    }
    fun getClientColor(): Color {
        return Color(AColorPalette.r.get(), AColorPalette.g.get(), AColorPalette.b.get())
    }

    fun getAlternateClientColor(): Color {
        return Color(AColorPalette.r2.get(), AColorPalette.g2.get(), AColorPalette.b2.get())
    }
    fun getClientColors(): Array<Color>? {
        val firstColor: Color
        val secondColor: Color
        when (HUD.clolormode.get().toLowerCase()) {
            "light rainbow" -> {
                firstColor = ColorUtil.rainbow(15, 1, .6f, 1F, 1F)
                secondColor = ColorUtil.rainbow(15, 40, .6f, 1F, 1F)
            }

            "rainbow" -> {
                firstColor = ColorUtil.rainbow(15, 1, 1F, 1F, 1F)
                secondColor = ColorUtil.rainbow(15, 40, 1F, 1F, 1F)
            }
            "gident" -> {
                firstColor =
                    mixColors(getClientColor(), getAlternateClientColor())
                secondColor =
                    mixColors(getAlternateClientColor(), getClientColor())
            }
            "double color" -> {
                firstColor =
                    ColorUtil.interpolateColorsBackAndForth(15, 0, Color.PINK, Color.BLUE, hueInterpolation.get())
                secondColor =
                    ColorUtil.interpolateColorsBackAndForth(15, 90, Color.PINK, Color.BLUE, hueInterpolation.get())
            }

            "static" -> {
                firstColor = Color(colorRedValue.get(), colorGreenValue.get(), colorBlueValue.get())
                secondColor = firstColor
            }

            else -> {
                firstColor = Color(-1)
                secondColor = Color(-1)
            }
        }
        return arrayOf(firstColor, secondColor)
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
    @EventTarget
    fun onUpdate(event: UpdateEvent?) {
        LiquidBounce.hud.update()
    }
    fun getHotbarEasePos(x: Int): Int {
        if (!state || !hotbarEaseValue.get()) return x
        easingValue = x
        return easingValue
    }
    private fun onArmor(target: IEntityLivingBase) {
    }
    @EventTarget
    fun onKey(event: KeyEvent) {
        LiquidBounce.hud.handleKey('a', event.key)
    }

    @EventTarget(ignoreCondition = true)
    fun onScreen(event: ScreenEvent) {
        if (mc.theWorld == null || mc.thePlayer == null) return
        if (state && blurValue.get() && !mc.entityRenderer.isShaderActive() && event.guiScreen != null &&
            !(classProvider.isGuiChat(event.guiScreen) || classProvider.isGuiHudDesigner(event.guiScreen))) mc.entityRenderer.loadShader(classProvider.createResourceLocation("liquidbounce" + "/blur.json")) else if (mc.entityRenderer.shaderGroup != null &&
            mc.entityRenderer.shaderGroup!!.shaderGroupName.contains("liquidbounce/blur.json")) mc.entityRenderer.stopUseShader()
    }

    init {
        state = true
    }
}