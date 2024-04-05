package net.ccbluex.liquidbounce.features.module.modules.player

import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
import net.ccbluex.liquidbounce.event.AttackEvent
import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.UpdateEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.value.BoolValue
import net.ccbluex.liquidbounce.value.TextValue
import java.util.*

@ModuleInfo(name = "AutoL", category = ModuleCategory.HYT, description = "修复版")
class AutoL : Module() {
    private val L = BoolValue ("L", true)
    private val AutoLmsg = TextValue("AutoLmsg", "@[Tomk]")
    // Target
    var target: IEntityLivingBase? = null
    var kill = 0

    @EventTarget
    fun onAttack(event: AttackEvent) {
        target = event.targetEntity as IEntityLivingBase?
    }

    @EventTarget
    fun onUpdate(event: UpdateEvent) {
        if (target!!.health <= 0.1) {
            kill += 1
            if (L.get()) {
                val r = Random()
                mc.thePlayer!!.sendChatMessage(AutoLmsg.get() + "我已经击杀了" + kill + "人 ")
            }
            target = null
        }
    }

    fun kills() : Int {
        return kill
    }
    override val tag: String?
        get() = "Kill $kill"
}
