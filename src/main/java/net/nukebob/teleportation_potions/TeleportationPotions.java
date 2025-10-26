package net.nukebob.teleportation_potions;

import net.fabricmc.api.ModInitializer;
import net.nukebob.teleportation_potions.init.TeleportationPotionsItems;

public class TeleportationPotions implements ModInitializer {
	@Override
	public void onInitialize() {
		TeleportationPotionsItems.init();
	}
}
