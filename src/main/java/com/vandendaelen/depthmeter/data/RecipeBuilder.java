package com.vandendaelen.depthmeter.data;

import com.vandendaelen.depthmeter.items.DepthMeterItems;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.common.crafting.conditions.TagEmptyCondition;

import java.util.function.Consumer;

public class RecipeBuilder extends RecipeProvider implements IConditionBuilder {

    private static final ResourceLocation COPPER_LOCATION = new ResourceLocation("forge", "ingots/copper");
    private static final Tags.IOptionalNamedTag<Item> COPPER_TAG = ItemTags.createOptional(COPPER_LOCATION);

    public RecipeBuilder(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        ResourceLocation ID_NO_COPPER = new ResourceLocation("data_gen", "depthmeter_granite");
        ResourceLocation ID_HAS_COPPER = new ResourceLocation("data_gen", "depthmeter_copper");

        ConditionalRecipe.builder()
                .addCondition(
                        new TagEmptyCondition(COPPER_LOCATION)
                )
                .addRecipe(
                        ShapedRecipeBuilder
                                .shapedRecipe(DepthMeterItems.DEPTHMETER.get(), 1)
                                .patternLine(" G ")
                                .patternLine("GCG")
                                .patternLine(" G ")
                                .key('G', Blocks.GRANITE)
                                .key('C', Items.COMPASS)
                                .setGroup("")
                                .addCriterion("has_dirt", hasItem(Blocks.DIRT))::build
                )
                .generateAdvancement()
                .build(consumer, ID_NO_COPPER);

        ConditionalRecipe.builder()
                .addCondition(
                        not(
                                new TagEmptyCondition(COPPER_LOCATION)
                        )
                )
                .addRecipe(
                        ShapedRecipeBuilder
                                .shapedRecipe(DepthMeterItems.DEPTHMETER.get(), 1)
                                .patternLine(" C ")
                                .patternLine("CRC")
                                .patternLine(" C ")
                                .key('R', Items.REDSTONE)
                                .key('C', COPPER_TAG)
                                .setGroup("")
                                .addCriterion("has_dirt", hasItem(Blocks.DIRT))::build
                )
                .generateAdvancement()
                .build(consumer, ID_HAS_COPPER);
    }
}
