package com.axelgamer.potionutility.registry.blockEntity;

import com.axelgamer.potionutility.PotionUtility;
import com.axelgamer.potionutility.registry.ModBlockEntities;
import com.axelgamer.potionutility.registry.menu.PotionInjectorStandMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class PotionInjectorStandBlockEntity extends BaseContainerBlockEntity {
    private NonNullList<ItemStack> items;

    public PotionInjectorStandBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.POTION_INJECTOR_STAND_BLOCK_ENTITY.get(), pos, blockState);
        this.items = NonNullList.withSize(4, ItemStack.EMPTY);
    }

    @Override
    protected Component getDefaultName() {
        if(!(getCustomName() == null)) {
            return getCustomName();
        }
        return Component.translatable("container.potion_injector");
    }

    @Override
    public @Nullable Component getCustomName() {
        return this.components().get(DataComponents.CUSTOM_NAME);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        this.items = nonNullList;
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new PotionInjectorStandMenu(i, inventory, this);
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }
}
