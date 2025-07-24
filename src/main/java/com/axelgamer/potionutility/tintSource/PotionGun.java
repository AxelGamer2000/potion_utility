package com.axelgamer.potionutility.tintSource;

import com.axelgamer.potionutility.registry.ModDataComponents;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.ARGB;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import org.jetbrains.annotations.Nullable;

public record PotionGun(int defaultColor) implements ItemTintSource {
    public static final MapCodec<PotionGun> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(ExtraCodecs.RGB_COLOR_CODEC.fieldOf("default").forGetter(PotionGun::defaultColor)).apply(instance, PotionGun::new));

    public PotionGun() {
        this(-13083194);
    }

    @Override
    public int calculate(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity) {
        PotionContents potionContents = (PotionContents) itemStack.get(ModDataComponents.CHARGED_POTION).potionItem().get(DataComponents.POTION_CONTENTS);
        return potionContents != null ? ARGB.opaque(potionContents.getColorOr(defaultColor)) : ARGB.opaque(defaultColor);
    }

    @Override
    public MapCodec<? extends ItemTintSource> type() {
        return MAP_CODEC;
    }
}
