package com.redstoneguy10ls.tfcscraping;

import com.redstoneguy10ls.tfcscraping.common.item.ScrapingItems;
import com.mojang.logging.LogUtils;
import com.redstoneguy10ls.tfcscraping.client.ClientEvents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.*;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.slf4j.Logger;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;

import static net.neoforged.fml.loading.FMLEnvironment.dist;

@Mod(TFCScraping.MOD_ID)
public final class TFCScraping {

	public static final Logger LOG = LogUtils.getLogger();
	public static final String MOD_ID = "tfcscraping";

	public TFCScraping(ModContainer mod, IEventBus bus) {

		//modBus.register(TFCScraping.class);
		ScrapingItems.ITEMS.register(bus);

		//ExampleModForgeEvents.init(NeoForge.EVENT_BUS);

		if (dist == Dist.CLIENT) {
			ClientEvents.init(bus, mod);
		}
	}

	@SubscribeEvent
	private static void onCreativeTabBuild(final BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
			//event.accept(ScrapingItems.EXAMPLE_ITEM.toStack(), TabVisibility.PARENT_AND_SEARCH_TABS);
		}
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