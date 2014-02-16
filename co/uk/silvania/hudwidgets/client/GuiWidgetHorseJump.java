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

public class GuiWidgetHorseJump extends GuiWidgetBase {

	public GuiWidgetHorseJump(Minecraft mc) {
		super(mc);
	}
	
	private static final ResourceLocation vanillaIcons = new ResourceLocation("textures/gui/icons.png");
	private static final ResourceLocation guiStatsBar = new ResourceLocation(HUDWidgets.modid, "textures/gui/" + config.horseJumpBarTextureStyle);
	
	@ForgeSubscribe(priority = EventPriority.NORMAL)
	public void onRenderGui(RenderGameOverlayEvent.Pre event) {
		boolean enabled = true;		
		if (!config.horseJumpBarEnabled) {
			enabled = false;
		}
		
		if (mc.thePlayer.capabilities.isCreativeMode && !config.renderHorseJumpBarCreative) {
			enabled = false;
		}
		
		if(mc.thePlayer.isRidingHorse() || config.alwaysRenderHorseJumpBar) {
		
			if (enabled) {
				FontRenderer font = mc.fontRenderer;
				float power = mc.thePlayer.getHorseJumpPower();
				int jump = (int) Math.round(power * 200);
				
				double widthMultiplier = getResIncreaseMultiplier("x");
				double heightMultiplier = getResIncreaseMultiplier("y");
				
				int sizeX = 204;
				int sizeY = 20;
				
				if (!config.horizontalHorseJumpBar) {
					sizeX = 20;
					sizeY = 204;
				}
				
				if (config.horseJumpBarAnchor == 0 || config.horseJumpBarAnchor > 8) {
					configX = (int) Math.round(config.horseJumpBarXPos * widthMultiplier);
					configY = (int) Math.round(config.horseJumpBarYPos * heightMultiplier);
				} else {
					configX = calculateAnchorPointX(config.horseJumpBarAnchor, sizeX);
					configY = calculateAnchorPointY(config.horseJumpBarAnchor, sizeY);
				}
				
				int xPos = configX + config.horseJumpBarXOffset;
				int yPos = configY + config.horseJumpBarYOffset;
				
		
				GL11.glPushMatrix();
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);;
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				mc.renderEngine.bindTexture(guiStatsBar);
				if (config.horizontalHorseJumpBar) {
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