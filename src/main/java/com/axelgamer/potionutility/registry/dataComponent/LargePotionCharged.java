package com.axelgamer.potionutility.registry.dataComponent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

public record LargePotionCharged(ItemStack potionItem) {
    public static final Codec<LargePotionCharged> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ItemStack.SINGLE_ITEM_CODEC.fieldOf("potion_item").forGetter(LargePotionCharged::potionItem)
    ).apply(instance, LargePotionCharged::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, LargePotionCharged> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC, LargePotionCharged::potionItem,
            LargePotionCharged::new
    );
}
