package net.ccbluex.liquidbounce.ui.client.hud.element.elements

import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.features.module.modules.render.ESP
import net.ccbluex.liquidbounce.ui.client.hud.element.Border
import net.ccbluex.liquidbounce.ui.client.hud.element.Element
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo
import net.ccbluex.liquidbounce.utils.EntityUtils
import net.ccbluex.liquidbounce.utils.render.RenderUtils
import net.ccbluex.liquidbounce.value.BoolValue
import net.ccbluex.liquidbounce.value.FloatValue
import net.ccbluex.liquidbounce.value.IntegerValue
import net.ccbluex.liquidbounce.value.ListValue
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL11.*
import org.lwjgl.util.vector.Vector2f
import java.awt.Color
import kotlin.math.*

@ElementInfo(name = "Radar")
class Radar(x: Double = 5.0, y: Double = 130.0) : Element(x, y) {
    companion object {
        private val SQRT_OF_TWO = sqrt(2f)
    }
    private val sizeValue = FloatValue("Size", 90f, 30f, 500f)
    private val viewDistanceValue = FloatValue("View Distance", 4F, 0.5F, 32F)
    private val playerShapeValue = ListValue("Player Shape", arrayOf("Rectangle", "Circle"), "Circle")
    private val playerSizeValue = FloatValue("Player Size", 5.0F, 0.5f, 20F)
    private val fovSizeValue = FloatValue("FOV Size", 10F, 0F, 50F)
    private val blur = BoolValue("Blur", false)
    private val BlurStrength = FloatValue("BlurStrength", 5f,0f,20f)
    val shadowValueopen = BoolValue("shadow", true)
    private val shadowValue = FloatValue("shadow-Value", 10F, 0f, 20f)
    private val shadowColorMode = ListValue("Shadow-Color", arrayOf("Background", "Custom"), "Background")
    private val bgValue = IntegerValue("Background-Alpha", 120, 0, 255)
    private val radiusValue = FloatValue("Radius", 4.25f, 0f, 10f)
    override fun drawElement(): Border? {
        val renderViewEntity = mc.renderViewEntity
        val viewDistance = viewDistanceValue.get() * 16.0F
        val maxDisplayableDistanceSquare = ((viewDistance + fovSizeValue.get().toDouble()) * (viewDistance + fovSizeValue.get().toDouble()))
        val size = sizeValue.get()
        val halfSize = size / 2f
        //背景
        RenderUtils.drawRoundedRect(
                1f,
                1f,
                size-1,
                size-1,
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
                        1f, 1f, size-1, size-1, radiusValue.get(),
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
                RenderUtils.fastRoundedRect(1f, 1f, size-1, size-1, radiusValue.get())
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
        if (blur.get()) {
            GL11.glTranslated(-renderX, -renderY, 0.0)
            GL11.glPushMatrix()
            tomk.utils.BlurBuffer.CustomBlurRoundArea(
                    renderX.toFloat()-1f,
                    renderY.toFloat()-2f,
                    size+2f,
                    size+2f,
                    radiusValue.get(), BlurStrength.get()
            )
            GL11.glPopMatrix()
            GL11.glTranslated(renderX, renderY, 0.0)
        }





        RenderUtils.makeScissorBox(x.toFloat(), y.toFloat(), x.toFloat() + ceil(size), y.toFloat() + ceil(size))
        glEnable(GL_SCISSOR_TEST)
        glPushMatrix()
        glTranslatef(halfSize, halfSize, 0f)
        glRotatef(renderViewEntity!!.rotationYaw, 0f, 0f, -1f)
        glEnable(GL_BLEND)
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
        glColor4f(1.0f, 1.0f, 1.0f, 1.0f)
        glDisable(GL_TEXTURE_2D)
        glEnable(GL_LINE_SMOOTH)
        val circleMode = playerShapeValue.get().equals("circle", true)
        val tessellator = Tessellator.getInstance()
        val worldRenderer = tessellator.buffer
        if (circleMode) glEnable(GL_POINT_SMOOTH)
        var playerSize = playerSizeValue.get()
        glEnable(GL_POLYGON_SMOOTH)
        worldRenderer.begin(GL_POINTS, DefaultVertexFormats.POSITION_COLOR)
        glPointSize(playerSize)
        for (entity in mc.theWorld!!.loadedEntityList) {
            if (entity != null && entity !== mc.thePlayer && EntityUtils.isSelected(entity, false)) {
                val positionRelativeToPlayer = Vector2f((renderViewEntity.posX - entity.posX).toFloat(),
                        (renderViewEntity.posZ - entity.posZ).toFloat())
                if (maxDisplayableDistanceSquare < positionRelativeToPlayer.lengthSquared())
                    continue
                val transform = fovSizeValue.get() > 0F
                if (transform) {
                    glPushMatrix()
                    glTranslatef((positionRelativeToPlayer.x / viewDistance) * size, (positionRelativeToPlayer.y / viewDistance) * size, 0f)
                    glRotatef(entity.rotationYaw, 0f, 0f, 1f)
                }
                val color = (LiquidBounce.moduleManager[ESP::class.java] as ESP).getColor(entity)
                worldRenderer.pos(((positionRelativeToPlayer.x / viewDistance) * size).toDouble(), ((positionRelativeToPlayer.y / viewDistance) * size).toDouble(), 0.0).color(color.red / 255.0f, color.green / 255.0f, color.blue / 255.0f, 1.0f).endVertex()
                if (transform)
                    glPopMatrix()
            }
        }

        tessellator.draw()

        if (circleMode) {
            glDisable(GL_POINT_SMOOTH)
        }

        glDisable(GL_POLYGON_SMOOTH)
        glEnable(GL_TEXTURE_2D)
        glDisable(GL_BLEND)
        glDisable(GL_LINE_SMOOTH)
        glDisable(GL_SCISSOR_TEST)
        glPopMatrix()
        return Border(0F, 0F, size, size)
    }

}