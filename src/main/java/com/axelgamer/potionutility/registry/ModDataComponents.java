package com.axelgamer.potionutility.registry;

import com.axelgamer.potionutility.PotionUtility;
import com.axelgamer.potionutility.registry.dataComponent.PotionLevel;
import com.axelgamer.potionutility.registry.dataComponentPredicate.PotionLevelPredicate;
import com.mojang.serialization.Codec;
import net.minecraft.client.renderer.item.properties.select.Charge;
import net.minecraft.core.component.DataComponentExactPredicate;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.predicates.DataComponentPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModDataComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENT_TYPES = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, PotionUtility.MODID);

    public static final Supplier<DataComponentType<PotionLevel>> POTION_LEVEL = DATA_COMPONENT_TYPES.registerComponentType(
            "potion_level",
            builder -> builder.persistent(PotionLevel.CODEC).networkSynchronized(PotionLevel.STREAM_CODEC)
    );

}
