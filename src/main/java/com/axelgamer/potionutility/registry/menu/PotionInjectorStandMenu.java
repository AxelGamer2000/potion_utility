package com.axelgamer.potionutility.registry.menu;

import com.axelgamer.potionutility.registry.ModMenus;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;

public class PotionInjectorStandMenu extends AbstractContainerMenu {
    private Container potionInjectorStand;
    private ContainerData potionInjectorStandData;

    public PotionInjectorStandMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, new SimpleContainer(4), new SimpleContainerData(2));
    }

    protected PotionInjectorStandMenu(int containerId, Inventory playerInventory, Container blockContainer, ContainerData blockContainerData) {
        super(ModMenus.POTION_INJECTOR_STAND_MENU.get(), containerId);
        this.potionInjectorStand = blockContainer;
        this.potionInjectorStandData = blockContainerData;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return false;
    }
}
