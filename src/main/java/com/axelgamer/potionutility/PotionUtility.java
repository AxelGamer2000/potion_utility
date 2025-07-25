package com.axelgamer.potionutility;

import com.axelgamer.potionutility.registry.*;
import com.axelgamer.potionutility.registry.dataComponentPredicate.ChargedPotionSelect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.neoforged.neoforge.client.event.RegisterSelectItemModelPropertyEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(PotionUtility.MODID)
public class PotionUtility {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "potionutility";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "potionutility" namespace

    // Create a Deferred Register to hold Items which will all be registered under the "potionutility" namespace

    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "potionutility" namespace


    // Creates a new Block with the id "potionutility:example_block", combining the namespace and path

    // Creates a new BlockItem with the id "potionutility:example_block", combining the namespace and path


    // Creates a new food item with the id "potionutility:example_id", nutrition 1 and saturation 2


    // Creates a creative tab with the id "potionutility:example_tab" for the example item, that is placed after the combat tab


    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public PotionUtility(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        ModDataComponentsPredicates.DATA_COMPONENT_PREDICATE_TYPES.register(modEventBus);
        ModDataComponents.DATA_COMPONENT_TYPES.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITY_TYPES.register(modEventBus);
        ModMenus.MENU_TYPES.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::registerSelectProperties);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }

        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());

        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    @SubscribeEvent
    public void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
    }

    public void registerSelectProperties(RegisterSelectItemModelPropertyEvent event) {
        event.register(
                ResourceLocation.fromNamespaceAndPath(MODID, "charged_potion"),
                ChargedPotionSelect.TYPE
        );
    }
}
