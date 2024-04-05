/*
 * LiquidBounce++ Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/TheMosKau/LiquidBouncePlusPlus/
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.vulcan

import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.event.EventState
import net.ccbluex.liquidbounce.event.MotionEvent
import net.ccbluex.liquidbounce.event.MoveEvent
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
import net.ccbluex.liquidbounce.utils.MovementUtils.isMoving
import net.ccbluex.liquidbounce.utils.MovementUtils.strafe
import java.util.*

class VulcanHop2 : SpeedMode("VulcanHop2") {
    private var groundTick = 0
    override fun onTick() {}
    override fun onMotion() {}
    fun onMotion(event: MotionEvent) {
        val speed = LiquidBounce.moduleManager.getModule(Speed::class.java) as Speed
        if (speed == null || event.eventState !== EventState.PRE) return
        var L = 0
        if (isMoving) {
            mc.timer.timerSpeed = if (Objects.requireNonNull(mc.thePlayer)!!.motionY > 0) 1.65f else 0.73f
            if (mc.thePlayer!!.onGround) {
                if (groundTick >= 0) {
                    strafe(0.483f)
                }
                if (0.42 != 0.0) {
                    mc.thePlayer!!.motionY = 0.42
                }
                groundTick++
            } else {
                groundTick = 0
                L += (0.0 * 0.03).toInt()
                mc.thePlayer!!.motionY = L.toDouble()
            }
        }
    }

    override fun onEnable() {
        super.onEnable()
    }

    override fun onDisable() {
        mc.timer.timerSpeed = 1f
        super.onDisable()
    }

    override fun onUpdate() {}
    override fun onMove(event: MoveEvent) {}
}