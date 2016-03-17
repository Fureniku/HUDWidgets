package co.uk.silvania.hudwidgets.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import org.lwjgl.opengl.GL11;



import co.uk.silvania.cities.econ.EconUtils;
//import co.uk.silvania.cities.econ.EconUtils;
import co.uk.silvania.hudwidgets.HUDWidgets;
import co.uk.silvania.hudwidgets.HUDWidgetsConfig;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GuiWidgetWallet extends GuiWidgetBase {

	public GuiWidgetWallet(Minecraft mc) {
		super(mc);
	}
	
	private static final ResourceLocation guiStatsBar = new ResourceLocation(HUDWidgets.modid, "textures/gui/" + HUDWidgetsConfig.walletTextureStyle);
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderGamemode(RenderGameOverlayEvent.Pre event) {
		boolean enabled = true;		
		if (!HUDWidgetsConfig.walletEnabled) {
			enabled = false;
		}
		if (!HUDWidgetsConfig.renderWalletCreative && mc.thePlayer.capabilities.isCreativeMode) {
			enabled = false;
		}
		
		if (enabled) {
			FontRenderer font = mc.fontRenderer;
			EconUtils econ = new EconUtils();
			
			String balance = econ.formatBalance(econ.getInventoryCash(this.mc.thePlayer));
			
			double widthMultiplier = getResIncreaseMultiplier("x");
			double heightMultiplier = getResIncreaseMultiplier("y");
			
			int sizeX = 79;
			int sizeY = 20;
			
			configX = calculateAnchorPointX(HUDWidgetsConfig.walletAnchor, sizeX);
			configY = calculateAnchorPointY(HUDWidgetsConfig.walletAnchor, sizeY);
			
			int xPos = configX + HUDWidgetsConfig.walletXOffset;
			int yPos = configY + HUDWidgetsConfig.walletYOffset;
			
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			mc.renderEngine.bindTexture(guiStatsBar);
			this.drawTexturedModalRect(xPos, yPos, 0, 228, sizeX, sizeY);

			font.drawString("$" + balance, xPos + 5, yPos + 6, HUDWidgetsConfig.walletTextColour);
			GL11.glPopMatrix();
		}
	}
}
