package com.axelgamer.potionutility.registry.dataComponent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

public record ChargedPotion(ItemStack potionItem) {
    public static final Codec<ChargedPotion> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ItemStack.SINGLE_ITEM_CODEC.fieldOf("potion_item").forGetter(ChargedPotion::potionItem)
    ).apply(instance, ChargedPotion::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ChargedPotion> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC, ChargedPotion::potionItem,
            ChargedPotion::new
    );
}
