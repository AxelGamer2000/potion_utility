package com.axelgamer.potionutility.registry.menu;

import com.axelgamer.potionutility.registry.ModMenus;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

public class PotionInjectorStandMenu extends AbstractContainerMenu {
    private Container potionInjectorStand;
    private ContainerData potionInjectorStandData;

    public PotionInjectorStandMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, new SimpleContainer(4));
    }

    public PotionInjectorStandMenu(int containerId, Inventory playerInventory, Container blockContainer) {
        super(ModMenus.POTION_INJECTOR_STAND_MENU.get(), containerId);
        this.potionInjectorStand = blockContainer;
        checkContainerSize(blockContainer, 4);

        addSlot(new Slot(blockContainer, 0, 56, 51));
        addSlot(new Slot(blockContainer, 1, 79, 58));
        addSlot(new Slot(blockContainer, 2, 102, 51));
        addSlot(new Slot(blockContainer, 3, 79, 17));

        addStandardInventorySlots(playerInventory, 8, 84);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < 4) {
                if (!this.moveItemStackTo(itemstack1, 4, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, 4, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }


    @Override
    public boolean stillValid(Player player) {
        return this.potionInjectorStand.stillValid(player);
    }
}
