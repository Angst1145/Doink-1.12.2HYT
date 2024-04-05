package net.ccbluex.liquidbounce.features.module.modules.donk;

import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.INetworkManager;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityOtherPlayerMP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.render.Breadcrumbs;
import net.ccbluex.liquidbounce.injection.backend.PacketImpl;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketConfirmTransaction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;


@ModuleInfo(name = "HytBlink", description = "sb blink.", category = ModuleCategory.DONK)
public final class HytBlink extends Module {
    private final LinkedBlockingQueue<IPacket> packets = new LinkedBlockingQueue<>();
    private final LinkedList<double[]> positions = new LinkedList<>();
    private final BoolValue pulseValue = new BoolValue("Pulse", false);
    private final BoolValue c0FValue = new BoolValue("C0FCancel", false);
    private final IntegerValue pulseDelayValue = new IntegerValue("PulseDelay", 1000, 500, 5000);
    private final MSTimer pulseTimer = new MSTimer();
    private IEntityOtherPlayerMP fakePlayer;
    private boolean disableLogger;

    public void onEnable() {
        IEntityPlayerSP iEntityPlayerSP = MinecraftInstance.mc.getThePlayer();
        if (iEntityPlayerSP == null) {
            return;
        }
        IEntityPlayerSP thePlayer = iEntityPlayerSP;
        if (!this.pulseValue.get()) {
            IWorldClient iWorldClient = MinecraftInstance.mc.getTheWorld();
            if (iWorldClient == null) {
                Intrinsics.throwNpe();
            }
            IEntityOtherPlayerMP faker = MinecraftInstance.classProvider.createEntityOtherPlayerMP(iWorldClient, thePlayer.getGameProfile());
            faker.setRotationYawHead(thePlayer.getRotationYawHead());
            faker.setRenderYawOffset(thePlayer.getRenderYawOffset());
            faker.copyLocationAndAnglesFrom(thePlayer);
            faker.setRotationYawHead(thePlayer.getRotationYawHead());
            IWorldClient iWorldClient2 = MinecraftInstance.mc.getTheWorld();
            if (iWorldClient2 == null) {
                Intrinsics.throwNpe();
            }
            iWorldClient2.addEntityToWorld(-1337, faker);
            this.fakePlayer = faker;
        }
        synchronized (this.positions) {
            this.positions.add(new double[]{thePlayer.getPosX(), thePlayer.getEntityBoundingBox().getMinY() + (double) (thePlayer.getEyeHeight() / (float) 2), thePlayer.getPosZ()});
            this.positions.add(new double[]{thePlayer.getPosX(), thePlayer.getEntityBoundingBox().getMinY(), thePlayer.getPosZ()});
        }
        this.pulseTimer.reset();
    }

    public void onDisable() {
        if (MinecraftInstance.mc.getThePlayer() == null) {
            return;
        }
        this.blink();
        IEntityOtherPlayerMP faker = this.fakePlayer;
        if (faker != null) {
            IWorldClient iWorldClient = MinecraftInstance.mc.getTheWorld();
            if (iWorldClient != null) {
                iWorldClient.removeEntityFromWorld(faker.getEntityId());
            }
            this.fakePlayer = null;
        }
    }

    @EventTarget
    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IPacket packet = event.getPacket();
        IPacket $this$unwrap$iv = event.getPacket();
        Packet e = ((PacketImpl<?>) $this$unwrap$iv).getWrapped();
        if (MinecraftInstance.mc.getThePlayer() == null || this.disableLogger) {
            return;
        }
        if (MinecraftInstance.classProvider.isCPacketPlayer(packet)) {
            event.cancelEvent();
        }
        if (MinecraftInstance.classProvider.isCPacketPlayerPosition(packet) || MinecraftInstance.classProvider.isCPacketPlayerPosLook(packet) || MinecraftInstance.classProvider.isCPacketPlayerBlockPlacement(packet) || MinecraftInstance.classProvider.isCPacketAnimation(packet) || MinecraftInstance.classProvider.isCPacketEntityAction(packet) || MinecraftInstance.classProvider.isCPacketUseEntity(packet) || this.c0FValue.get().booleanValue() && e instanceof CPacketConfirmTransaction) {
            event.cancelEvent();
            this.packets.add(packet);
        }
    }

    @EventTarget
    public void onUpdate(@Nullable UpdateEvent event) {
        IEntityPlayerSP iEntityPlayerSP = MinecraftInstance.mc.getThePlayer();
        if (iEntityPlayerSP == null) {
            return;
        }
        synchronized (this.positions) {
            this.positions.add(new double[]{iEntityPlayerSP.getPosX(), iEntityPlayerSP.getEntityBoundingBox().getMinY(), iEntityPlayerSP.getPosZ()});
        }
        if (this.pulseValue.get() && this.pulseTimer.hasTimePassed(((Number) this.pulseDelayValue.get()).intValue())) {
            this.blink();
            this.pulseTimer.reset();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @EventTarget
    public void onRender3D(@Nullable Render3DEvent event) {
        Breadcrumbs breadcrumbs;
        Breadcrumbs breadcrumbs2 = breadcrumbs = (Breadcrumbs) LiquidBounce.INSTANCE.getModuleManager().getModule(Breadcrumbs.class);
        Color color = breadcrumbs2.getColorRainbow().get() ? ColorUtils.rainbow() : new Color(((Number) breadcrumbs.getColorRedValue().get()).intValue(), ((Number) breadcrumbs.getColorGreenValue().get()).intValue(), ((Number) breadcrumbs.getColorBlueValue().get()).intValue());
        synchronized (this.positions) {
            GL11.glPushMatrix();
            GL11.glDisable(3553);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(2848);
            GL11.glEnable(3042);
            GL11.glDisable(2929);
            MinecraftInstance.mc.getEntityRenderer().disableLightmap();
            GL11.glBegin(3);
            RenderUtils.glColor(color);
            double renderPosX = MinecraftInstance.mc.getRenderManager().getViewerPosX();
            double renderPosY = MinecraftInstance.mc.getRenderManager().getViewerPosY();
            double renderPosZ = MinecraftInstance.mc.getRenderManager().getViewerPosZ();
            for (double[] pos : this.positions) {
                GL11.glVertex3d(pos[0] - renderPosX, pos[1] - renderPosY, pos[2] - renderPosZ);
            }
            GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
            GL11.glEnd();
            GL11.glEnable(2929);
            GL11.glDisable(2848);
            GL11.glDisable(3042);
            GL11.glEnable(3553);
            GL11.glPopMatrix();
        }
    }

    @NotNull
    public String getTag() {
        return String.valueOf(this.packets.size());
    }

    private void blink() {
        try {
            this.disableLogger = true;
            while (!this.packets.isEmpty()) {
                INetworkManager iNetworkManager = MinecraftInstance.mc.getNetHandler().getNetworkManager();
                IPacket iPacket = this.packets.take();
                Intrinsics.checkExpressionValueIsNotNull(iPacket, "packets.take()");
                iNetworkManager.sendPacket(iPacket);
            }
            this.disableLogger = false;
        } catch (Exception e) {
            e.printStackTrace();
            this.disableLogger = false;
        }
        synchronized (this.positions) {
            this.positions.clear();
        }
    }
}
