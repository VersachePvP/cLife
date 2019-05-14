package me.vertises.aztec.tablist.example;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import me.vertises.aztec.tablist.TablistEntrySupplier;

public class ExampleSupplier implements TablistEntrySupplier {

	@Override
	public Table<Integer, Integer, String> getEntries(Player player) {
		Table<Integer, Integer, String> table = HashBasedTable.create();
		table.put(1, 10, ChatColor.BLUE + "What a good example!");
		return table;
	}

	@Override
	public String getHeader(Player player) {
		return "Godly header";
	}

	@Override
	public String getFooter(Player player) {
		return "Godly footer";
	}

}
