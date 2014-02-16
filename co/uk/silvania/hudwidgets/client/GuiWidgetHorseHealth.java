package co.uk.silvania.hudwidgets.client;

import org.lwjgl.opengl.GL11;

import co.uk.silvania.hudwidgets.HUDWidgets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;

public class GuiWidgetHorseHealth extends GuiWidgetBase {

	public GuiWidgetHorseHealth(Minecraft mc) {
		super(mc);
	}
	
	private static final ResourceLocation guiStatsBar = new ResourceLocation(HUDWidgets.modid, "textures/gui/" + config.horseHealthTextureStyle);
	
	@ForgeSubscribe(priority = EventPriority.NORMAL)
	public void onRenderGui(RenderGameOverlayEvent.Pre event) {
		boolean enabled = true;		
		if (!config.horseHealthEnabled) {
			enabled = false;
		}
		
		if (!(mc.thePlayer.ridingEntity instanceof EntityLivingBase)) {
			enabled = false;
		}
		
		if (mc.thePlayer.capabilities.isCreativeMode && !config.renderHorseHealthCreative) {
			enabled = false;
		}
		
		if (mc.thePlayer.isRiding() || config.alwaysRenderHorseHealth) {
			if (enabled) {
		        Entity entity = mc.thePlayer.ridingEntity;
				EntityLivingBase mount = (EntityLivingBase) entity;
				
				FontRenderer font = mc.fontRenderer;
				
				String mountName = mc.thePlayer.ridingEntity.getEntityName();
				float mountHealth = mount.getHealth();
				float mountMaxHealth = mount.getMaxHealth();
				int healthAmount = (int) Math.round((200 / mountMaxHealth) * mountHealth);
				
				double widthMultiplier = getResIncreaseMultiplier("x");
				double heightMultiplier = getResIncreaseMultiplier("y");
				
				int sizeX = 204;
				int sizeY = 20;
				
				if (config.horseHealthAnchor == 0 || config.horseHealthAnchor > 8) {
					configX = (int) Math.round(config.horseHealthXPos * widthMultiplier);
					configY = (int) Math.round(config.horseHealthYPos * heightMultiplier);
				} else {
					configX = calculateAnchorPointX(config.horseHealthAnchor, sizeX);
					configY = calculateAnchorPointY(config.horseHealthAnchor, sizeY);
				}
				
				int xPos = configX + config.horseHealthXOffset;
				int yPos = configY + config.horseHealthYOffset;
				

				GL11.glPushMatrix();
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);;
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				mc.renderEngine.bindTexture(guiStatsBar);
				
				this.drawTexturedModalRect(xPos, yPos, 0, 108, sizeX, sizeY);
				this.drawTexturedModalRect(xPos + 2, yPos + 2, 0, 60, healthAmount, 16);
				this.drawTexturedModalRect(xPos, yPos, 0, 0, sizeX, sizeY);
				
				this.mc.renderEngine.bindTexture(statIcons);
				this.drawTexturedModalRect(xPos + 2, yPos + 1, 0, 18, 18, 18);
				
				if (config.textHorseHealth) {
					font.drawStringWithShadow(mountName + "'s Health: " + (int) mountHealth + "/" + (int) mountMaxHealth, xPos + 22, yPos + 6, 0xFFFFFF);
				}
				GL11.glPopMatrix();
			}
		}
	}
}
