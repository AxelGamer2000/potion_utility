package com.axelgamer.potionutility.registry.dataComponentPredicate;

import com.axelgamer.potionutility.registry.ModDataComponents;
import com.axelgamer.potionutility.registry.ModItems;
import com.axelgamer.potionutility.registry.item.PotionGunItem;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.OnlyIns;
import org.jetbrains.annotations.Nullable;

public record ChargedPotionSelect() implements SelectItemModelProperty<PotionGunItem.ChargedPotionType> {
    public static final SelectItemModelProperty.Type<ChargedPotionSelect, PotionGunItem.ChargedPotionType> TYPE =
            SelectItemModelProperty.Type.create(MapCodec.unit(new ChargedPotionSelect()), PotionGunItem.ChargedPotionType.CODEC);

    @Override
    public @Nullable PotionGunItem.ChargedPotionType get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i, ItemDisplayContext itemDisplayContext) {
        if (itemStack.get(ModDataComponents.CHARGED_POTION).potionItem().isEmpty()) {
            return PotionGunItem.ChargedPotionType.EMPTY;
        } else {
            return itemStack.get(ModDataComponents.CHARGED_POTION).potionItem().is(ModItems.LARGE_POTION.get()) ? PotionGunItem.ChargedPotionType.FILLED : PotionGunItem.ChargedPotionType.BOTTLE;
        }
    }

    @Override
    public Codec<PotionGunItem.ChargedPotionType> valueCodec() {
        return PotionGunItem.ChargedPotionType.CODEC;
    }

    @Override
    public Type<? extends SelectItemModelProperty<PotionGunItem.ChargedPotionType>, PotionGunItem.ChargedPotionType> type() {
        return TYPE;
    }
}
