/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.liquidbounce.features.module.modules.combat

import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.event.*
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed
import net.ccbluex.liquidbounce.injection.backend.unwrap
import net.ccbluex.liquidbounce.utils.MovementUtils
import net.ccbluex.liquidbounce.utils.timer.MSTimer
import net.ccbluex.liquidbounce.value.BoolValue
import net.ccbluex.liquidbounce.value.FloatValue
import net.ccbluex.liquidbounce.value.ListValue
import net.minecraft.block.BlockSlab
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.network.play.client.CPacketPlayerDigging
import net.minecraft.network.play.server.SPacketEntityVelocity
import net.minecraft.network.play.server.SPacketPlayerPosLook
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import kotlin.math.cos
import kotlin.math.sin

@ModuleInfo(name = "Velocity", description = "Allows you to modify the amount of knockback you take.", category = ModuleCategory.DONK)
class VelocityTest : Module() {

    /**
     * OPTIONS
     */
    private val horizontalValue = FloatValue("Horizontal", 0F, 0F, 1F)
    private val verticalValue = FloatValue("Vertical", 0F, 0F, 1F)
    private val modeValue = ListValue("Mode", arrayOf("GrimReverse", "AAC4","GrimAC","Simple", "AAC", "AACPush", "AACZero",
            "Reverse", "SmoothReverse", "Jump", "Glitch","LatestTestHyt"), "Simple")
    private val CancelS12= BoolValue("CancelS12", false).displayable { modeValue.get() =="GrimAC"}
    private val OnlyMove = BoolValue("OnlyMove", true).displayable { modeValue.get() =="GrimAC"}
    private val OnlyGround = BoolValue("OnlyGround", false).displayable { modeValue.get() =="GrimAC"}
    private val hurtTime = BoolValue("HurtTime", false).displayable { modeValue.get() =="GrimAC"}
    private var debug = BoolValue("Debug", true).displayable { modeValue.get() =="GrimAC"}
    // Reverse
    private val reverseStrengthValue = FloatValue("ReverseStrength", 1F, 0.1F, 1F)
    private val reverse2StrengthValue = FloatValue("SmoothReverseStrength", 0.05F, 0.02F, 0.1F)

    // AAC Push
    private val aacPushXZReducerValue = FloatValue("AACPushXZReducer", 2F, 1F, 3F)
    private val aacPushYReducerValue = BoolValue("AACPushYReducer", false)
    private val onlyGroundValue = BoolValue("OnlyGround", false)



    /**
     * VALUES
     */
    private var velocityTimer = MSTimer()
    private var velocityInput = false

    // SmoothReverse
    private var reverseHurt = false

    // AACPush
    private var jump = false
    private var S08 = 0
    private var isvel = false
    override val tag: String
        get() = if (modeValue.get() == "Simple")
            "${(horizontalValue.get() * 100).toInt()}% ${(verticalValue.get() * 100).toInt()}%"
        else
            modeValue.get()

    override fun onDisable() {
        if(modeValue.get()=="GrimAC") {
            if (mc.thePlayer == null) return
        }else {
            mc.thePlayer?.speedInAir = 0.02F
        }
    }
    override fun onEnable() {
        if (mc.thePlayer == null) return

    }

    private fun isPlayerOnSlab(player: EntityPlayer): Boolean {
        val playerPos = BlockPos(player.posX, player.posY, player.posZ)

        val block = player.world.getBlockState(playerPos).block
        val boundingBox = player.entityBoundingBox

        return block is BlockSlab && player.posY - playerPos.y <= boundingBox.minY + 0.1
    }
    @EventTarget
    fun onUpdate(event: UpdateEvent) {
        val thePlayer = mc.thePlayer ?: return
        if (thePlayer.isInWater || thePlayer.isInLava || thePlayer.isInWeb)
            return



        if ((onlyGroundValue.get() && !mc.thePlayer!!.onGround)){
            return
        }

        when (modeValue.get().toLowerCase()) {
            "grimreverse" -> {
                if (thePlayer.hurtTime > 0 && thePlayer.onGround) {
                    thePlayer.motionY = 0.42

                    thePlayer.motionX += -1.0E-7
                    thePlayer.motionY += -1.0E-7
                    thePlayer.motionZ += -1.0E-7
                    thePlayer.isAirBorne = true
                    mc2.player.addVelocity(-1.0E-9,-1.0E-7,-1.0E-5)
                }
                if (thePlayer.hurtTime > 1 && velocityInput) {
                    thePlayer.motionX *= 0.81
                    thePlayer.motionZ *= 0.81
                    mc2.player.addVelocity(-2.0E-9,-1.0E-8,-1.0E-5)
                    thePlayer.speedInAir = 0.02f
                }
                if (velocityInput && (thePlayer.hurtTime <5 || thePlayer.onGround) && velocityTimer.hasTimePassed(120L)) {
                    velocityInput = false
                }
            }
            "latesttesthyt"->{
                if (thePlayer.hurtTime > 0 && !thePlayer.isDead && thePlayer.hurtTime <=5 ) {
                    thePlayer.motionX *= 0.35
                    thePlayer.motionZ *= 0.35
                    thePlayer.motionY *= 0.001
                    thePlayer.motionY /= 0.01F
                }
            }
            "jump" -> if (thePlayer.hurtTime > 0 && thePlayer.onGround) {
                thePlayer.motionY = 0.42

                val yaw = thePlayer.rotationYaw * 0.017453292F

                thePlayer.motionX -= sin(yaw) * 0.2
                thePlayer.motionZ += cos(yaw) * 0.2
            }

            "glitch" -> {
                thePlayer.noClip = velocityInput

                if (thePlayer.hurtTime == 7)
                    thePlayer.motionY = 0.4

                velocityInput = false
            }

            "reverse" -> {
                if (!velocityInput)
                    return

                if (!thePlayer.onGround) {
                    MovementUtils.strafe(MovementUtils.speed * reverseStrengthValue.get())
                } else if (velocityTimer.hasTimePassed(80L))
                    velocityInput = false
            }

            "aac4" -> {
                if (!thePlayer.onGround) {
                    if (velocityInput) {
                        thePlayer.speedInAir = 0.02f
                        thePlayer.motionX *= 0.6
                        thePlayer.motionZ *= 0.6
                    }
                } else if (velocityTimer.hasTimePassed(80L)) {
                    velocityInput = false
                    thePlayer.speedInAir = 0.02f
                }
            }

            "smoothreverse" -> {
                if (!velocityInput) {
                    thePlayer.speedInAir = 0.02F
                    return
                }

                if (thePlayer.hurtTime > 0)
                    reverseHurt = true

                if (!thePlayer.onGround) {
                    if (reverseHurt)
                        thePlayer.speedInAir = reverse2StrengthValue.get()
                } else if (velocityTimer.hasTimePassed(80L)) {
                    velocityInput = false
                    reverseHurt = false
                }
            }

            "aac" -> if (velocityInput && velocityTimer.hasTimePassed(80L)) {
                thePlayer.motionX *= horizontalValue.get()
                thePlayer.motionZ *= horizontalValue.get()
                //mc.thePlayer.motionY *= verticalValue.get() ?
                velocityInput = false
            }

            "aacpush" -> {
                if (jump) {
                    if (thePlayer.onGround)
                        jump = false
                } else {
                    // Strafe
                    if (thePlayer.hurtTime > 0 && thePlayer.motionX != 0.0 && thePlayer.motionZ != 0.0)
                        thePlayer.onGround = true

                    // Reduce Y
                    if (thePlayer.hurtResistantTime > 0 && aacPushYReducerValue.get()
                            && !LiquidBounce.moduleManager[Speed::class.java]!!.state)
                        thePlayer.motionY -= 0.014999993
                }

                // Reduce XZ
                if (thePlayer.hurtResistantTime >= 19) {
                    val reduce = aacPushXZReducerValue.get()

                    thePlayer.motionX /= reduce
                    thePlayer.motionZ /= reduce
                }
            }

            "aaczero" -> if (thePlayer.hurtTime > 0) {
                if (!velocityInput || thePlayer.onGround || thePlayer.fallDistance > 2F)
                    return

                thePlayer.motionY -= 1.0
                thePlayer.isAirBorne = true
                thePlayer.onGround = true
            } else
                velocityInput = false
        }
    }

    @EventTarget
    fun onPacket(event: PacketEvent) {
        val thePlayer = mc.thePlayer ?: return
        val packet = event.packet.unwrap()
        val packet2 = event.packet
        if(modeValue.get()=="GrimAC") {
            if (((OnlyMove.get() && !MovementUtils.isMoving) || (OnlyGround.get() && !mc.thePlayer!!.onGround)) && (hurtTime.get() &&mc2.player.hurtTime <= 0)) {
                return
            }

            if (S08 > 0) {
                S08--
                return
            }

            if (packet is SPacketPlayerPosLook) {
                S08 = 10

            }

            if (packet is SPacketEntityVelocity) {
                event.cancelEvent()

                isvel = true
            }
            if (CancelS12.get()) {
                if (packet is SPacketEntityVelocity && mc2.player.hurtTime > 0) {
                    event.cancelEvent()
                    if (debug.get()) {
                        val timer = MSTimer()
                        if (timer.hasTimePassed(50 * 1000)) {
                            debugMessage("Cancel S12")
                            timer.reset()
                        }
                    }
                }
            }
        }else {
            if (classProvider.isSPacketEntityVelocity(packet2)) {
                val packetEntityVelocity = packet2.asSPacketEntityVelocity()

                if ((mc.theWorld?.getEntityByID(packetEntityVelocity.entityID) ?: return) != thePlayer)
                    return

                velocityTimer.reset()

                when (modeValue.get().toLowerCase()) {
                    "simple" -> {
                        val horizontal = horizontalValue.get()
                        val vertical = verticalValue.get()

                        if (horizontal == 0F && vertical == 0F)
                            event.cancelEvent()

                        packetEntityVelocity.motionX = (packetEntityVelocity.motionX * horizontal).toInt()
                        packetEntityVelocity.motionY = (packetEntityVelocity.motionY * vertical).toInt()
                        packetEntityVelocity.motionZ = (packetEntityVelocity.motionZ * horizontal).toInt()
                    }

                    "aac", "aac4", "reverse", "smoothreverse", "aaczero" -> velocityInput = true

                    "glitch" -> {
                        if (!thePlayer.onGround)
                            return

                        velocityInput = true
                        event.cancelEvent()
                    }
                }
            }
        }
    }
    @EventTarget
    fun onMotion(event: MotionEvent) {
        if(modeValue.get()=="GrimAC") {
            if (((OnlyMove.get() && !MovementUtils.isMoving) || (OnlyGround.get() && !mc.thePlayer!!.onGround)) && (hurtTime.get() && mc2.player.hurtTime <= 0)) {
                return
            }
            if (event.eventState == EventState.PRE && !mc2.playerController.isHittingBlock && mc2.player.hurtTime > 0 && !isPlayerOnSlab(mc2.player) && isvel) {
                isvel = false
                val blockPos = BlockPos(mc2.player.posX, mc2.player.posY, mc2.player.posZ)
                mc2.connection!!.sendPacket(CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.NORTH))
                if (debug.get()) {
                    val timer = MSTimer()
                    if (timer.hasTimePassed(50 * 1000)) {
                        debugMessage("Send C07")
                        timer.reset()
                    }
                }
            }
        }else{

        }
    }
    fun debugMessage(str: String) {
        if (debug.get()) {
            alert("§7[§c§lNET>§7] §b$str")
        }
    }
    @EventTarget
    fun onJump(event: JumpEvent) {
        val thePlayer = mc.thePlayer

        if (thePlayer == null || thePlayer.isInWater || thePlayer.isInLava || thePlayer.isInWeb)
            return

        when (modeValue.get().toLowerCase()) {
            "aacpush" -> {
                jump = true

                if (!thePlayer.isCollidedVertically)
                    event.cancelEvent()
            }
            "aaczero" -> if (thePlayer.hurtTime > 0)
                event.cancelEvent()
        }
    }
}
