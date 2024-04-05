/*
 * ColorByte Hacked Client
 * A free half-open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/SkidderRyF/ColorByte/
 */
package net.ccbluex.liquidbounce.features.module.modules.misc

import net.ccbluex.liquidbounce.api.minecraft.potion.IPotion
import net.ccbluex.liquidbounce.api.minecraft.potion.PotionType
import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.UpdateEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.utils.ClientUtils.displayChatMessage
import net.ccbluex.liquidbounce.utils.EntityUtils
import net.ccbluex.liquidbounce.utils.timer.MSTimer
import net.ccbluex.liquidbounce.value.BoolValue
import net.ccbluex.liquidbounce.value.IntegerValue
import net.ccbluex.liquidbounce.value.ListValue

@ModuleInfo(name = "PotionWarn", description = "Check Potion Warn", category = ModuleCategory.MISC)
class PotionWarn : Module() {
    private val checkPotionNameValue =
            ListValue("CheckPotionName", arrayOf("DamageBoost", "MoveSpeed", "Jump", "Regen"), "DamageBoost")
    private val checkDelayValue = IntegerValue("CheckDelay", 2, 2, 100)
    private val messageDelayValue = IntegerValue("MessageDelay", 25, 10, 100)
    private val messageValue = BoolValue("CheckMessage", true)
    private var checkPotionName: String = ""
    private val distanceValue = IntegerValue("WarnDistance", 14, 4, 20)
    private val noticeDistanceValue = IntegerValue("NoticeDistance", 45, 20, 100)

    private val ms = MSTimer()

    @EventTarget
    fun onUpdate(event: UpdateEvent) {
        if (mc.thePlayer!!.ticksExisted % checkDelayValue.get() == 0) {
            for (entity in mc.theWorld!!.loadedEntityList) {
                if (entity != null && entity != mc.thePlayer && classProvider.isEntityPlayer(entity) && entity.asEntityLivingBase()
                                .isPotionActive(potionActiveName(checkPotionNameValue.get())!!) && EntityUtils.isSelected(
                                entity,
                                true
                        )
                ) {
                    if (messageValue.get() && ms.hasTimePassed(messageDelayValue.get().toLong())) {
                        if (mc.thePlayer!!.getDistanceToEntity(entity) >= noticeDistanceValue.get()) return
                        if (mc.thePlayer!!.getDistanceToEntity(entity) >= distanceValue.get()) {
                            displayChatMessage(
                                    "§8[§bPotionWarn§8] §c${entity.name}§f拥有§c${checkPotionName}§f药水效果，距你${
                                        mc.thePlayer!!.getDistanceToEntity(
                                                entity
                                        ).toInt()
                                    }米!"
                            )
                            ms.reset()
                        } else {
                            displayChatMessage(
                                    "§8[§bPotionWarn§8] §c${entity.name}§f拥有§c${checkPotionName}§f药水效果，距你${
                                        mc.thePlayer!!.getDistanceToEntity(
                                                entity
                                        ).toInt()
                                    }米! 离你较近。"
                            )
                            ms.reset()
                        }
                    }
                }
            }
        }
        when (checkPotionNameValue.get().toLowerCase()) {
            "damageboost" -> checkPotionName = "力量"
            "movespeed" -> checkPotionName = "速度"
            "jump" -> checkPotionName = "跳跃提升"
            "regen" -> checkPotionName = "生命恢复"
        }
    }

    private fun potionActiveName(potionName: String): IPotion? {
        return when (potionName.toLowerCase()) {
            "damageboost" -> classProvider.getPotionEnum(PotionType.STRENGTH)
            "movespeed" -> classProvider.getPotionEnum(PotionType.MOVE_SPEED)
            "jump" -> classProvider.getPotionEnum(PotionType.JUMP)
            "regen" -> classProvider.getPotionEnum(PotionType.REGENERATION)
            else -> null
        }
    }
}