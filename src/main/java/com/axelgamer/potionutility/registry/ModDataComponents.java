package com.axelgamer.potionutility.registry;

import com.axelgamer.potionutility.PotionUtility;
import com.axelgamer.potionutility.registry.dataComponent.ChargedPotion;
import com.axelgamer.potionutility.registry.dataComponent.PotionLevel;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModDataComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENT_TYPES = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, PotionUtility.MODID);

    public static final Supplier<DataComponentType<PotionLevel>> POTION_LEVEL = DATA_COMPONENT_TYPES.registerComponentType(
            "potion_level",
            builder -> builder.persistent(PotionLevel.CODEC).networkSynchronized(PotionLevel.STREAM_CODEC)
    );

    public static final Supplier<DataComponentType<ChargedPotion>> CHARGED_POTION = DATA_COMPONENT_TYPES.registerComponentType(
            "potion_charged",
            builder -> builder.persistent(ChargedPotion.CODEC).networkSynchronized(ChargedPotion.STREAM_CODEC)
    );
}
