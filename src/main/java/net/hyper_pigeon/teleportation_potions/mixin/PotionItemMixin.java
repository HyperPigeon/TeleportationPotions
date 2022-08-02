package net.hyper_pigeon.teleportation_potions.mixin;

import net.hyper_pigeon.teleportation_potions.init.TeleportationPotionsItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.PotionItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PotionItem.class)
public abstract class PotionItemMixin extends Item {

    public PotionItemMixin(Settings settings) {
        super(settings);
    }

    public void saveRecallPos(ItemStack recallPotion, BlockPos pos){
        NbtCompound nbtCompound = recallPotion.getOrCreateNbt();
        if (!nbtCompound.contains("RecallPosX")) {
            nbtCompound.putInt("RecallPosX", pos.getX());
        }
        if (!nbtCompound.contains("RecallPosY")) {
            nbtCompound.putInt("RecallPosY", pos.getY());
        }
        if (!nbtCompound.contains("RecallPosZ")) {
            nbtCompound.putInt("RecallPosZ", pos.getZ());
        }
    }

//    public BlockPos getRecallPos(ItemStack recallPotion, ServerPlayerEntity playerEntity){
//        NbtCompound nbtCompound = recallPotion.getOrCreateTag();
//        if (!nbtCompound.contains("RecallPosX") || !nbtCompound.contains("RecallPosY") || !nbtCompound.contains("RecallPosZ")) {
//            return playerEntity.getSpawnPointPosition();
//        }
//        else {
//            BlockPos recallPos = new BlockPos(nbtCompound.getInt("RecallPosX"),nbtCompound.getInt("RecallPosY"),
//                    nbtCompound.getInt("RecallPosZ"));
//            return recallPos;
//        }
//    }

//    @Inject(at = @At("HEAD"), method = "use")
//    public void useRecall(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable callbackInfoReturnable){
//        ItemStack potionItem = user.getActiveItem();
//
//        System.out.println("check1");
//        System.out.println(potionItem.getItem().getName().toString());
//        if(potionItem.getItem().equals(TeleportationPotionsItems.RECALL_POTION_ITEM)){
//            System.out.println("check2");
//            BlockPos recallPos = getRecallPos(potionItem, (ServerPlayerEntity) user);
//            MinecraftServer server = user.getServer();
//            ServerTask teleport_task = new ServerTask((server.getTicks()) + 1, () -> ((ServerPlayerEntity)(user)).teleport(((ServerPlayerEntity) user).getServerWorld(), recallPos.getX(), recallPos.getY(), recallPos.getZ(), 5.0F, 5.0F));
//            server.send(teleport_task);
//            world.sendEntityStatus(user, (byte) 35);
//        }
//
//    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        ItemStack potionItem = context.getStack();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = context.getWorld().getBlockState(blockPos);
        Block block = blockState.getBlock();
        PlayerEntity playerEntity = context.getPlayer();
        if(block.equals(Blocks.RESPAWN_ANCHOR)){
            ItemStack recallPotion = new ItemStack(TeleportationPotionsItems.RECALL_POTION_ITEM);
            playerEntity.getMainHandStack().decrement(1);
            playerEntity.getInventory().insertStack(recallPotion);
            //ItemUsage.exchangeStack(potionItem, Objects.requireNonNull(context.getPlayer()), recallPotion);
            saveRecallPos(recallPotion, blockPos);
        }

        return super.useOnBlock(context);
    }



}
