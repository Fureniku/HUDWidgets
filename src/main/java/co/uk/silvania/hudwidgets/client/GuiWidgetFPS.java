package co.uk.silvania.hudwidgets.client;

import org.lwjgl.opengl.GL11;

import co.uk.silvania.hudwidgets.HUDWidgets;
import co.uk.silvania.hudwidgets.HUDWidgetsConfig;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class GuiWidgetFPS extends GuiWidgetBase {

	public GuiWidgetFPS(Minecraft mc) {
		super(mc);
	}
	
	long debugUpdateTime = Minecraft.getSystemTime();
	private static int debugFPS;
    int fpsCounter;
    long prevFrameTime = -1L;
	
	private static final ResourceLocation guiStatsBar = new ResourceLocation(HUDWidgets.modid, "textures/gui/" + HUDWidgetsConfig.fpsTextureStyle);
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderGamemode(RenderGameOverlayEvent.Pre event) {
		boolean enabled = true;		
		if (!HUDWidgetsConfig.fpsEnabled) {
			enabled = false;
		}
		if (!HUDWidgetsConfig.renderFPSCreative && mc.thePlayer.capabilities.isCreativeMode) {
			enabled = false;
		}
		
		if (enabled) {
			FontRenderer font = mc.fontRenderer;

			++this.fpsCounter;

	        while (Minecraft.getSystemTime() >= this.debugUpdateTime + 1000L) {
	        	setDebugFPS(this.fpsCounter);
	        	this.debugUpdateTime += 1000L;
	        	this.fpsCounter = 0;
	        }
			
			double widthMultiplier = getResIncreaseMultiplier("x");
			double heightMultiplier = getResIncreaseMultiplier("y");
			
			int sizeX = 79;
			int sizeY = 20;
			
			configX = calculateAnchorPointX(HUDWidgetsConfig.fpsAnchor, sizeX);
			configY = calculateAnchorPointY(HUDWidgetsConfig.fpsAnchor, sizeY);
			
			int xPos = configX + HUDWidgetsConfig.fpsXOffset;
			int yPos = configY + HUDWidgetsConfig.fpsYOffset;
			
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			mc.renderEngine.bindTexture(guiStatsBar);
			this.drawTexturedModalRect(xPos, yPos, 0, 228, sizeX, sizeY);

			font.drawString("FPS: " + fpsCounter, xPos + 5, yPos + 6, HUDWidgetsConfig.fpsTextColour);
			GL11.glPopMatrix();
		}
	}

	public static int getDebugFPS() {
		return debugFPS;
	}

	public static void setDebugFPS(int debugFPS) {
		GuiWidgetFPS.debugFPS = debugFPS;
	}
}
