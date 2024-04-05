package net.ccbluex.liquidbounce.features.module.modules.color;

import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;


@ModuleInfo(name = "CustomColor", description = "CustomColor", category = ModuleCategory.RENDER)
public class CustomColor extends Module {
    public static final IntegerValue r = new IntegerValue("Red", 5, 0, 255);
    public static final IntegerValue g = new IntegerValue("Green", 97, 0, 255);
    public static final IntegerValue b = new IntegerValue("Blue", 157, 0, 255);
    public static final IntegerValue a = new IntegerValue("Alpha", 100, 0, 255);
    public static final IntegerValue r2 = new IntegerValue("Red-2", 255, 0, 255);
    public static final IntegerValue g2 = new IntegerValue("Green-2", 255, 0, 255);
    public static final IntegerValue b2 = new IntegerValue("Blue-2", 255, 0, 255);

    public static final FloatValue ra = new FloatValue("Radius", 6.44f, 0.1f, 10.0f);
    public static final FloatValue line = new FloatValue("Line", 2f,0f,5f);

    public static final FloatValue office = new FloatValue("Office", 3f,0f,5f);

    public CustomColor() {
        setState(true);
    }
}
