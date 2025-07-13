package com.axelgamer.potionutility.registry.block;

import com.axelgamer.potionutility.registry.blockEntity.PotionInjectorStandBlockEntity;
import com.fasterxml.jackson.databind.util.ArrayIterator;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.gui.screens.inventory.BrewingStandScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BrewingStandBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PotionInjectorStandBlock extends BaseEntityBlock {
    public PotionInjectorStandBlock(Properties properties) {
        super(properties);
    }

    private static final Iterator<Integer> POTION_SLOTS = List.of(0, 1, 2).iterator();

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(PotionInjectorStandBlock::new);
    }

    VoxelShape SHAPE = Shapes.or(Block.column((double)2.0F, (double)2.0F, (double)14.0F), Block.column((double)14.0F, (double)0.0F, (double)2.0F));

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new PotionInjectorStandBlockEntity(blockPos, blockState);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof PotionInjectorStandBlockEntity potionInjectorStandBlockEntity) {
                if (!player.isCrouching()) {
                    player.openMenu(potionInjectorStandBlockEntity);
                } else {
                    Iterator<MobEffectInstance> effector = potionInjectorStandBlockEntity.getItem(3).get(DataComponents.POTION_CONTENTS).getAllEffects().iterator();
                    List<MobEffectInstance> effector_list = new ArrayList<>();
                    effector.forEachRemaining(effector_list::add);

                    POTION_SLOTS.forEachRemaining(index -> {

                        if (!(potionInjectorStandBlockEntity.getItem(index) == ItemStack.EMPTY)) {

                        }
                    });
                }
            }
        }
        return InteractionResult.SUCCESS;
    }
}
