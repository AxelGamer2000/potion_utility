package com.axelgamer.potionutility.registry.dataComponent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record PotionLevel(int level) {
    public static final Codec<PotionLevel> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("level").forGetter(PotionLevel::level)
        ).apply(instance, PotionLevel::new)
    );

    public static final StreamCodec<ByteBuf, PotionLevel> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, PotionLevel::level,
            PotionLevel::new
    );
}
