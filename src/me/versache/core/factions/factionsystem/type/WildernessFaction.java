package me.versache.core.factions.factionsystem.type;

import org.bukkit.command.CommandSender;

import me.versache.core.ConfigurationService;

import java.util.Map;

public class WildernessFaction extends Faction {
	public WildernessFaction() {
		super("Wilderness");
	}

	public WildernessFaction(final Map<String, Object> map) {
		super(map);
	}

	@Override
	public String getDisplayName(final CommandSender sender) {
		return ConfigurationService.WILDERNESS_COLOUR + this.getName();
	}
}
