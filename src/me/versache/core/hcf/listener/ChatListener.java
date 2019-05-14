package me.versache.core.hcf.listener;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.google.common.collect.ImmutableSet;

import me.versache.core.Base;
import me.versache.core.factions.factionsystem.event.FactionChatEvent;
import me.versache.core.factions.factionsystem.struct.ChatChannel;
import me.versache.core.factions.factionsystem.type.PlayerFaction;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;
public class ChatListener implements Listener
  {
  public static final List<String> blocked;
  public ChatListener(Base plugin)
  {
    this.plugin = plugin;
  }
  
  @SuppressWarnings({ "deprecation", "unchecked" })
@EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
  public void onPlayerChat(AsyncPlayerChatEvent event)
  {
    String message = event.getMessage();
    Player player = event.getPlayer();
    final PermissionUser user = PermissionsEx.getUser(player);
    PlayerFaction playerFaction = this.plugin.getFactionManager().getPlayerFaction(player);
    ChatChannel chatChannel = playerFaction == null ? ChatChannel.PUBLIC : playerFaction.getMember(player).getChatChannel();
    Set<Player> recipients = event.getRecipients();
    
    if ((chatChannel == ChatChannel.FACTION) || (chatChannel == ChatChannel.ALLIANCE))
    {
      if (!isGlobalChannel(message))
      {
        Collection<Player> online = playerFaction.getOnlinePlayers();
        if (chatChannel == ChatChannel.ALLIANCE)
        {
          Collection<PlayerFaction> allies = playerFaction.getAlliedFactions();
          for (PlayerFaction ally : allies) { 
            online.addAll(ally.getOnlinePlayers());
          }
        }
        recipients.retainAll(online);
        event.setFormat(chatChannel.getRawFormat(player));
        Bukkit.getPluginManager().callEvent(new FactionChatEvent(true, playerFaction, player, chatChannel, recipients, event.getMessage()));
        return;
      }
      message = message.substring(1, message.length()).trim();
      event.setMessage(message);
    }
    event.setCancelled(true);
    Boolean.valueOf(true);
    if (player.hasPermission("faction.removetag")) {
      Boolean.valueOf(true);
    }
    String rank = ChatColor.translateAlternateColorCodes('&', "&f" + PermissionsEx.getUser(player).getPrefix()).replace("_", " ");
    String displayName = player.getDisplayName();
    displayName = rank + displayName;
    ConsoleCommandSender console = Bukkit.getConsoleSender();
    for (final String word : blocked) {
	    if ((message.toLowerCase().contains(word)))
	    {
	      Bukkit.dispatchCommand(console, "tempmute " + player.getName() + " 5m Blocked Word -s");
	      Player[] arrayOfPlayer;
	      int j = (arrayOfPlayer = Bukkit.getOnlinePlayers()).length;
	      for (int i = 0; i < j; i++)
	      {
	        Player on = arrayOfPlayer[i];
	        if (on.hasPermission("command.mod")) {
	          on.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Filtered" + ChatColor.DARK_GRAY + "] " + ChatColor.RED + player.getDisplayName() + ChatColor.YELLOW + " attempted to say " + ChatColor.RED + message);
	        }
	      }
	      event.setCancelled(true);
	      return;
	    }
    }
    String tag = playerFaction == null ? ChatColor.DARK_RED + "-" : playerFaction.getDisplayName(console);
    console.sendMessage(Base.getPlugin().getConfig().getString("Chat.WithFaction").replace("%faction%", tag).replace("%prefix%", user.getPrefix()).replace("%player%", player.getName()).replace("%msg%", message).replaceAll("&", "§"));
    for (Player recipient : event.getRecipients())
    {
    	if (playerFaction != null)
    	{
    	      tag = playerFaction == null ? ChatColor.RED + "*" : playerFaction.getDisplayName(recipient);
    	      recipient.sendMessage (Base.getPlugin().getConfig().getString("Chat.WithFaction").replace("%faction%", tag).replace("%prefix%", user.getPrefix()).replace("%player%", player.getName()).replace("%msg%", message).replaceAll("&", "§"));
    	}
    	else
    	{
    		recipient.sendMessage(Base.getPlugin().getConfig().getString("Chat.WithoutFaction").replace("%prefix%", user.getPrefix()).replace("%player%", player.getName()).replace("%msg%", message).replaceAll("&", "§"));
        }
      }
  }
  
  private boolean isGlobalChannel(String input)
  {
    int length = input.length();
    if ((length <= 1) || (!input.startsWith("!"))) {
      return false;
    }
    int i = 1;
    while (i < length)
    {
      char character = input.charAt(i);
      if (character == ' ')
      {
        i++;
      }
      else
      {
        if (character != '/') {
          break;
        }
        return false;
      }
    }
    return true;
  }
  
  static
  {
	blocked = Arrays.asList("hitler", "kys", "nigga", "kill yourself", "nigger", "rip off", "nigg3r", "Niggers", "Niggger", "Niga", "Niggerz", "shit staff", "shit server", "gay server", "kys", "leaked", "ddos", "kill yourself", "shit owner", "join my server", "new hcf server", "server is shit", "server is crap", "bad server", "kult", "this is shit", "customkkk", "niggerfaggot", "faggot", "heil");  
    ImmutableSet.builder();
  }
  
  private final Base plugin;
}
