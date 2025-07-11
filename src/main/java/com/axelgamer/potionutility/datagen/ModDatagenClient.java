package com.axelgamer.potionutility.datagen;


import com.axelgamer.potionutility.PotionUtility;
import com.axelgamer.potionutility.datagen.model.ModModelProvider;
import com.axelgamer.potionutility.datagen.recipe.ModRecipesProvider;
import com.axelgamer.potionutility.datagen.translation.ModTranslationProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = PotionUtility.MODID)
public class ModDatagenClient {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(true, new ModTranslationProvider(output, PotionUtility.MODID, "en_us"));
        generator.addProvider(true, new ModRecipesProvider.Runner(output, lookupProvider));
        generator.addProvider(true, new ModModelProvider(output, PotionUtility.MODID));
    }
}
