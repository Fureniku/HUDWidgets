package co.uk.silvania.hudwidgets.client;

import java.util.Set;

import co.uk.silvania.hudwidgets.client.configs.HUDWidgetsConfigGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import cpw.mods.fml.client.IModGuiFactory;

public class ConfigEditor implements IModGuiFactory {
	
	@Override
	public void initialize(Minecraft minecraftInstance) {
		
	}
	
	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass() {
		return HUDWidgetsConfigGUI.class;
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}
	
	@Override
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
		return null;
	}
}
