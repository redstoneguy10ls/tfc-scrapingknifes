package com.redstoneguy10ls.tfcscraping.client;

import com.redstoneguy10ls.tfcscraping.common.item.ScrapingItems;
import net.dries007.tfc.client.model.ContainedFluidModel;
import net.neoforged.bus.api.*;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

public final class ClientEvents {

	public static void init(IEventBus bus, ModContainer mod) {
		bus.addListener(ClientEvents::registerColorHandlerItems);
	}
	public static void registerColorHandlerItems(RegisterColorHandlersEvent.Item event)
	{
		event.register(ContainedFluidModel.COLOR, ScrapingItems.SCRAPING_KNIFE_MOLD.get());
	}
}