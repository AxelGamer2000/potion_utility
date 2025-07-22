package com.axelgamer.potionutility.registry.dataComponentPredicate;

import com.axelgamer.potionutility.registry.ModDataComponents;
import com.axelgamer.potionutility.registry.dataComponent.PotionLevel;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SingleComponentItemPredicate;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.predicates.DataComponentPredicate;
import net.minecraft.world.item.CrossbowItem;

public record PotionLevelPredicate(MinMaxBounds.Ints level) implements SingleComponentItemPredicate<PotionLevel> {
    public static final Codec<PotionLevelPredicate> CODEC = RecordCodecBuilder.create(instance -> instance.group(MinMaxBounds.Ints.CODEC.optionalFieldOf("level", MinMaxBounds.Ints.ANY).forGetter(PotionLevelPredicate::level)).apply(instance, PotionLevelPredicate::new));
    CrossbowItem.ChargeType

    @Override
    public DataComponentType<PotionLevel> componentType() {
        return ModDataComponents.POTION_LEVEL.get();
    }

    @Override
    public boolean matches(PotionLevel potionLevel) {
        return this.level.matches(potionLevel.level());
    }
}
