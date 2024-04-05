package net.ccbluex.liquidbounce.ui.client.hud.element.elements

import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
import net.ccbluex.liquidbounce.event.*
import net.minecraft.network.handshake.client.C00Handshake
import net.minecraft.network.play.server.SPacketChat
import net.minecraft.network.play.server.SPacketTitle

object Recorder : Listenable{
    var syncEntity: IEntityLivingBase? = null
    var killCounts = 0
    var win = 0
    var totalPlayed = 0
    var totalPlayed2 =0
    var ban = 0
    var startTime = System.currentTimeMillis()

    @EventTarget
    fun onAttack(event: AttackEvent) { syncEntity = event.targetEntity as IEntityLivingBase? //ok
    }
    @EventTarget
    fun onUpdate(event: UpdateEvent) {
        if( syncEntity!!.isDead){
            ++killCounts
            syncEntity = null
        }
    }
    @EventTarget
    fun onPacket(event: PacketEvent) {
        if (event.packet is C00Handshake) startTime = System.currentTimeMillis()
        val message = (event.packet as SPacketChat).chatComponent.unformattedText
        val packet = event.packet
        if (packet is SPacketTitle) {
            val title = (packet.message ?: return@onPacket).formattedText
            if (title.startsWith("游戏开始")
            ) totalPlayed++
            if (title.startsWith("恭喜"))
                win++
        }
        if(message.contains("Reason")){
            ban++
        }
    }

    override fun handleEvents(): Boolean {
        return true
    }
}