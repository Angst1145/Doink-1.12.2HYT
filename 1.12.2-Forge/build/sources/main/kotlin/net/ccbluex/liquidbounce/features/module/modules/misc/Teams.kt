/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.liquidbounce.features.module.modules.misc

import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.value.BoolValue
import net.ccbluex.liquidbounce.value.IntegerValue
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemArmor
import java.awt.Color

@ModuleInfo(name = "Teams", description = "Prevents Killaura from attacking team mates.", category = ModuleCategory.MISC)
class Teams : Module() {

    private val scoreboardValue = BoolValue("ScoreboardTeam", true)
    private val colorValue = BoolValue("Color", true)
    private val gommeSWValue = BoolValue("GommeSW", false)
    private val armorColorValue = BoolValue("ArmorColor", false)
    private val armorColor2Value = BoolValue("Hyt4v4", false)
    private val AntiBot1Value = BoolValue("Hyt4v4Anti", false)
    private val armorIndexValue = IntegerValue("ArmorIndex", 3, 0, 3)

    /**
     * Check if [entity] is in your own team using scoreboard, name color or team prefix
     */
    fun isInYourTeam(entity: IEntityLivingBase): Boolean {
        val thePlayer = mc.thePlayer ?: return false

        if (scoreboardValue.get() && thePlayer.team != null && entity.team != null &&
            thePlayer.team!!.isSameTeam(entity.team!!))
            return true

        val displayName = thePlayer.displayName


        if(AntiBot1Value.get()){
            if(thePlayer.inventory.armorInventory[3] != null){
                val myHead = thePlayer.inventory.armorInventory[3]
                val myItemArmor = myHead!!.item!!.asItemArmor()
            }
            val entityPlayer = entity.asEntityPlayer()

            if(thePlayer.inventory.armorInventory[3] != null && entityPlayer.inventory.armorInventory[3] != null){
                val myHead = thePlayer.inventory.armorInventory[3]
                val myItemArmor = myHead!!.item!!.asItemArmor()


                val entityHead = entityPlayer.inventory.armorInventory[3]
                var entityItemArmor = myHead.item!!.asItemArmor()

                if(entityItemArmor.getColor(entityHead!!).toString() == "10511680"){
                    return true
                }
                if(myItemArmor.getColor(myHead) == entityItemArmor.getColor(entityHead)){
                    return true
                }
            }
        }

        if(armorColor2Value.get()){
            if(thePlayer.inventory.armorInventory[3] != null){
                val myHead = thePlayer.inventory.armorInventory[3]
                val myItemArmor = myHead!!.item!!.asItemArmor()
            }
            val entityPlayer = entity.asEntityPlayer()

            if(thePlayer.inventory.armorInventory[3] != null && entityPlayer.inventory.armorInventory[3] != null){
                val myHead = thePlayer.inventory.armorInventory[3]
                val myItemArmor = myHead!!.item!!.asItemArmor()


                val entityHead = entityPlayer.inventory.armorInventory[3]
                var entityItemArmor = myHead.item!!.asItemArmor()

                if(myItemArmor.getColor(myHead) == entityItemArmor.getColor(entityHead!!)){
                    return true
                }
            }
        }

        if(armorColorValue.get()){
            val entityPlayer = entity.asEntityPlayer()
            if(thePlayer.inventory.armorInventory[armorIndexValue.get()] != null && entityPlayer.inventory.armorInventory[armorIndexValue.get()] != null){
                val myHead = thePlayer.inventory.armorInventory[armorIndexValue.get()]
                val myItemArmor = myHead!!.item!!.asItemArmor()


                val entityHead = entityPlayer.inventory.armorInventory[armorIndexValue.get()]
                var entityItemArmor = myHead.item!!.asItemArmor()

                if(myItemArmor.getColor(myHead) == entityItemArmor.getColor(entityHead!!)){
                    return true
                }
            }
        }

        if (gommeSWValue.get() && displayName != null && entity.displayName != null) {
            val targetName = entity.displayName!!.formattedText.replace("§r", "")
            val clientName = displayName.formattedText.replace("§r", "")
            if (targetName.startsWith("T") && clientName.startsWith("T"))
                if (targetName[1].isDigit() && clientName[1].isDigit())
                    return targetName[1] == clientName[1]
        }

        if (colorValue.get() && displayName != null && entity.displayName != null) {
            val targetName = entity.displayName!!.formattedText.replace("§r", "")
            val clientName = displayName.formattedText.replace("§r", "")
            return targetName.startsWith("§${clientName[1]}")
        }

        return false
    }

}