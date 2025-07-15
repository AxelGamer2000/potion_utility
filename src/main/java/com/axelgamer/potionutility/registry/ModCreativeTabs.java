package com.axelgamer.potionutility.registry;

import com.axelgamer.potionutility.PotionUtility;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PotionUtility.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.potionutility"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModItems.POTION_INJECTOR_STAND_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ModItems.POTION_INJECTOR_STAND_ITEM.get());
                output.accept(ModItems.LARGE_POTION.get());
            }).build());
}
