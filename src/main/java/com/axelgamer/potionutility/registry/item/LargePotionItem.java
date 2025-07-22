package com.axelgamer.potionutility.registry.item;

import com.axelgamer.potionutility.PotionUtility;
import com.axelgamer.potionutility.registry.ModDataComponents;
import com.axelgamer.potionutility.registry.ModItems;
import com.axelgamer.potionutility.registry.dataComponent.PotionLevel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class LargePotionItem extends PotionItem {
    public LargePotionItem(Properties properties) {
        super(properties.component(ModDataComponents.POTION_LEVEL, new PotionLevel(3)));
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack itemStack = super.getDefaultInstance();
        itemStack.set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.WATER));
        itemStack.set(ModDataComponents.POTION_LEVEL, new PotionLevel(3));
        return itemStack;
    }

    private int getPotionLevel(ItemStack itemStack) {
        return itemStack.get(ModDataComponents.POTION_LEVEL).level();
    }

    private void setPotionLevel(ItemStack itemStack, int level) {
        itemStack.set(ModDataComponents.POTION_LEVEL, new PotionLevel(level));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        ItemStack newStack = stack.copy();
        newStack.setCount(1);
        if (livingEntity instanceof Player player) {
            if (player.gameMode().isSurvival()) {
                if (!(getPotionLevel(stack) < 2)) {
                    setPotionLevel(newStack, getPotionLevel(newStack) - 1);
                    livingEntity.setItemInHand(livingEntity.getUsedItemHand(), newStack);
                } else {
                    livingEntity.setItemInHand(livingEntity.getUsedItemHand(), new ItemStack(ModItems.LARGE_BOTTLE.get()));
                }
            }
        }
        return super.finishUsingItem(stack, level, livingEntity);
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (other.isEmpty()) {
            return false;
        }
        if (action == ClickAction.PRIMARY) {
            if (stack.get(DataComponents.POTION_CONTENTS).equals(other.get(DataComponents.POTION_CONTENTS))) {
                if (getPotionLevel(stack) < 3) {
                    playBottleFill(player);
                    setPotionLevel(stack, getPotionLevel(stack) + 1);
                    access.set(new ItemStack(Items.GLASS_BOTTLE));
                }
            } else if (other.is(Items.GLASS_BOTTLE)) {
                playBottleEmpty(player);
                ItemStack potion = new ItemStack(Items.POTION);
                potion.set(DataComponents.POTION_CONTENTS, stack.get(DataComponents.POTION_CONTENTS));

                if (!(getPotionLevel(stack) < 2)) {
                    setPotionLevel(stack, getPotionLevel(stack) - 1);
                    if (other.getCount() > 1) {
                        ItemStack bottle = other.copy();
                        bottle.setCount(bottle.getCount() - 1);
                        player.addItem(potion);
                        access.set(bottle);
                    } else {
                        access.set(potion);
                    }
                } else {
                    slot.set(new ItemStack(ModItems.LARGE_BOTTLE.get()));
                    if (other.getCount() > 1) {
                        ItemStack bottle = other.copy();
                        bottle.setCount(bottle.getCount() - 1);
                        player.addItem(potion);
                        access.set(bottle);
                    } else {
                        access.set(potion);
                    }
                }
            } else if (other.is(Items.BUCKET)) {
                playBucketEmpty(player);
                if (getPotionLevel(stack) == 3) {
                    if (stack.get(DataComponents.POTION_CONTENTS).equals(new PotionContents(Potions.WATER)) || stack.get(DataComponents.POTION_CONTENTS).equals(new PotionContents(Potions.AWKWARD)) || stack.get(DataComponents.POTION_CONTENTS).equals(new PotionContents(Potions.MUNDANE)) || stack.get(DataComponents.POTION_CONTENTS).equals(new PotionContents(Potions.THICK))) {
                        slot.set(new ItemStack(ModItems.LARGE_BOTTLE.get()));
                        if (other.getCount() > 1) {
                            ItemStack bottle = other.copy();
                            bottle.setCount(bottle.getCount() - 1);
                            player.addItem(new ItemStack(Items.WATER_BUCKET));
                            access.set(bottle);
                        } else {
                            access.set(new ItemStack(Items.WATER_BUCKET));
                        }
                    }
                }
            }
            return true;
        }
        return super.overrideOtherStackedOnMe(stack, other, slot, action, player, access);
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
        ItemStack other = slot.getItem();
        if (slot.getItem().isEmpty()) {
            return false;
        }
        if (action == ClickAction.PRIMARY) {
            if (slot.getItem().is(Items.GLASS_BOTTLE)) {
                playBottleEmpty(player);
                ItemStack potion = new ItemStack(Items.POTION);
                potion.set(DataComponents.POTION_CONTENTS, stack.get(DataComponents.POTION_CONTENTS));
                slot.set(potion);
                if (!(getPotionLevel(stack) < 2)) {
                    setPotionLevel(stack, getPotionLevel(stack) - 1);
                } else {
                    player.containerMenu.setCarried(new ItemStack(ModItems.LARGE_BOTTLE.get()));
                }
            } else if (slot.getItem().is(Items.POTION) || slot.getItem().is(Items.SPLASH_POTION) || slot.getItem().is(Items.LINGERING_POTION)) {
                if (stack.get(DataComponents.POTION_CONTENTS).equals(slot.getItem().get(DataComponents.POTION_CONTENTS))) {
                    if (getPotionLevel(stack) < 3) {
                        playBottleFill(player);
                        setPotionLevel(stack, getPotionLevel(stack) + 1);
                        slot.set(new ItemStack(Items.GLASS_BOTTLE));
                    }
                }
            } else if (slot.getItem().is(Items.BUCKET)) {
                if (getPotionLevel(stack) == 3) {
                    if (stack.get(DataComponents.POTION_CONTENTS).equals(new PotionContents(Potions.WATER)) || stack.get(DataComponents.POTION_CONTENTS).equals(new PotionContents(Potions.AWKWARD)) || stack.get(DataComponents.POTION_CONTENTS).equals(new PotionContents(Potions.MUNDANE)) || stack.get(DataComponents.POTION_CONTENTS).equals(new PotionContents(Potions.THICK))) {
                        playBucketFill(player);
                        player.containerMenu.setCarried(new ItemStack(ModItems.LARGE_BOTTLE.get()));
                        if (other.getCount() > 1) {
                            ItemStack bottle = other.copy();
                            bottle.setCount(bottle.getCount() - 1);
                            player.addItem(new ItemStack(Items.WATER_BUCKET));
                            slot.set(bottle);
                        } else {
                            slot.set(new ItemStack(Items.WATER_BUCKET));
                        }
                    }
                }
            }
            return true;
        }
        return false;
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
