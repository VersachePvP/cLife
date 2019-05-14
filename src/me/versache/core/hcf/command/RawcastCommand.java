package me.versache.core.hcf.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.versache.core.utilities.util.Color;

public class RawcastCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] args) {
		StringBuilder sb = new StringBuilder();
		for(String s : args) {
			sb.append(s+" ");
		}
		
		Bukkit.broadcastMessage(Color.translate(sb.toString()));
		return false;
	}
	
	

}
