package com.redstoneguy10ls.tfcscraping.common.items;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.items.MoldItem;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

import static com.redstoneguy10ls.tfcscraping.TFCScraping.MOD_ID;

public class ScrapingItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MOD_ID);


    public static final Map<Metal.Default, RegistryObject<Item>> SCRAPING_KNIFE =
            Helpers.mapOfKeys(Metal.Default.class, Metal.Default::hasTools, metals ->
                    register("metal/scraping_knife/" + metals.name(), () ->
                    new ScrapingKnife(metals.toolTier(), new Item.Properties().rarity(metals.getRarity()))));
    public static final Map<Metal.Default, RegistryObject<Item>> SCRAPING_HEAD =
            Helpers.mapOfKeys(Metal.Default.class, Metal.Default::hasTools, metals ->
                    register("metal/scraping_knife_blade/" + metals.name(), basicItem())
            );

    public static final RegistryObject<Item> UNFIRED_SCRAPING_KNIFE_MOLD = register("ceramic/unfired_scraping_knife_blade_mold");
    public static final RegistryObject<Item> SCRAPING_KNIFE_MOLD = register("ceramic/scraping_knife_blade_mold",
            () -> new MoldItem(TFCConfig.SERVER.moldSwordBladeCapacity, TFCTags.Fluids.USABLE_IN_TOOL_HEAD_MOLD, new Item.Properties()));


    private static RegistryObject<Item> register(String name)
    {
        return register(name, () -> new Item(new Item.Properties()));
    }

    private static <T extends Item> RegistryObject<T> register(String name, Supplier<T> item)
    {
        return ITEMS.register(name.toLowerCase(Locale.ROOT), item);
    }

    private static Item.Properties prop()
    {
        return new Item.Properties();
    }
    private static Supplier<Item> basicItem() {
        return () -> new Item( new Item.Properties());
    }

    private static Item.Properties foodProperties()
    {
        return new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.3f).build());
    }
}
