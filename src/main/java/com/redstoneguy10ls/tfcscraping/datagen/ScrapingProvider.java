package com.redstoneguy10ls.tfcscraping.datagen;

import com.redstoneguy10ls.tfcscraping.common.item.ScrapingItems;
import com.redstoneguy10ls.tfcscraping.common.item.ScrapingKnife;
import com.redstoneguy10ls.tfcscraping.datagen.builders.AnvilRecipeBuilder;
import com.redstoneguy10ls.tfcscraping.datagen.builders.CastingRecipeBuilder;
import com.redstoneguy10ls.tfcscraping.datagen.builders.HeatingRecipeBuilder;
import com.redstoneguy10ls.tfcscraping.datagen.builders.KnappingRecipeBuilder;
import net.dries007.tfc.common.component.forge.ForgeRule;
import net.dries007.tfc.common.recipes.AnvilRecipe;
import net.dries007.tfc.common.recipes.outputs.ItemStackProvider;
import net.dries007.tfc.util.DataGenerationHelpers;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static com.redstoneguy10ls.tfcscraping.TFCScraping.MOD_ID;
import static com.redstoneguy10ls.tfcscraping.TFCScraping.location;

public class ScrapingProvider extends RecipeProvider implements IConditionBuilder
{

    public ScrapingProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        super.buildRecipes(recipeOutput);

        HeatingRecipes(recipeOutput);
        castingRecipes(recipeOutput);
        craftingRecipes(recipeOutput);
        knappingRecipes(recipeOutput);
        anvilRecipes(recipeOutput);
    }

    private void HeatingRecipes(RecipeOutput recipeOutput)
    {
        HeatingRecipeBuilder.heat(ScrapingItems.UNFIRED_SCRAPING_KNIFE_MOLD, ScrapingItems.SCRAPING_KNIFE_MOLD,1399f)
                .save(recipeOutput, MOD_ID +":scraping_knife_blade_mold");
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
    private void craftingRecipes(RecipeOutput recipeOutput)
    {
        for (Metal metal :Metal.values())
        {
            if( metal.allParts())
            {
                recipe(recipeOutput, "crafting/metal/scraping_knife/" +metal.name())
                        .input('S', Tags.Items.RODS_WOODEN)
                        .input('X', ScrapingItems.SCRAPING_HEAD.get(metal))
                        .pattern("SXS")
                        .copyForging()
                        .source(0,1)
                        .shaped(ScrapingItems.SCRAPING_KNIFE.get(metal), 1);
            }
        }
    }

    private void castingRecipes(RecipeOutput recipeOutput)
    {
        for (Metal metal : Metal.values())
        {
            if(metal.allParts() && metal.tier() <= 2)
            {
                CastingRecipeBuilder.cast(ScrapingItems.SCRAPING_KNIFE_MOLD, metal,200, ScrapingItems.SCRAPING_HEAD.get(metal), 1, 1F)
                        .save(recipeOutput, MOD_ID + ":"+metal.name().toLowerCase(Locale.ROOT)+"_scraping_knife_blade");
            }
        }
    }

    private void knappingRecipes(RecipeOutput recipeOutput)
    {
        KnappingRecipeBuilder.clayKnapping(ScrapingItems.UNFIRED_SCRAPING_KNIFE_MOLD,    "XXXXX", "     ","     ","X   X","XXXXX")
                .save(recipeOutput,MOD_ID+":scraping_knife_blade_mold");
    }

    private void anvilRecipes(RecipeOutput recipeOutput)
    {
        for (Metal metal : Metal.values())
        {
            if(metal.tier() != 0)
            {
                AnvilRecipeBuilder.anvil(Ingredient.of(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "sheets/" + metal.name().toLowerCase()))),
                        metal.tier(), List.of(ForgeRule.HIT_LAST, ForgeRule.DRAW_SECOND_LAST, ForgeRule.DRAW_SECOND_LAST),
                        true, ScrapingItems.SCRAPING_HEAD.get(metal))
                        .save(recipeOutput);
            }
        }
    }

    private DataGenerationHelpers.Builder recipe(RecipeOutput output, String customName)
    {
        return new DataGenerationHelpers.Builder((name, recipe) -> output.accept(location(Objects.requireNonNullElse(name, customName).toLowerCase(Locale.ROOT)), recipe, null));
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
