package com.axelgamer.potionutility.registry.item;

import com.axelgamer.potionutility.registry.ModDataComponents;
import com.axelgamer.potionutility.registry.ModItems;
import com.axelgamer.potionutility.registry.dataComponent.ChargedPotion;
import com.axelgamer.potionutility.registry.dataComponent.PotionLevel;
import net.minecraft.client.color.item.Dye;
import net.minecraft.client.color.item.Potion;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class LargeBottleItem extends Item {
    public LargeBottleItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        ItemStack stack = new ItemStack(ModItems.LARGE_POTION.get());
        stack.set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.WATER));
        stack.setCount(1);

        HitResult hit = player.pick(5.0d, 0.0f, true);
        if (hit.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHit = (BlockHitResult) hit;
            BlockState state = context.getLevel().getBlockState(blockHit.getBlockPos());

            if (state.getBlock() == Blocks.WATER || state.getFluidState().is(Fluids.WATER)) {
                player.swing(player.getUsedItemHand());
                context.getLevel().playSound(player, blockHit.getBlockPos(), SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!context.getLevel().isClientSide) {
                    ItemStack bucket = player.getItemInHand(player.getUsedItemHand());

                    if (bucket.getCount() > 1 || player.gameMode().isCreative()) {
                        bucket.consume(1, player);
                        player.addItem(stack);
                    } else {
                        player.setItemInHand(player.getUsedItemHand(), stack);
                    }

                    context.getLevel().setBlock(blockHit.getBlockPos(), Blocks.AIR.defaultBlockState(), 0);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (other.isEmpty()) {
            return false;
        }
        if (action == ClickAction.PRIMARY) {
            if (other.is(Items.POTION) || other.is(Items.SPLASH_POTION) || other.is(Items.LINGERING_POTION)) {
                playBottleEmpty(player);
                ItemStack large = new ItemStack(ModItems.LARGE_POTION.get());
                large.set(ModDataComponents.POTION_LEVEL, new PotionLevel(1));
                large.set(DataComponents.POTION_CONTENTS, other.get(DataComponents.POTION_CONTENTS));
                slot.set(large);
                access.set(new ItemStack(Items.GLASS_BOTTLE));
            } else if (other.is(Items.WATER_BUCKET)) {
                playBucketFill(player);
                ItemStack large = new ItemStack(ModItems.LARGE_POTION.get());
                large.set(ModDataComponents.POTION_LEVEL, new PotionLevel(3));
                large.set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.WATER));
                slot.set(large);
                access.set(new ItemStack(Items.BUCKET));
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
        ItemStack other = slot.getItem();
        if (other.isEmpty()) {
            return false;
        }
        if (action == ClickAction.PRIMARY) {
            if (other.is(Items.WATER_BUCKET)) {
                playBucketEmpty(player);
                ItemStack large = new ItemStack(ModItems.LARGE_POTION.get());
                large.set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.WATER));
                large.set(ModDataComponents.POTION_LEVEL, new PotionLevel(3));
                player.containerMenu.setCarried(large);
                slot.set(new ItemStack(Items.BUCKET));
            } else if (other.is(Items.POTION) || other.is(Items.SPLASH_POTION) || other.is(Items.LINGERING_POTION)) {
                playBottleEmpty(player);
                ItemStack large = new ItemStack(ModItems.LARGE_POTION.get());
                large.set(DataComponents.POTION_CONTENTS, other.get(DataComponents.POTION_CONTENTS));
                large.set(ModDataComponents.POTION_LEVEL, new PotionLevel(1));
                player.containerMenu.setCarried(large);
                slot.set(new ItemStack(Items.GLASS_BOTTLE));
            } else if (other.is(ModItems.POTION_GUN)) {
                other.set(ModDataComponents.CHARGED_POTION, new ChargedPotion(stack));
                player.containerMenu.setCarried(ItemStack.EMPTY);
            }
        }
        return true;
    }

    private static void playBucketFill(Entity entity) {
        entity.playSound(SoundEvents.BUCKET_FILL, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    private static void playBucketEmpty(Entity entity) {
        entity.playSound(SoundEvents.BUCKET_EMPTY, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    private static void playBottleFill(Entity entity) {
        entity.playSound(SoundEvents.BOTTLE_FILL, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    private static void playBottleEmpty(Entity entity) {
        entity.playSound(SoundEvents.BOTTLE_EMPTY, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }
}
