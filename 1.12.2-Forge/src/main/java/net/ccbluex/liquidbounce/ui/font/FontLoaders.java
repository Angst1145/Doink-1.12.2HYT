package net.ccbluex.liquidbounce.ui.font;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import novoline.font.Fonts;
import scala.tools.nsc.doc.model.Public;

import java.awt.*;
import java.io.InputStream;

public abstract class FontLoaders {
    public static FontDrawer F18;
    public static FontDrawer F15;
    public static FontDrawer F16;
    public static FontDrawer C24;
    public static FontDrawer C16;
    public static FontDrawer C18;
    public static FontDrawer pop18;

    public static FontDrawer C12;
    public static FontDrawer tenacity22;
    public static FontDrawer tenacitybold20;
    public static FontDrawer tenacitybold18;
    public static  FontDrawer foricotwo24;
    public static  FontDrawer for18;

    public static FontDrawer xyz20;
    public static FontDrawer novologo245;
    public static FontDrawer jelloFontMedium16;
    public static FontDrawer jellolightBig2;
    public static FontDrawer xyz18;
    public static FontDrawer Check24;
    public static FontDrawer NL16;
    public static FontDrawer NL35;
    public static FontDrawer NL20;

    public static FontDrawer NL18;
    public static FontDrawer NL24;
    public static FontDrawer NIcon24;
    public static FontDrawer NL14;
    public static FontDrawer N2Icon24;

    public static FontDrawer F14;
    public static FontDrawer F20;
    public static FontDrawer F22;
    public static FontDrawer F24;
    public static FontDrawer F30;
    public static FontDrawer S22;
    public static FontDrawer JB;
    public static FontDrawer SB30;
    public static FontDrawer SB20;
    public static FontDrawer SB15;
    public static FontDrawer SB35;
    public static FontDrawer SB18;
    public static FontDrawer F26;
    public static FontDrawer jellolightBig;
    public static FontDrawer jellolight18;
    public static FontDrawer jellom18;
    public static FontDrawer jellor16;
    public static FontDrawer jellor18;

    public static FontDrawer R20;
    public static FontDrawer R22;
    public static FontDrawer F28;
    public static FontDrawer j30;
    public static FontDrawer S16;
    public static FontDrawer j20;
    public static FontDrawer T24;
    public static FontDrawer T40;
    public static FontDrawer nt18;
    public static FontDrawer rb18;
    public static FontDrawer T22;
    public static FontDrawer T20;
    public static FontDrawer T16;
    public static FontDrawer T18;
    public static FontDrawer Tcheck;




    public static void initFonts() {

        F14 = getFont("misans.ttf", 14, true);
        F30= getFont("misans.ttf", 30, true);
        F22= getFont("misans.ttf", 22, true);
        F24= getFont("misans.ttf", 24, true);
        SB20 = getFont("sfbold.ttf", 20, true);
        SB30= getFont("sfbold.ttf", 30, true);
        SB35= getFont("sfbold.ttf", 35, true);

        SB15= getFont("sfbold.ttf", 15, true);
        SB18= getFont("sfbold.ttf", 18, true);
        jellor16 = getFont("jelloregular.ttf",16,true);
        jellor18 = getFont("jelloregular.ttf",18,true);
        jellom18 = getFont("jellomedium.ttf",18,true);
        jellolight18 = getFont("jellolight.ttf", 18, true);
        jellolightBig = getFont("jellomedium.ttf",45,true);
        pop18 = getFont("pop.ttf", 18, true);
        S16 = getFont("sfui.ttf",16,true);
        S22 = getFont("sfui.ttf",22,true);
        JB = getFont("misans.ttf",45,true);
        F20 = getFont("misans.ttf", 20, true);
        R20 = getFont("regular.ttf", 20, true);
        R22 = getFont("regular.ttf", 22, true);
        F26= getFont("misans.ttf", 22, true);
        F28 = getFont("misans.ttf", 20, true);
        j30 = getFont("jello.ttf", 30, true);
        j20 = getFont("jello.ttf", 20, true);
        T24 = getFont("bold.ttf", 24, true);
        T20 = getFont("bold.ttf", 20, true);
        T16 = getFont("bold.ttf", 16, true);
        T18 = getFont("bold.ttf", 18, true);
        T22 = getFont("bold.ttf", 22, true);
        T40 = getFont("bold.ttf", 40, true);
        nt18 = getFont("gcf.ttf", 18, true);
        rb18 = getFont("rubik.ttf", 18, true);
        Tcheck = getFont("check.ttf", 36, true);

        N2Icon24 = getFont("icon2.ttf", 24, true);
        NL14 = getFont("sfui.ttf", 14, true);
        NL18= getFont("sfui.ttf", 18, true);
        NL16 = getFont("sfui.ttf", 16, true);
        NL20 = getFont("sfui.ttf", 20, true);
        NL24 = getFont("sfui.ttf", 24, true);
        NL35 = getFont("sfui.ttf", 35, true);
        NIcon24 = getFont("icon.ttf", 24, true);
        Check24 = getFont("check.ttf", 24, true);
        xyz18 = getFont("misans.ttf", 18, true);
        xyz20 = getFont("misans.ttf", 20, true);
        jelloFontMedium16 = getFont("jellomedium.ttf", 16, true);
        jellolightBig2 = getFont("jellolight2.ttf", 45, true);
        novologo245 = getFont("iconnovo.ttf", 45, true);
        F15 = getFont("misans", 15, true);
        F16 = getFont("misans", 16, true);
        F18 = getFont("misans", 18, true);
        C16 = new FontDrawer(FontLoaders.getComfortaa(16), true);
        C24 = new FontDrawer(FontLoaders.getComfortaa(24), true);
        C18 = new FontDrawer(FontLoaders.getComfortaa(18), true);
        C12 = new FontDrawer(FontLoaders.getComfortaa(12), true);
        for18= new FontDrawer(FontLoaders.getNovo(18), true);
        foricotwo24= new FontDrawer(FontLoaders.forico2(24), true);
        tenacitybold20=getFont("tenacitybold", 20, true);
        tenacitybold18=getFont("tenacitybold", 18, true);
        tenacity22=getFont("tenacity", 22, true);
        for18=getFont("for", 18, true);
        foricotwo24=getFont("forico2", 24, true);

    }

    public static FontDrawer getFont(String name, int size, boolean antiAliasing) {
        Font font;
        try {
            font = Font.createFont(0, Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("tomk/font/" + name + ".ttf")).getInputStream()).deriveFont(Font.PLAIN, (float) size);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", Font.PLAIN, size);
        }
        return new FontDrawer(font, antiAliasing);
    }


    private static Font forico2(int size) {
        Font font;

        try {
            InputStream ex = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("tomk/font/forico2.ttf")).getInputStream();

            font = Font.createFont(0, ex);
            font = font.deriveFont(0, (float) size);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }

        return font;
    }
    public static Font getNovo(int size) {
        Font font;
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager()
                    .getResource(new ResourceLocation("tomk/font/Novofont.ttf")).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(0, size);
        } catch (Exception ex) {
            ex.printStackTrace();
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

}
