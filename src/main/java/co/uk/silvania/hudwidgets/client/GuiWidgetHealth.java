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

public class GuiWidgetHealth extends GuiWidgetBase {
	
	public GuiWidgetHealth(Minecraft mc) {
		super(mc);
	}

	boolean debugMode = this.mc.gameSettings.showDebugInfo;
	private static final ResourceLocation guiStatsBar = new ResourceLocation(HUDWidgets.modid, "textures/gui/" + HUDWidgetsConfig.healthTextureStyle);

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderGui(RenderGameOverlayEvent.Pre event) {
		boolean enabled = true;		
		if (!HUDWidgetsConfig.healthEnabled) {
			enabled = false;
		}
		if (mc.thePlayer.capabilities.isCreativeMode && !HUDWidgetsConfig.renderHealthCreative) {
			enabled = false;
		}
		if (debugMode) {
			enabled = false;
		}
		
		if (enabled) {
			FontRenderer font = mc.fontRenderer;
			
			double health = Math.round(mc.thePlayer.getHealth());
			double maxHealth = Math.round(mc.thePlayer.getMaxHealth());
			double healthAmount = Math.round((200 / maxHealth) * health);
			int armourAmount = (158 / 75) * mc.thePlayer.getTotalArmorValue();
			int oxygenAmount = Math.round(mc.thePlayer.getAir() / 2);
			
			double widthMultiplier = getResIncreaseMultiplier("x");
			double heightMultiplier = getResIncreaseMultiplier("y");
			
			int sizeX = 204;
			int sizeY = 20;
			
			if (HUDWidgetsConfig.healthAnchor == 0 || HUDWidgetsConfig.healthAnchor > 8) {
				configX = (int) Math.round(HUDWidgetsConfig.healthXPos * widthMultiplier);
				configY = (int) Math.round(HUDWidgetsConfig.healthYPos * heightMultiplier);
			} else {
				configX = calculateAnchorPointX(HUDWidgetsConfig.healthAnchor, sizeX);
				configY = calculateAnchorPointY(HUDWidgetsConfig.healthAnchor, sizeY);
			}
			
			int xPos = configX + HUDWidgetsConfig.healthXOffset;
			int yPos = configY + HUDWidgetsConfig.healthYOffset;
	
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			mc.renderEngine.bindTexture(guiStatsBar);
			
			this.drawTexturedModalRect(xPos, yPos, 0, 108, sizeX, sizeY);
			this.drawTexturedModalRect(xPos + 2, yPos + 2, 0, 60, (int) Math.round(healthAmount), 16);
			this.drawTexturedModalRect(xPos, yPos, 0, 0, sizeX, sizeY);
			
			if (HUDWidgetsConfig.armourBarStyle == 2) {
				if (HUDWidgetsConfig.alwaysRenderArmour || mc.thePlayer.getTotalArmorValue() > 0) {
					this.drawTexturedModalRect(xPos + 23, yPos + 2, 0, 190, armourAmount, 6);
				}
			}
			
			if (HUDWidgetsConfig.oxygenBarStyle == 2) {
				if (mc.thePlayer.getAir() < 300) {
					this.drawTexturedModalRect(xPos + 27, yPos + 2, 0, 196, oxygenAmount, 6);
					this.drawTexturedModalRect(xPos + 25, yPos + 2, 0, 210, 154, 8);
				}
			}

			this.mc.renderEngine.bindTexture(hudStatIcons);
			this.drawTexturedModalRect(xPos + 2, yPos + 1, 0, 0, 18, 18);
			
			if (HUDWidgetsConfig.healthText) {
				font.drawStringWithShadow("Health: " + health + "/" + maxHealth, xPos + 22, yPos + 6, HUDWidgetsConfig.healthTextColour);
			}
			GL11.glPopMatrix();

		}
	}
}
