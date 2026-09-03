package com.redstoneguy10ls.tfcscraping.datagen;

import com.redstoneguy10ls.tfcscraping.common.item.ScrapingItems;
import com.redstoneguy10ls.tfcscraping.datagen.builders.HeatingRecipeBuilder;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.redstoneguy10ls.tfcscraping.TFCScraping.MOD_ID;

public class ScrapingProvider extends RecipeProvider implements IConditionBuilder
{

    public ScrapingProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        super.buildRecipes(recipeOutput);

        HeatingRecipes(recipeOutput);
    }

    private void HeatingRecipes(RecipeOutput recipeOutput)
    {
        for(Metal metal : Metal.values())
        {
            if(metal.tier() != 0)
            {
                HeatingRecipeBuilder.heat(ScrapingItems.SCRAPING_KNIFE.get(metal), metal, 200, toolMetalTemperatureMap.get(metal),true)
                        .save(recipeOutput, MOD_ID +":metal/"+metal.name().toLowerCase()+"_scraping_knife");
                HeatingRecipeBuilder.heat(ScrapingItems.SCRAPING_HEAD.get(metal), metal, 200, toolMetalTemperatureMap.get(metal),true)
                        .save(recipeOutput, MOD_ID +":metal/"+metal.name().toLowerCase()+"_scraping_knife_blade");
            }
        }
    }
    private static final Map<Metal, Integer> toolMetalTemperatureMap = Helpers.mapOf(Metal.class, metal ->
            switch (metal)
            {
                case COPPER -> 1080;
                case BRONZE -> 950;
                case BLACK_BRONZE -> 1070;
                case BISMUTH_BRONZE -> 985;
                case WROUGHT_IRON -> 1535;
                case STEEL, RED_STEEL, BLUE_STEEL -> 1540;
                case BLACK_STEEL -> 1485;
                default -> 0;
            }
    );
}
