package co.uk.silvania.hudwidgets.client;

import org.lwjgl.opengl.GL11;

import co.uk.silvania.hudwidgets.HUDWidgets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;

public class GuiWidgetTime extends GuiWidgetBase {

	public GuiWidgetTime(Minecraft mc) {
		super(mc);
	}
	
	private static final ResourceLocation guiStatsBar = new ResourceLocation(HUDWidgets.modid, "textures/gui/" + config.timeTextureStyle);
	
	@ForgeSubscribe(priority = EventPriority.NORMAL)
	public void onRenderGamemode(RenderGameOverlayEvent.Pre event) {
		boolean enabled = true;		
		if (!config.timeEnabled) {
			enabled = false;
		}
		if (!config.renderTimeCreative && mc.thePlayer.capabilities.isCreativeMode) {
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
			if (config.timeStyle == 0) {
				if (hour == 0) {
					hour = 12;
				}
				if (hour > 12) {
					hour = hour - 12;
				}
				if (worldTime < 6000 || worldTime > 18000) {
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
			if (minutes.length() <= 1) {
				minutes = "0" + (minute - 1);
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
			
			if (config.timeStyle == 0) {
				sizeX = 36;
				sizeY = 28;
			} else if (config.timeStyle == 2) {
				sizeX = 36;
				sizeY = 36;
			}
			
			if (config.timeAnchor == 0 || config.timeAnchor > 8) {
				configX = (int) Math.round(config.timeXPos * widthMultiplier);
				configY = (int) Math.round(config.timeYPos * heightMultiplier);
			} else {
				configX = calculateAnchorPointX(config.timeAnchor, sizeX);
				configY = calculateAnchorPointY(config.timeAnchor, sizeY);
			}
			
			int xPos = configX + config.timeXOffset;
			int yPos = configY + config.timeYOffset;
			
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			mc.renderEngine.bindTexture(guiStatsBar);
			if (config.timeStyle == 0) {
				this.drawTexturedModalRect(xPos, yPos, 194, 224, sizeX, sizeY);
			} else if (config.timeStyle == 1) {
				this.drawTexturedModalRect(xPos, yPos, 158, 208, sizeX, sizeY);
			} else
				this.drawTexturedModalRect(xPos, yPos, 122, 208, sizeX, sizeY);
			font.drawString(hours, xPos + 5, yPos + 6, config.timeTextColour);
			font.drawString(divider, xPos + 17, yPos + 6, config.timeTextColour);
			font.drawString(minutes, xPos + 20, yPos + 6, config.timeTextColour);
			font.drawString(time, xPos + 9, yPos + 15, config.timeTextColour);
			GL11.glPopMatrix();
		}
	}
}
