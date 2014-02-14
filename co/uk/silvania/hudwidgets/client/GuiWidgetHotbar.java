package co.uk.silvania.hudwidgets.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import co.uk.silvania.hudwidgets.HUDWidgets;

public class GuiWidgetHotbar extends GuiWidgetBase {
	
	public GuiWidgetHotbar(Minecraft mc) {
		super(mc);
	}
	
	private static final ResourceLocation guiStatsBar = new ResourceLocation(HUDWidgets.modid, "textures/gui/" + config.hotbarTextureStyle);
	protected static final RenderItem itemRenderer = new RenderItem();

    @ForgeSubscribe(priority = EventPriority.NORMAL)
	public void renderHotbar(RenderGameOverlayEvent.Pre event) {
    	if (config.hotbarEnabled) {
		
			double widthMultiplier = getResIncreaseMultiplier("x");
			double heightMultiplier = getResIncreaseMultiplier("y");
			
			int sizeX = 182;
			int sizeY = 22;
			
			if (!config.horizontalHotbar) {
				sizeX = 22;
				sizeY = 182;
			}
			
			if (config.hotbarAnchor == 0 || config.hotbarAnchor > 8) {
				configX = (int) Math.round(config.hotbarXPos * widthMultiplier);
				configY = (int) Math.round(config.hotbarYPos * heightMultiplier);
			} else {
				configX = calculateAnchorPointX(config.hotbarAnchor, sizeX);
				configY = calculateAnchorPointY(config.hotbarAnchor, sizeY);
			}
			
			int xPos = configX + config.hotbarXOffset;
			int yPos = configY + config.hotbarYOffset;
	
	        GL11.glEnable(GL11.GL_BLEND);
	        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        mc.renderEngine.bindTexture(guiStatsBar);
	        
	        if (config.horizontalHotbar) {
	        	InventoryPlayer inv = mc.thePlayer.inventory;
	            drawTexturedModalRect(xPos, yPos, 0, 168, sizeX, sizeY);
	            drawTexturedModalRect(xPos - 1 + (inv.currentItem * 20), yPos - 1, 182, 168, 24, 24);
	
	        	GL11.glDisable(GL11.GL_BLEND);
	        	GL11.glEnable(GL12.GL_RESCALE_NORMAL);
	        	RenderHelper.enableGUIStandardItemLighting();
	
	        	for (int i = 0; i < 9; ++i) {
	                int x = xPos +  (i * 20) + 3;
	                int z = yPos + 3;
	        		renderInventorySlot(i, x, z, 1.0F);
	        	}
	        } else {
	        	InventoryPlayer inv = mc.thePlayer.inventory;
		        drawTexturedModalRect(xPos, yPos, 234, 0, sizeX, sizeY);
		        drawTexturedModalRect(xPos - 1, yPos - 1 + (inv.currentItem * 20), 182, 168, 24, 24);
		
		        GL11.glDisable(GL11.GL_BLEND);
		        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		        RenderHelper.enableGUIStandardItemLighting();
		        
	            for (int i = 0; i < 9; ++i) {
	                int x = xPos + 3;
	                int z = yPos + (i * 20) + 2;
	                renderInventorySlot(i, x, z, 1.0F);
	            }
	        }
	
	        RenderHelper.disableStandardItemLighting();
	        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
    	}
	}
    
    @Override
	public int calculateAnchorPointX(int anchor, int widgetWidth) {
		if (anchor == 1 || anchor == 4 || anchor == 6) {
			//Left-hand side
			return 2;
		}
		if (anchor == 2 || anchor == 7) {
			//Center
			return (screenWidth / 2)- Math.round(widgetWidth / 2);
		}
		if (anchor == 3 || anchor == 5 || anchor == 8) {
			//Right-hand side
			return (screenWidth) - widgetWidth - 2;
		}
		return 0;
	}
	
	@Override
	public int calculateAnchorPointY(int anchor, int widgetHeight) {
		if (anchor == 1 || anchor == 2 || anchor == 3) {
			//Left-hand side
			return 2;
		}
		if (anchor == 4 || anchor == 5) {
			//Center
			return (screenHeight / 2) - Math.round(widgetHeight / 2);
		}
		if (anchor == 6 || anchor == 7 || anchor == 8) {
			//Right-hand side
			return (screenHeight)- widgetHeight - 2;
		}
		return 0;
	}
	
	protected void renderInventorySlot(int par1, int par2, int par3, float par4) {
        ItemStack itemstack = this.mc.thePlayer.inventory.mainInventory[par1];

        if (itemstack != null) {
            float f1 = (float)itemstack.animationsToGo - par4;

            if (f1 > 0.0F) {
                GL11.glPushMatrix();
                float f2 = 1.0F + f1 / 5.0F;
                GL11.glTranslatef((float)(par2 + 8), (float)(par3 + 12), 0.0F);
                GL11.glScalef(1.0F / f2, (f2 + 1.0F) / 2.0F, 1.0F);
                GL11.glTranslatef((float)(-(par2 + 8)), (float)(-(par3 + 12)), 0.0F);
            }

            itemRenderer.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), itemstack, par2, par3);

            if (f1 > 0.0F) {
                GL11.glPopMatrix();
            }

            itemRenderer.renderItemOverlayIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), itemstack, par2, par3);
        }
    }

}
