package me.versache.core.hcf.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import me.versache.core.Base;
import me.versache.core.ConfigurationService;

/**
 * Listener that prevents the brewing of illegal
 * {@link org.bukkit.potion.PotionEffectType}s.
 */
public class PotionLimitListener implements Listener {

	private static final int EMPTY_BREW_TIME = 400;

	private final Base plugin;

	public PotionLimitListener(Base plugin) {
		this.plugin = plugin;
	}


	private boolean testValidity(ItemStack[] contents) {
		for (ItemStack stack : contents) {

			if (stack != null && stack.getType() == Material.POTION && stack.getDurability() != 0) {
				Potion potion = Potion.fromItemStack(stack);

				// Just to be safe, null check this.
				if (potion == null) {
					continue;
				}

				// Mundane potions etc, can return a null type
				PotionType type = potion.getType();
				if (type == null) {
					continue;
				}

				// I suck at naming methods & stuff
				if (potion.hasExtendedDuration() && ConfigurationService.isExtendedDurationDisallowed(type)) {
					return false;
				}

				if (type == PotionType.POISON && !potion.hasExtendedDuration() && potion.getLevel() == 1) {
					continue;
				}

				if (potion.getLevel() > ConfigurationService.getPotionLimit(type)) {
					return false;
				}
			}
		}

		return true;
	}
}
