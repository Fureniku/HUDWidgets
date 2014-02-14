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

public class GuiWidgetHealth extends GuiWidgetBase {

	public GuiWidgetHealth(Minecraft mc) {
		super(mc);
	}

	private static final ResourceLocation guiStatsBar = new ResourceLocation(HUDWidgets.modid, "textures/gui/" + config.healthTextureStyle);

	@ForgeSubscribe(priority = EventPriority.NORMAL)
	public void onRenderGui(RenderGameOverlayEvent event) {
		boolean enabled = true;		
		if (!config.healthEnabled) {
			enabled = false;
		}
		if (mc.thePlayer.capabilities.isCreativeMode && !config.renderHealthCreative) {
			enabled = false;
		}
		
		if (enabled) {
			FontRenderer font = mc.fontRenderer;
			
			float health = mc.thePlayer.getHealth();
			float maxHealth = mc.thePlayer.getMaxHealth();
			int healthAmount = (int) Math.round((200 / maxHealth) * health);
			int armourAmount = (158 / 20) * mc.thePlayer.getTotalArmorValue();
			int oxygenAmount = (158 / 20) * mc.thePlayer.getAir();
			
			double widthMultiplier = getResIncreaseMultiplier("x");
			double heightMultiplier = getResIncreaseMultiplier("y");
			
			int sizeX = 204;
			int sizeY = 20;
			
			if (config.healthAnchor == 0 || config.healthAnchor > 8) {
				configX = (int) Math.round(config.healthXPos * widthMultiplier);
				configY = (int) Math.round(config.healthYPos * heightMultiplier);
			} else {
				configX = calculateAnchorPointX(config.healthAnchor, sizeX);
				configY = calculateAnchorPointY(config.healthAnchor, sizeY);
			}
			
			int xPos = configX + config.healthXOffset;
			int yPos = configY + config.healthYOffset;
	
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);;
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			mc.renderEngine.bindTexture(guiStatsBar);
			
			this.drawTexturedModalRect(xPos, yPos, 0, 108, sizeX, sizeY);
			this.drawTexturedModalRect(xPos + 2, yPos + 2, 0, 60, healthAmount, 16);
			this.drawTexturedModalRect(xPos, yPos, 0, 0, sizeX, sizeY);
			
			if (config.armourBarStyle == 2) {
				if (config.alwaysRenderArmour || mc.thePlayer.getTotalArmorValue() > 0) {
					this.drawTexturedModalRect(xPos + 23, yPos + 2, 0, 190, armourAmount, 6);
				}
			}
			
			if (config.oxygenBarStyle == 2) {
				if (config.alwaysRenderOxygen || mc.thePlayer.getAir() < 300) {
					this.drawTexturedModalRect(xPos + 23, yPos + 2, 0, 196, oxygenAmount, 6);
				}
			}

			this.mc.renderEngine.bindTexture(statIcons);
			this.drawTexturedModalRect(Math.round(xPos / 2) + 3, Math.round(yPos / 2) + 13, 0, 0, 19, 19);
			
			if (config.healthText) {
				GL11.glScalef(2.0F, 2.0F, 2.0F);
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				font.drawStringWithShadow("Health: " + health + "/" + maxHealth, xPos + 22, yPos + 6, config.healthTextColour);
			}
			GL11.glPopMatrix();
		}
	}
}
