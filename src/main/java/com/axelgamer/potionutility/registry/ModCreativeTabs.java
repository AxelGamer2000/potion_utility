package com.axelgamer.potionutility.registry;

import com.axelgamer.potionutility.PotionUtility;
import com.axelgamer.potionutility.registry.dataComponent.PotionLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ModCreativeTabs {
    private static final List<Integer> POTION_LEVELS = new ArrayList<>(List.of(1, 2, 3));

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PotionUtility.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.potionutility"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModItems.POTION_INJECTOR_STAND_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                BuiltInRegistries.POTION.forEach(potion -> {
                    POTION_LEVELS.forEach(level -> {
                        ItemStack stack1 = new ItemStack(ModItems.LARGE_POTION.get());
                        stack1.set(DataComponents.POTION_CONTENTS, new PotionContents(BuiltInRegistries.POTION.wrapAsHolder(potion)));
                        stack1.set(ModDataComponents.POTION_LEVEL, new PotionLevel(level));
                        output.accept(stack1);
                    });
                });
                output.accept(ModItems.LARGE_BOTTLE.get());
                output.accept(ModItems.POTION_INJECTOR_STAND_ITEM.get());
            }).build());
}
