package com.vandendaelen.depthmeter.data;

import com.vandendaelen.depthmeter.items.DepthMeterItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class RecipeBuilder extends RecipeProvider implements IConditionBuilder {

    public RecipeBuilder(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {

        ShapedRecipeBuilder
                .shaped(DepthMeterItems.DEPTHMETER.get(), 1)
                .pattern(" C ")
                .pattern("CRC")
                .pattern(" C ")
                .define('R', Items.REDSTONE)
                .define('C', Items.COPPER_INGOT)
                .group("")
                .unlockedBy("has_dirt", has(Items.COPPER_INGOT))
                .save(consumer);
    }
}
