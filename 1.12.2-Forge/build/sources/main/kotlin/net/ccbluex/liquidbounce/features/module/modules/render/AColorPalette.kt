package net.ccbluex.liquidbounce.features.module.modules.render

import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.value.FloatValue
import net.ccbluex.liquidbounce.value.IntegerValue
import net.ccbluex.liquidbounce.value.ListValue


/**
 *
 *  by Lynn.
 * @Date
 */

@ModuleInfo(name="AColorPalette", description = "调色板",category = ModuleCategory.RENDER, canEnable = false)
class AColorPalette : Module(){

    companion object {
        @JvmField
        val colorModeValue = ListValue("Novo-Line-Color", arrayOf("Custom", "Rainbow", "Gident","Sky", "Slowly", "Fade", "Health"), "Gident")
        @JvmField
        val r = IntegerValue("Red", 255, 0, 255)
        @JvmField
        val g = IntegerValue("Green", 255, 0, 255)
        @JvmField
        val b = IntegerValue("Blue", 255, 0, 255)
        @JvmField
        val r2= IntegerValue("Red2", 255, 0, 255)
        @JvmField
        val g2= IntegerValue("Green2", 255, 0, 255)
        @JvmField
        val b2 = IntegerValue("Blue2", 255, 0, 255)
        @JvmField
        val r3= IntegerValue("Red3", 255, 0, 255)
        @JvmField
        val g3= IntegerValue("Green3", 255, 0, 255)
        @JvmField
        val b3 = IntegerValue("Blue3", 255, 0, 255)
        @JvmField
        val RA= IntegerValue("GuiRed", 105, 0, 255)
        @JvmField
        val GA= IntegerValue("GuiGreen", 25, 0, 255)
        @JvmField
        val BA = IntegerValue("GuiBlue", 255, 0, 255)
        @JvmField
        val RA2= IntegerValue("GuiRed2", 255, 0, 255)
        @JvmField
        val GA2= IntegerValue("GuiGreen2", 55, 0, 255)
        @JvmField
        val BA2 = IntegerValue("GuiBlue2", 255, 0, 255)
        @JvmField
        public val a = IntegerValue("A", 0, 0, 255)
        @JvmField
        public val a2 = IntegerValue("A2", 100, 0, 255)
        @JvmField
        public val ra = FloatValue("Radius", 4.5f, 0.1f, 8.0f)
        @JvmField
        val gradientLoopValue = IntegerValue("NewNovoline-GradientLoop", 7, 1, 40)
        val gradientDistanceValue = IntegerValue("NewNovoline-GradientDistance", 56, 1, 200)
        val saturationValue = FloatValue("NewNovoline-Saturation", 1F, 0F, 1F)
        val brightnessValue = FloatValue("NewNovoline-Brightness", 1F, 0F, 1F)
        val waveSecondValue = IntegerValue("NewNovoline-Seconds", 2, 1, 10)
        val gradientSpeed = IntegerValue("ColorSpeed", 100, 10, 1000)
    }
}