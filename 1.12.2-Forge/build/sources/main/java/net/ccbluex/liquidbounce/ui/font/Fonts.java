package net.ccbluex.liquidbounce.ui.font;

import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.*;

public class Fonts extends MinecraftInstance {
    @FontDetails(fontName = "NewTenacity", fontSize = 20)
    public static IFontRenderer newtenacity40;
    @FontDetails(fontName = "NewTenacity", fontSize = 23)
    public static IFontRenderer newtenacity45;
    @FontDetails(fontName = "Minecraft Font")
    public static final IFontRenderer minecraftFont = mc.getFontRendererObj();

    @FontDetails(fontName = "yangzi", fontSize = 20)
    public static IFontRenderer icon50;

    @FontDetails(fontName = "Roboto Medium", fontSize = 18)
    public static IFontRenderer roboto35;
    @FontDetails(fontName = "Roboto Medium", fontSize = 20)
    public static IFontRenderer roboto40;
    @FontDetails(fontName = "Rubik", fontSize = 15)
    public static IFontRenderer rubik30;
    @FontDetails(fontName = "Rubik", fontSize = 18)
    public static IFontRenderer rubik35;
    @FontDetails(fontName = "Rubik", fontSize = 20)
    public static IFontRenderer rubik40;
    @FontDetails(fontName = "Rubik", fontSize = 23)
    public static IFontRenderer rubik45;
    @FontDetails(fontName = "Rubik", fontSize = 13)
    public static IFontRenderer rubik26;
    @FontDetails(fontName = "flux", fontSize = 18)
    public static IFontRenderer flux;
    @FontDetails(fontName = "flux", fontSize = 23)
    public static IFontRenderer flux45;
    @FontDetails(fontName = "Tenacity", fontSize = 40)
    public static IFontRenderer tenacity80;
    @FontDetails(fontName = "Tenacity", fontSize = 80)
    public static IFontRenderer tenacity100;
    @FontDetails(fontName = "Tenacity", fontSize = 20)
    public static IFontRenderer tenacity40;
    @FontDetails(fontName = "Tenacity", fontSize = 10)
    public static IFontRenderer tenacity20;
    @FontDetails(fontName = "Tenacitybold", fontSize = 15)
    public static IFontRenderer tenacitybold30;
    @FontDetails(fontName = "Tenacitybold", fontSize = 13)
    public static IFontRenderer tenacitybold25;
    @FontDetails(fontName = "Tenacitybold", fontSize = 18)
    public static IFontRenderer tenacitybold35;
    @FontDetails(fontName = "Tenacitybold", fontSize = 20)
    public static IFontRenderer tenacitybold40;
    @FontDetails(fontName = "Tenacitybold", fontSize = 21)
    public static IFontRenderer tenacitybold42;
    @FontDetails(fontName = "Roboto Bold", fontSize = 90)
    public static IFontRenderer robotoBold180;
    @FontDetails(fontName = "Product Sans", fontSize = 18)
    public static IFontRenderer productSans35;
    @FontDetails(fontName = "Product Sans", fontSize = 20)
    public static IFontRenderer productSans40;
    @FontDetails(fontName = "Product Sans", fontSize = 37)
    public static IFontRenderer productSans70;
    @FontDetails(fontName = "Product Sans", fontSize = 40)
    public static IFontRenderer productSans80;
    @FontDetails(fontName = "Product Sans", fontSize = 26)
    public static IFontRenderer productSans52;
    @FontDetails(fontName = "Product Sans", fontSize = 15)
    public static IFontRenderer productSans30;
    @FontDetails(fontName = "Product Sans", fontSize = 13)
    public static IFontRenderer productSans26;

    @FontDetails(fontName = "Product Sans", fontSize = 23)
    public static IFontRenderer productSans45;
    @FontDetails(fontName = "Product Sans", fontSize = 25)
    public static IFontRenderer productSans50;

    @FontDetails(fontName = "Notification Icon", fontSize = 35)
    public static IFontRenderer notificationIcon70;
    @FontDetails(fontName = "Tenacitybold", fontSize = 25)
    public static IFontRenderer tenacitybold50;
    @FontDetails(fontName = "Tenacitybold", fontSize = 21)
    public static IFontRenderer tenacitybold43;
    @FontDetails(fontName = "Tenacitycheck", fontSize = 30)
    public static IFontRenderer tenacitycheck60;
    @FontDetails(fontName = "Posterama", fontSize = 15)
    public static IFontRenderer posterama30;
    @FontDetails(fontName = "Posterama", fontSize = 18)
    public static IFontRenderer posterama35;
    @FontDetails(fontName = "Posterama", fontSize = 20)
    public static IFontRenderer posterama40;
    @FontDetails(fontName = "NB", fontSize = 18)
    public static IFontRenderer nbicon18;
    @FontDetails(fontName = "NB", fontSize = 20)
    public static IFontRenderer nbicon20;
    @FontDetails(fontName = "NB", fontSize = 40)
    public static IFontRenderer nbicon40;

    @FontDetails(fontName = "NB", fontSize = 45)
    public static IFontRenderer nbicon45;
    @FontDetails(fontName = "Roboto Medium", fontSize = 13)
    public static IFontRenderer font25;
    @FontDetails(fontName = "Roboto Medium", fontSize = 15)
    public static IFontRenderer font30;
    @FontDetails(fontName = "Roboto Medium", fontSize = 18)
    public static IFontRenderer font35;
    @FontDetails(fontName = "Roboto Medium", fontSize = 20)
    public static IFontRenderer font40;
    @FontDetails(fontName = "comfortaa", fontSize = 96)
    public static IFontRenderer com96;
    @FontDetails(fontName = "SFUI Regular", fontSize = 35)
    public static IFontRenderer fontSFUI35;
    @FontDetails(fontName = "Bold", fontSize = 30)
    public static IFontRenderer bold30;
    @FontDetails(fontName = "Bold", fontSize = 35)
    public static IFontRenderer bold35;
    @FontDetails(fontName = "Chinese", fontSize = 18)
    public static GameFontRenderer Chinese18;
    @FontDetails(fontName = "sfbold100", fontSize = 40)
    public static GameFontRenderer sfbold100;
    @FontDetails(fontName = "sfbold80", fontSize = 40)
    public static GameFontRenderer sfbold80;
    @FontDetails(fontName = "sfbold40", fontSize = 40)
    public static GameFontRenderer sfbold40;
    @FontDetails(fontName = "sfbold35", fontSize = 40)
    public static GameFontRenderer sfbold35;
    @FontDetails(fontName = "sfbold30", fontSize = 40)
    public static GameFontRenderer sfbold30;
    @FontDetails(fontName = "sfbold28", fontSize = 28)
    public static GameFontRenderer sfbold28;
    @FontDetails(fontName = "sfbold28", fontSize = 12)
    public static GameFontRenderer sfbold12;
    @FontDetails(fontName = "sfbold28", fontSize = 25)
    public static GameFontRenderer sfbold25;
    @FontDetails(fontName = "MiSans", fontSize = 18)
    public static IFontRenderer misans35;
    @FontDetails(fontName = "MiSans", fontSize = 20)
    public static IFontRenderer misans40;
    @FontDetails(fontName = "MiSans", fontSize = 15)
    public static IFontRenderer misans30;
    @FontDetails(fontName = "MiSans", fontSize = 10)
    public static IFontRenderer misans20;
    @FontDetails(fontName = "MiSans", fontSize = 13)
    public static IFontRenderer misans25;
    @FontDetails(fontName = "MiSans", fontSize = 16)
    public static IFontRenderer misans32;

    @FontDetails(fontName = "Roboto Medium", fontSize = 22)
    public static IFontRenderer font43;
    @FontDetails(fontName = "Rise", fontSize = 80)
    public static IFontRenderer rise100;
    @FontDetails(fontName = "Rise", fontSize = 18)
    public static IFontRenderer rise35;
    @FontDetails(fontName = "Rise", fontSize = 15)
    public static IFontRenderer rise30;
    @FontDetails(fontName = "Rise", fontSize = 13)
    public static IFontRenderer rise25;
    @FontDetails(fontName = "Rise", fontSize = 20)
    public static IFontRenderer rise40;
    @FontDetails(fontName = "Rise", fontSize = 23)
    public static IFontRenderer rise45;
    @FontDetails(fontName = "Rise", fontSize = 25)
    public static IFontRenderer rise50;
    @FontDetails(fontName = "Rise", fontSize = 30)
    public static IFontRenderer riseGuilogin;
    @FontDetails(fontName = "Title", fontSize = 15)
    public static IFontRenderer title30;
    @FontDetails(fontName = "Title", fontSize = 13)
    public static IFontRenderer title25;
    @FontDetails(fontName = "Title", fontSize = 18)
    public static IFontRenderer title35;
    @FontDetails(fontName = "Title", fontSize = 20)
    public static IFontRenderer title40;
    @FontDetails(fontName = "Neverlose900", fontSize = 18)
    public static IFontRenderer never900_35;
    @FontDetails(fontName = "Neverlose900", fontSize = 20)
    public static IFontRenderer never900_40;
    @FontDetails(fontName = "Neverlose900", fontSize = 23)
    public static IFontRenderer never900_45;
    @FontDetails(fontName = "Newuifont", fontSize = 15)
    public static IFontRenderer Newuifont12;
    @FontDetails(fontName = "Newuifont", fontSize = 17)
    public static IFontRenderer Newuifont17;
    @FontDetails(fontName = "Novoicon", fontSize = 25)
    public static IFontRenderer Novoicon_25;
    @FontDetails(fontName = "Newuifont", fontSize = 15)
    public static IFontRenderer Newuifont15;
    @FontDetails(fontName = "Newbold", fontSize = 20)
    public static IFontRenderer NewBold20;
    @FontDetails(fontName = "Roboto Medium", fontSize = 35)
    public static IFontRenderer font50;
    @FontDetails(fontName = "Notion 40", fontSize = 40)
    public static IFontRenderer no40;
    public static void loadFonts() {
        long l = System.currentTimeMillis();

        ClientUtils.getLogger().info("Loading Fonts.");
        icon50 = getFont("hicon.ttf", 20);
        font50= getFont("sfui.ttf", 50);
        NewBold20 = getFont("huahuo.ttf", 20);
        Novoicon_25 = getFont("novosession.ttf", 25);
        Newuifont15 = getFont("tenacitybold.ttf",15);
        Newuifont17 = getFont("tenacitybold.ttf",17);
        Newuifont12 = getFont("tenacitybold.ttf",13);
        newtenacity40 = getFont("gcf.ttf", 20);
        newtenacity45 = getFont("gcf.ttf", 23);
        tenacity80 = getFont("bold.ttf", 40);
        tenacity100 = getFont("bold.ttf", 80);
        tenacity40 = getFont("bold.ttf",20);
        tenacity20 = getFont("bold.ttf",10);
        tenacitybold25 = getFont("bold.ttf", 13);
        tenacitybold30 = getFont("bold.ttf", 15);
        tenacitybold35 = getFont("bold.ttf", 18);
        tenacitybold40 = getFont("bold.ttf", 20);
        tenacitybold42 = getFont("bold.ttf", 21);

        tenacitybold50= getFont("bold.ttf", 25);
        never900_35 = getFont("neverlose900.ttf", 18);
        never900_40 = getFont("neverlose900.ttf", 20);
        never900_45 = getFont("neverlose900.ttf", 23);
        title25 = getFont("title.ttf", 13);
        title30 = getFont("title.ttf", 15);
        title35 = getFont("title.ttf", 18);
        title40 = getFont("title.ttf", 20);
        rise100 = getFont("notosans1.ttf", 80);
        rise40 = getFont("notosans1.ttf", 20);
        rise45 = getFont("notosans1.ttf", 23);
        rise35 = getFont("notosans1.ttf", 18);
        rise30 = getFont("notosans1.ttf", 15);
        rise25 = getFont("notosans1.ttf", 13);
        rise50 = getFont("notosans1.ttf", 25);
        rubik26 = getFont("rubik.ttf", 13);
        rubik30 = getFont("rubik.ttf", 15);
        rubik35 = getFont("rubik.ttf", 18);
        rubik40 = getFont("rubik.ttf", 20);
        rubik45 = getFont("rubik.ttf", 23);
        Chinese18 =  (new GameFontRenderer(getsfbold(38)));
        bold30 = getFont("blod.ttf", 15);
        bold35 = getFont("blod.ttf", 13);
        com96 = getFont("comfortaa.ttf", 13);
        fontSFUI35 = getFont("sfuidisplayregular.ttf", 13);
        font25 = getFont("sfui.ttf", 13);
        font30 = getFont("sfui.ttf", 15);
        font35 = getFont("sfui.ttf", 18);
        font40 = getFont("sfui.ttf", 20);
        font43 = getFont("sfui.ttf", 22);
        roboto35 = getFont("roboto-medium.ttf", 18);
        roboto40 = getFont("roboto-medium.ttf", 20);
        robotoBold180 = getFont("roboto-bold.ttf", 90);
        productSans35 = getFont("product-sans.ttf", 18);
        productSans40 = getFont("product-sans.ttf", 20);
        productSans70 = getFont("product-sans.ttf", 37);
        productSans80 = getFont("product-sans.ttf", 40);
        productSans35 = getFont("product-sans.ttf", 18);
        productSans52 = getFont("product-sans.ttf", 26);
        productSans26 = getFont("product-sans.ttf", 13);
        productSans30 = getFont("product-sans.ttf", 14);
        productSans40 = getFont("product-sans.ttf", 20);
        productSans45 = getFont("product-sans.ttf", 23);
        productSans50 = getFont("product-sans.ttf", 25);
        productSans80 = getFont("product-sans.ttf", 40);
        notificationIcon70 = getFont("notification-icon.ttf", 35);
        posterama30 = getFont("posterama.ttf", 15);
        posterama35 = getFont("posterama.ttf", 18);
        posterama40 = getFont("posterama.ttf", 20);
        tenacity40 = getFont("tenacitybold.ttf", 40);
        tenacity80 = getFont("tenacitybold.ttf", 40);
        tenacitybold30 = getFont("tenacitybold.ttf", 15);
        tenacitybold35 = getFont("tenacitybold.ttf", 18);
        tenacitybold40 = getFont("tenacitybold.ttf", 20);
        tenacitybold43 = getFont("tenacitybold.ttf", 21);
        tenacitycheck60 = getFont("tenacitycheck.ttf", 30);
        flux = getFont("fluxicon.ttf", 18);
        flux45 = getFont("fluxicon.ttf", 23);
        sfbold100 = (new GameFontRenderer(getsfbold(100)));
        sfbold80 = (new GameFontRenderer(getsfbold(80)));
        sfbold40 = (new GameFontRenderer(getsfbold(40)));
        sfbold35 = (new GameFontRenderer(getsfbold(35)));
        sfbold30 = (new GameFontRenderer(getsfbold(30)));
        sfbold28 = (new GameFontRenderer(getsfbold(28)));
        sfbold12 = (new GameFontRenderer(getsfbold(15)));
        sfbold25 = (new GameFontRenderer(getsfbold(22)));
        nbicon18 = getFont("newicon.ttf", 18);
        nbicon20 = getFont("newicon.ttf", 23);
        nbicon40 = getFont("newicon.ttf", 40);
        nbicon45 = getFont("newicon.ttf", 45);
        misans35 = getFont("misans.ttf", 18);
        misans40 = getFont("misans.ttf", 20);
        misans30 = getFont("misans.ttf", 15);
        misans20 = getFont("misans.ttf", 10);
        misans25 = getFont("misans.ttf", 13);
        misans32 = getFont("misans.ttf", 16);
        misans40 = getFont("misans.ttf", 20);
        no40 = getFont("notion.ttf", 40);
        ClientUtils.getLogger().info("Loaded Fonts. (" + (System.currentTimeMillis() - l) + "ms)");
    }

    public static IFontRenderer getFontRenderer(final String name, final int size) {
        for (final Field field : Fonts.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);

                Object o = field.get(null);

                if (o instanceof IFontRenderer) {
                    FontDetails fontDetails = field.getAnnotation(FontDetails.class);

                    if (fontDetails.fontName().equals(name) && fontDetails.fontSize() == size)
                        return (IFontRenderer) o;
                }
            } catch (final IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return getFont("default", 35);
    }

    public static FontInfo getFontDetails(final IFontRenderer fontRenderer) {
        for (final Field field : Fonts.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);

                final Object o = field.get(null);

                System.out.println(field.get(null) == null);

                if (o.equals(fontRenderer)) {
                    final FontDetails fontDetails = field.getAnnotation(FontDetails.class);

                    return new FontInfo(fontDetails.fontName(), fontDetails.fontSize());
                }
            } catch (final IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    public static List<IFontRenderer> getFonts() {
        final List<IFontRenderer> fonts = new ArrayList<>();

        for (final Field fontField : Fonts.class.getDeclaredFields()) {
            try {
                fontField.setAccessible(true);

                final Object fontObj = fontField.get(null);

                if (fontObj instanceof IFontRenderer) fonts.add((IFontRenderer) fontObj);
            } catch (final IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return fonts;
    }
    private static Font gett(int size) {
        Font font;
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("tomk/font/tenacitybold.ttf")).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(0, size);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("Posterama", 0, size);
        }
        return font;
    }
    private static Font getsfbold(int size) {
        Font font;
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("tomk/font/fz.ttf")).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(0, size);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("Posterama", 0, size);
        }
        return font;
    }

    private static IFontRenderer getFont(final String fontName, final int size) {
        Font font;
        try {
            final InputStream inputStream = minecraft.getResourceManager().getResource(new ResourceLocation("tomk/font/" + fontName)).getInputStream();
            Font awtClientFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            awtClientFont = awtClientFont.deriveFont(Font.PLAIN, size);
            inputStream.close();
            font = awtClientFont;
        } catch (final Exception e) {
            e.printStackTrace();
            font = new Font("default", Font.PLAIN, size);
        }

        return classProvider.wrapFontRenderer(new GameFontRenderer(font));
    }

    public static class FontInfo {
        private final String name;
        private final int fontSize;

        public FontInfo(String name, int fontSize) {
            this.name = name;
            this.fontSize = fontSize;
        }

        public FontInfo(Font font) {
            this(font.getName(), font.getSize());
        }

        public String getName() {
            return name;
        }

        public int getFontSize() {
            return fontSize;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            FontInfo fontInfo = (FontInfo) o;

            if (fontSize != fontInfo.fontSize) return false;
            return Objects.equals(name, fontInfo.name);
        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + fontSize;
            return result;
        }
    }
}