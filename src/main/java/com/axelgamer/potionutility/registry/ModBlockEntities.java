package com.axelgamer.potionutility.registry;

import com.axelgamer.potionutility.PotionUtility;
import com.axelgamer.potionutility.registry.blockEntity.PotionInjectorStandBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, PotionUtility.MODID);

    public static final Supplier<BlockEntityType<PotionInjectorStandBlockEntity>> POTION_INJECTOR_STAND_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register(
                    "potion_injector_stand_entity",

                    () -> new BlockEntityType<>(
                            PotionInjectorStandBlockEntity::new,
                            false,
                            ModBlocks.POTION_INJECTOR_STAND.get()
                    )
            );
}
