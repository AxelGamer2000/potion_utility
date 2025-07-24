package com.axelgamer.potionutility.registry.item;

import com.axelgamer.potionutility.PotionUtility;
import com.axelgamer.potionutility.registry.ModDataComponents;
import com.axelgamer.potionutility.registry.ModItems;
import com.axelgamer.potionutility.registry.dataComponent.ChargedPotion;
import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

public class PotionGunItem extends Item {
    public PotionGunItem(Properties properties) {
        super(properties.component(ModDataComponents.CHARGED_POTION, new ChargedPotion(ItemStack.EMPTY)));
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (!other.isEmpty()) {
            return false;
        }
        if (action == ClickAction.SECONDARY) {
            if (!stack.get(ModDataComponents.CHARGED_POTION).potionItem().isEmpty()) {
                access.set(stack.get(ModDataComponents.CHARGED_POTION).potionItem());
                stack.set(ModDataComponents.CHARGED_POTION, new ChargedPotion(ItemStack.EMPTY));
                return true;
            }
        }
        return false;
    }

    public enum ChargedPotionType implements StringRepresentable {
        EMPTY("empty"),
        BOTTLE("bottle"),
        FILLED("filled");

        public static final Codec<ChargedPotionType> CODEC = StringRepresentable.fromEnum(ChargedPotionType::values);
        private String name;

        ChargedPotionType(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}
