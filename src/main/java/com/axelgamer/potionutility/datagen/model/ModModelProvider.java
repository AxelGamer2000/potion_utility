package com.axelgamer.potionutility.datagen.model;

import com.axelgamer.potionutility.PotionUtility;
import com.axelgamer.potionutility.registry.ModBlocks;
import com.axelgamer.potionutility.registry.ModDataComponentsPredicates;
import com.axelgamer.potionutility.registry.ModItems;
import com.axelgamer.potionutility.registry.dataComponentPredicate.ChargedPotionSelect;
import com.axelgamer.potionutility.registry.dataComponentPredicate.PotionLevelPredicate;
import com.axelgamer.potionutility.registry.item.PotionGunItem;
import com.axelgamer.potionutility.tintSource.PotionGun;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.client.color.item.Constant;
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
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.client.renderer.item.properties.conditional.ComponentMatches;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperties;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.core.Holder;
import net.minecraft.core.component.predicates.DataComponentPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.client.model.generators.template.ExtendedModelTemplate;
import net.neoforged.neoforge.client.model.generators.template.ExtendedModelTemplateBuilder;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
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

        itemModels.itemModelOutput.accept(ModItems.POTION_GUN.get(),
                new SelectItemModel.Unbaked(
                        new SelectItemModel.UnbakedSwitch(
                                new ChargedPotionSelect(),
                                List.of(
                                       new SelectItemModel.SwitchCase(
                                               List.of(PotionGunItem.ChargedPotionType.EMPTY),
                                               new BlockModelWrapper.Unbaked(
                                                       modLocation("item/potion_gun_empty"),
                                                       Collections.emptyList()
                                               )
                                       ),
                                        new SelectItemModel.SwitchCase(
                                                List.of(PotionGunItem.ChargedPotionType.BOTTLE),
                                                new BlockModelWrapper.Unbaked(
                                                        modLocation("item/potion_gun_bottle"),
                                                        Collections.emptyList()
                                                )
                                        ),
                                        new SelectItemModel.SwitchCase(
                                                List.of(PotionGunItem.ChargedPotionType.FILLED),
                                                new BlockModelWrapper.Unbaked(
                                                        modLocation("item/potion_gun_filled"),
                                                        List.of(
                                                                new PotionGun(-13083194)
                                                        )
                                                )
                                        )
                                )

                        ),
                        Optional.of(
                                new BlockModelWrapper.Unbaked(
                                        ModelLocationUtils.getModelLocation(Items.EGG),
                                        Collections.emptyList()
                                )
                        )
                )
                );
    }

    private static void generateTwoLayerItemModelWithCustomSuffix(Item item, ResourceLocation layer0, ResourceLocation layer1, String suffix, BiConsumer<ResourceLocation, ModelInstance> output) {
        ExtendedModelTemplate template = ModelTemplates.FLAT_ITEM.extend()
                .suffix(suffix)
                .requiredTextureSlot(TextureSlot.LAYER0)
                .requiredTextureSlot(TextureSlot.LAYER1)
                .build();

        template.create(item, TextureMapping.layered(layer0, layer1), output);
    }

    private static void generateOneLayerItemModelWithCustomSuffix(Item item, ResourceLocation layer0, String suffix, BiConsumer<ResourceLocation, ModelInstance> output) {
        ExtendedModelTemplate template = ModelTemplates.FLAT_ITEM.extend()
                .suffix(suffix)
                .requiredTextureSlot(TextureSlot.LAYER0)
                .build();

        template.create(item, TextureMapping.layer0(layer0), output);
    }
}
