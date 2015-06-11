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

public class GuiWidgetArmour extends GuiWidgetBase {
	
	private Minecraft mc;

	public GuiWidgetArmour(Minecraft mc) {
		super(mc);
		this.mc = mc;
	}
	
	private static final ResourceLocation guiStatsBar = new ResourceLocation(HUDWidgets.modid, "textures/gui/" + HUDWidgetsConfig.armourTextureStyle);
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderArmourWidget(RenderGameOverlayEvent.Pre event) {
		boolean enabled = true;
		int armour = mc.thePlayer.getTotalArmorValue() * 10;
		
		if (!HUDWidgetsConfig.armourEnabled || HUDWidgetsConfig.armourBarStyle >= 2) {
			enabled = false;
		}
		if (mc.thePlayer.capabilities.isCreativeMode && !HUDWidgetsConfig.renderArmourCreative) {
			enabled = false;
		}
		if (armour == 0 && !HUDWidgetsConfig.alwaysRenderArmour) {
			enabled = false;
		}
		
		if (enabled) {
			FontRenderer font = mc.fontRenderer;
		
			double widthMultiplier = getResIncreaseMultiplier("x");
			double heightMultiplier = getResIncreaseMultiplier("y");
			
			int sizeX = 204;
			int sizeY = 20;
			
			if (HUDWidgetsConfig.armourBarStyle == 1) {
				sizeX = 79;
				armour = Math.round((armour / 8) * 3);
			}
			
			configX = calculateAnchorPointX(HUDWidgetsConfig.armourAnchor, sizeX);
			configY = calculateAnchorPointY(HUDWidgetsConfig.armourAnchor, sizeY);
			
			int xPos = configX + HUDWidgetsConfig.armourXOffset;
			int yPos = configY + HUDWidgetsConfig.armourYOffset;
			
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			mc.renderEngine.bindTexture(guiStatsBar);
			
			if (HUDWidgetsConfig.armourBarStyle == 1) {
				this.drawTexturedModalRect(xPos, yPos, 79, 188, sizeX, sizeY);
				this.drawTexturedModalRect(xPos + 2, yPos + 2, 79, 168, armour, 16);
				this.drawTexturedModalRect(xPos, yPos, 79, 148, sizeX, sizeY);
			} else {
				this.drawTexturedModalRect(xPos, yPos, 0, 128, sizeX, sizeY);
				this.drawTexturedModalRect(xPos + 2, yPos + 2, 0, 76, armour, 16);
				this.drawTexturedModalRect(xPos, yPos, 0, 20, sizeX, sizeY);
			}
			this.mc.renderEngine.bindTexture(hudStatIcons);
			this.drawTexturedModalRect(xPos + 2, yPos + 1, 36, 0, 18, 18);
			
			if (HUDWidgetsConfig.armourText) {
				String amr = "";
				if (HUDWidgetsConfig.armourBarStyle != 1) {
					amr = "Armour: ";
				}
				font.drawStringWithShadow(amr + armour + "/" + 75, xPos + 22, yPos + 6, HUDWidgetsConfig.armourTextColour);
			}
			GL11.glPopMatrix();
		}
	}
}
