package net.hyper_pigeon.teleportation_potions;

import net.fabricmc.api.ModInitializer;
import net.hyper_pigeon.teleportation_potions.init.TeleportationPotionsItems;

public class TeleportationPotions implements ModInitializer {
	@Override
	public void onInitialize() {
		TeleportationPotionsItems.init();
	}
}
