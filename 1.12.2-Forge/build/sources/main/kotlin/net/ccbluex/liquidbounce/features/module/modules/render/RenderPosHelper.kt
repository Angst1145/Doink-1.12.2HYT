/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.liquidbounce.features.module.modules.render


import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayerBlockPlacement
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayerLook
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayerPosLook
import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.PacketEvent
import net.ccbluex.liquidbounce.event.UpdateEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.injection.backend.unwrap
import net.ccbluex.liquidbounce.utils.ClientUtils
import net.ccbluex.liquidbounce.utils.timer.MSTimer
import net.ccbluex.liquidbounce.value.BoolValue
import net.ccbluex.liquidbounce.value.FloatValue
import net.ccbluex.liquidbounce.value.IntegerValue
import net.ccbluex.liquidbounce.value.ListValue
import net.minecraft.network.Packet
import net.minecraft.network.play.INetHandlerPlayServer
import net.minecraft.network.play.client.*
import java.util.*

@ModuleInfo(name="视觉坐标助手", description = "RenderPosHelperSpoofs your ping to a given value.",category = ModuleCategory.RENDER)
class RenderPosHelper : Module() {

    var x1 = FloatValue("x1", 2f, 0f, 100f)
    var x2 = FloatValue("x2", 2f, 0f, 100f)
    var x3 = FloatValue("x3", 2f, 0f, 5f)
    var x4 = FloatValue("x4", 2f, 0f, 100f)
    var x5 = FloatValue("x5", 2f, 1f, 10f)


}