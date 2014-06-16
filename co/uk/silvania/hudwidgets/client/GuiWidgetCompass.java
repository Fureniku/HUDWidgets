package co.uk.silvania.hudwidgets.client;

import org.lwjgl.opengl.GL11;

import co.uk.silvania.hudwidgets.HUDWidgets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;

public class GuiWidgetCompass extends GuiWidgetBase {

	public GuiWidgetCompass(Minecraft mc) {
		super(mc);
	}
	
	private static final ResourceLocation guiStatsBar = new ResourceLocation(HUDWidgets.modid, "textures/gui/" + config.compassTextureStyle);
	
	@ForgeSubscribe(priority = EventPriority.NORMAL)
	public void onRenderCompass(RenderGameOverlayEvent.Pre event) {
		boolean enabled = true;		
		if (!config.compassEnabled) {
			enabled = false;
		}
		if (mc.thePlayer.capabilities.isCreativeMode && !config.renderCompassCreative) {
			enabled = false;
		}
		
		if (enabled) {
			FontRenderer font = mc.fontRenderer;
			int rotation = MathHelper.floor_double(mc.thePlayer.getRotationYawHead());
			while (rotation > 360) {
				rotation = rotation - 360;
			}
			while (rotation < 0) {
				rotation = rotation + 360;
			}
			
			double widthMultiplier = getResIncreaseMultiplier("x");
			double heightMultiplier = getResIncreaseMultiplier("y");
			
			int sizeX = 36;
			int sizeY = 20;
			
			if (config.compassAnchor == 0 || config.compassAnchor > 8) {
				configX = (int) Math.round(config.compassXPos * widthMultiplier);
				configY = (int) Math.round(config.compassYPos * heightMultiplier);
			} else {
				configX = calculateAnchorPointX(config.compassAnchor, sizeX);
				configY = calculateAnchorPointY(config.compassAnchor, sizeY);
			}
			
			int xPos = configX + config.compassXOffset;
			int yPos = configY + config.compassYOffset;
			
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);;
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			mc.renderEngine.bindTexture(guiStatsBar);
			if (!config.textCompass) {

			} else {
				String n = "N";
				String e = "E";
				String s = "S";
				String w = "W";
				String p = " .:!!:. ";
				
				String compass =  "!:. " + s + p + w + p + n + p + e + p + s + p;
				int stringLength = (int) Math.round(rotation / 10);
				String compassShort = compass.substring(stringLength, stringLength + 9);
				
				this.drawTexturedModalRect(xPos, yPos, 158, 188, sizeX, sizeY);
				font.drawString(compassShort, xPos + 6, yPos + 6, 0xFFFFFF);
			}
			GL11.glPopMatrix();
		}
	}
}
