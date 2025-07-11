package com.axelgamer.potionutility.registry;

import com.axelgamer.potionutility.PotionUtility;
import com.axelgamer.potionutility.registry.block.PotionInjectorStandBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(PotionUtility.MODID);

    public static final DeferredBlock<Block> POTION_INJECTOR_STAND = BLOCKS.registerBlock("potion_injector_stand", PotionInjectorStandBlock::new,BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).sound(SoundType.IRON).strength(1f).noOcclusion());
}
