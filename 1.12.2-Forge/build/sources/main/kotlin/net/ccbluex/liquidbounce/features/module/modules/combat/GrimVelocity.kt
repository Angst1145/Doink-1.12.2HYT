package net.ccbluex.liquidbounce.features.module.modules.combat

import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.PacketEvent
import net.ccbluex.liquidbounce.event.UpdateEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.injection.backend.unwrap
import net.minecraft.network.Packet
import net.minecraft.network.play.INetHandlerPlayClient
import net.minecraft.network.play.client.*
import net.minecraft.network.play.server.SPacketConfirmTransaction
import net.minecraft.network.play.server.SPacketEntityVelocity
import net.minecraft.network.play.server.SPacketExplosion
import net.minecraft.network.play.server.SPacketPlayerAbilities
import java.util.*
import java.util.concurrent.LinkedBlockingQueue

@ModuleInfo(name = "Velocit",description = "Grim", category = ModuleCategory.COMBAT)
class GrimVelocity : Module() {

    var cancelPackets = 2
    var grimTCancel = 0

    private var resetPersec = 8
    private var updates = 0
    private val inBus = LinkedList<Packet<INetHandlerPlayClient>>()
    private val packets = LinkedBlockingQueue<Packet<*>>()


    override fun onEnable() {
        if (mc.thePlayer == null) return
        grimTCancel = 0
        inBus.clear()
    }
    override fun onDisable(){
        if (mc.thePlayer == null) return
        grimTCancel = 0
        inBus.clear()
    }

    @EventTarget
    fun onPacket(event: PacketEvent){
        if (mc.thePlayer == null) return
        if (mc.theWorld == null) return
        if (mc.thePlayer!!.isDead) return

        val packet = event.packet.unwrap()

        if (packet is SPacketEntityVelocity) {
            if ((mc.theWorld?.getEntityByID(packet.entityID) ?: return) != mc.thePlayer) {
                return
            }
            event.cancelEvent()
            grimTCancel = cancelPackets
        }
        if (packet is SPacketExplosion) {
            event.cancelEvent()
            grimTCancel = cancelPackets
        }
        if(grimTCancel > 0) {
            if (mc.thePlayer == null) return
            if (packet is CPacketPlayer) // Cancel all movement stuff
                event.cancelEvent()
            if (packet is CPacketPlayer.Position || packet is CPacketPlayer.PositionRotation ||
                packet is CPacketPlayerTryUseItemOnBlock ||
                packet is CPacketAnimation ||
                packet is CPacketEntityAction || packet is CPacketUseEntity
            ) {
                event.cancelEvent()
                packets.add(packet)
            }
            if (packet is SPacketConfirmTransaction || packet is SPacketPlayerAbilities) {
                event.cancelEvent()
                inBus.add(packet as Packet<INetHandlerPlayClient>)
            }
        }
    }
    @EventTarget
    fun onUpdate(event: UpdateEvent){
        updates++
        if (resetPersec > 0) {
            if (updates >= 0) {
                updates = 0
                if (grimTCancel > 0){
                    grimTCancel--
                }
            }
        }
        if (mc2.connection == null) return
        if(grimTCancel == 0){
            while (!packets.isEmpty()) {
                mc2.connection!!.networkManager.sendPacket(packets.take())
            }
            while (!inBus.isEmpty()) {
                inBus.poll()?.processPacket(mc2.connection!!)
            }
        }
    }
    override val tag: String
        get() = "Grim"

}