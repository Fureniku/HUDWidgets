package co.uk.silvania.hudwidgets.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import org.lwjgl.opengl.GL11;

import co.uk.silvania.hudwidgets.HUDWidgets;
import co.uk.silvania.hudwidgets.HUDWidgetsConfig;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GuiWidgetHorseHealth extends GuiWidgetBase {

	public GuiWidgetHorseHealth(Minecraft mc) {
		super(mc);
	}
	
	private static final ResourceLocation guiStatsBar = new ResourceLocation(HUDWidgets.modid, "textures/gui/" + HUDWidgetsConfig.horseHealthTextureStyle);
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderGui(RenderGameOverlayEvent.Pre event) {
		boolean enabled = true;		
		if (!HUDWidgetsConfig.horseHealthEnabled) {
			enabled = false;
		}
		
		if (!(mc.thePlayer.ridingEntity instanceof EntityLivingBase)) {
			enabled = false;
		}
		
		if (mc.thePlayer.capabilities.isCreativeMode && !HUDWidgetsConfig.renderHorseHealthCreative) {
			enabled = false;
		}
		
		if (mc.thePlayer.isRiding() || HUDWidgetsConfig.alwaysRenderHorseHealth) {
			if (enabled) {
		        Entity entity = mc.thePlayer.ridingEntity;
				EntityLivingBase mount = (EntityLivingBase) entity;
				
				FontRenderer font = mc.fontRenderer;
				
				String mountName = "" + mc.thePlayer.ridingEntity.getEntityId(); //TODO Fix .getEntityName();
				float mountHealth = mount.getHealth();
				float mountMaxHealth = mount.getMaxHealth();
				int healthAmount = (int) Math.round((200 / mountMaxHealth) * mountHealth);
				
				double widthMultiplier = getResIncreaseMultiplier("x");
				double heightMultiplier = getResIncreaseMultiplier("y");
				
				int sizeX = 204;
				int sizeY = 20;
				
				if (HUDWidgetsConfig.horseHealthAnchor == 0 || HUDWidgetsConfig.horseHealthAnchor > 8) {
					configX = (int) Math.round(HUDWidgetsConfig.horseHealthXPos * widthMultiplier);
					configY = (int) Math.round(HUDWidgetsConfig.horseHealthYPos * heightMultiplier);
				} else {
					configX = calculateAnchorPointX(HUDWidgetsConfig.horseHealthAnchor, sizeX);
					configY = calculateAnchorPointY(HUDWidgetsConfig.horseHealthAnchor, sizeY);
				}
				
				int xPos = configX + HUDWidgetsConfig.horseHealthXOffset;
				int yPos = configY + HUDWidgetsConfig.horseHealthYOffset;
				

				GL11.glPushMatrix();
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				mc.renderEngine.bindTexture(guiStatsBar);
				
				this.drawTexturedModalRect(xPos, yPos, 0, 108, sizeX, sizeY);
				this.drawTexturedModalRect(xPos + 2, yPos + 2, 0, 60, healthAmount, 16);
				this.drawTexturedModalRect(xPos, yPos, 0, 0, sizeX, sizeY);
				
				this.mc.renderEngine.bindTexture(hudStatIcons);
				this.drawTexturedModalRect(xPos + 2, yPos + 1, 0, 18, 18, 18);
				
				if (HUDWidgetsConfig.textHorseHealth) {
					font.drawStringWithShadow(mountName + "'s Health: " + (int) mountHealth + "/" + (int) mountMaxHealth, xPos + 22, yPos + 6, 0xFFFFFF);
				}
				GL11.glPopMatrix();
			}
		}
	}
}
