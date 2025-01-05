package com.redstoneguy10ls.tfcscraping;

import com.redstoneguy10ls.tfcscraping.common.items.ScrapingItems;
import net.dries007.tfc.client.model.ContainedFluidModel;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ClientEventHandler {

    public static void init()
    {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(ClientEventHandler::registerColorHandlerItems);
    }

    public static void registerColorHandlerItems(RegisterColorHandlersEvent.Item event)
    {
        event.register(new ContainedFluidModel.Colors(), ScrapingItems.SCRAPING_KNIFE_MOLD.get());

    }
}
