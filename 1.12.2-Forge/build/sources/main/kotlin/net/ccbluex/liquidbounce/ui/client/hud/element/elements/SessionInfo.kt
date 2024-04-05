package net.ccbluex.liquidbounce.ui.client.hud.element.elements
import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.features.module.modules.player.AutoL
import net.ccbluex.liquidbounce.ui.client.hud.element.Border
import net.ccbluex.liquidbounce.ui.client.hud.element.Element
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo
import net.ccbluex.liquidbounce.ui.client.hud.element.Side
import net.ccbluex.liquidbounce.ui.font.Fonts
import net.ccbluex.liquidbounce.utils.render.RenderUtils
import net.ccbluex.liquidbounce.value.*
import net.minecraft.client.renderer.GlStateManager
import org.lwjgl.opengl.GL11
import java.awt.Color
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.hypot
import kotlin.math.roundToLong

@ElementInfo(name = "SessionInfo")//by Angst
class SessionInfo(
    x: Double = 15.0, y: Double = 10.0, scale: Float = 1F,
    side: Side = Side(Side.Horizontal.LEFT, Side.Vertical.UP)
) : Element(x, y, scale, side) {
    private val bV = BoolValue("Blur", true)
    private val BlurStrength = FloatValue("BlurStrength", 5f,0f,20f)
    val shadowValueopen = BoolValue("shadow", true)
    private val shadowValue = FloatValue("shadow-Value", 10F, 0f, 20f)
    private val shadowColorMode = ListValue("Shadow-Color", arrayOf("Background", "Custom"), "Background")
    private val bgValue = IntegerValue("Background-Alpha", 120, 0, 255)
    private val radiusValue = FloatValue("Radius", 4.25f, 0f, 10f)

    override fun drawElement(): Border {
        //背景
        RenderUtils.drawRoundedRect(
                -5f,
                1F,
                125f,
                71f,
                radiusValue.get(),
                Color(0, 0, 0, bgValue.get()).rgb
        )
        //shadow
        GL11.glTranslated(-renderX, -renderY, 0.0)
        GL11.glScalef(1F, 1F, 1F)
        GL11.glPushMatrix()
        if (shadowValueopen.get()) {
            tomk.utils.ShadowUtils.shadow(shadowValue.get(), {
                GL11.glPushMatrix()
                GL11.glTranslated(renderX, renderY, 0.0)
                GL11.glScalef(scale, scale, scale)

                RenderUtils.originalRoundedRect(
                        -5f, 1F, 125F, 71F, radiusValue.get(),
                        if (shadowColorMode.get().equals("background", true))
                            Color(32, 30, 30).rgb
                        else
                            Color(0, 0, 0).rgb
                )
                GL11.glPopMatrix()
            }, {
                GL11.glPushMatrix()
                GL11.glTranslated(renderX, renderY, 0.0)
                GL11.glScalef(scale, scale, scale)
                GlStateManager.enableBlend()
                GlStateManager.disableTexture2D()
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
                RenderUtils.fastRoundedRect(-5f, 1F, 125F, 71F, radiusValue.get())
                GlStateManager.enableTexture2D()
                GlStateManager.disableBlend()
                GL11.glPopMatrix()
            }
            )
        }
        GL11.glPopMatrix()
        GL11.glScalef(scale, scale, scale)
        GL11.glTranslated(renderX, renderY, 0.0)
        //blur
        if (bV.get()) {
            GL11.glTranslated(-renderX, -renderY, 0.0)
            GL11.glPushMatrix()
            tomk.utils.BlurBuffer.CustomBlurRoundArea(
                    renderX.toFloat() - 5,
                    renderY.toFloat() + 1  ,
                    130F,
                    71F,
                    radiusValue.get(), BlurStrength.get()
            )
            GL11.glPopMatrix()
            GL11.glTranslated(renderX, renderY, 0.0)
        }
        val x2 = 120f
        val autoL = LiquidBounce.moduleManager.getModule(AutoL::class.java) as AutoL

        val DATE_FORMAT = SimpleDateFormat("HH:mm:ss")
        Fonts.posterama40.drawCenteredString("Player", x2 / 2f, 5f, Color.WHITE.rgb)
        Fonts.icon50.drawString("B",0f,Fonts.posterama30.fontHeight * 1.5f + 8f,Color(255,255,255,255).rgb)
        Fonts.posterama30.drawString("Time:${DATE_FORMAT.format(Date(System.currentTimeMillis() - Recorder.startTime - 8000L * 3600L))}", 20f, Fonts.posterama30.fontHeight * 1.5f + 8f, Color.WHITE.rgb)
        Fonts.icon50.drawString("F",0f,Fonts.posterama30.fontHeight * 3.0f + 8f,Color(255,255,255,255).rgb)
        Fonts.posterama30.drawString("you is Kill fw:" + autoL.kills().toString(),20f, Fonts.posterama30.fontHeight * 3.0f + 8f, Color.WHITE.rgb)
        Fonts.posterama30.drawString("name is :" + autoL.tagName.toString(),20f, Fonts.posterama30.fontHeight * 4.5f + 8f, Color.WHITE.rgb)

        return Border(-5f, 1F, 125f,  71f)
    }

}