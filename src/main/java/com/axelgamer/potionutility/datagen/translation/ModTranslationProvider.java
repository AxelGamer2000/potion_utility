package com.axelgamer.potionutility.datagen.translation;


import com.axelgamer.potionutility.registry.ModBlocks;
import com.axelgamer.potionutility.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModTranslationProvider extends LanguageProvider {

    public ModTranslationProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void addTranslations() {
        this.add(ModBlocks.POTION_INJECTOR_STAND.toStack(), "Potion Injector Stand");
        this.add("itemGroup.potionutility", "Potion Utility");
    }
}
