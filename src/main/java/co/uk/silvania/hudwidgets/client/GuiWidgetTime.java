package co.uk.silvania.hudwidgets.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import org.lwjgl.opengl.GL11;

import co.uk.silvania.hudwidgets.HUDWidgets;
import co.uk.silvania.hudwidgets.HUDWidgetsConfig;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GuiWidgetTime extends GuiWidgetBase {

	public GuiWidgetTime(Minecraft mc) {
		super(mc);
	}
	
	private static final ResourceLocation guiStatsBar = new ResourceLocation(HUDWidgets.modid, "textures/gui/" + HUDWidgetsConfig.timeTextureStyle);
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderGamemode(RenderGameOverlayEvent.Pre event) {
		boolean enabled = true;		
		if (!HUDWidgetsConfig.timeEnabled) {
			enabled = false;
		}
		if (!HUDWidgetsConfig.renderTimeCreative && mc.thePlayer.capabilities.isCreativeMode) {
			enabled = false;
		}
		
		if (enabled) {
			FontRenderer font = mc.fontRenderer;
			
			double worldTime = mc.theWorld.getWorldInfo().getWorldTime();
			int hour = 0;
			int minute = 0;
			double storedTime = worldTime;
			
			while (worldTime > 24000) {
				worldTime = worldTime - 24000;
			}
			while (worldTime > 1000) {
				hour = hour + 1;
				worldTime = worldTime - 1000;
			}
			if (storedTime >= 24000) {
				storedTime = storedTime - 24000;
			}
			
			if (hour <= 17) {
				hour = hour + 6;
			} else {
				hour = hour - 18;
			}
			
			String time = "";
			if (HUDWidgetsConfig.timeStyle == 0) {
				if (hour == 0) {
					hour = 12;
				}
				if (hour > 12) {
					hour = hour - 12;
				}
				
				long hourTimer = mc.theWorld.getWorldInfo().getWorldTime();
				while (hourTimer > 24000) {
					hourTimer = hourTimer - 24000;
				}
				
				if (hourTimer < 6000 || hourTimer > 18000) {
					time = " AM";
				} else {
					time = " PM";
				}
			}
			
			minute = (int) calculateWorldMinutes(worldTime);
			if (minute >= 60) {
				minute = 0;
			}
			if (minute <= 0) {
				minute = 0;
			}
			String hours = "" + hour;
			String minutes = "" + minute;
			String divider = "";
			int min = 0;
			if (minute < 1) {
				min = 1;
			} else {
				min = minute;
			}
			if (minutes.length() <= 1) {
				minutes = "0" + (min - 1);
			}
			if (hours.length() <= 1) {
				hours = "0" + hour;
			}
			if (minute % 2 == 0) {
				divider = ":";
			} else {
				divider = " ";
			}
			

			
			double widthMultiplier = getResIncreaseMultiplier("x");
			double heightMultiplier = getResIncreaseMultiplier("y");
			
			int sizeX = 36;
			int sizeY = 20;
			
			if (HUDWidgetsConfig.timeStyle == 0) {
				sizeX = 36;
				sizeY = 28;
			} else if (HUDWidgetsConfig.timeStyle == 2) {
				sizeX = 36;
				sizeY = 36;
			}
			
			configX = calculateAnchorPointX(HUDWidgetsConfig.timeAnchor, sizeX);
			configY = calculateAnchorPointY(HUDWidgetsConfig.timeAnchor, sizeY);
			
			int xPos = configX + HUDWidgetsConfig.timeXOffset;
			int yPos = configY + HUDWidgetsConfig.timeYOffset;
			
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			mc.renderEngine.bindTexture(guiStatsBar);
			if (HUDWidgetsConfig.timeStyle == 0) {
				this.drawTexturedModalRect(xPos, yPos, 194, 224, sizeX, sizeY);
			} else if (HUDWidgetsConfig.timeStyle == 1) {
				this.drawTexturedModalRect(xPos, yPos, 158, 208, sizeX, sizeY);
			} else
				this.drawTexturedModalRect(xPos, yPos, 122, 208, sizeX, sizeY);
			font.drawString(hours, xPos + 5, yPos + 6, HUDWidgetsConfig.timeTextColour);
			font.drawString(divider, xPos + 17, yPos + 6, HUDWidgetsConfig.timeTextColour);
			font.drawString(minutes, xPos + 20, yPos + 6, HUDWidgetsConfig.timeTextColour);
			font.drawString(time, xPos + 9, yPos + 15, HUDWidgetsConfig.timeTextColour);
			GL11.glPopMatrix();
		}
	}
}
