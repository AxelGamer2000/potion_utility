package com.axelgamer.potionutility.registry;

import com.axelgamer.potionutility.PotionUtility;
import com.axelgamer.potionutility.registry.item.LargePotionItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.Consumables;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(PotionUtility.MODID);

    public static final DeferredItem<BlockItem> POTION_INJECTOR_STAND_ITEM = ITEMS.registerSimpleBlockItem("potion_injector_stand", ModBlocks.POTION_INJECTOR_STAND);
    public static final DeferredItem<Item> LARGE_POTION = ITEMS.registerItem("large_potion", LargePotionItem::new, new Item.Properties().stacksTo(1).component(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).component(DataComponents.CONSUMABLE, Consumables.DEFAULT_DRINK).component(ModDataComponents.POTION_LEVEL.value(), 3));
}
