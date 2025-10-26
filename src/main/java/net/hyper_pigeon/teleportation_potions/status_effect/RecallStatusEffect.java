package net.hyper_pigeon.teleportation_potions.status_effect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class RecallStatusEffect extends InstantStatusEffect {
    public RecallStatusEffect(StatusEffectType statusEffectType, int i) {
        super(statusEffectType, i);
    }

//    public void applyInstantEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
//        if(target instanceof ServerPlayerEntity){
//            BlockPos spawnPos = ((ServerPlayerEntity)target).getSpawnPointPosition();
//            target.teleport(spawnPos.getX(),spawnPos.getY(),spawnPos.getZ());
//        }
//    }
}
