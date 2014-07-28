package co.uk.silvania.hudwidgets.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import co.uk.silvania.hudwidgets.HUDWidgets;
import co.uk.silvania.hudwidgets.HUDWidgetsConfig;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GuiWidgetBase extends Gui {
	
	public Minecraft mc;
	
	public static int defaultWidth;
	public static int defaultHeight;
	public int screenWidth;
	public int screenHeight;
	public int configX = 0;
	public int configY = 0;
	public static HUDWidgetsConfig config;
	
	public static final ResourceLocation hudStatIcons = new ResourceLocation(HUDWidgets.modid, "textures/gui/staticons.png");
	
	public GuiWidgetBase(Minecraft mc) {
		super();
		this.mc = mc;
	}
	
	public static void defaultResolution() {
		//Ran on the game load. Gets the resolution your PC sets minecraft to initially, before you get a chance to resize it. Used for scaling.
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
		defaultWidth = res.getScaledWidth();
		defaultHeight = res.getScaledHeight();
	}
	
	public double calculateWorldMinutes(double ticks) {
		for (int i = 0; i < 61; i++) {
			double minute = Math.round(16.9 * i);
			if (minute >= ticks) {
				if (i >= 0) {
					return i;
				} else {
					return 0;
				}
			}
		}
		return 0;
	}
	
	public double getResIncreaseMultiplier(String str) {
		ScaledResolution res = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
		int width = res.getScaledWidth();
		int height = res.getScaledHeight();
		
		int widthDiff = width - GuiWidgetBase.defaultWidth;
		int heightDiff = height - GuiWidgetBase.defaultHeight;
		
		//Get the difference between the launch resolution setting and the current one as a percentage
		//Divide difference by default value, multiply by 100 to get the percentage as a decimel, multiply again & round it to a whole number
		//and then divide by 100 again, giving a number of two decimal places. Just keeps it a bit tidier later on.
		double widthMult = (Math.round(((widthDiff / (double)GuiWidgetBase.defaultWidth) * 100) * 100)) / 100;
		//And then turn that % into a double, which you can multiply against a value to increase said value by said %
		double widthMultiplier = (widthMult / 100) + 1;
		
		double heightMult = (Math.round(((heightDiff / (double)GuiWidgetBase.defaultHeight) * 100) * 100)) / 100;
		double heightMultiplier = (heightMult / 100) + 1; //Changed from widthMult- assumed a bug I never noticed.
		
		if (str.equalsIgnoreCase("x")) {
			return widthMultiplier;
		} else if (str.equalsIgnoreCase("y")) {
			return heightMultiplier;
		}
		return 0.0;
	}
	
	public int calculateAnchorPointX(int anchor, int widgetWidth) {
		if (anchor == 1 || anchor == 4 || anchor == 6) {
			//Left-hand side
			return 2;
		}
		if (anchor == 2 || anchor == 7) {
			//Center
			return screenWidth - Math.round(widgetWidth / 2);
		}
		if (anchor == 3 || anchor == 5 || anchor == 8) {
			//Right-hand side
			return (screenWidth * 2) - widgetWidth - 2;
		}
		return 0;
	}
	
	public int calculateAnchorPointY(int anchor, int widgetHeight) {
		if (anchor == 1 || anchor == 2 || anchor == 3) {
			//Left-hand side
			return 2;
		}
		if (anchor == 4 || anchor == 5) {
			//Center
			return screenHeight - Math.round(widgetHeight / 2);
		}
		if (anchor == 6 || anchor == 7 || anchor == 8) {
			//Right-hand side
			return (screenHeight * 2)- widgetHeight - 2;
		}
		return 0;
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void cancelRenders(RenderGameOverlayEvent.Pre event) {
		ScaledResolution res = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
		screenWidth = res.getScaledWidth();
		screenHeight = res.getScaledHeight();
		
		if (event.type.equals(ElementType.BOSSHEALTH)) {
			event.setCanceled(false);
		}
		if (event.type.equals(ElementType.ARMOR)) {
			if (HUDWidgetsConfig.armourEnabled) {
				event.setCanceled(true);
			}
		}
		if (event.type.equals(ElementType.HEALTH)) {
			if (HUDWidgetsConfig.healthEnabled) {
				event.setCanceled(true);
			}
		}
		if (event.type.equals(ElementType.FOOD)) {
			if (HUDWidgetsConfig.hungerEnabled) {
				event.setCanceled(true);
			}
		}
		if (event.type.equals(ElementType.AIR)) {
			if (HUDWidgetsConfig.oxygenEnabled) {
				event.setCanceled(true);
			}
		}
		if (event.type.equals(ElementType.HOTBAR)) {
			if (HUDWidgetsConfig.hotbarEnabled) {
				event.setCanceled(true);
			}
		}
		if (event.type.equals(ElementType.EXPERIENCE)) {
			if (HUDWidgetsConfig.expEnabled) {
				event.setCanceled(true);
			}
		}
		if (event.type.equals(ElementType.HEALTHMOUNT)) {
			if (HUDWidgetsConfig.healthEnabled) {
				event.setCanceled(true);
			}
		}
		if (event.type.equals(ElementType.JUMPBAR)) {
			if (HUDWidgetsConfig.horseJumpBarEnabled) {
				event.setCanceled(true);
			}
		}
	}
}
