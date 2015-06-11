package co.uk.silvania.hudwidgets.client;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import org.lwjgl.opengl.GL11;

import co.uk.silvania.hudwidgets.HUDWidgets;
import co.uk.silvania.hudwidgets.HUDWidgetsConfig;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GuiWidgetHorseJump extends GuiWidgetBase {

	public GuiWidgetHorseJump(Minecraft mc) {
		super(mc);
	}
	
	private static final ResourceLocation guiStatsBar = new ResourceLocation(HUDWidgets.modid, "textures/gui/" + HUDWidgetsConfig.horseJumpBarTextureStyle);
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderGui(RenderGameOverlayEvent.Pre event) {
		boolean enabled = true;		
		if (!HUDWidgetsConfig.horseJumpBarEnabled) {
			enabled = false;
		}
		
		if (mc.thePlayer.capabilities.isCreativeMode && !HUDWidgetsConfig.renderHorseJumpBarCreative) {
			enabled = false;
		}
		
		if(mc.thePlayer.isRidingHorse() || HUDWidgetsConfig.alwaysRenderHorseJumpBar) {
		
			if (enabled) {
				float power = mc.thePlayer.getHorseJumpPower();
				int jump = (int) Math.round(power * 200);
				
				double widthMultiplier = getResIncreaseMultiplier("x");
				double heightMultiplier = getResIncreaseMultiplier("y");
				
				int sizeX = 204;
				int sizeY = 20;
				
				if (!HUDWidgetsConfig.horizontalHorseJumpBar) {
					sizeX = 20;
					sizeY = 204;
				}
				
				configX = calculateAnchorPointX(HUDWidgetsConfig.horseJumpBarAnchor, sizeX);
				configY = calculateAnchorPointY(HUDWidgetsConfig.horseJumpBarAnchor, sizeY);
				
				int xPos = configX + HUDWidgetsConfig.horseJumpBarXOffset;
				int yPos = configY + HUDWidgetsConfig.horseJumpBarYOffset;
				
		
				GL11.glPushMatrix();
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);;
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				mc.renderEngine.bindTexture(guiStatsBar);
				if (HUDWidgetsConfig.horizontalHorseJumpBar) {
					this.drawTexturedModalRect(xPos, yPos, 0, 76, sizeX, sizeY);
					this.drawTexturedModalRect(xPos + 2, yPos + 2, 0, 96, jump, 16);
					this.drawTexturedModalRect(xPos, yPos, 0, 20, sizeX, sizeY);
				} else {
					this.drawTexturedModalRect(xPos, yPos, 76, 0, sizeX, sizeY);
					this.drawTexturedModalRect(xPos + 2, yPos + 202 - jump, 96, 200 - jump, 16, jump);
					this.drawTexturedModalRect(xPos, yPos, 20, 0, sizeX, sizeY);
				}
				
				GL11.glPopMatrix();
			}
		}
	}
}