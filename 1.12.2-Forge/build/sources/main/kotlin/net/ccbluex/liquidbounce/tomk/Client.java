package net.ccbluex.liquidbounce.tomk;

import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;

import net.ccbluex.liquidbounce.features.module.modules.color.CustomColor;
import net.ccbluex.liquidbounce.utils.novoline.ScaleUtils;
import net.minecraft.network.handshake.client.C00Handshake;
import org.lwjgl.opengl.GL11;
import java.awt.*;
import java.io.IOException;

import static net.ccbluex.liquidbounce.utils.novoline.ScaleUtils.*;


public enum Client {
    instance;
    public final static String name = "KingSense";
    public static String title;
    public long startTime = System.currentTimeMillis();



    public static void drawOutline(float x,float y,float width,float height,float radius,float line,float offset){
        enableRender2D();
        GL11.glLineWidth(line);
        GL11.glBegin(3);
        float edgeRadius = radius;
        float centerX = x + edgeRadius;
        float centerY = y + edgeRadius;
        int vertices = (int) Math.min(Math.max(edgeRadius, 10.0F), 90.0F);
        int i;
        int colorI = 0;
        double angleRadians;
        centerX = width;
        centerY = height + edgeRadius;
        vertices = (int) Math.min(Math.max(edgeRadius, 10.0F), 90.0F);
        for (i = 0; i <= vertices; ++i) {
            ScaleUtils.setColor(fadeBetween(new Color(CustomColor.r.get().intValue(),CustomColor.g.get().intValue(),CustomColor.b.get().intValue()).getRGB(),new Color(CustomColor.r2.get().intValue(),CustomColor.g2.get().intValue(),CustomColor.b2.get().intValue()).getRGB(),(long) 10L * colorI));
            angleRadians = 6.283185307179586D * (double) (i) / (double) (vertices * 4);
            GL11.glVertex2d((double) centerX + Math.sin(angleRadians) * (double) edgeRadius, (double) centerY + Math.cos(angleRadians) * (double) edgeRadius);
            colorI++;
        }

        GL11.glEnd();
        GL11.glLineWidth(line);
        GL11.glBegin(3);
        centerX = width + edgeRadius;
        centerY = height + edgeRadius;
        for (i = 0; i <= (height - y); ++i) {
            ScaleUtils.setColor(fadeBetween(new Color(CustomColor.r.get().intValue(),CustomColor.g.get().intValue(),CustomColor.b.get().intValue()).getRGB(),new Color(CustomColor.r2.get().intValue(),CustomColor.g2.get().intValue(),CustomColor.b2.get().intValue()).getRGB(),(long) 10L * colorI));
            GL11.glVertex2d(centerX,centerY -  i);
            colorI++;
        }
        GL11.glEnd();
        GL11.glLineWidth(line);
        GL11.glBegin(3);
        centerX = width;
        centerY = (y) + edgeRadius;
        for (i = 0; i <= vertices; ++i) {
            ScaleUtils.setColor(fadeBetween(new Color(CustomColor.r.get().intValue(),CustomColor.g.get().intValue(),CustomColor.b.get().intValue()).getRGB(),new Color(CustomColor.r2.get().intValue(),CustomColor.g2.get().intValue(),CustomColor.b2.get().intValue()).getRGB(),(long) 10L * colorI));
            angleRadians = 6.283185307179586D * (double) (i + 90) / (double) (vertices * 4);
            GL11.glVertex2d((double) centerX + Math.sin(angleRadians) * (double) edgeRadius, (double) centerY + Math.cos(angleRadians) * (double) edgeRadius);
            colorI++;
        }
        GL11.glEnd();
        GL11.glLineWidth(line);
        GL11.glBegin(3);
        centerX = width;
        centerY = (y);
        for (i = 0; i <= (width - x); ++i) {
            ScaleUtils.setColor(fadeBetween(new Color(CustomColor.r.get().intValue(),CustomColor.g.get().intValue(),CustomColor.b.get().intValue()).getRGB(),new Color(CustomColor.r2.get().intValue(),CustomColor.g2.get().intValue(),CustomColor.b2.get().intValue()).getRGB(),(long) 10L * colorI));
            GL11.glVertex2d(centerX - i,centerY);
            colorI++;
        }
        GL11.glEnd();
        GL11.glLineWidth(line);
        GL11.glBegin(3);
        centerX = x;
        centerY = (y + edgeRadius);
        for (i = 0; i <= vertices; ++i) {
            angleRadians = 6.283185307179586D * (double) (i+180) / (double) (vertices * 4);
            GL11.glVertex2d((double) centerX + Math.sin(angleRadians) * (double) edgeRadius, (double) centerY + Math.cos(angleRadians) * (double) edgeRadius);
            colorI++;
        }
        colorI = 0;
        GL11.glEnd();
        GL11.glLineWidth(line);
        GL11.glBegin(3);
        centerX = width;
        centerY = (height + vertices + offset);
        for (i = 0; i <= (width - x); ++i) {
            ScaleUtils.setColor(fadeBetween(new Color(CustomColor.r.get().intValue(),CustomColor.g.get().intValue(),CustomColor.b.get().intValue()).getRGB(),new Color(CustomColor.r2.get().intValue(),CustomColor.g2.get().intValue(),CustomColor.b2.get().intValue()).getRGB(),(long) 10L * colorI));
            GL11.glVertex2d(centerX - i,centerY);
            colorI++;
        }
        GL11.glEnd();
        GL11.glLineWidth(line);
        GL11.glBegin(3);
        centerX = x;
        centerY = (height + edgeRadius);
        for (i = 0; i <= vertices; ++i) {
            ScaleUtils.setColor(fadeBetween(new Color(CustomColor.r.get().intValue(),CustomColor.g.get().intValue(),CustomColor.b.get().intValue()).getRGB(),new Color(CustomColor.r2.get().intValue(),CustomColor.g2.get().intValue(),CustomColor.b2.get().intValue()).getRGB(),(long) 10L * colorI));
            angleRadians = 6.283185307179586D * (double) (i + 180) / (double) (vertices * 4);
            GL11.glVertex2d((double) centerX + Math.sin(angleRadians) * (double) edgeRadius, (double) centerY - Math.cos(angleRadians) * (double) edgeRadius);
            colorI++;
        }
        GL11.glEnd();
        GL11.glLineWidth(line);
        GL11.glBegin(3);
        centerX = x - edgeRadius;
        centerY = (height + edgeRadius);

        for (i = 0; i <= (height - y); ++i) {
            ScaleUtils.setColor(fadeBetween(new Color(CustomColor.r.get().intValue(),CustomColor.g.get().intValue(),CustomColor.b.get().intValue()).getRGB(),new Color(CustomColor.r2.get().intValue(),CustomColor.g2.get().intValue(),CustomColor.b2.get().intValue()).getRGB(),(long) 10L * colorI));
            GL11.glVertex2d(centerX,centerY -  i);
            colorI++;
        }
        GL11.glEnd();
        disableRender2D();
    }

    @EventTarget
    private void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof C00Handshake){
            startTime = System.currentTimeMillis();
        }
    }
}
