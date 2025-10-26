package net.hyper_pigeon.teleportation_potions.item;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class RecallPotionItem extends Item {
    public RecallPotionItem(Settings settings) {
        super(settings);
    }


//    public void saveRecallPos(ItemStack recallPotion, BlockPos pos){
//        NbtCompound nbtCompound = recallPotion.getOrCreateTag();
//        if (!nbtCompound.contains("RecallPosX")) {
//            nbtCompound.putInt("RecallPosX", pos.getX());
//        }
//        if (!nbtCompound.contains("RecallPosY")) {
//            nbtCompound.putInt("RecallPosY", pos.getY());
//        }
//        if (!nbtCompound.contains("RecallPosZ")) {
//            nbtCompound.putInt("RecallPosZ", pos.getZ());
//        }
//    }


    public BlockPos getRecallPos(ItemStack recallPotion, ServerPlayerEntity playerEntity){
        NbtCompound nbtCompound = recallPotion.getOrCreateTag();
        if (!nbtCompound.contains("RecallPosX") || !nbtCompound.contains("RecallPosY") || !nbtCompound.contains("RecallPosZ")) {
            return playerEntity.getSpawnPointPosition();
        }
        else {
            BlockPos recallPos = new BlockPos(nbtCompound.getInt("RecallPosX"),nbtCompound.getInt("RecallPosY"),
                    nbtCompound.getInt("RecallPosZ"));
            return recallPos;
        }
    }


    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        PlayerEntity playerEntity = user instanceof PlayerEntity ? (PlayerEntity)user : null;

        if (playerEntity instanceof ServerPlayerEntity && (playerEntity.world.getRegistryKey() == World.NETHER)) {
            Criteria.CONSUME_ITEM.trigger((ServerPlayerEntity)playerEntity, stack);
            BlockPos recallPos = getRecallPos(stack, (ServerPlayerEntity) user);

            if (playerEntity.hasVehicle()) {
                playerEntity.requestTeleportAndDismount(recallPos.getX(), recallPos.getY(), recallPos.getZ());
            } else {
                playerEntity.requestTeleport(recallPos.getX(), recallPos.getY(), recallPos.getZ());
            }
        }

        if (playerEntity != null) {
            playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
            if (!playerEntity.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }

        if (playerEntity == null || !playerEntity.getAbilities().creativeMode) {
            if (stack.isEmpty()) {
                return new ItemStack(Items.GLASS_BOTTLE);
            }

            if (playerEntity != null) {
                playerEntity.getInventory().insertStack(new ItemStack(Items.GLASS_BOTTLE));
            }
        }

        world.emitGameEvent(user, GameEvent.DRINKING_FINISH, user.getCameraBlockPos());
        return stack;
    }

    public int getMaxUseTime(ItemStack stack) {
        return 32;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }



}
