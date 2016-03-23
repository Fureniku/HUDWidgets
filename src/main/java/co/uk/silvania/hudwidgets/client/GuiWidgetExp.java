package co.uk.silvania.hudwidgets.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import org.lwjgl.opengl.GL11;

import co.uk.silvania.hudwidgets.HUDWidgets;
import co.uk.silvania.hudwidgets.HUDWidgetsConfig;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GuiWidgetExp extends GuiWidgetBase {

	public GuiWidgetExp(Minecraft mc) {
		super(mc);
	}
	
	int xpColour;
	
	//private static final ResourceLocation experienceOrbTextures = new ResourceLocation("textures/entity/experience_orb.png");
	private static final ResourceLocation guiStatsBar = new ResourceLocation(HUDWidgets.modid, "textures/gui/" + HUDWidgetsConfig.expTextureStyle);
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderGui(RenderGameOverlayEvent.Pre event) {
		boolean enabled = true;
		if (!HUDWidgetsConfig.expEnabled) {
			enabled = false;
		}
		if (mc.thePlayer.capabilities.isCreativeMode && !HUDWidgetsConfig.renderExpCreative) {
			enabled = false;
		}
		
		if (enabled) {
			FontRenderer font = mc.fontRenderer;
			
			int currentLevel = mc.thePlayer.experienceLevel;
			int experienceAmount = Math.round((mc.thePlayer.experience * 1000) / 5);

			int expCap = 0;
			
			if (currentLevel >= 30) {
		        expCap = 62 + (currentLevel - 30) * 7;
		    } else if (currentLevel >= 15) {
		    	expCap = 17 + (currentLevel - 15) * 3;
		    } else {
		    	expCap = 17;
		    }
			
			float experience = mc.thePlayer.experience * expCap;
			
			double widthMultiplier = getResIncreaseMultiplier("x");
			double heightMultiplier = getResIncreaseMultiplier("y");
			
			int sizeX = 204;
			int sizeY = 20;
			
			configX = calculateAnchorPointX(HUDWidgetsConfig.expAnchor, sizeX);
			configY = calculateAnchorPointY(HUDWidgetsConfig.expAnchor, sizeY);
			
			int xPos = configX + HUDWidgetsConfig.expXOffset;
			int yPos = configY + HUDWidgetsConfig.expYOffset;
			
			int saoXP = 0;
			
			if (HUDWidgetsConfig.saoColouredHealth) {
				saoXP = 126;
			}
			
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);;
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			mc.renderEngine.bindTexture(guiStatsBar);
	
			this.drawTexturedModalRect(xPos, yPos, 0, 148, sizeX, sizeY);
			this.drawTexturedModalRect(xPos + 2, yPos + 2, 0, 92 + saoXP, experienceAmount, 16);
			this.drawTexturedModalRect(xPos, yPos, 0, 40, sizeX, sizeY);
			
			/*if (xpColour < 2000) {
				++this.xpColour;
			} else {
				xpColour = 0;
			}
			int l = (int)((MathHelper.sin(xpColour + 0.0F) + 1.0F) * 0.5F * 255.0F);*/
			
			this.mc.renderEngine.bindTexture(hudStatIcons);
	        float f = 0.00390625F;
	        float f1 = 0.00390625F;
	        Tessellator tessellator = Tessellator.instance;
	        tessellator.startDrawingQuads();
			//tessellator.setColorRGBA_I(l << 16, 128);
	        tessellator.addVertexWithUV((double)(xPos + 2), (double)(yPos + 19), (double)this.zLevel, (double)((float)(18) * f), (double)((float)(36) * f1));
	        tessellator.addVertexWithUV((double)(xPos + 20), (double)(yPos + 19), (double)this.zLevel, (double)((float)(36) * f), (double)((float)(36) * f1));
	        tessellator.addVertexWithUV((double)(xPos + 20), (double)(yPos + 1), (double)this.zLevel, (double)((float)(36) * f), (double)((float)(18) * f1));
	        tessellator.addVertexWithUV((double)(xPos + 2), (double)(yPos + 1), (double)this.zLevel, (double)((float)(18) * f), (double)((float)(18) * f1));
	        tessellator.draw();
			
			font.drawStringWithShadow("Experience: " + (int)experience + "/" + expCap, xPos + 22, yPos + 6, HUDWidgetsConfig.expTextColour);
			font.drawStringWithShadow("Lvl: " + currentLevel, xPos + 142, yPos + 6, HUDWidgetsConfig.expTextColour);
			GL11.glPopMatrix();
		}
	}
}
