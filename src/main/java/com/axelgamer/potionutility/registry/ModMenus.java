package com.axelgamer.potionutility.registry;

import com.axelgamer.potionutility.PotionUtility;
import com.axelgamer.potionutility.registry.blockEntity.PotionInjectorStandBlockEntity;
import com.axelgamer.potionutility.registry.menu.PotionInjectorStandMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, PotionUtility.MODID);

    public static final Supplier<MenuType<PotionInjectorStandMenu>> POTION_INJECTOR_STAND_MENU = MENU_TYPES.register("potion_injector_stand_menu", () -> new MenuType<>(PotionInjectorStandMenu::new, FeatureFlags.DEFAULT_FLAGS));
}
