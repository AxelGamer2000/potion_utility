package com.axelgamer.potionutility.registry.block;

import com.axelgamer.potionutility.PotionUtility;
import com.axelgamer.potionutility.registry.ModDataComponents;
import com.axelgamer.potionutility.registry.ModItems;
import com.axelgamer.potionutility.registry.blockEntity.PotionInjectorStandBlockEntity;
import com.axelgamer.potionutility.registry.dataComponent.PotionLevel;
import com.axelgamer.potionutility.util.InjectorUtil;
import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PotionInjectorStandBlock extends BaseEntityBlock {
    public PotionInjectorStandBlock(Properties properties) {
        super(properties);
    }

    private static final Iterable<Integer> POTION_SLOTS = List.of(0, 1, 2);

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(PotionInjectorStandBlock::new);
    }

    VoxelShape SHAPE = Shapes.or(Block.column(2.0F, 2.0F, 14.0F), Block.column(14.0F, 0.0F, 2.0F));

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
                potionInjectorStandBlockEntity.setChanged();
                if (!player.isCrouching()) {
                    player.openMenu(potionInjectorStandBlockEntity);
                } else {
                    if (craftPossible(potionInjectorStandBlockEntity)) {
                        List<Integer> occupiedSlotsList = occupiedSlots(StreamSupport.stream(POTION_SLOTS.spliterator(), false).collect(Collectors.toList()), potionInjectorStandBlockEntity);

                        occupiedSlotsList.forEach(index -> {
                            Iterable<MobEffectInstance> effector = potionInjectorStandBlockEntity.getItem(3).get(DataComponents.POTION_CONTENTS).getAllEffects();
                            List<MobEffectInstance> effector_list = new ArrayList<>();
                            effector.forEach(effector_list::add);

                            ItemStack stack = potionInjectorStandBlockEntity.getItem(index).copy();
                            if (!stack.isEmpty()) {
                                Iterable<MobEffectInstance> target = potionInjectorStandBlockEntity.getItem(index).get(DataComponents.POTION_CONTENTS).getAllEffects();
                                List<MobEffectInstance> target_list = new ArrayList<>();
                                target.forEach(target_list::add);

                                stack.set(DataComponents.POTION_CONTENTS, new PotionContents(Optional.empty(), Optional.empty(), InjectorUtil.combineEffects(target_list, effector_list), Optional.empty()));
                                potionInjectorStandBlockEntity.setItem(index, stack);
                            }

                        });

                        if (potionInjectorStandBlockEntity.getItem(3).get(ModDataComponents.POTION_LEVEL).level() == occupiedSlotsList.size()) {
                            potionInjectorStandBlockEntity.setItem(3, new ItemStack(ModItems.LARGE_BOTTLE.get()));
                        } else {
                            potionInjectorStandBlockEntity.getItem(3).set(ModDataComponents.POTION_LEVEL, new PotionLevel(potionInjectorStandBlockEntity.getItem(3).get(ModDataComponents.POTION_LEVEL).level() - occupiedSlotsList.size()));
                        }
                    } else {
                        player.displayClientMessage(Component.literal("Craft not possible").withStyle(style -> style.withColor(ChatFormatting.RED).withBold(true)), true);
                    }
                }
            }
        }

        if (level.isClientSide) {
            if (player.isCrouching()) {
                Minecraft.getInstance().getSoundManager().play(
                        new SimpleSoundInstance(
                                SoundEvents.BREWING_STAND_BREW,
                                SoundSource.BLOCKS,
                                1.0f,
                                1.0f,
                                RandomSource.create(),
                                pos.getX(),
                                pos.getY(), SoundInstance.createUnseededRandom().nextDouble()
                        ));
            }
        }
        return InteractionResult.SUCCESS;
    }

    public static boolean craftPossible(PotionInjectorStandBlockEntity potionInjectorStandBlockEntity) {
        AtomicBoolean skip = new AtomicBoolean(false);
        if (potionInjectorStandBlockEntity.getItem(3).isEmpty()) {
            return false;
        } else if (potionInjectorStandBlockEntity.getItem(0).isEmpty() && potionInjectorStandBlockEntity.getItem(1).isEmpty() && potionInjectorStandBlockEntity.getItem(2).isEmpty()) {
            return false;
        } else if (!potionInjectorStandBlockEntity.getItem(3).is(ModItems.LARGE_POTION)) {
            return false;
        } else if (potionInjectorStandBlockEntity.getItem(3).get(ModDataComponents.POTION_LEVEL).level() < occupiedSlots(StreamSupport.stream(POTION_SLOTS.spliterator(), false).collect(Collectors.toList()), potionInjectorStandBlockEntity).size()) {
            return false;
        }

        POTION_SLOTS.forEach(slot -> {
            if (potionInjectorStandBlockEntity.getItem(slot).is(ModItems.LARGE_POTION.get())) {
                skip.set(true);
            }
        });

        if (skip.get()) {
            return false;
        }
        return true;
    }

    public static List<Integer> occupiedSlots(List<Integer> slots, PotionInjectorStandBlockEntity potionInjectorStandBlockEntity) {
        List<Integer> remover = new ArrayList<>();

        slots.forEach(slot -> {
            if (potionInjectorStandBlockEntity.getItem(slot).isEmpty()) {
                remover.add(slot);
            }
        });

        remover.forEach(slots::remove);

        PotionUtility.LOGGER.info(slots.toString());
        return slots;
    }
}
