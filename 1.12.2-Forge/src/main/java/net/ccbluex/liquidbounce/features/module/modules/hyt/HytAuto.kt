package net.ccbluex.liquidbounce.features.module.modules.hyt

import net.ccbluex.liquidbounce.api.enums.ItemType
import net.ccbluex.liquidbounce.api.enums.WEnumHand
import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.UpdateEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.utils.InventoryUtils
import net.ccbluex.liquidbounce.utils.timer.MSTimer
import net.ccbluex.liquidbounce.value.FloatValue


@ModuleInfo(name = "AutoSave", description = "自动急救平台 ",
    category = ModuleCategory.HYT)
class HytAuto : Module() {
    private val maxFallDistValue = FloatValue("MaxFallDistance", 10F, 5F, 20F)
    var isEnabled: Boolean = false
    private val timer = MSTimer()
    private var tried = false
    override fun onEnable() {
        tried = false
    }
    @EventTarget
    fun onUpdate(event: UpdateEvent?) {
        if (mc.thePlayer!!.onGround) {
            tried = false
        }
        if (!isEnabled) {
            if (mc.thePlayer!!.fallDistance > maxFallDistValue.get() && !tried) {
                val fireRodInHotbar = InventoryUtils.findItem(36, 45, classProvider.getItemEnum(ItemType.BLAZE_ROD))

                if (fireRodInHotbar != -1) {
                    mc.netHandler.addToSendQueue(classProvider.createCPacketHeldItemChange(fireRodInHotbar - 36))
                    mc.netHandler.addToSendQueue(classProvider.createCPacketTryUseItem(WEnumHand.OFF_HAND))
                    mc.netHandler.addToSendQueue(classProvider.createCPacketHeldItemChange(mc.thePlayer!!.inventory.currentItem))
                    alert("§b自动急救平台§7>> true!")
                    isEnabled = true
                    timer.reset()
                }
            }
        } else {
            if (timer.hasTimePassed(12000)) {
                isEnabled = false
            }
        }
    }
    override val tag: String
        get() = "Bypass"
}