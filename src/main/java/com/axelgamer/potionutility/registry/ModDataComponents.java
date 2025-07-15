package com.axelgamer.potionutility.registry;

import com.axelgamer.potionutility.PotionUtility;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModDataComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENT_TYPES = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, PotionUtility.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> POTION_LEVEL = DATA_COMPONENT_TYPES.registerComponentType(
           "potion_level",
            integerBuilder ->  integerBuilder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT)
    );
}
