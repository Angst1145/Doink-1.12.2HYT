/*
 * LiquidBounce+ Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/WYSI-Foundation/LiquidBouncePlus/
 * 
 * This code belongs to WYSI-Foundation. Please give credits when using this in your repository.
 */
package tomk.module.render;

import net.ccbluex.liquidbounce.value.IntegerValue;

import static tomk.module.render.ColorMixer.regenerateColors;

public class ColorElement extends IntegerValue {

    public ColorElement(int counter, Material m, IntegerValue basis) {
        super("Color" + counter + "-" + m.getColorName(), 255, 0, 255);
    }

    public ColorElement(int counter, Material m) {
        super("Color" + counter + "-" + m.getColorName(), 255, 0, 255);
    }

    @Override
    protected void onChanged(final Integer oldValue, final Integer newValue) {
        regenerateColors(true);
    }

    enum Material {
        RED("Red"),
        GREEN("Green"),
        BLUE("Blue");

        private final String colName;
        Material(String name) {
            this.colName = name;
        }

        public String getColorName() {
            return this.colName;
        }
    }
    
}