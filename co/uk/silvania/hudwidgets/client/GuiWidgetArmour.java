package co.uk.silvania.hudwidgets.client;

import org.lwjgl.opengl.GL11;

import co.uk.silvania.hudwidgets.HUDWidgets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;

public class GuiWidgetArmour extends GuiWidgetBase {
	
	private Minecraft mc;

	public GuiWidgetArmour(Minecraft mc) {
		super(mc);
		this.mc = mc;
	}
	
	private static final ResourceLocation vanillaIcons = new ResourceLocation("textures/gui/icons.png");
	private static final ResourceLocation guiStatsBar = new ResourceLocation(HUDWidgets.modid, "textures/gui/" + config.armourTextureStyle);
	
	@ForgeSubscribe(priority = EventPriority.NORMAL)
	public void onRenderArmourWidget(RenderGameOverlayEvent event) {
		boolean enabled = true;
		int armour = mc.thePlayer.getTotalArmorValue() * 10;
		
		if (!config.armourEnabled || config.armourBarStyle >= 2) {
			enabled = false;
		}
		if (mc.thePlayer.capabilities.isCreativeMode && !config.renderArmourCreative) {
			enabled = false;
		}
		if (armour == 0 && !config.alwaysRenderArmour) {
			enabled = false;
		}
		
		if (enabled) {
			FontRenderer font = mc.fontRenderer;
		
			double widthMultiplier = getResIncreaseMultiplier("x");
			double heightMultiplier = getResIncreaseMultiplier("y");
			
			int sizeX = 204;
			int sizeY = 20;
			
			if (config.armourBarStyle == 1) {
				sizeX = 79;
				armour = Math.round((armour / 8) * 3);
			}
			
			if (config.armourAnchor == 0 || config.armourAnchor > 8) {
				configX = (int) Math.round(config.armourXPos * widthMultiplier);
				configY = (int) Math.round(config.armourYPos * heightMultiplier);
			} else {
				configX = calculateAnchorPointX(config.armourAnchor, sizeX);
				configY = calculateAnchorPointY(config.armourAnchor, sizeY);
			}
			
			int xPos = configX + config.armourXOffset;
			int yPos = configY + config.armourYOffset;
			
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);;
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			mc.renderEngine.bindTexture(guiStatsBar);
			
			if (config.armourBarStyle == 1) {
				this.drawTexturedModalRect(xPos, yPos, 79, 188, sizeX, sizeY);
				this.drawTexturedModalRect(xPos + 2, yPos + 2, 79, 168, armour, 16);
				this.drawTexturedModalRect(xPos, yPos, 79, 148, sizeX, sizeY);
			} else {
				this.drawTexturedModalRect(xPos, yPos, 0, 128, sizeX, sizeY);
				this.drawTexturedModalRect(xPos + 2, yPos + 2, 0, 76, armour, 16);
				this.drawTexturedModalRect(xPos, yPos, 0, 20, sizeX, sizeY);
			}
			this.mc.renderEngine.bindTexture(vanillaIcons);
			GL11.glScalef(2.0F, 2.0F, 2.0F);
			this.drawTexturedModalRect(Math.round(xPos / 2) + 1, Math.round(yPos / 2), 43, 9, 9, 9);	
			
			if (config.armourText) {
				String amr = "";
				if (config.armourBarStyle != 1) {
					amr = "Armour: ";
				}
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				font.drawStringWithShadow(amr + armour + "/" + 75, xPos + 22, yPos + 6, config.armourTextColour);
			}
			GL11.glPopMatrix();
		}
	}
}
