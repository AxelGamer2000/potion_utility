package com.axelgamer.potionutility.registry.blockEntity;

import com.axelgamer.potionutility.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class PotionInjectorStandBlockEntity extends BlockEntity {
    public PotionInjectorStandBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.POTION_INJECTOR_STAND_BLOCK_ENTITY.get(), pos, blockState);
    }
}
