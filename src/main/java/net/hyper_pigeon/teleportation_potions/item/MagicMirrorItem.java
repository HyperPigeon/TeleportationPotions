package net.hyper_pigeon.teleportation_potions.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;

public class MagicMirrorItem extends Item {
    public MagicMirrorItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        user.getItemCooldownManager().set(Identifier.of("teleportation_potions","magic_mirror"), 60);
        if (user instanceof ServerPlayerEntity player) {
            if (player.getRespawn()!=null) {
                if (player.getServer() != null)
                    player.teleport(player.getServer().getWorld(player.getRespawn().dimension()), player.getRespawn().pos().getX(), player.getRespawn().pos().getY(), player.getRespawn().pos().getZ(), null,0,0, false);
            } else if (player.getServer()!=null && player.getServer().getOverworld()!=null) {
                BlockPos worldSpawn = player.getServer().getOverworld().getSpawnPos();
                player.teleport(player.getServer().getOverworld(), worldSpawn.getX(), worldSpawn.getY(), worldSpawn.getZ(), new HashSet<>(),0,0, false);
            }

            user.playSoundToPlayer(SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.PLAYERS, 1, 0.5f);
        }
        return ActionResult.PASS;
    }
}
