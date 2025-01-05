package com.redstoneguy10ls.tfcscraping;


import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static com.redstoneguy10ls.tfcscraping.TFCScraping.MOD_ID;

public class ScrapingTags {
    public static class Items
    {
        public static final TagKey<Item> LINE_SCRAPING = create("line_scraping");
        public static final TagKey<Item> QUARTER_SCRAPING = create("quarter_scraping");
        public static final TagKey<Item> HALF_SCRAPING = create("half_scraping");
        public static final TagKey<Item> FULL_SCRAPING = create("full_scraping");
        public static final TagKey<Item> SCRAPING_KNIVES = create("scraping_knives");


        private static TagKey<Item> create(String id)
        {
            return TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID,id));
        }

    }
}
