package com.axelgamer.potionutility.util;

import net.minecraft.world.effect.MobEffectInstance;

import java.util.Iterator;
import java.util.List;

public class InjectorUtil {
    public static List<MobEffectInstance> combineEffects(List<MobEffectInstance> target, List<MobEffectInstance> effector) {
        target.addAll(effector);
        return target;
    }
}
