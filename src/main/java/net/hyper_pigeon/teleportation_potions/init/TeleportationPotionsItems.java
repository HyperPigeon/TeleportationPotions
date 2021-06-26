package net.hyper_pigeon.teleportation_potions.init;

import net.hyper_pigeon.teleportation_potions.item.RecallPotionItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TeleportationPotionsItems {
    public static final RecallPotionItem RECALL_POTION_ITEM = new RecallPotionItem(new Item.Settings().group(ItemGroup.BREWING).maxCount(1));

    public static void init(){
        Registry.register(Registry.ITEM,new Identifier("teleportation_potions","recall_potion"), RECALL_POTION_ITEM);
    }
}
