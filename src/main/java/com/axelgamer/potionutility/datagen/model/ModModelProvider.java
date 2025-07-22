package com.axelgamer.potionutility.datagen.model;

import com.axelgamer.potionutility.PotionUtility;
import com.axelgamer.potionutility.registry.ModBlocks;
import com.axelgamer.potionutility.registry.ModDataComponentsPredicates;
import com.axelgamer.potionutility.registry.ModItems;
import com.axelgamer.potionutility.registry.dataComponentPredicate.PotionLevelPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.client.color.item.ItemTintSources;
import net.minecraft.client.color.item.Potion;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.client.renderer.item.BlockModelWrapper;
import net.minecraft.client.renderer.item.ConditionalItemModel;
import net.minecraft.client.renderer.item.properties.conditional.ComponentMatches;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperties;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.core.Holder;
import net.minecraft.core.component.predicates.DataComponentPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.template.ExtendedModelTemplateBuilder;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

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
        itemModels.generateFlatItem(ModItems.LARGE_BOTTLE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.itemModelOutput.accept(ModItems.LARGE_POTION.get(), new ConditionalItemModel.Unbaked(
                new ComponentMatches(
                        new DataComponentPredicate.Single<PotionLevelPredicate>(ModDataComponentsPredicates.POTION_LEVEL_PREDICATE.get(), new PotionLevelPredicate(MinMaxBounds.Ints.exactly(2)))
                ),
                new BlockModelWrapper.Unbaked(
                        ResourceLocation.fromNamespaceAndPath(PotionUtility.MODID, "item/large_potion_1"),
                        List.of(
                                new Potion(0xFF385DC6)
                        )
                ),
                new ConditionalItemModel.Unbaked(
                        new ComponentMatches(new DataComponentPredicate.Single<PotionLevelPredicate>(ModDataComponentsPredicates.POTION_LEVEL_PREDICATE.get(), new PotionLevelPredicate(MinMaxBounds.Ints.exactly(1)))),
                        new BlockModelWrapper.Unbaked(
                                ResourceLocation.fromNamespaceAndPath(PotionUtility.MODID, "item/large_potion_2"),
                                List.of(
                                        new Potion(0xFF385DC6)
                                )
                        ),
                        new BlockModelWrapper.Unbaked(
                                ResourceLocation.fromNamespaceAndPath(PotionUtility.MODID, "item/large_potion_0"),
                                List.of(
                                        new Potion(0xFF385DC6)
                                )
                        )
                )
        ));

    }
}
