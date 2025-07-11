package com.axelgamer.potionutility.registry;

import com.axelgamer.potionutility.PotionUtility;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(PotionUtility.MODID);

    public static final DeferredItem<BlockItem> POTION_INJECTOR_STAND_ITEM = ITEMS.registerSimpleBlockItem("potion_injector_stand", ModBlocks.POTION_INJECTOR_STAND);
}
