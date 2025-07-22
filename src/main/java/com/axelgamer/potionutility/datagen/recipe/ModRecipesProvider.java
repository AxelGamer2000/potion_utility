package com.axelgamer.potionutility.datagen.recipe;

import com.axelgamer.potionutility.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class ModRecipesProvider extends RecipeProvider {
    protected ModRecipesProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModItems.POTION_INJECTOR_STAND_ITEM)
                .pattern("ibi")
                .pattern("isi")
                .pattern("bbb")

                .define('b', Blocks.BLACKSTONE)
                .define('s', Blocks.BREWING_STAND)
                .define('i', Items.IRON_INGOT)

                .unlockedBy("has_brewing_stand", this.has(Items.BREWING_STAND))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModItems.LARGE_BOTTLE)
                .pattern("ggg")
                .pattern("gbg")
                .pattern("ggg")

                .define('g', Blocks.GLASS)
                .define('b', Items.GLASS_BOTTLE)

                .unlockedBy("has_glass_bottle", this.has(Items.GLASS_BOTTLE))
                .save(this.output);
    }

    public static class Runner extends RecipeProvider.Runner {

        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
            return new ModRecipesProvider(provider, recipeOutput);
        }

        @Override
        public String getName() {
            return "PotionUtility RecipeProvider";
        }
    }
}
