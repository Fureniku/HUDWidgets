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

public class GuiWidgetName extends GuiWidgetBase {

	public GuiWidgetName(Minecraft mc) {
		super(mc);
	}

	private static final ResourceLocation guiStatsBar = new ResourceLocation(HUDWidgets.modid, "textures/gui/" + HUDWidgetsConfig.nameTextureStyle);
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderGui(RenderGameOverlayEvent.Pre event) {
		boolean enabled = true;		
		if (!HUDWidgetsConfig.nameEnabled) {
			enabled = false;
		}

		if (mc.thePlayer.capabilities.isCreativeMode && !HUDWidgetsConfig.renderNameCreative) {
			enabled = false;
		}
		
		if (enabled) {
			FontRenderer font = mc.fontRenderer;
			String name = mc.thePlayer.getDisplayName();
			
			double widthMultiplier = getResIncreaseMultiplier("x");
			double heightMultiplier = getResIncreaseMultiplier("y");
			
			int sizeX = 79;
			int sizeY = 20;
			
			if (HUDWidgetsConfig.nameRelativeResize) {
				sizeX = 10 + (name.length() * 6);
			}
			
			if (HUDWidgetsConfig.nameAnchor == 0 || HUDWidgetsConfig.nameAnchor > 8) {
				configX = (int) Math.round(HUDWidgetsConfig.nameXPos * widthMultiplier);
				configY = (int) Math.round(HUDWidgetsConfig.nameYPos * heightMultiplier);
			} else {
				configX = calculateAnchorPointX(HUDWidgetsConfig.nameAnchor, sizeX);
				configY = calculateAnchorPointY(HUDWidgetsConfig.nameAnchor, sizeY);
			}
			
			int xPos = configX + HUDWidgetsConfig.nameXOffset;
			int yPos = configY + HUDWidgetsConfig.nameYOffset;
			int xTextPos = xPos;
			int yTextPos = yPos;
			
			if (HUDWidgetsConfig.nameTextAlignRight) {
				xPos = (79 - sizeX) + xPos - 2;
			}
			
			if (HUDWidgetsConfig.nameTextAlignRight) {
				if (!HUDWidgetsConfig.nameRelativeResize) {
					xTextPos = xPos + (name.length() * 6);
				}
			}
	
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);;
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			mc.renderEngine.bindTexture(guiStatsBar);
			
			this.drawTexturedModalRect(xPos, yPos, 158, 148, sizeX, sizeY);
			if (HUDWidgetsConfig.nameRelativeResize) {
				this.drawTexturedModalRect(xPos + sizeX, yPos, 235, 148, 2, 20);
			}
			font.drawString(name, xTextPos + 6, yTextPos + 6, HUDWidgetsConfig.nameTextColour);
			GL11.glPopMatrix();
		}
	}
}
