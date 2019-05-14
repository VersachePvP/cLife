package me.versache.core.utilities.config;

import java.io.File; 
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class PlayerData {
	static PlayerData instance = new PlayerData();
	Plugin p;
	FileConfiguration Data;
	File Datafile;

	public static PlayerData getInstance() {
		return instance;
	}

	public void setup(Plugin p) {
		this.p = p;
		this.Datafile = new File(p.getDataFolder(), "playerdata.yml");
		this.Data = YamlConfiguration.loadConfiguration(this.Datafile);
		if (!this.Datafile.exists()) {
			try {
				this.Datafile.createNewFile();
			} catch (IOException localIOException) {
			}
		}
	}

	public FileConfiguration getConfig() {
		return this.Data;
	}

	public void saveConfig() {
		try {
			this.Data.save(this.Datafile);
		} catch (IOException localIOException) {
		}
	}

	public PluginDescriptionFile getDescription() {
		return this.p.getDescription();
	}

	public void reloadConfig() {
		this.Data = YamlConfiguration.loadConfiguration(this.Datafile);
	}
}