package com.axelgamer.potionutility.util;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class InjectorUtil {
    public static List<MobEffectInstance> combineEffects(List<MobEffectInstance> target, List<MobEffectInstance> effector) {
        List<MobEffectInstance> combinedEffects = new ArrayList<>();
        
        List<Integer> targetRemover = new ArrayList<>();
        List<Integer> effectorRemover = new ArrayList<>();

        for (int i = 0; i < target.size(); i++) {
            MobEffectInstance targetEffect = target.get(i);
            int finalI = i;
            IntStream.range(0, effector.size()).forEach(y -> {
                MobEffectInstance effect = effector. get(y);
                if (targetEffect.getEffect().value() == effect.getEffect().value()) {
                    combinedEffects.add(new MobEffectInstance((Holder<MobEffect>) targetEffect.getEffect(), Math.max(targetEffect.getDuration(), effect.getDuration()), Math.max(targetEffect.getAmplifier(), effect.getAmplifier())));
                    targetRemover.add(finalI);
                    effectorRemover.add(y);
                }
            });
        }

        targetRemover.forEach(index -> {
            target.remove((int) index);
        });

        effectorRemover.forEach(index -> {
            effector.remove((int) index);
        });

        combinedEffects.addAll(target);
        combinedEffects.addAll(effector);

        return combinedEffects;
    }
}
