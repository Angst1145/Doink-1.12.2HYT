package net.ccbluex.liquidbounce.features.module.modules.hyt


import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.PacketEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoArmor
import net.ccbluex.liquidbounce.features.module.modules.combat.GrimVelocity
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura
import net.ccbluex.liquidbounce.features.module.modules.combat.Velocity
import net.ccbluex.liquidbounce.features.module.modules.player.InventoryCleaner
import net.ccbluex.liquidbounce.features.module.modules.world.ChestStealer
import net.ccbluex.liquidbounce.injection.backend.unwrap
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Notification
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.NotifyType
import net.ccbluex.liquidbounce.value.BoolValue
import net.ccbluex.liquidbounce.value.ListValue
import net.ccbluex.liquidbounce.value.TextValue
import net.minecraft.network.play.server.SPacketChat
import java.util.regex.Pattern
@ModuleInfo(name = "AutoPlay", category = ModuleCategory.HYT, description = "Auto GG")
class AutoPlay : Module() {

    private val modeValue = ListValue("Server", arrayOf( "HuaYuTingBW","HuaYuTingSw","HuaYuTing16"), "HuaYuTingBW")
    private val autodis = BoolValue("Auto-Disable",true)
    private val Text = BoolValue("TextBoolValue",true)
    private val prefix = BoolValue("@",true)
    private val textValue = TextValue("Text", "[TomkClient]GG")
    private val textValu = TextValue("Text2", "@我正在使用TomkClient")
    var totalPlayed = 0
    var win = 0
    var ban = 0
    @EventTarget
    fun onPacket(event: PacketEvent) {
        val packet = event.packet.unwrap()
        val GrimVelocity = LiquidBounce.moduleManager.getModule(GrimVelocity::class.java) as GrimVelocity
        val KillAura = LiquidBounce.moduleManager.getModule(KillAura::class.java) as KillAura
        val Velocity = LiquidBounce.moduleManager.getModule(Velocity::class.java) as Velocity
        val ChestStealer = LiquidBounce.moduleManager.getModule(ChestStealer::class.java) as ChestStealer
        val InventoryCleaner = LiquidBounce.moduleManager.getModule(InventoryCleaner::class.java) as InventoryCleaner
        val AutoArmor = LiquidBounce.moduleManager.getModule(AutoArmor::class.java) as AutoArmor
        if (packet is SPacketChat) {
            val ftchat = packet.chatComponent.formattedText
            if (ftchat.contains("起床战争>> 游戏开始 ...")) modeValue.set("HuaYuTingBW")
            if (ftchat.contains("开始倒计时")) modeValue.set("HuaYuTingSw")
            if (ftchat.contains("§f[起床战争]")) modeValue.set("HuaYuTing16")
        }
        if (packet is SPacketChat) {
            val text = packet.chatComponent.unformattedText
            if(packet is SPacketChat){
                val matcher = Pattern.compile("玩家(.*?)在本局游戏中行为异常").matcher(packet.chatComponent.unformattedText)
                if(matcher.find()){
                    ban ++
                    val banname = matcher.group(1)
                    LiquidBounce.hud.addNotification(Notification("BanChecker","$banname was banned. (banned:$ban)",NotifyType.INFO, animeTime = 1000))
                }
            }
            when (modeValue.get().toLowerCase()) {

                "huayutingbw" -> {
                    if (text.contains("      喜欢      一般      不喜欢", true)) {
                        if (Text.get()) {
                        mc.thePlayer!!.sendChatMessage((if (prefix.get()) "@" else "")+textValue.get())
                        }
                        win += 1
                        LiquidBounce.hud.addNotification(Notification("AutoPlay", "恭喜胜利！", NotifyType.INFO))
                        if (autodis.get()) {
                            KillAura.state = false
                            Velocity.state = false
                            GrimVelocity.state = false
                        }
                    }
                    if (text.contains("起床战争>> 游戏开始 ...", true)) {
                        totalPlayed ++
                        LiquidBounce.hud.addNotification(Notification("AutoPlay", "游戏开始！！", NotifyType.INFO))
                        if (Text.get()) {
                        mc.thePlayer!!.sendChatMessage(textValu.get())
                        }

                    }
                }
                "huayuting16" -> {
                    if (text.contains("[起床战争] Game 结束！感谢您的参与！", true)) {
                        LiquidBounce.hud.addNotification(Notification("AutoPlay","Game Over", NotifyType.INFO))
                        if (Text.get()) {
                        mc.thePlayer!!.sendChatMessage((if (prefix.get()) "@" else "")+textValue.get())
                        }
                        win += 1
                        if (autodis.get()) {
                            KillAura.state = false
                            GrimVelocity.state = false
                            Velocity.state = false
                        }
                    }
                }
                "huayutingsw" -> {
                    if (text.contains("开始倒计时", true)) {
                        totalPlayed++
                        LiquidBounce.moduleManager[net.ccbluex.liquidbounce.features.module.modules.world.ChestStealer::class.java].state = true
                        LiquidBounce.moduleManager[net.ccbluex.liquidbounce.features.module.modules.player.InventoryCleaner::class.java].state = true
                        LiquidBounce.hud.addNotification(Notification("AutoPlay", "游戏开始！！", NotifyType.INFO))
                    }
                    val matcher = Pattern.compile("你在地图 (.*?)\\(").matcher(packet.chatComponent.unformattedText)
                    if (text.contains("你现在是观察者状态. 按E打开菜单.", true)) {
                        LiquidBounce.hud.addNotification(Notification("AutoPlay","Game Over", NotifyType.INFO))
                        if (Text.get()) {
                        mc.thePlayer!!.sendChatMessage((if (prefix.get()) "@" else "")+textValue.get())
                        }
                        if (autodis.get()) {
                        KillAura.state = false
                        Velocity.state = false
                            GrimVelocity.state = false
                        ChestStealer.state = false
                        InventoryCleaner.state = false
                        AutoArmor.state = false
                        }
                        win += 1

                    }
                }
            }
        }
    }
    override fun handleEvents() = true
    override val tag: String
        get() = modeValue.get()
}
