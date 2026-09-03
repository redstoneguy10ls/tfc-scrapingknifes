package com.redstoneguy10ls.tfcscraping.common.item;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.items.MoldItem;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.registry.RegistryHolder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.*;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;

import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

import static com.redstoneguy10ls.tfcscraping.TFCScraping.MOD_ID;

public final class ScrapingItems {
	
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MOD_ID);
	
	
	public static final Map<Metal, ItemId> SCRAPING_KNIFE =
			Helpers.mapOf(Metal.class, Metal::allParts, metals ->
					register("metal/scraping_knife/" + metals.name(), () ->
							new ScrapingKnife(metals.toolTier(), new Item.Properties().rarity(metals.rarity()))));
	public static final Map<Metal, ItemId> SCRAPING_HEAD =
			Helpers.mapOf(Metal.class, Metal::allParts, metals ->
					register("metal/scraping_knife_blade/" + metals.name(), basicItem()));

	public static final ItemId UNFIRED_SCRAPING_KNIFE_MOLD  = register("ceramic/unfired_scraping_knife_blade_mold");
	public static final ItemId SCRAPING_KNIFE_MOLD  = register("ceramic/scraping_knife_blade_mold",
			() -> new MoldItem(TFCConfig.SERVER.moldSwordBladeCapacity, TFCTags.Fluids.USABLE_IN_TOOL_HEAD_MOLD, new Item.Properties())
	);

	private static Supplier<Item> basicItem() {
		return () -> new Item( new Item.Properties());
	}
	
	private static ItemId register(String name)
	{
		return register(name, () -> new Item(new Properties()));
	}
	private static ItemId register(String name, Supplier<Item> item)
	{
		return new ItemId(ITEMS.register(name.toLowerCase(Locale.ROOT), item));
	}
	
	public record ItemId(DeferredHolder<Item, Item> holder) implements RegistryHolder<Item, Item>, ItemLike
	{
		@Override
		public Item asItem()
		{
			return get();
		}
	}
}