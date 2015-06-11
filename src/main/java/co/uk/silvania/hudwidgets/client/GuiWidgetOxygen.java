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

public class GuiWidgetOxygen extends GuiWidgetBase {

	public GuiWidgetOxygen(Minecraft mc) {
		super(mc);
	}
	
	private static final ResourceLocation guiStatsBar = new ResourceLocation(HUDWidgets.modid, "textures/gui/" + HUDWidgetsConfig.oxygenTextureStyle);
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderGui(RenderGameOverlayEvent.Pre event) {
		boolean enabled = true;
		int oxygen = mc.thePlayer.getAir();
		if (!HUDWidgetsConfig.oxygenEnabled || HUDWidgetsConfig.oxygenBarStyle >= 2) {
			enabled = false;
		}

		if (mc.thePlayer.capabilities.isCreativeMode && !HUDWidgetsConfig.renderOxygenCreative) {
			enabled = false;
		}
		
		if (!HUDWidgetsConfig.alwaysRenderOxygen && oxygen >= 300) {
			enabled = false;
		}
		
		if (enabled) {
			FontRenderer font = mc.fontRenderer;
			
			double widthMultiplier = getResIncreaseMultiplier("x");
			double heightMultiplier = getResIncreaseMultiplier("y");
			
			int sizeX = 204;
			int sizeY = 20;
			int oxyBar = 0;
			
			if (HUDWidgetsConfig.oxygenBarStyle == 1) {
				sizeX = 79;
				oxyBar = Math.round(oxygen / 4);
			} else if (HUDWidgetsConfig.oxygenBarStyle == 0) {
				oxyBar = Math.round((oxygen / 3) * 2);
			}
			
			configX = calculateAnchorPointX(HUDWidgetsConfig.oxygenAnchor, sizeX);
			configY = calculateAnchorPointY(HUDWidgetsConfig.oxygenAnchor, sizeY);
			
			int xPos = configX + HUDWidgetsConfig.oxygenXOffset;
			int yPos = configY + HUDWidgetsConfig.oxygenYOffset;
	
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);;
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			mc.renderEngine.bindTexture(guiStatsBar);
			
			if (HUDWidgetsConfig.oxygenBarStyle == 1) {
				this.drawTexturedModalRect(xPos, yPos, 0, 188, sizeX, sizeY);
				this.drawTexturedModalRect(xPos + 2, yPos + 2, 0, 168, oxyBar, 16);
				this.drawTexturedModalRect(xPos, yPos, 0, 148, sizeX, sizeY);
			} else {
				this.drawTexturedModalRect(xPos, yPos, 0, 108, sizeX, sizeY);
				this.drawTexturedModalRect(xPos + 2, yPos + 2, 0, 60, oxyBar, 16);
				this.drawTexturedModalRect(xPos, yPos, 0, 0, sizeX, sizeY);
			}
			
			this.mc.renderEngine.bindTexture(hudStatIcons);
			this.drawTexturedModalRect(xPos + 2, yPos + 1, 54, 0, 18, 18);
			
			if (HUDWidgetsConfig.oxygenText) {
				String oxy = "Oxygen: ";
				if (HUDWidgetsConfig.oxygenBarStyle == 1) {
					oxy = "";
				}
				font.drawStringWithShadow(oxy + (Math.round(oxygen / 20) + 1) + "/" + 15, xPos + 22, yPos + 6, HUDWidgetsConfig.healthTextColour);
			}
			
			GL11.glPopMatrix();
		}
	}
}
