package net.hyper_pigeon.teleportation_potions.item;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.HashSet;

public class RecallPotionItem extends PotionItem {
    public RecallPotionItem(Settings settings) {
        super(settings);
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        PlayerEntity playerEntity = user instanceof PlayerEntity ? (PlayerEntity)user : null;

        if (playerEntity==null&&user.getServer()!=null) {
            BlockPos worldSpawn = user.getServer().getOverworld().getSpawnPos();
            user.teleport(user.getServer().getOverworld(), worldSpawn.getX(), worldSpawn.getY(), worldSpawn.getZ(), null, 0,0,true);
        }

        if (playerEntity instanceof ServerPlayerEntity player) {
            player.getItemCooldownManager().set(Identifier.of("teleportation_potions","recall_potion"), 60);
            if (player.getRespawn()!=null) {
                if (player.getServer() != null)
                    player.teleport(player.getServer().getWorld(player.getRespawn().dimension()), player.getRespawn().pos().getX(), player.getRespawn().pos().getY(), player.getRespawn().pos().getZ(), null, 0f, 0f, false);
            }
            else if (player.getServer()!=null && player.getServer().getOverworld()!=null) {
                BlockPos worldSpawn = player.getServer().getOverworld().getSpawnPos();
                player.teleport(player.getServer().getOverworld(), worldSpawn.getX(), worldSpawn.getY(), worldSpawn.getZ(), new HashSet<>(),0,0, false);
            }
            Criteria.CONSUME_ITEM.trigger(player, stack);
            player.playSoundToPlayer(SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.PLAYERS, 1, 0.5f);
        }

        if (playerEntity != null) {
            playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
            if (!playerEntity.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }

        world.emitGameEvent(user, GameEvent.DRINK, user.getCameraPosVec(0));
        return stack;
    }
}
