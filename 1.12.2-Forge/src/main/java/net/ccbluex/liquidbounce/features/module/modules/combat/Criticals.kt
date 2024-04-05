/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.liquidbounce.features.module.modules.combat


import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket
import net.ccbluex.liquidbounce.event.*
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.features.module.modules.movement.Fly
import net.ccbluex.liquidbounce.utils.ClientUtils
import net.ccbluex.liquidbounce.utils.timer.MSTimer
import net.ccbluex.liquidbounce.value.BoolValue
import net.ccbluex.liquidbounce.value.IntegerValue
import net.ccbluex.liquidbounce.value.ListValue


@ModuleInfo(name = "Criticals", description = "自动打出暴击伤害", category = ModuleCategory.COMBAT)
class Criticals : Module() {

    val modeValue = ListValue("Mode", arrayOf("Vulcan","Grim","GrimACTest1","GrimACTest2","TestPit","HytGrimAC","HytTest4v4","GrimAC","Packet","NoGround","Hop","TPHop","Jump","LowJump","Simple","HuaYuTingPacket"), "packet")
    val delayValue = IntegerValue("Delay", 0, 0, 1000)
    private val hurtTimeValue = IntegerValue("HurtTime", 10, 0, 20)
    private val debugValue = BoolValue("DebugMessage", false)
    private val lookValue = BoolValue("UseC06Packet", false)
    private val killchatValue = BoolValue("KillChat", true)
    private var attacks = 0
    val msTimer = MSTimer()
    private var target = 0
    private var targetName = "NoTarget"
    private val critList = HashMap<Int, Int>()

    override fun onEnable() {
        if (modeValue.get() === "Vulcan") {
            attacks = 0
        }else{
        if (modeValue.get().equals("NoGround", ignoreCase = true))
            mc.thePlayer!!.jump()
    }
    }
    @EventTarget
    fun onAttack(event: AttackEvent) {
        if (classProvider.isEntityLivingBase(event.targetEntity)) {
            val thePlayer = mc.thePlayer ?: return
            val entity = event.targetEntity!!.asEntityLivingBase()
            target = entity.entityId
            targetName = entity.name.toString()

            if (!thePlayer.onGround || thePlayer.isOnLadder || thePlayer.isInWeb || thePlayer.isInWater ||
                thePlayer.isInLava || thePlayer.ridingEntity != null || entity.hurtTime > hurtTimeValue.get() ||
                LiquidBounce.moduleManager[Fly::class.java].state || !msTimer.hasTimePassed(delayValue.get().toLong()))
                return

            val x = thePlayer.posX
            val y = thePlayer.posY
            val z = thePlayer.posZ
            var n: Int
            when (modeValue.get().toLowerCase()) {
                "vulcan" -> {
                    attacks++ //Vulcan updated and it fully bypassed, that's pogggg *bruh
                    if (attacks > 7) { //TODO: when you stopped moving it may flags sometimes (doesn't matter)
                        sendCriticalPacket(yOffset = 0.16477328182606651, ground = false)
                        sendCriticalPacket(yOffset = 0.08307781780646721, ground = false)
                        sendCriticalPacket(yOffset = 0.0030162615090425808, ground = false)
                        attacks = 0
                    }
                }
                "grim" -> {
                    attacks++
                    if (attacks > 6) {
                        mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(x,y+0.2,z,false))
                        mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(x,y+0.1216,z,false))
                        attacks = 0
                    }
                }
                "grimactest1" -> {
                    if(thePlayer.isAirBorne)
                        mc.netHandler.addToSendQueue(
                            (classProvider.createCPacketPlayerPosition(
                                x,
                                y + 0.06250000001304,
                                z,
                                true
                            ) as IPacket)!!
                        )
                    mc.netHandler.addToSendQueue(
                        (classProvider.createCPacketPlayerPosition(
                            x,
                            y + 0.06150000001304,
                            z,
                            false
                        ) as IPacket)!!
                    )
                    this.msTimer.reset();
                }
                "grimactest2" -> {
                    this.attacks = attacks + 1;
                    if (this.attacks <= 6) {

                    } else {
                        if(thePlayer.onGround)
                            mc.netHandler.addToSendQueue(
                                classProvider.createCPacketPlayerPosition(
                                    x,
                                    y + 0.01,
                                    z,
                                    false
                                )
                            );
                        mc.netHandler.addToSendQueue(
                            classProvider.createCPacketPlayerPosition(
                                x,
                                y + 1.0E-10,
                                z,
                                false
                            )
                        );
                        mc.netHandler.addToSendQueue(
                            classProvider.createCPacketPlayerPosition(
                                x,
                                y + 0.114514,
                                z,
                                false
                            )
                        );
                        this.attacks = 0;
                    }
                }
                "grimactest-3" -> {

                    if(thePlayer.isAirBorne)
                        return

                    mc.netHandler.addToSendQueue(
                        (classProvider.createCPacketPlayerPosition(
                            x,
                            y + 0.021500070001304,
                            z,
                            false
                        ) as IPacket)!!
                    )
                    this.msTimer.reset();
                }
                "packet" -> {
                    mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(x, y + 0.0625, z, true))
                    mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(x, y, z, false))
                    mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(x, y + 1.1E-5, z, false))
                    mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(x, y, z, false))
                    thePlayer.onCriticalHit(entity)
                }

                "hytgrimac" -> {
                    n = this.attacks;
                    this.attacks = n + 1;
                    if (this.attacks <= 6){

                    }else{
                        mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(x, y + 0.01, z, false))
                        mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(x, y + 1.0E-10, z, false))
                        mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(x, y + 0.114514, z, false))
                        this.attacks = 0;
                    }
                }
                "hyttest4v4" -> {
                    n = this.attacks
                    this.attacks = n + 1
                    if (this.attacks >= 6)
                    {
                        mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(x, y + 0.01011, z, false))
                        mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(x, y + 1.0E-10, z, false))
                        mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(x, y + 0.10114, z, false))
                        thePlayer.onCriticalHit(entity)
                        this.attacks = 0
                    }
                }
                "testpit" -> {
                    mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(x, y + 0.2, z, false))
                    mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(x, y + 0.1216, z, false))
                    thePlayer.onCriticalHit(entity)
                }
                "grimac" -> {
                    n = this.attacks
                    this.attacks = n + 1
                    if (this.attacks >= 12)
                    {
                        mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(x, y + 0.01011, z, false))
                        mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(x, y + 1.0E-10, z, false))
                        mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(x, y + 0.05014, z, false))
                        thePlayer.onCriticalHit(entity)
                        this.attacks = 0
                    }
                }

                "hop" -> {
                    thePlayer.motionY = 0.1
                    thePlayer.fallDistance = 0.1f
                    thePlayer.onGround = false
                }

                "tphop" -> {
                    mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(x, y + 0.02, z, false))
                    mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(x, y + 0.01, z, false))
                    thePlayer.setPosition(x, y + 0.01, z)
                }

                "jump" -> thePlayer.motionY = 0.42
                "Simple" -> thePlayer.onCriticalHit(entity)
                "lowjump" -> thePlayer.motionY = 0.3425
            }

            msTimer.reset()
        }
    }

    @EventTarget
    fun onPacket(event: PacketEvent) {
        val packet = event.packet

        if (classProvider.isSPacketAnimation(packet)) {
            val packetAnimation = packet.asSPacketAnimation()
            if (packetAnimation.animationType == 4 && packetAnimation.entityID == target) {
                if (critList[target] != null) {
                    critList[target] = critList[target]!! + 1
                } else {
                    critList[target] = 1
                }
                if (classProvider.isSPacketAnimation(packet) && debugValue.get()) {
                    if (packet.asSPacketAnimation().animationType == 4 && packet.asSPacketAnimation().entityID == target) {
                        val name = (LiquidBounce.moduleManager.getModule(KillAura::class.java) as KillAura).target!!.name
                        ClientUtils.displayChatMessage("§bTomkClient§7>> §f触发暴击§b(§5玩家:§a$name§b)")
                    }
                }
            }
        }
        if (classProvider.isCPacketPlayer(packet) && modeValue.get().equals("NoGround", ignoreCase = true))
            packet.asCPacketPlayer().onGround = false
    }

    var entity: IEntity? = null
    @EventTarget
    fun onUpdate(event: UpdateEvent?) {
        val info = critList[entity!!.entityId]
        if(entity != null){
            if(entity!!.asEntityPlayer().isDead){
                if (info != null && killchatValue.get()) {
                    critList.remove(entity!!.entityId)
                    ClientUtils.displayChatMessage("§7${entity!!.name} §ewas killed by §7${mc.thePlayer!!.name}.")
                    ClientUtils.displayChatMessage("§b+1 Kill")
                }
            }
        }
    }
    fun sendCriticalPacket(
        xOffset: Double = 0.0,
        yOffset: Double = 0.0,
        zOffset: Double = 0.0,
        ground: Boolean
    ) {
        val x = mc.thePlayer!!.posX + xOffset
        val y = mc.thePlayer!!.posY + yOffset
        val z = mc.thePlayer!!.posZ + zOffset
        if (lookValue.get()) {
            mc.netHandler.addToSendQueue(
                classProvider.createCPacketPlayerPosLook(
                    x,
                    y,
                    z,
                    mc.thePlayer!!.rotationYaw,
                    mc.thePlayer!!.rotationPitch,
                    ground
                )
            )
        } else {
            mc.netHandler.addToSendQueue(classProvider.createCPacketPlayerPosition(x, y, z, ground))
        }
    }

    override val tag: String
        get() = modeValue.get()
}
