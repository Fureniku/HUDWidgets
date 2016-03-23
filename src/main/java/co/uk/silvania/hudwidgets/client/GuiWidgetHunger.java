package co.uk.silvania.hudwidgets.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import org.lwjgl.opengl.GL11;

import co.uk.silvania.hudwidgets.HUDWidgets;
import co.uk.silvania.hudwidgets.HUDWidgetsConfig;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GuiWidgetHunger extends GuiWidgetBase {
	
	public GuiWidgetHunger(Minecraft mc) {
		super(mc);
	}
	
	private static final ResourceLocation guiStatsBar = new ResourceLocation(HUDWidgets.modid, "textures/gui/" + HUDWidgetsConfig.hungerTextureStyle);

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderGui(RenderGameOverlayEvent.Pre event) {
		boolean enabled = true;
		if (!HUDWidgetsConfig.hungerEnabled) {
			enabled = false;
		}
		if (mc.thePlayer.capabilities.isCreativeMode && !HUDWidgetsConfig.renderHungerCreative) {
			enabled = false;
		}
		
		if (enabled) {
			FontRenderer font = mc.fontRenderer;
			
			int hunger = mc.thePlayer.getFoodStats().getFoodLevel();
			int maxHunger = 20;
			
			int hungerAmount = (200 / maxHunger) * hunger;
			
			double widthMultiplier = getResIncreaseMultiplier("x");
			double heightMultiplier = getResIncreaseMultiplier("y");
			
			int sizeX = 204;
			int sizeY = 20;
			
			configX = calculateAnchorPointX(HUDWidgetsConfig.hungerAnchor, sizeX);
			configY = calculateAnchorPointY(HUDWidgetsConfig.hungerAnchor, sizeY);
			
			int xPos = configX + HUDWidgetsConfig.hungerXOffset;
			int yPos = configY + HUDWidgetsConfig.hungerYOffset;
			
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			mc.renderEngine.bindTexture(guiStatsBar);
			
			this.drawTexturedModalRect(xPos, yPos, 0, 128, sizeX, sizeY);
			this.drawTexturedModalRect(xPos + 2, yPos + 2, 0, 76, hungerAmount, 16);
			this.drawTexturedModalRect(xPos, yPos, 0, 20, sizeX, sizeY);
			
			this.mc.renderEngine.bindTexture(hudStatIcons);
			this.drawTexturedModalRect(xPos + 2, yPos + 1, 18, 0, 18, 18);
			
			if (HUDWidgetsConfig.hungerText) {
				font.drawStringWithShadow("Hunger: " + hunger + "/" + maxHunger, xPos + 22, yPos + 6, HUDWidgetsConfig.hungerTextColour);
			}
			GL11.glPopMatrix();
		}
	}
}
