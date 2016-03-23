package co.uk.silvania.hudwidgets.client;

import org.lwjgl.opengl.GL11;

import co.uk.silvania.hudwidgets.HUDWidgets;
import co.uk.silvania.hudwidgets.HUDWidgetsConfig;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class GuiWidgetHealth extends GuiWidgetBase {
	
	public GuiWidgetHealth(Minecraft mc) {
		super(mc);
	}

	boolean debugMode = this.mc.gameSettings.showDebugInfo;
	
	static String healthTexture = HUDWidgetsConfig.healthTextureStyle.equalsIgnoreCase("gui_stats_vanilla.png") ? "staticons.png" : HUDWidgetsConfig.healthTextureStyle;
	
	private static final ResourceLocation guiStatsBar = new ResourceLocation(HUDWidgets.modid, "textures/gui/" + healthTexture);
	private static final ResourceLocation armourBar = new ResourceLocation(HUDWidgets.modid, "textures/gui/" + HUDWidgetsConfig.armourTextureStyle);
	private static final ResourceLocation oxygenBar = new ResourceLocation(HUDWidgets.modid, "textures/gui/" + HUDWidgetsConfig.oxygenTextureStyle);

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderGui(RenderGameOverlayEvent.Pre event) {
		boolean enabled = true;		
		if (!HUDWidgetsConfig.healthEnabled) {
			enabled = false;
		}
		if (mc.thePlayer.capabilities.isCreativeMode && !HUDWidgetsConfig.renderHealthCreative) {
			enabled = false;
		}
		if (debugMode) {
			enabled = false;
		}
		
		if (enabled) {
			FontRenderer font = mc.fontRenderer;
			
			double health = Math.round(mc.thePlayer.getHealth());
			double maxHealth = Math.round(mc.thePlayer.getMaxHealth());
			double healthAmount = Math.round((200 / maxHealth) * health);
			int armourAmount = (158 / 75) * mc.thePlayer.getTotalArmorValue();
			int oxygenAmount = Math.round(mc.thePlayer.getAir() / 2);
			
			int healthLast = MathHelper.ceiling_float_int(mc.thePlayer.prevHealth);
	    	
	    	boolean highlight = mc.thePlayer.hurtResistantTime / 3 % 2 == 1;
            if (mc.thePlayer.hurtResistantTime < 10) { highlight = false; }
			
			double widthMultiplier = getResIncreaseMultiplier("x");
			double heightMultiplier = getResIncreaseMultiplier("y");
			
			boolean saoHealth = HUDWidgetsConfig.saoColouredHealth;
			
			int sizeX = 204;
			int sizeY = 20;
			
			configX = calculateAnchorPointX(HUDWidgetsConfig.healthAnchor, sizeX);
			configY = calculateAnchorPointY(HUDWidgetsConfig.healthAnchor, sizeY);
			
			int xPos = configX + HUDWidgetsConfig.healthXOffset;
			int yPos = configY + HUDWidgetsConfig.healthYOffset;
	
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			mc.renderEngine.bindTexture(guiStatsBar);
			
			boolean poisoned = mc.thePlayer.isPotionActive(Potion.poison);
			boolean withered = mc.thePlayer.isPotionActive(Potion.wither);
			
			int backgroundHeart = 0; //+18
			int backgroundBar = 0; //+36
			int heart = 0;
			int highlightHeart = 0;
			int healthBar = 0;
			
			if (highlight) { 
				backgroundHeart = 18;
				backgroundBar = 20;
				if (!withered && !poisoned && !saoHealth) {
					highlightHeart = 15;
					healthBar = 36;
				}
			}
			
			if (withered) { heart = 56; healthBar = 18; }
			else if (poisoned) { heart = 28; healthBar = 54; }
			else if (saoHealth) {  //20hp max
				if (health > (maxHealth / 2.0)) { // health more than 10
					healthBar = 72; //GREEN
				} else if (health > (maxHealth / 10.0)) { //health more than 2
					healthBar = 90; //YELLOW
				} else {
					healthBar = 0; //RED
				}
			}
			
			this.drawTexturedModalRect(xPos, yPos, 0, 208 + backgroundBar, sizeX, sizeY);
			this.drawTexturedModalRect(xPos + 2, yPos + 2, 56, 36 + healthBar, (int) Math.round(healthAmount), 16);
			this.drawTexturedModalRect(xPos, yPos, 0, 168 + backgroundBar, sizeX, sizeY);
			
			this.drawTexturedModalRect(xPos + 2, yPos + 1, 0, 0 + backgroundHeart, 18, 18);
			this.drawTexturedModalRect(xPos + 4, yPos + 3,  + highlightHeart, 56 + heart, 14, 14);
			
			if (HUDWidgetsConfig.armourBarStyle == 2) {
				if (HUDWidgetsConfig.alwaysRenderArmour || mc.thePlayer.getTotalArmorValue() > 0) {
					this.mc.renderEngine.bindTexture(armourBar);
					this.drawTexturedModalRect(xPos + 23, yPos + 2, 0, 190, armourAmount, 6);
				}
			}
			
			if (HUDWidgetsConfig.oxygenBarStyle == 2) {
				if (mc.thePlayer.getAir() < 300) {
					this.mc.renderEngine.bindTexture(oxygenBar);
					this.drawTexturedModalRect(xPos + 27, yPos + 2, 0, 196, oxygenAmount, 6);
					this.drawTexturedModalRect(xPos + 25, yPos + 2, 0, 210, 154, 8);
				}
			}

			if (HUDWidgetsConfig.healthText) {
				font.drawStringWithShadow("Health: " + health + "/" + maxHealth, xPos + 22, yPos + 6, HUDWidgetsConfig.healthTextColour);
			}
			GL11.glPopMatrix();

		}
	}
}
