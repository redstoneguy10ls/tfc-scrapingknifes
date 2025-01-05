package com.redstoneguy10ls.tfcscraping.common.items;

import com.redstoneguy10ls.tfcscraping.TFCScraping;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.SelfTests;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.redstoneguy10ls.tfcscraping.TFCScraping.MOD_ID;

public class ScrapingTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final ScrapingTab.CreativeTabHolder TAB =
            register("scraping", () -> new ItemStack(ScrapingItems.SCRAPING_KNIFE.get(Metal.Default.RED_STEEL).get()), ScrapingTab::fillTab);

    private static void fillTab(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output out)
    {

        ScrapingItems.SCRAPING_KNIFE.values().forEach(reg -> accept(out, reg));
        ScrapingItems.SCRAPING_HEAD.values().forEach(reg -> accept(out, reg));
        accept(out, ScrapingItems.UNFIRED_SCRAPING_KNIFE_MOLD);
        accept(out, ScrapingItems.SCRAPING_KNIFE_MOLD);

    }

    private static <T extends ItemLike, R extends Supplier<T>> void accept(CreativeModeTab.Output out, R reg)
    {
        if (reg.get().asItem() == Items.AIR)
        {
            TerraFirmaCraft.LOGGER.error("BlockItem with no Item added to creative tab: " + reg);
            SelfTests.reportExternalError();
            return;
        }
        out.accept(reg.get());
    }
    private static <T extends ItemLike, R extends Supplier<T>, K> void accept(CreativeModeTab.Output out, Map<K, R> map, K key)
    {
        if (map.containsKey(key))
        {
            out.accept(map.get(key).get());
        }
    }
    private static <T extends ItemLike, R extends Supplier<T>, K1, K2> void accept(CreativeModeTab.Output out, Map<K1, Map<K2, R>> map, K1 key1, K2 key2)
    {
        if (map.containsKey(key1) && map.get(key1).containsKey(key2))
        {
            out.accept(map.get(key1).get(key2).get());
        }
    }

    private static ScrapingTab.CreativeTabHolder register(String name, Supplier<ItemStack> icon, CreativeModeTab.DisplayItemsGenerator displayItems)
    {
        final RegistryObject<CreativeModeTab> reg = CREATIVE_TABS.register(name, () -> CreativeModeTab.builder()
                .icon(icon)
                .title(Component.translatable("tfcscraping.creative_tab." + name))
                .displayItems(displayItems)
                .build());
        return new ScrapingTab.CreativeTabHolder(reg, displayItems);
    }

    private static <T> void consumeOurs(IForgeRegistry<T> registry, Consumer<T> consumer)
    {
        for (T value : registry)
        {
            if (Objects.requireNonNull(registry.getKey(value)).getNamespace().equals(TFCScraping.MOD_ID))
            {
                consumer.accept(value);
            }
        }
    }
    public record CreativeTabHolder(RegistryObject<CreativeModeTab> tab, CreativeModeTab.DisplayItemsGenerator generator) {}
}
