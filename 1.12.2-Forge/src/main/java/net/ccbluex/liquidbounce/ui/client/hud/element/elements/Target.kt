package net.ccbluex.liquidbounce.ui.client.hud.element.elements

import com.mojang.realmsclient.gui.ChatFormatting
import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura
import net.ccbluex.liquidbounce.ui.client.hud.element.*
import net.ccbluex.liquidbounce.ui.font.Fonts
import net.ccbluex.liquidbounce.utils.render.EaseUtils
import net.ccbluex.liquidbounce.utils.render.RenderUtils
import net.ccbluex.liquidbounce.utils.render.RoundedUtil
import net.ccbluex.liquidbounce.value.BoolValue
import net.ccbluex.liquidbounce.value.FloatValue
import net.ccbluex.liquidbounce.value.IntegerValue
import net.ccbluex.liquidbounce.value.ListValue
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import org.lwjgl.opengl.GL11
import tomk.ui.Stencil
import java.awt.Color

@ElementInfo(name = "Target")
class Target : Element(-46.0,-40.0,1F,Side(Side.Horizontal.MIDDLE,Side.Vertical.MIDDLE)) {
    private val bV = BoolValue("Blur", true)
    private val BlurStrength = FloatValue("BlurStrength", 5f,0f,20f)
    val shadowValueopen = BoolValue("shadow", true)
    private val shadowValue = FloatValue("shadow-Value", 10F, 0f, 20f)
    private val shadowColorMode = ListValue("Shadow-Color", arrayOf("Background", "Custom"), "Background")
    private val bgValue = IntegerValue("Background-Alpha", 120, 0, 255)
    private val radiusValue = FloatValue("Radius", 4.25f, 0f, 10f)
    private val modeValue = ListValue("Mode", arrayOf("distance"), "novo")
    private val animSpeedValue = IntegerValue("AnimSpeed",10,5,20)
    private val switchAnimSpeedValue = IntegerValue("SwitchAnimSpeed",8,5,40)



    private var prevTarget:IEntityLivingBase?=null
    private var lastHealth=20F
    private var lastChangeHealth=20F
    private var changeTime=System.currentTimeMillis()
    private var displayPercent=0f
    private var lastUpdate = System.currentTimeMillis()
    private fun getHealth(entity: IEntityLivingBase?):Float{
        return if(entity==null || entity.isDead){ 0f }else{ entity.health }
    }
    override fun drawElement(): Border? {
        var target=(LiquidBounce.moduleManager[KillAura::class.java] as KillAura).target
        val time=System.currentTimeMillis()
        val pct = (time - lastUpdate) / (switchAnimSpeedValue.get()*50f)
        lastUpdate=System.currentTimeMillis()

        if (classProvider.isGuiHudDesigner(mc.currentScreen)|| classProvider.isGuiChat(mc.currentScreen)) {
            target=mc.thePlayer
        }
        if (target != null) {
            prevTarget = target
        }
        prevTarget ?: return getTBorder()

        if (target!=null) {
            if (displayPercent < 1) {
                displayPercent += pct
            }
            if (displayPercent > 1) {
                displayPercent = 1f
            }
        } else {
            if (displayPercent > 0) {
                displayPercent -= pct
            }
            if (displayPercent < 0) {
                displayPercent = 0f
                prevTarget=null
                return getTBorder()
            }
        }
        if(getHealth(prevTarget)!=lastHealth){
            lastChangeHealth=lastHealth
            lastHealth=getHealth(prevTarget)
            changeTime=time
        }
        val nowAnimHP=if((time-(animSpeedValue.get()*50))<changeTime){
            getHealth(prevTarget)+(lastChangeHealth-getHealth(prevTarget))*(1-((time-changeTime)/(animSpeedValue.get()*50F)))
        }else{
            getHealth(prevTarget)
        }


        when(modeValue.get().toLowerCase()) {
            "distance" -> distance(prevTarget!!,nowAnimHP)
        }

        return getTBorder()
    }


    private fun distance(target: IEntityLivingBase, easingHealth: Float) {
        //背景
        RenderUtils.drawRoundedRect(0f, 0f, 150F, 30F, radiusValue.get(), Color(0, 0, 0, bgValue.get()).rgb)
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
                        0f, 0f, 150F, 30F, radiusValue.get(),
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
                RenderUtils.fastRoundedRect(0f, 0f, 150F, 30F, radiusValue.get())
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
                    renderX.toFloat(),
                    renderY.toFloat(),
                    150F,
                    30F,
                    radiusValue.get(), BlurStrength.get()
            )
            GL11.glPopMatrix()
            GL11.glTranslated(renderX, renderY, 0.0)
        }
        Fonts.productSans35.drawString(target.name!!, 36, 6, -1)
        Fonts.productSans35.drawString("Distance:   " + ChatFormatting.WHITE + Math.round(target.getDistance(mc.thePlayer!!.posX,mc.thePlayer!!.posY,mc.thePlayer!!.posZ)) + "m", 36, 18, Color(41,132,163).rgb)
        RenderUtils.drawCircle(123f, 15f,10f, -90, (270f * (easingHealth / 20f)).toInt(), Color(41,132,163))
        Fonts.productSans35.drawCenteredString(Math.round(easingHealth).toString(), 123.1f, 12f, -1)

        var playerInfo = mc.netHandler.getPlayerInfo(mc.thePlayer!!.uniqueID)
        if (classProvider.isEntityPlayer(target)) {
            playerInfo = mc.netHandler.getPlayerInfo(target.uniqueID)
        }
        if (playerInfo != null) {
            // Draw head
            val locationSkin = playerInfo.locationSkin
            val renderHurtTime = target.hurtTime - if (target.hurtTime != 0) {
                Minecraft.getMinecraft().timer.renderPartialTicks
            } else {
                0f
            }
            // 受伤的红色效果
            val hurtPercent = renderHurtTime / 10.0F
            GL11.glColor4f(1f, 1 - hurtPercent, 1 - hurtPercent, 1f)
            val size = 24

            GL11.glPushMatrix()

            mc.textureManager.bindTexture(locationSkin)
            RenderUtils.drawScaledCustomSizeModalCircle(
                3, 3, 8F, 8F, 8, 8, size, size,
                64F, 64F
            )
            GL11.glPopMatrix()
        }
    }

    private fun getTBorder():Border?{
        return when(modeValue.get().toLowerCase()){
            "distance"->Border(0F,0F,150F,30F)
            else -> null
        }

    }
}