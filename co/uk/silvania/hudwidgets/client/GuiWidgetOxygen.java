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

public class GuiWidgetOxygen extends GuiWidgetBase {

	public GuiWidgetOxygen(Minecraft mc) {
		super(mc);
	}
	
	private static final ResourceLocation vanillaIcons = new ResourceLocation("textures/gui/icons.png");
	private static final ResourceLocation guiStatsBar = new ResourceLocation(HUDWidgets.modid, "textures/gui/" + config.oxygenTextureStyle);
	
	@ForgeSubscribe(priority = EventPriority.NORMAL)
	public void onRenderGui(RenderGameOverlayEvent.Pre event) {
		boolean enabled = true;
		int oxygen = mc.thePlayer.getAir();
		if (!config.oxygenEnabled || config.oxygenBarStyle >= 2) {
			enabled = false;
		}

		if (mc.thePlayer.capabilities.isCreativeMode && !config.renderOxygenCreative) {
			enabled = false;
		}
		
		if (!config.alwaysRenderOxygen && oxygen >= 300) {
			enabled = false;
		}
		
		if (enabled) {
			FontRenderer font = mc.fontRenderer;
			
			double widthMultiplier = getResIncreaseMultiplier("x");
			double heightMultiplier = getResIncreaseMultiplier("y");
			
			int sizeX = 204;
			int sizeY = 20;
			int oxyBar = 0;
			
			if (config.oxygenBarStyle == 1) {
				sizeX = 79;
				oxyBar = Math.round(oxygen / 4);
			} else if (config.oxygenBarStyle == 0) {
				oxyBar = Math.round((oxygen / 3) * 2);
			}
			
			if (config.oxygenAnchor == 0 || config.oxygenAnchor > 8) {
				configX = (int) Math.round(config.oxygenXPos * widthMultiplier);
				configY = (int) Math.round(config.oxygenYPos * heightMultiplier);
			} else {
				configX = calculateAnchorPointX(config.oxygenAnchor, sizeX);
				configY = calculateAnchorPointY(config.oxygenAnchor, sizeY);
			}
			
			int xPos = configX + config.oxygenXOffset;
			int yPos = configY + config.oxygenYOffset;
	
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);;
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			mc.renderEngine.bindTexture(guiStatsBar);
			
			if (config.oxygenBarStyle == 1) {
				this.drawTexturedModalRect(xPos, yPos, 0, 188, sizeX, sizeY);
				this.drawTexturedModalRect(xPos + 2, yPos + 2, 0, 168, oxyBar, 16);
				this.drawTexturedModalRect(xPos, yPos, 0, 148, sizeX, sizeY);
			} else {
				this.drawTexturedModalRect(xPos, yPos, 0, 108, sizeX, sizeY);
				this.drawTexturedModalRect(xPos + 2, yPos + 2, 0, 60, oxyBar, 16);
				this.drawTexturedModalRect(xPos, yPos, 0, 0, sizeX, sizeY);
			}
			
			this.mc.renderEngine.bindTexture(statIcons);
			this.drawTexturedModalRect(xPos + 2, yPos + 1, 54, 0, 18, 18);
			
			if (config.oxygenText) {
				String oxy = "Oxygen: ";
				if (config.oxygenBarStyle == 1) {
					oxy = "";
				}
				font.drawStringWithShadow(oxy + (Math.round(oxygen / 20) + 1) + "/" + 15, xPos + 22, yPos + 6, config.healthTextColour);
			}
			
			GL11.glPopMatrix();
		}
	}
}
