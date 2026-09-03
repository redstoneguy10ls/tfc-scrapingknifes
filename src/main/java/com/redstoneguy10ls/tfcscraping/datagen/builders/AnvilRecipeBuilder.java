package com.redstoneguy10ls.tfcscraping.datagen.builders;

import net.dries007.tfc.common.component.forge.ForgeRule;
import net.dries007.tfc.common.recipes.AnvilRecipe;
import net.dries007.tfc.common.recipes.outputs.ItemStackProvider;
import net.dries007.tfc.util.Metal;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AnvilRecipeBuilder implements RecipeBuilder
{
    private final Ingredient input;
    private final int minTier;
    private final List<ForgeRule> rules;
    private final boolean applyForgingBonus;
    private final ItemStackProvider resultProvider;
    private final Item result;
    private final Factory<?> factory;

    public AnvilRecipeBuilder(Ingredient input, int minTier, List<ForgeRule> rules, boolean applyForgingBonus, ItemStackProvider output, Factory<?> factory)
    {
        this.input = input;
        this.minTier = minTier;
        this.rules = rules;
        this.applyForgingBonus = applyForgingBonus;
        this.resultProvider = output;
        this.result = output.stack().getItem();
        this.factory = factory;
    }

    /**
     * Required implementation by {@link RecipeBuilder}, but as we don't want to lock recipes behind any criterion, we make this a no-op
     */
    @Override
    public AnvilRecipeBuilder unlockedBy(String name, Criterion<?> criterion)
    {
        return this;
    }

    /**
     * Required implementation by {@link RecipeBuilder}, but as we don't divide minting recipes into groups, we make this a no-op
     */
    @Override
    public AnvilRecipeBuilder group(@Nullable String groupName)
    {
        return this;
    }

    @Override
    public Item getResult()
    {
        return this.result;
    }

    public static AnvilRecipeBuilder anvil(Ingredient input, int minTier, List<ForgeRule> rules, boolean applyForgingBonus, ItemLike output)
    {
        return new AnvilRecipeBuilder(input, minTier, rules, applyForgingBonus, ItemStackProvider.of(output), AnvilRecipe::new);
    }


    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation resourceLocation)
    {
        AnvilRecipe anvilRecipe = this.factory.create(this.input, this.minTier, this.rules, this.applyForgingBonus, this.resultProvider);
        recipeOutput.accept(resourceLocation.withPrefix("anvil/"), anvilRecipe, null);
    }


    public interface Factory<T extends AnvilRecipe>
    {
        T create(Ingredient input, int minTier, List<ForgeRule> rules, boolean applyForgingBonus, ItemStackProvider output);
    }
}
