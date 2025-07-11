package com.axelgamer.potionutility.datagen;

import com.axelgamer.potionutility.PotionUtility;
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
public class ModDatagenServer {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Server event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();


    }
}
