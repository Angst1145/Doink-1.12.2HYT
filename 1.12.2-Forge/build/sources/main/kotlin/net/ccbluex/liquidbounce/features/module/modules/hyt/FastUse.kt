/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.liquidbounce.features.module.modules.hyt

import tomk.utils.MovementUtils
import tomk.utils.PacketUtils
import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.MoveEvent
import net.ccbluex.liquidbounce.event.PacketEvent
import net.ccbluex.liquidbounce.event.UpdateEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.utils.timer.MSTimer
import net.ccbluex.liquidbounce.value.BoolValue
import net.ccbluex.liquidbounce.value.FloatValue
import net.ccbluex.liquidbounce.value.IntegerValue
import net.ccbluex.liquidbounce.value.ListValue
import net.minecraft.network.play.client.CPacketPlayer


@ModuleInfo(name = "FastUse", description = "Allows you to use items faster.", category = ModuleCategory.PLAYER)
class FastUse : Module() {

    private val modeValue = ListValue("Mode", arrayOf("NewHyt","GrimAC","Instant","GrimAC-Timer","Grim","GrimInstant" ,"NCP", "AAC", "CustomDelay"), "GrimInstant")

    private val noMoveValue = BoolValue("NoMove", false)

    private val delayValue = IntegerValue("CustomDelay", 0, 0, 300)
    private val customSpeedValue = IntegerValue("CustomSpeed", 2, 1, 35)
    private val customTimer = FloatValue("CustomTimer", 1.1f, 0.5f, 2f)
    private val timer = FloatValue("GrimAC-timer", 0.4f, 0.0f, 2f)

    private val msTimer = MSTimer()
    private var usedTimer = false
    private var usedTimer2 = false
    private var packet = 0
    @EventTarget
    fun onPacket(event: PacketEvent){
        val thePlayer = mc.thePlayer ?: return

        val usingItem = thePlayer.itemInUse!!.item

        if (classProvider.isItemFood(usingItem) || classProvider.isItemBucketMilk(usingItem) || classProvider.isItemPotion(usingItem)) {
            when (modeValue.get().toLowerCase()) {
                "grimac-timer"->{
                    mc.timer.timerSpeed = timer.get()
                    val packet = event.packet
                    usedTimer = true

                    if(thePlayer.onGround) {
                        if (classProvider.isCPacketConfirmTransaction(packet)){
                            event.cancelEvent()
                        }
                    }
                }
            }
        }
    }
    @EventTarget
    fun onUpdate(event: UpdateEvent) {
        val thePlayer = mc.thePlayer ?: return

        if (usedTimer) {
            mc.timer.timerSpeed = 1F


            usedTimer = false
        }
        if (usedTimer2) {
            mc.timer.timerSpeed = 2F
            usedTimer2 = false
        }
        if (!thePlayer.isUsingItem) {
            msTimer.reset()
            return
        }

        val usingItem = thePlayer.itemInUse!!.item

        if (classProvider.isItemFood(usingItem) || classProvider.isItemBucketMilk(usingItem) || classProvider.isItemPotion(usingItem)) {
            when (modeValue.get().toLowerCase()) {
                "instant" -> {
                    repeat(35) {
                        mc.netHandler.addToSendQueue(classProvider.createCPacketPlayer(thePlayer.onGround))
                    }/* no-op */

                    mc.playerController.onStoppedUsingItem(thePlayer)
                }
                "newhyt" -> {
                    if (packet != 16) {
                        if (mc.thePlayer!!.ticksExisted % 2 === 0) {
                            mc.timer.timerSpeed = 0.33f
                        } else {
                            mc.timer.timerSpeed = 1f
                        }
                        PacketUtils.sendPacketNoEvent(CPacketPlayer(true))
                        packet++
                    } else {
                        mc.playerController.onStoppedUsingItem(mc.thePlayer!!)
                        mc.gameSettings.keyBindUseItem.pressed = false
                    }
                }

                "ncp" -> if (thePlayer.itemInUseDuration > 14) {
                    repeat(20) {
                        mc.netHandler.addToSendQueue(classProvider.createCPacketPlayer(thePlayer.onGround))
                    }

                    mc.playerController.onStoppedUsingItem(thePlayer)
                }
                "grim" -> {
                    if (MovementUtils.isMoving()) {
                        mc.timer.timerSpeed = 0.45F
                        usedTimer = true

                        if (!msTimer.hasTimePassed(140L))
                            return

                        repeat(2) {
                            mc.netHandler.addToSendQueue(classProvider.createCPacketPlayer(thePlayer.onGround))
                        }

                        msTimer.reset()
                    }
                }

                "griminstant" -> {
                    if (MovementUtils.isMoving()) {
                        mc.timer.timerSpeed = 0.13F
                        usedTimer = true

                        if (!msTimer.hasTimePassed(60L))
                            return

                        repeat(5) {
                            mc.netHandler.addToSendQueue(classProvider.createCPacketPlayer(thePlayer.onGround))
                        }

                        msTimer.reset()
                    }
                }
                "aac" -> {
                    mc.timer.timerSpeed = 1.22F
                    usedTimer = true
                }

                "grimac" -> {
                    mc.timer.timerSpeed = 0.5F
                    usedTimer = true
                    mc.netHandler.addToSendQueue(classProvider.createCPacketPlayer(thePlayer.onGround))

                }

                "customdelay" -> {
                    mc.timer.timerSpeed = customTimer.get()
                    usedTimer = true

                    if (!msTimer.hasTimePassed(delayValue.get().toLong()))
                        return

                    repeat(customSpeedValue.get()) {
                        mc.netHandler.addToSendQueue(classProvider.createCPacketPlayer(thePlayer.onGround))
                    }

                    msTimer.reset()
                }
            }
        }
    }

    @EventTarget
    fun onMove(event: MoveEvent?) {
        val thePlayer = mc.thePlayer

        if (thePlayer == null || event == null)
            return
        if (!state || !thePlayer.isUsingItem || !noMoveValue.get())
            return

        val usingItem = thePlayer.itemInUse!!.item

        if (classProvider.isItemFood(usingItem) || classProvider.isItemBucketMilk(usingItem) || classProvider.isItemPotion(usingItem))
            event.zero()
    }

    override fun onDisable() {
        if (usedTimer) {
            mc.timer.timerSpeed = 1F
            usedTimer = false
        }
    }

    override val tag: String?
        get() = modeValue.get()
}
