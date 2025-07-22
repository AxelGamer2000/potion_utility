package com.axelgamer.potionutility.registry;

import com.axelgamer.potionutility.PotionUtility;
import com.axelgamer.potionutility.registry.dataComponentPredicate.PotionLevelPredicate;
import net.minecraft.core.component.predicates.DataComponentPredicate;
import net.minecraft.core.component.predicates.DataComponentPredicates;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;

public class ModDataComponentsPredicates {
    public static final DeferredRegister<DataComponentPredicate.Type<?>> DATA_COMPONENT_PREDICATE_TYPES = DeferredRegister.create(Registries.DATA_COMPONENT_PREDICATE_TYPE, PotionUtility.MODID);

    public static final DeferredHolder<DataComponentPredicate.Type<?>, DataComponentPredicate.Type<PotionLevelPredicate>> POTION_LEVEL_PREDICATE = DATA_COMPONENT_PREDICATE_TYPES.register("potion_level",() -> new DataComponentPredicate.Type<>(PotionLevelPredicate.CODEC));
}
