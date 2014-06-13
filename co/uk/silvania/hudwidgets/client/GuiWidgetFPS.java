package co.uk.silvania.hudwidgets.client;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import co.uk.silvania.cities.econ.EconUtils;
import co.uk.silvania.hudwidgets.HUDWidgets;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.network.MemoryConnection;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;

public class GuiWidgetFPS extends GuiWidgetBase {

	public GuiWidgetFPS(Minecraft mc) {
		super(mc);
	}
	
	long debugUpdateTime = mc.getSystemTime();
	private static int debugFPS;
    int fpsCounter;
    long prevFrameTime = -1L;
	
	private static final ResourceLocation guiStatsBar = new ResourceLocation(HUDWidgets.modid, "textures/gui/" + config.fpsTextureStyle);
	
	@ForgeSubscribe(priority = EventPriority.NORMAL)
	public void onRenderGamemode(RenderGameOverlayEvent.Pre event) {
		boolean enabled = true;		
		if (!config.fpsEnabled) {
			enabled = false;
		}
		if (!config.renderFPSCreative && mc.thePlayer.capabilities.isCreativeMode) {
			enabled = false;
		}
		
		if (enabled) {
			FontRenderer font = mc.fontRenderer;

			++this.fpsCounter;

	        while (mc.getSystemTime() >= this.debugUpdateTime + 1000L) {
	        	debugFPS = this.fpsCounter;
	        	this.debugUpdateTime += 1000L;
	        	this.fpsCounter = 0;
	        }
			
			double widthMultiplier = getResIncreaseMultiplier("x");
			double heightMultiplier = getResIncreaseMultiplier("y");
			
			int sizeX = 79;
			int sizeY = 20;
			
			if (config.fpsAnchor == 0 || config.fpsAnchor > 8) {
				configX = (int) Math.round(config.fpsXPos * widthMultiplier);
				configY = (int) Math.round(config.fpsYPos * heightMultiplier);
			} else {
				configX = calculateAnchorPointX(config.fpsAnchor, sizeX);
				configY = calculateAnchorPointY(config.fpsAnchor, sizeY);
			}
			
			int xPos = configX + config.fpsXOffset;
			int yPos = configY + config.fpsYOffset;
			
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			mc.renderEngine.bindTexture(guiStatsBar);
			this.drawTexturedModalRect(xPos, yPos, 0, 228, sizeX, sizeY);

			font.drawString("FPS: " + fpsCounter, xPos + 5, yPos + 6, config.fpsTextColour);
			GL11.glPopMatrix();
		}
	}
}
