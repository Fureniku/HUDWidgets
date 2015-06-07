package co.uk.silvania.hudwidgets.client.configs;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import co.uk.silvania.hudwidgets.HUDWidgets;
import co.uk.silvania.hudwidgets.HUDWidgetsConfig;
import cpw.mods.fml.client.config.DummyConfigElement;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;

public class HUDWidgetsConfigGUI extends GuiConfig {
	
	public HUDWidgetsConfigGUI(GuiScreen parent) {
		super(parent, getConfigElements(), HUDWidgets.modid, false, false, GuiConfig.getAbridgedConfigPath(HUDWidgetsConfig.config.toString()));
	}
	
	private static List<IConfigElement> getConfigElements() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		
		list.add(categoryElement(HUDWidgetsConfig.CATEGORY_ARMOUR, "Armour", "hudwidgets.configgui.ctgy.armour"));
		list.add(categoryElement(HUDWidgetsConfig.CATEGORY_COMPASS, "Compass", "hudwidgets.configgui.ctgy.compass"));
		list.add(categoryElement(HUDWidgetsConfig.CATEGORY_EXP, "EXP", "hudwidgets.configgui.ctgy.exp"));
		list.add(categoryElement(HUDWidgetsConfig.CATEGORY_FPS, "FPS", "hudwidgets.configgui.ctgy.fps"));
		list.add(categoryElement(HUDWidgetsConfig.CATEGORY_GAMEMODE, "Gamemode", "hudwidgets.configgui.ctgy.gamemode"));
		list.add(categoryElement(HUDWidgetsConfig.CATEGORY_HEALTH, "Health", "hudwidgets.configgui.ctgy.health"));
		list.add(categoryElement(HUDWidgetsConfig.CATEGORY_HORSEHEALTH, "HorseHealth", "hudwidgets.configgui.ctgy.horsehealth"));
		list.add(categoryElement(HUDWidgetsConfig.CATEGORY_HORSEJUMP, "HorseJump", "hudwidgets.configgui.ctgy.horsejump"));
		list.add(categoryElement(HUDWidgetsConfig.CATEGORY_HOTBAR, "Hotbar", "hudwidgets.configgui.ctgy.hotbar"));
		list.add(categoryElement(HUDWidgetsConfig.CATEGORY_HUNGER, "Hunger", "hudwidgets.configgui.ctgy.hunger"));
		list.add(categoryElement(HUDWidgetsConfig.CATEGORY_NAME, "Name", "hudwidgets.configgui.ctgy.name"));
		list.add(categoryElement(HUDWidgetsConfig.CATEGORY_OXYGEN, "Oxygen", "hudwidgets.configgui.ctgy.oxygen"));
		list.add(categoryElement(HUDWidgetsConfig.CATEGORY_POTION, "Potions", "hudwidgets.configgui.ctgy.potion"));
		list.add(categoryElement(HUDWidgetsConfig.CATEGORY_TIME, "Time", "hudwidgets.configgui.ctgy.time"));
		list.add(categoryElement(HUDWidgetsConfig.CATEGORY_WALLET, "Wallet", "hudwidgets.configgui.ctgy.wallet"));
		
		return list;
	}
	
	private static IConfigElement categoryElement(String category, String name, String tooltip_key) {
		return new DummyConfigElement.DummyCategoryElement(name, tooltip_key, new ConfigElement(HUDWidgetsConfig.config.getCategory(category)).getChildElements());
	}

}
