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

public class GuiWidgetGamemode extends GuiWidgetBase {

	public GuiWidgetGamemode(Minecraft mc) {
		super(mc);
	}
	
	private static final ResourceLocation guiStatsBar = new ResourceLocation(HUDWidgets.modid, "textures/gui/" + HUDWidgetsConfig.gamemodeTextureStyle);
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderGamemode(RenderGameOverlayEvent.Pre event) {
		boolean enabled = true;		
		if (!HUDWidgetsConfig.gamemodeEnabled) {
			enabled = false;
		}
		
		if (enabled) {
			FontRenderer font = mc.fontRenderer;
			
			String gameMode = "Survival";
			int textColour = HUDWidgetsConfig.gamemodeSTextColour;
			if (mc.thePlayer.capabilities.isCreativeMode) {
				gameMode = "Creative";
				textColour = HUDWidgetsConfig.gamemodeCTextColour;
			}
			if (!mc.thePlayer.capabilities.allowEdit) {
				gameMode = "Adventure";
				textColour = HUDWidgetsConfig.gamemodeATextColour;
			}
			
			double widthMultiplier = getResIncreaseMultiplier("x");
			double heightMultiplier = getResIncreaseMultiplier("y");
			
			int sizeX = 79;
			int sizeY = 20;
			
			if(HUDWidgetsConfig.gamemodeRelativeResize) {
				sizeX = 6 + (gameMode.length() * 6);
				if (gameMode.equals("Survival")) {
					sizeX = sizeX - 3;
				}
			}
			
			configX = calculateAnchorPointX(HUDWidgetsConfig.gamemodeAnchor, sizeX) - 1;
			configY = calculateAnchorPointY(HUDWidgetsConfig.gamemodeAnchor, sizeY);
			
			int xPos = configX + HUDWidgetsConfig.gamemodeXOffset;
			int yPos = configY + HUDWidgetsConfig.gamemodeYOffset;
			
			int xTextPos = xPos;
			int yTextPos = yPos;
			
			if (HUDWidgetsConfig.gamemodeRelativeResize) {
				xPos = (79 - sizeX) + xPos;
			}
			
			if (HUDWidgetsConfig.gamemodeTextAlignRight) {
				if (!HUDWidgetsConfig.gamemodeRelativeResize) {
					if (gameMode.equalsIgnoreCase("Survival")) {
						xTextPos = xPos + 27;
					} else if (gameMode.equalsIgnoreCase("Creative")) {
						xTextPos = xPos + 26;
					} else if (gameMode.equalsIgnoreCase("Adventure")) {
						xTextPos = xPos + 16;
					}
				}
			}
			
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			mc.renderEngine.bindTexture(guiStatsBar);

			if (HUDWidgetsConfig.gamemodeRelativeResize) {
				this.drawTexturedModalRect(xPos + 1, yPos, 158, 168, sizeX - 3, sizeY);
				this.drawTexturedModalRect(xPos + sizeX - 2, yPos, 235, 168, 2, 20);
			} else
				this.drawTexturedModalRect(xPos + 1, yPos, 158, 168, sizeX, sizeY);
			font.drawString(gameMode, xTextPos + 6, yTextPos + 6, textColour);
			GL11.glPopMatrix();
		}
	}
}
