package com.axelgamer.potionutility.datagen.translation;


import com.axelgamer.potionutility.registry.ModBlocks;
import com.axelgamer.potionutility.registry.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ModTranslationProvider extends LanguageProvider {

    public ModTranslationProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void addTranslations() {
        this.add(ModBlocks.POTION_INJECTOR_STAND.toStack(), "Potion Injector Stand");
        this.add("itemGroup.potionutility", "Potion Utility");
        this.add("container.potion_injector", "Potion Injector Stand");
        this.add(ModItems.LARGE_BOTTLE.get(), "Large Bottle");

        BuiltInRegistries.POTION.forEach(potion -> {
            ResourceLocation key = BuiltInRegistries.POTION.getKey(potion);
            String name = key.getPath().replaceAll("_", " ");
            String translateName = Arrays.stream(name.split("\\s+"))
                    .map(w -> Character.toUpperCase(w.charAt(0)) + w.substring(1))
                    .collect(Collectors.joining(" "));
            this.add("item.potionutility.large_potion.effect."+key.getPath(), "Large Potion of "+translateName);
        });
    }
}
