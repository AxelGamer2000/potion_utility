package com.axelgamer.potionutility.datagen.model;

import com.axelgamer.potionutility.PotionUtility;
import com.axelgamer.potionutility.registry.ModBlocks;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output, String modId) {
        super(output, modId);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {

        ResourceLocation potionInjectorStandBlockModelLoc = ResourceLocation.fromNamespaceAndPath(PotionUtility.MODID, "block/potion_injector_stand");

        blockModels.registerSimpleItemModel(ModBlocks.POTION_INJECTOR_STAND.get(), ResourceLocation.fromNamespaceAndPath(PotionUtility.MODID, "block/potion_injector_stand"));
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(ModBlocks.POTION_INJECTOR_STAND.get(),
                new MultiVariant(WeightedList.of(
                        new Weighted<>(
                                new Variant(potionInjectorStandBlockModelLoc), 5
                        ).value()
            ))));
    }
}
