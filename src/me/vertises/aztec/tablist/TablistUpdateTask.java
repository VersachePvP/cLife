package me.vertises.aztec.tablist;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TablistUpdateTask implements Runnable {

	@Override
	public void run() {
		TablistManager manager = TablistManager.INSTANCE;
		if (manager == null) return;
		List<Player> players = new ArrayList<>();
        for(Player online : Bukkit.getServer().getOnlinePlayers()) {
            players.add(online);
        }
		players.forEach(player -> {
			Tablist tablist = manager.getTablist(player);
			if (tablist != null) {
				tablist.hideRealPlayers().update();
			}
		});
	}
}