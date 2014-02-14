package co.uk.silvania.hudwidgets.client;

import org.lwjgl.opengl.GL11;

import co.uk.silvania.hudwidgets.HUDWidgets;
import co.uk.silvania.hudwidgets.HUDWidgetsConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;

public class GuiWidgetName extends GuiWidgetBase {

	public GuiWidgetName(Minecraft mc) {
		super(mc);
	}
	
	private static final ResourceLocation vanillaIcons = new ResourceLocation("textures/gui/icons.png");
	private static final ResourceLocation guiStatsBar = new ResourceLocation(HUDWidgets.modid, "textures/gui/" + HUDWidgetsConfig.nameTextureStyle);
	
	@ForgeSubscribe(priority = EventPriority.NORMAL)
	public void onRenderGui(RenderGameOverlayEvent event) {
		boolean enabled = true;		
		if (!HUDWidgetsConfig.nameEnabled) {
			enabled = false;
		}

		if (mc.thePlayer.capabilities.isCreativeMode && !HUDWidgetsConfig.renderNameCreative) {
			enabled = false;
		}
		
		if (enabled) {
			FontRenderer font = mc.fontRenderer;
			
			String name = mc.thePlayer.username;
			
			ScaledResolution res = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
			int width = res.getScaledWidth() * 2;
			int height = res.getScaledHeight() * 2;
	
			int configX = HUDWidgetsConfig.nameXPos;
			int configY = HUDWidgetsConfig.nameYPos;
			
			float scale = 1;
			
			int posX = (int) Math.round(configX * scale);
			int posY = (int) Math.round(configY * scale);
			
			if (width == 800) {
				scale = 1.5F;
			}
			if (width == 1600) {
				scale = 3;
			}
			
			int xPos = 2 + posX;
			int yPos = 2 + posY;
	
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);;
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			mc.renderEngine.bindTexture(guiStatsBar);
			this.mc.renderEngine.bindTexture(vanillaIcons);
			

			int textXPos = 2 + Math.round(posX / 20);
			int textYPos = 2 + Math.round(posY / 20);
				
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			font.drawStringWithShadow(name, (xPos) + 22, (yPos) + 6, 0xFFFFFF);
			GL11.glPopMatrix();
		}
	}
}
