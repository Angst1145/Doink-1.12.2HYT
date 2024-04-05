package tomk.utils.flux;

import com.google.common.collect.Lists;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

import java.lang.reflect.Field;
import java.util.List;
public class BlurBuffer{

    private static ShaderGroup blurShader;
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static Framebuffer buffer;
    private static float lastScale;
    private static int lastScaleWidth;
    private static int lastScaleHeight;
    private static ResourceLocation shader = new ResourceLocation("shaders/post/blur.json");
    private static TimeHelper updateTimer = new TimeHelper();
    private static List<Shader> listShaders = Lists.newArrayList();
    public static void init() {
        try {
            buffer = new Framebuffer(mc.displayWidth, mc.displayHeight, true);
            buffer.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
            blurShader = new ShaderGroup(mc.getTextureManager(), mc.getResourceManager(), buffer, shader);
            blurShader.createBindFramebuffers(mc.displayWidth, mc.displayHeight);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void setShaderConfigs(float intensity, float blurWidth, float blurHeight) {
        Field field = null;
        try {
            Class clazz = Class.forName("net.minecraft.client.shader.ShaderGroup");
            Field[] fields = clazz.getDeclaredFields();
            for (int i=0;i<=4;i++) {
                if (i==3) {
                    field = (Field) clazz.getDeclaredField(fields[i].getName());
                }
            }//"field_148031_d" 混淆过后的listShaders
            field.setAccessible(true);
            listShaders = (List<Shader>) field.get(blurShader);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
/*	 try {
			Class clazz = Class.forName("net.minecraft.client.shader.ShaderGroup");
			Field[] fields = clazz.getDeclaredFields();
			for (Field field1 : fields){
				ClientUtils.displayChatMessage(field1.getName());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}*/

        listShaders.get(0).getShaderManager().getShaderUniform("Radius").set(intensity);
        listShaders.get(1).getShaderManager().getShaderUniform("Radius").set(intensity);
        listShaders.get(0).getShaderManager().getShaderUniform("BlurDir").set(blurWidth, blurHeight);
        listShaders.get(1).getShaderManager().getShaderUniform("BlurDir").set(blurHeight, blurWidth);
    }

    public static void blurArea(float x, float y, float width, float height, boolean setupOverlay) {
        ScaledResolution scale = new ScaledResolution(mc);
        float factor = scale.getScaleFactor();
        int factor2 = scale.getScaledWidth();
        int factor3 = scale.getScaledHeight();
        if (lastScale != factor || lastScaleWidth != factor2 || lastScaleHeight != factor3 || buffer == null || blurShader == null) {
            init();
        }
        lastScale = factor;
        lastScaleWidth = factor2;
        lastScaleHeight = factor3;

        // 渲染
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        RenderUtils.doGlScissor((int) x, (int) y, (int) width, (int) height);
        GL11.glPushMatrix();
        buffer.framebufferRenderExt(mc.displayWidth,  mc.displayHeight, true);
        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_SCISSOR_TEST);

        if (setupOverlay) {
            mc.entityRenderer.setupOverlayRendering();
        }

        GlStateManager.enableDepth();
    }

    public static void blurRoundArea(float x, float y, float width, float height, float roundRadius, boolean setupOverlay) {
        ScaledResolution scale = new ScaledResolution(mc);
        float factor = scale.getScaleFactor();
        int factor2 = scale.getScaledWidth();
        int factor3 = scale.getScaledHeight();

        if (lastScale != factor || lastScaleWidth != factor2 || lastScaleHeight != factor3 || buffer == null || blurShader == null) {
            init();
        }

        lastScale = factor;
        lastScaleWidth = factor2;
        lastScaleHeight = factor3;

        // 渲染
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        RenderUtils.doGlScissor((int) x, (int) y, (int) width, (int) height);
        GL11.glPushMatrix();
        buffer.framebufferRenderExt(mc.displayWidth, mc.displayHeight, true);
        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_SCISSOR_TEST);

        if (setupOverlay) {
            mc.entityRenderer.setupOverlayRendering();
        }

        GlStateManager.enableDepth();
    }

    public static void updateBlurBuffer(boolean setupOverlay) {
        if (blurShader != null) {
            mc.getFramebuffer().unbindFramebuffer();

            setShaderConfigs(20f, 0f, 1f);
            buffer.bindFramebuffer(true);

            mc.getFramebuffer().framebufferRenderExt(mc.displayWidth, mc.displayHeight, true);

            if (OpenGlHelper.shadersSupported) {
                GlStateManager.matrixMode(5890);
                GlStateManager.pushMatrix();
                GlStateManager.loadIdentity();
            //    blurShader.loadShaderGroup(mc.timer.renderPartialTicks);
                GlStateManager.popMatrix();
            }

            buffer.unbindFramebuffer();
            mc.getFramebuffer().bindFramebuffer(true);

            if (mc.getFramebuffer() != null && mc.getFramebuffer().depthBuffer > -1) {
                setupFBO(mc.getFramebuffer());
                mc.getFramebuffer().depthBuffer = -1;
            }

            if (setupOverlay) {
                mc.entityRenderer.setupOverlayRendering();
            }
        }
    }

    public static void setupFBO(Framebuffer fbo) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.depthBuffer);
        int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT(36161, stencil_depth_buffer_ID);
        EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, mc.displayWidth, mc.displayHeight);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencil_depth_buffer_ID);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencil_depth_buffer_ID);
    }
}
