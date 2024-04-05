/*
 * liying Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/SkidderMC/liying/
 */

package net.ccbluex.liquidbounce.ui.client.font1;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.InputStream;
import java.util.ArrayList;
public abstract class FontLoaders {
    public static CFontRenderer F14 = new CFontRenderer(getFont(14), true, true);
    public static CFontRenderer F12 = new CFontRenderer(getFont(12), true, true);
    public static CFontRenderer F16 = new CFontRenderer(getFont(16), true, true);
    public static CFontRenderer F18 = new CFontRenderer(getFont(18), true, true);
    public static CFontRenderer F20 = new CFontRenderer(getFont(20), true, true);
    public static CFontRenderer J20 = new CFontRenderer(getJello(20), true, true);
    public static CFontRenderer F22 = new CFontRenderer(getFont(22), true, true);
    public static CFontRenderer SF16 = new CFontRenderer(getSF(16), true, true);
    public static CFontRenderer M20 = new CFontRenderer(getMisans(20), true, true);
    public static CFontRenderer jelloFontMarker = new CFontRenderer(FontLoaders.getJelloFont(19, false), true, true);

    public static CFontRenderer T40 = new CFontRenderer(gettenacitybold(40), true, true);
    public static CFontRenderer T18 = new CFontRenderer(gettenacitybold(18), true, true);
    public static CFontRenderer xyz16 = new CFontRenderer(getxyz(16), true, true);
    public static CFontRenderer xyz18 = new CFontRenderer(getxyz(18), true, true);
    public static CFontRenderer xyz20 = new CFontRenderer(getxyz(20), true, true);
    public static CFontRenderer xyz28 = new CFontRenderer(getxyz(28), true, true);
    public static CFontRenderer xyz32 = new CFontRenderer(getxyz(32), true, true);
    public static CFontRenderer xyz100 = new CFontRenderer(getxyz(100), true, true);
    public static CFontRenderer xyz40 = new CFontRenderer(getxyz(40), true, true);
    public static CFontRenderer xyz70 = new CFontRenderer(getxyz(70), true, true);
    public static CFontRenderer jelloFontBig = new CFontRenderer(FontLoaders.getJelloFont(41, true), true, true);
    public static CFontRenderer jellolightBig = new CFontRenderer(FontLoaders.getJelloFont(45, false), true, true);

    public static CFontRenderer jelloFont30 = new CFontRenderer(FontLoaders.getJelloFont(30, true), true, true);
    public static CFontRenderer jellolight22 = new CFontRenderer(FontLoaders.getJelloFont(22, false), true, true);
    public static CFontRenderer jellolight20 = new CFontRenderer(FontLoaders.getJelloFont(20, false), true, true);
    public static CFontRenderer jellolight30 = new CFontRenderer(FontLoaders.getJelloFont(30, false), true, true);
    public static CFontRenderer jelloFontMedium20 = new CFontRenderer(FontLoaders.getJelloFont(20, true), true, true);
    public static CFontRenderer jellolight18 = new CFontRenderer(FontLoaders.getJelloFont(18, false), true, true);
    public static CFontRenderer jellomedium18 = new CFontRenderer(FontLoaders.getJelloFont(18, true), true, true);
    public static CFontRenderer F23 = new CFontRenderer(getFont(23), true, true);
    public static CFontRenderer F24 = new CFontRenderer(getFont(24), true, true);
    public static CFontRenderer F30 = new CFontRenderer(getFont(30), true, true);
    public static CFontRenderer F40 = new CFontRenderer(getFont(40), true, true);
    public static CFontRenderer F50 = new CFontRenderer(getFont(50), true, true);
    public static CFontRenderer C12 = new CFontRenderer(getComfortaa(12), true, true);
    public static CFontRenderer C16 = new CFontRenderer(getComfortaa(16), true, true);
    public static CFontRenderer C18 = new CFontRenderer(getComfortaa(18), true, true);
    public static CFontRenderer C20 = new CFontRenderer(getComfortaa(20), true, true);
    public static CFontRenderer C35 = new CFontRenderer(getComfortaa(35), true, true);
    public static CFontRenderer C22 = new CFontRenderer(getComfortaa(22), true, true);
    public static CFontRenderer Logo = new CFontRenderer(getNovo(40), true, true);
    public static CFontRenderer SF18 = new CFontRenderer(getSF(18), true, true);
    public static CFontRenderer SF22 = new CFontRenderer(getSF(22), true, true);

    public static CFontRenderer I10 = new CFontRenderer(getIcon(10), true, true);
    public static CFontRenderer I14 = new CFontRenderer(getIcon(14), true, true);
    public static CFontRenderer I15 = new CFontRenderer(getIcon(15), true, true);
    public static CFontRenderer I16 = new CFontRenderer(getIcon(16), true, true);
    public static CFontRenderer I18 = new CFontRenderer(getIcon(18), true, true);
    public static CFontRenderer I20 = new CFontRenderer(getIcon(20), true, true);
    public static CFontRenderer I25 = new CFontRenderer(getIcon(25), true, true);

    public static ArrayList<CFontRenderer> fonts = new ArrayList<>();

    public static CFontRenderer getFontRender(int size) {
        return fonts.get(size - 10);
    }
    public static void initFonts() {
        // 触发static加载font
    }
    private static Font getJelloFont(float size, boolean bold) {
        Font font;
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(bold ? "tomk/font/jellomedium.ttf": "onlooker/font/jellolight.ttf")).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(0, size);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, +10);
        }
        return font;
    }
    private static Font getxyz(int size) {
        Font font;

        try {
            InputStream ex = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("tomk/font/huahuo.ttf")).getInputStream();

            font = Font.createFont(0, ex);
            font = font.deriveFont(0, (float) size);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }

        return font;
    }
    public static Font getComfortaa(int size) {
        Font font;
        try {
            font = Font.createFont(0, Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("misans.ttf")).getInputStream()).deriveFont(0, (float) size);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }
        return font;
    }
    public static Font gettenacitybold(int size) {
        Font font;
        try {
            font = Font.createFont(0, Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("tomk/font/tenacitybold.ttf")).getInputStream()).deriveFont(0, (float) size);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }
        return font;
    }

    public static Font getNico80(int size) {
        Font font;
        try {
            font = Font.createFont(0, Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("tomk/font/nico.ttf")).getInputStream()).deriveFont(0, (float) size);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }
        return font;
    }

    public static Font getIcon(int size) {
        Font font;
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("tomk/font/icon.ttf")).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(Font.PLAIN, size);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", Font.PLAIN, size);
        }
        return font;
    }
    public static Font getSF(int size) {
        Font font;
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("tomk/font/sfbold.ttf")).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(Font.PLAIN, size);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", Font.PLAIN, size);
        }
        return font;
    }

    public static Font getNovo(int size) {
        Font font;
        font = new Font("default", 0, size);
        return font;
    }
    public static Font getFont(int size) {
        Font font;
        try {
            font = Font.createFont(0, Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("tomk/font/sfui.ttf")).getInputStream()).deriveFont(0, (float) size);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }
        return font;
    }
    public static Font getJello(int size) {
        Font font;
        try {
            font = Font.createFont(0, Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("tomk/font/jellolight2.ttf")).getInputStream()).deriveFont(0, (float) size);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }
        return font;
    }
    public static Font getMisans(int size) {
        Font font;
        try {
            font = Font.createFont(0, Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("tomk/font/misans.ttf")).getInputStream()).deriveFont(0, (float) size);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }
        return font;
    }
}
