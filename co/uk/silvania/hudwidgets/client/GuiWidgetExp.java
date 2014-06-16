package co.uk.silvania.hudwidgets.client;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import co.uk.silvania.hudwidgets.HUDWidgets;
import co.uk.silvania.hudwidgets.HUDWidgetsConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;

public class GuiWidgetExp extends GuiWidgetBase {

	public GuiWidgetExp(Minecraft mc) {
		super(mc);
	}
	
	int xpColour;
	
	private static final ResourceLocation experienceOrbTextures = new ResourceLocation("textures/entity/experience_orb.png");
	private static final ResourceLocation guiStatsBar = new ResourceLocation(HUDWidgets.modid, "textures/gui/" + config.expTextureStyle);
	
	@ForgeSubscribe(priority = EventPriority.NORMAL)
	public void onRenderGui(RenderGameOverlayEvent.Pre event) {
		boolean enabled = true;
		if (!config.expEnabled) {
			enabled = false;
		}
		if (mc.thePlayer.capabilities.isCreativeMode && !config.renderExpCreative) {
			enabled = false;
		}
		
		if (enabled) {
			FontRenderer font = mc.fontRenderer;
			
			int currentLevel = mc.thePlayer.experienceLevel;
			float currentExp = mc.thePlayer.experience;
			
			int expLevel = mc.thePlayer.experienceLevel;
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
			
			if (config.expAnchor == 0 || config.expAnchor > 8) {
				configX = (int) Math.round(config.expXPos * widthMultiplier);
				configY = (int) Math.round(config.expYPos * heightMultiplier);
			} else {
				configX = calculateAnchorPointX(config.expAnchor, sizeX);
				configY = calculateAnchorPointY(config.expAnchor, sizeY);
			}
			
			int xPos = configX + config.expXOffset;
			int yPos = configY + config.expYOffset;
			
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);;
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			mc.renderEngine.bindTexture(guiStatsBar);
	
			this.drawTexturedModalRect(xPos, yPos, 0, 148, sizeX, sizeY);
			this.drawTexturedModalRect(xPos + 2, yPos + 2, 0, 92, experienceAmount, 16);
			this.drawTexturedModalRect(xPos, yPos, 0, 40, sizeX, sizeY);
			
			/*if (xpColour < 2000) {
				++this.xpColour;
			} else {
				xpColour = 0;
			}
			int l = (int)((MathHelper.sin(xpColour + 0.0F) + 1.0F) * 0.5F * 255.0F);*/
			
			this.mc.renderEngine.bindTexture(statIcons);
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
			
			font.drawStringWithShadow("Experience: " + (int)experience + "/" + expCap, xPos + 22, yPos + 6, config.expTextColour);
			font.drawStringWithShadow("Lvl: " + currentLevel, xPos + 142, yPos + 6, config.expTextColour);
			GL11.glPopMatrix();
		}
	}
}
