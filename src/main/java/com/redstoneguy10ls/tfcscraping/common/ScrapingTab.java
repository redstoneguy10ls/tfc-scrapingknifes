package com.redstoneguy10ls.tfcscraping.common;

import com.redstoneguy10ls.tfcscraping.common.item.ScrapingItems;
import net.dries007.tfc.util.Metal;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.redstoneguy10ls.tfcscraping.TFCScraping.MOD_ID;

public class ScrapingTab
{
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final Id SCRAPING =
            register("scraping", () -> new ItemStack(ScrapingItems.SCRAPING_KNIFE.get(Metal.RED_STEEL).get()), ScrapingTab::fill);

    private static void fill(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output out)
    {
        ScrapingItems.SCRAPING_KNIFE.values().forEach(out::accept);
        ScrapingItems.SCRAPING_HEAD.values().forEach(out::accept);
        out.accept(ScrapingItems.UNFIRED_SCRAPING_KNIFE_MOLD);
        out.accept(ScrapingItems.SCRAPING_KNIFE_MOLD);

    }



    private static Id register(String name, Supplier<ItemStack> icon, CreativeModeTab.DisplayItemsGenerator displayItems)
    {
        final var holder = CREATIVE_TABS.register(name, () -> CreativeModeTab.builder()
                .icon(icon)
                .title(Component.translatable("tfcscraping.creative_tab." + name))
                .displayItems(displayItems)
                .build());
        return new Id(holder, displayItems);
    }
    public record Id(DeferredHolder<CreativeModeTab, CreativeModeTab> tab, CreativeModeTab.DisplayItemsGenerator generator) {}

}
