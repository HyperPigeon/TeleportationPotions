package net.nukebob.teleportation_potions.init;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.nukebob.teleportation_potions.item.MagicMirrorItem;
import net.nukebob.teleportation_potions.item.RecallPotionItem;

import java.util.function.Function;

public class TeleportationPotionsItems {
    public static final RecallPotionItem RECALL_POTION = (RecallPotionItem) register("recall_potion",RecallPotionItem::new, new Item.Settings().maxCount(1).component(DataComponentTypes.CONSUMABLE, ConsumableComponents.DRINK));
    public static final MagicMirrorItem MAGIC_MIRROR = (MagicMirrorItem) register("magic_mirror",MagicMirrorItem::new, new Item.Settings().maxCount(1));


    public static final RegistryKey<ItemGroup> TELEPORTATION_POTIONS_ITEMS = RegistryKey.of(RegistryKeys.ITEM_GROUP,Identifier.of("teleportation_potions", "tp"));

    public static void init(){
        Registry.register(Registries.ITEM_GROUP, TELEPORTATION_POTIONS_ITEMS, FabricItemGroup.builder().icon(() -> new ItemStack(RECALL_POTION))
                .displayName(Text.translatable("itemgroup.teleportation_potions.tp"))
                .entries((displayContext, entries) -> {
                    entries.add(new ItemStack(RECALL_POTION));
                    entries.add(new ItemStack(MAGIC_MIRROR));
                }).build());
    }

    public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of("teleportation_potions", name));
        Item item = itemFactory.apply(settings.registryKey(itemKey));
        Registry.register(Registries.ITEM, itemKey, item);

        return item;
    }
}
