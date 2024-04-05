
package net.ccbluex.liquidbounce.ui.client.hud.element.elements


import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.features.module.modules.color.CustomColor
import net.ccbluex.liquidbounce.tomk.Client
import net.ccbluex.liquidbounce.ui.client.hud.element.Border
import net.ccbluex.liquidbounce.ui.client.hud.element.Element
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo
import net.ccbluex.liquidbounce.ui.client.hud.element.Side
import net.ccbluex.liquidbounce.ui.font.Fonts
import net.ccbluex.liquidbounce.utils.render.EaseUtils
import net.ccbluex.liquidbounce.utils.render.RoundedUtil
import org.lwjgl.opengl.GL11
import java.awt.Color
import java.math.BigDecimal


@ElementInfo(name = "Notifications", single = true)
class Notifications(x: Double = 19.0, y: Double = 8.0, scale: Float = 1F,
                    side: Side = Side(Side.Horizontal.RIGHT, Side.Vertical.DOWN)) : Element(x, y, scale, side) {

    private val exampleNotification = Notification("Notification", "", NotifyType.INFO)

    override fun drawElement(): Border? {

        val notifications = mutableListOf<Notification>()
        for((index, notify) in LiquidBounce.hud.notifications.withIndex()){
            GL11.glPushMatrix()

            if(notify.drawNotification(index,this.renderX.toFloat(), this.renderY.toFloat(),scale, this)){
                notifications.add(notify)
            }

            GL11.glPopMatrix()
        }
        for(notify in notifications){
            LiquidBounce.hud.notifications.remove(notify)
        }

        if (classProvider.isGuiHudDesigner(mc.currentScreen)) {
            if (!LiquidBounce.hud.notifications.contains(exampleNotification))
                LiquidBounce.hud.addNotification(exampleNotification)

            exampleNotification.fadeState = FadeState.STAY
            exampleNotification.displayTime = System.currentTimeMillis()

            return Border(-exampleNotification.width.toFloat(), -exampleNotification.height.toFloat(),0F,0F)
        }

        return null
    }
    fun drawBoarderBlur() {}

}


class Notification(val title: String, val content: String, val type: NotifyType, val time: Int=1000, val animeTime: Int=350) {
    private var s: String? = null
    var n2: Int = Fonts.posterama30.getStringWidth(content)
    var textLength = Math.max(n2 as Int, 0 as Int)
    val width=this.textLength.toFloat() + 80.0f
    val height=30
    var fadeState = FadeState.IN
    var nowY=-height
    var x = 0F
    var displayTime=System.currentTimeMillis()
    var animeXTime=System.currentTimeMillis()
    var animeYTime=System.currentTimeMillis()


    fun drawNotification(index: Int, blurRadius: Float, y: Float, scale: Float,notifications: Notifications):Boolean {
        val renderX:Double = notifications.renderX;
        val renderY:Double = notifications.renderY;
        val realY=-(index+1) * (height + 2)

        val nowTime=System.currentTimeMillis()

        var transY=nowY.toDouble()
        //Y-Axis Animation
        if(nowY!=realY){
            var pct=(nowTime-animeYTime)/animeTime.toDouble()
            if(pct>1){
                nowY=realY
                pct=1.0
            }else{
                pct= EaseUtils.easeOutQuart(pct)
            }
            GL11.glTranslated(0.0,(realY-nowY)*pct,0.0)
        }else{
            animeYTime=nowTime

        }
        GL11.glTranslated(1.0,nowY.toDouble(),0.0)

        //X-Axis Animation
        var pct=(nowTime-animeXTime)/animeTime.toDouble()
        when(fadeState){
            FadeState.IN -> {
                if(pct>1){
                    fadeState= FadeState.STAY
                    animeXTime=nowTime
                    pct=1.0
                }
                pct= EaseUtils.easeOutQuart(pct)
                transY+=(realY-nowY)*pct
            }

            FadeState.STAY -> {
                pct=1.0
                if((nowTime-animeXTime)>time){
                    fadeState= FadeState.OUT
                    animeXTime=nowTime
                }
            }

            FadeState.OUT -> {
                if(pct>1){
                    fadeState= FadeState.END
                    animeXTime=nowTime
                    pct=2.0
                }
                pct=1- EaseUtils.easeInQuart(pct)
            }

            FadeState.END -> {
                return true
            }
        }

        GL11.glTranslated(width-(width*pct),0.0,0.0)
        GL11.glTranslatef(-width.toFloat(),0F,0F)

        if (type == NotifyType.SUCCESS)
            s = "SUCCESS";
        else if (type == NotifyType.ERROR)
            s = "ERROR";
        else if (type == NotifyType.WARNING)
            s = "WARNING";
        else if ( type == NotifyType.INFO)
            s = "INFO";
        //分段绘制
        if (s == "INFO") {
            Client.drawOutline(20F,-3F,width - 7F,13F, CustomColor.ra.get(),CustomColor.line.get(),CustomColor.office.get())
            RoundedUtil.drawRound(14F,-2F,width - 15F,28F, CustomColor.ra.get(), Color(0 ,0, 0,50))
            Fonts.no40.drawString("C",23F,5F,Color(255,255,255,255).rgb)
            Fonts.posterama40.drawString(title,48f,3f,Color(255,255,255,240).rgb)
            Fonts.posterama30.drawString(content + " (" + BigDecimal(((time - time * ((nowTime - displayTime) / (animeTime * 2F + time))) / 1000).toDouble()).setScale(1, BigDecimal.ROUND_HALF_UP).toString() + "s)", 48f,16f, Color(255,255,255,255).rgb)

        }
        if (s == "WARNING") {
            Client.drawOutline(20F,-3F,width - 7F,13F, CustomColor.ra.get(),CustomColor.line.get(),CustomColor.office.get())
            RoundedUtil.drawRound(14F,-2F,width - 15F,28F,CustomColor.ra.get(), Color(0 ,0, 0,50))
            Fonts.no40.drawString("D",23F,5F,Color(255,255,255,255).rgb)
            Fonts.posterama40.drawString(title,48f,3f,Color(255,255,255,240).rgb)
            Fonts.posterama30.drawString(content + " (" + BigDecimal(((time - time * ((nowTime - displayTime) / (animeTime * 2F + time))) / 1000).toDouble()).setScale(1, BigDecimal.ROUND_HALF_UP).toString() + "s)", 48f,16f, Color(255,255,255,255).rgb)
        }
        if (s == "SUCCESS") {
            Client.drawOutline(20F,-3F,width - 7F,13F, CustomColor.ra.get(),CustomColor.line.get(),CustomColor.office.get())
            RoundedUtil.drawRound(14F,-2F,width - 15F,28F,CustomColor.ra.get(), Color(0 ,0, 0,50))
            Fonts.no40.drawString("A",23F,5F,Color(255 ,255, 255,200).rgb)
            Fonts.posterama40.drawString(title,48f,3f,Color(255,255,255,240).rgb)
            Fonts.posterama30.drawString(content + " (" + BigDecimal(((time - time * ((nowTime - displayTime) / (animeTime * 2F + time))) / 1000).toDouble()).setScale(1, BigDecimal.ROUND_HALF_UP).toString() + "s)", 48f,16f, Color(255,255,255,255).rgb)
        }
        if (s == "ERROR") {
            Client.drawOutline(20F,-3F,width - 7F,13F, CustomColor.ra.get(),CustomColor.line.get(),CustomColor.office.get())
            RoundedUtil.drawRound(14F,-2F,width - 15F,28F,CustomColor.ra.get(),Color(0 ,0, 0,50))
            Fonts.no40.drawString("B",23F,5F,Color(255,255,255,240).rgb)
            Fonts.posterama40.drawString(title,48F,3f,Color(255,255,255,240).rgb)
            Fonts.posterama30.drawString(content + " (" + BigDecimal(((time - time * ((nowTime - displayTime) / (animeTime * 2F + time))) / 1000).toDouble()).setScale(1, BigDecimal.ROUND_HALF_UP).toString() + "s)", 48f,16f, Color(255,255,255,255).rgb)
        }

        return false
    }

}

enum class NotifyType(var renderColor: Color) {
    SUCCESS(Color(0 ,157, 255,240)),
    ERROR(Color(255,0,0,200)),
    WARNING(Color(0xF5FD00)),
    INFO(Color(0xF5FD00));
}


enum class FadeState { IN, STAY, OUT, END }
