package com.redstoneguy10ls.tfcscraping;

import com.redstoneguy10ls.tfcscraping.common.ScrapingTab;
import com.redstoneguy10ls.tfcscraping.common.item.ScrapingItems;
import com.mojang.logging.LogUtils;
import com.redstoneguy10ls.tfcscraping.client.ClientEvents;
import net.dries007.tfc.common.capabilities.ItemCapabilities;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.*;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.slf4j.Logger;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;

import java.util.stream.Stream;

import static net.neoforged.fml.loading.FMLEnvironment.dist;

@Mod(TFCScraping.MOD_ID)
public final class TFCScraping {

	public static final Logger LOG = LogUtils.getLogger();
	public static final String MOD_ID = "tfcscraping";

	public TFCScraping(ModContainer mod, IEventBus bus) {

		//modBus.register(TFCScraping.class);
		ScrapingItems.ITEMS.register(bus);
		ScrapingTab.CREATIVE_TABS.register(bus);
		bus.addListener(this::registerCapabilities);
		NeoForgeEvents.init();

		//ExampleModForgeEvents.init(NeoForge.EVENT_BUS);

		if (dist == Dist.CLIENT) {
			ClientEvents.init(bus, mod);
		}
	}
	private void registerCapabilities(RegisterCapabilitiesEvent event)
	{
		event.registerItem(ItemCapabilities.MOLD, ItemCapabilities::forMold, ScrapingItems.SCRAPING_KNIFE_MOLD);
		event.registerItem(ItemCapabilities.HEAT, ItemCapabilities::forMold, ScrapingItems.SCRAPING_KNIFE_MOLD);
		event.registerItem(ItemCapabilities.FLUID, ItemCapabilities::forMold, ScrapingItems.SCRAPING_KNIFE_MOLD);

	}

	/**
	 * Shorthand for {@code ResourceLocation.fromNamespaceAndPath(MOD_ID, path)}
	 */
	public static ResourceLocation location(final String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}

	/**
	 * Helper for creating modid prepended lang keys
	 */
	public static String lang(final String langKey) {
		return MOD_ID + "." + langKey;
	}
}