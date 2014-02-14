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

public class GuiWidgetGamemode extends GuiWidgetBase {

	public GuiWidgetGamemode(Minecraft mc) {
		super(mc);
	}
	
	private static final ResourceLocation vanillaIcons = new ResourceLocation("textures/gui/icons.png");
	private static final ResourceLocation guiStatsBar = new ResourceLocation(HUDWidgets.modid, "textures/gui/" + HUDWidgetsConfig.gamemodeTextureStyle);
	
	@ForgeSubscribe(priority = EventPriority.NORMAL)
	public void onRenderGui(RenderGameOverlayEvent event) {
		boolean enabled = true;		
		if (!HUDWidgetsConfig.gamemodeEnabled) {
			enabled = false;
		}
		
		if (enabled) {
			FontRenderer font = mc.fontRenderer;
						
			int gamemode = 0;
			if (mc.thePlayer.capabilities.isCreativeMode) {
				gamemode = 1;
			}
			if (!mc.thePlayer.capabilities.allowEdit) {
				gamemode = 2;
			}
			
			ScaledResolution res = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
			int width = res.getScaledWidth() * 2;
			int height = res.getScaledHeight() * 2;
	
			int configX = HUDWidgetsConfig.gamemodeXPos;
			int configY = HUDWidgetsConfig.gamemodeYPos;
			
			float scale = 1;
			
			int posX = (int) Math.round(configX * scale);
			int posY = (int) Math.round(configY * scale);
			
			if (width == 800) {
				scale = 1.5F;
			}
			if (width == 1600) {
				scale = 3;
			}
			
			
			String gameMode = "Survival";
			if (gamemode == 1) {
				gameMode = "Creative";
			}
			if (gamemode == 2) {
				gameMode = "Adventure";
			}
			
			int xPos = 2 + posX;
			int yPos = 2 + posY;
	
			GL11.glPushMatrix();
			int textXPos = 2 + Math.round(posX / 20);
			int textYPos = 2 + Math.round(posY / 20);
				
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			font.drawStringWithShadow(gameMode, (xPos) + 22, (yPos) + 6, 0xFFFFFF);
			GL11.glPopMatrix();
		}
	}
}
