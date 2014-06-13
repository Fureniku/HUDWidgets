package co.uk.silvania.hudwidgets.client;

import org.lwjgl.opengl.GL11;

import co.uk.silvania.cities.econ.EconUtils;
import co.uk.silvania.hudwidgets.HUDWidgets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;

public class GuiWidgetWallet extends GuiWidgetBase {

	public GuiWidgetWallet(Minecraft mc) {
		super(mc);
	}
	
	private static final ResourceLocation guiStatsBar = new ResourceLocation(HUDWidgets.modid, "textures/gui/" + config.walletTextureStyle);
	
	@ForgeSubscribe(priority = EventPriority.NORMAL)
	public void onRenderGamemode(RenderGameOverlayEvent.Pre event) {
		boolean enabled = true;		
		if (!config.walletEnabled) {
			enabled = false;
		}
		if (!config.renderWalletCreative && mc.thePlayer.capabilities.isCreativeMode) {
			enabled = false;
		}
		
		if (enabled) {
			FontRenderer font = mc.fontRenderer;
			
			String balance = EconUtils.formatBalance(EconUtils.getInventoryCash(this.mc.thePlayer));
			
			double widthMultiplier = getResIncreaseMultiplier("x");
			double heightMultiplier = getResIncreaseMultiplier("y");
			
			int sizeX = 79;
			int sizeY = 20;
			
			if (config.walletAnchor == 0 || config.walletAnchor > 8) {
				configX = (int) Math.round(config.walletXPos * widthMultiplier);
				configY = (int) Math.round(config.walletYPos * heightMultiplier);
			} else {
				configX = calculateAnchorPointX(config.walletAnchor, sizeX);
				configY = calculateAnchorPointY(config.walletAnchor, sizeY);
			}
			
			int xPos = configX + config.walletXOffset;
			int yPos = configY + config.walletYOffset;
			
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			mc.renderEngine.bindTexture(guiStatsBar);
			this.drawTexturedModalRect(xPos, yPos, 0, 228, sizeX, sizeY);

			font.drawString("$" + balance, xPos + 5, yPos + 6, config.walletTextColour);
			GL11.glPopMatrix();
		}
	}
}
