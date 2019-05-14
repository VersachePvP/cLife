package me.versache.core;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import lombok.Getter;
import me.versache.core.factions.combatlog.CombatLogListener;
import me.versache.core.factions.combatlog.CustomEntityRegistration;
import me.versache.core.factions.factionsystem.FactionExecutor;
import me.versache.core.factions.factionsystem.FactionManager;
import me.versache.core.factions.factionsystem.FactionMember;
import me.versache.core.factions.factionsystem.FlatFileFactionManager;
import me.versache.core.factions.factionsystem.claim.Claim;
import me.versache.core.factions.factionsystem.claim.ClaimHandler;
import me.versache.core.factions.factionsystem.claim.ClaimWandListener;
import me.versache.core.factions.factionsystem.claim.Subclaim;
import me.versache.core.factions.factionsystem.type.ClaimableFaction;
import me.versache.core.factions.factionsystem.type.EndPortalFaction;
import me.versache.core.factions.factionsystem.type.Faction;
import me.versache.core.factions.factionsystem.type.GlowstoneFaction;
import me.versache.core.factions.factionsystem.type.PlayerFaction;
import me.versache.core.factions.factionsystem.type.RoadFaction;
import me.versache.core.factions.factionsystem.type.SpawnFaction;
import me.versache.core.hcf.balance.EconomyCommand;
import me.versache.core.hcf.balance.EconomyManager;
import me.versache.core.hcf.balance.FlatFileEconomyManager;
import me.versache.core.hcf.balance.PayCommand;
import me.versache.core.hcf.balance.ShopSignListener;
import me.versache.core.hcf.classes.PvpClassManager;
import me.versache.core.hcf.classes.archer.ArcherClass;
import me.versache.core.hcf.classes.bard.BardRestorer;
import me.versache.core.hcf.classes.type.RogueClass;
import me.versache.core.hcf.command.BroadcastCommand;
import me.versache.core.hcf.command.ClearCommand;
import me.versache.core.hcf.command.CobbleCommand;
import me.versache.core.hcf.command.CoordsCommand;
import me.versache.core.hcf.command.CrowbarCommand;
import me.versache.core.hcf.command.DevCommand;
import me.versache.core.hcf.command.DiscordCommand;
import me.versache.core.hcf.command.EnchantCommand;
import me.versache.core.hcf.command.EndPortalCommand;
import me.versache.core.hcf.command.FeedCommand;
import me.versache.core.hcf.command.FightCommand;
import me.versache.core.hcf.command.FixCommand;
import me.versache.core.hcf.command.FlyCommand;
import me.versache.core.hcf.command.FreezeCommand;
import me.versache.core.hcf.command.GMCCommand;
import me.versache.core.hcf.command.GMSCommand;
import me.versache.core.hcf.command.GameModeCommand;
import me.versache.core.hcf.command.GiveCommand;
import me.versache.core.hcf.command.GodCommand;
import me.versache.core.hcf.command.GoppleCommand;
import me.versache.core.hcf.command.HatCommand;
import me.versache.core.hcf.command.HealCommand;
import me.versache.core.hcf.command.HelpCommand;
import me.versache.core.hcf.command.InvSeeCommand;
import me.versache.core.hcf.command.ItemCommand;
import me.versache.core.hcf.command.KillCommand;
import me.versache.core.hcf.command.LFFCommand;
import me.versache.core.hcf.command.LagCommand;
import me.versache.core.hcf.command.ListCommand;
import me.versache.core.hcf.command.LockdownCommand;
import me.versache.core.hcf.command.LogoutCommand;
import me.versache.core.hcf.command.MapKitCommand;
import me.versache.core.hcf.command.MedicCommand;
import me.versache.core.hcf.command.MessageCommand;
import me.versache.core.hcf.command.MiscCommands;
import me.versache.core.hcf.command.MoreCommand;
import me.versache.core.hcf.command.OreStatsCommand;
import me.versache.core.hcf.command.PanicCommand;
import me.versache.core.hcf.command.PlayTimeCommand;
import me.versache.core.hcf.command.PlayerVaultCommand;
import me.versache.core.hcf.command.PvpTimerCommand;
import me.versache.core.hcf.command.RandomCommand;
import me.versache.core.hcf.command.RawcastCommand;
import me.versache.core.hcf.command.RefundCommand;
import me.versache.core.hcf.command.RenameCommand;
import me.versache.core.hcf.command.ReplyCommand;
import me.versache.core.hcf.command.ResetCommand;
import me.versache.core.hcf.command.SetBorderCommand;
import me.versache.core.hcf.command.SetCommand;
import me.versache.core.hcf.command.SkullCommand;
import me.versache.core.hcf.command.SpawnCommand;
import me.versache.core.hcf.command.SpawnerCommand;
import me.versache.core.hcf.command.StaffGuideCommand;
import me.versache.core.hcf.command.StaffModeCommand;
import me.versache.core.hcf.command.StatsCommand;
import me.versache.core.hcf.command.TLCommand;
import me.versache.core.hcf.command.TeamspeakCommand;
import me.versache.core.hcf.command.TeleportAllCommand;
import me.versache.core.hcf.command.TeleportCommand;
import me.versache.core.hcf.command.TeleportHereCommand;
import me.versache.core.hcf.command.ToggleMessageCommand;
import me.versache.core.hcf.command.TopCommand;
import me.versache.core.hcf.command.VanishCommand;
import me.versache.core.hcf.command.WhoisCommand;
import me.versache.core.hcf.command.WorldCommand;
import me.versache.core.hcf.deathban.Deathban;
import me.versache.core.hcf.deathban.DeathbanListener;
import me.versache.core.hcf.deathban.DeathbanManager;
import me.versache.core.hcf.deathban.FlatFileDeathbanManager;
import me.versache.core.hcf.deathban.lives.LivesExecutor;
import me.versache.core.hcf.deathban.lives.StaffReviveCommand;
import me.versache.core.hcf.event.CaptureZone;
import me.versache.core.hcf.event.EventExecutor;
import me.versache.core.hcf.event.EventScheduler;
import me.versache.core.hcf.event.conquest.ConquestExecutor;
import me.versache.core.hcf.event.eotw.EOTWHandler;
import me.versache.core.hcf.event.eotw.EotwCommand;
import me.versache.core.hcf.event.eotw.EotwListener;
import me.versache.core.hcf.event.faction.CapturableFaction;
import me.versache.core.hcf.event.faction.ConquestFaction;
import me.versache.core.hcf.event.faction.KothFaction;
import me.versache.core.hcf.event.glmountain.GlowstoneMountain;
import me.versache.core.hcf.event.koth.KothExecutor;
import me.versache.core.hcf.listener.AutoSmeltOreListener;
import me.versache.core.hcf.listener.BookDeenchantListener;
import me.versache.core.hcf.listener.BorderListener;
import me.versache.core.hcf.listener.BottledExpListener;
import me.versache.core.hcf.listener.ChatListener;
import me.versache.core.hcf.listener.CoreListener;
import me.versache.core.hcf.listener.CrowbarListener;
import me.versache.core.hcf.listener.DeathListener;
import me.versache.core.hcf.listener.DeathMessageListener;
import me.versache.core.hcf.listener.ElevatorListener;
import me.versache.core.hcf.listener.EntityLimitListener;
import me.versache.core.hcf.listener.ExpMultiplierListener;
import me.versache.core.hcf.listener.FactionListener;
import me.versache.core.hcf.listener.FactionsCoreListener;
import me.versache.core.hcf.listener.FoundDiamondsListener;
import me.versache.core.hcf.listener.FurnaceSmeltSpeederListener;
import me.versache.core.hcf.listener.GodListener;
import me.versache.core.hcf.listener.KillMoneyListener;
import me.versache.core.hcf.listener.KillStreaks;
import me.versache.core.hcf.listener.KillstreakListener;
import me.versache.core.hcf.listener.KitMapListener;
import me.versache.core.hcf.listener.LoginEvent;
import me.versache.core.hcf.listener.PearlGlitchListener;
import me.versache.core.hcf.listener.PlayTimeManager;
import me.versache.core.hcf.listener.PotionLimitListener;
import me.versache.core.hcf.listener.SignSubclaimListener;
import me.versache.core.hcf.listener.SkullListener;
import me.versache.core.hcf.listener.StaffModeListener;
import me.versache.core.hcf.listener.UnRepairableListener;
import me.versache.core.hcf.listener.VanishListener;
import me.versache.core.hcf.listener.WorldListener;
import me.versache.core.hcf.listener.fixes.ArmorFixListener;
import me.versache.core.hcf.listener.fixes.BeaconStrengthFixListener;
import me.versache.core.hcf.listener.fixes.BlockHitFixListener;
import me.versache.core.hcf.listener.fixes.BlockJumpGlitchFixListener;
import me.versache.core.hcf.listener.fixes.BoatGlitchFixListener;
import me.versache.core.hcf.listener.fixes.BookQuillFixListener;
import me.versache.core.hcf.listener.fixes.CommandBlocker;
import me.versache.core.hcf.listener.fixes.DupeGlitchFix;
import me.versache.core.hcf.listener.fixes.EnchantLimitListener;
import me.versache.core.hcf.listener.fixes.EnderChestRemovalListener;
import me.versache.core.hcf.listener.fixes.HungerFixListener;
import me.versache.core.hcf.listener.fixes.InfinityArrowFixListener;
import me.versache.core.hcf.listener.fixes.NaturalMobSpawnFixListener;
import me.versache.core.hcf.listener.fixes.PexCrashFixListener;
import me.versache.core.hcf.listener.fixes.PortalListener;
import me.versache.core.hcf.listener.fixes.StrengthListener;
import me.versache.core.hcf.listener.fixes.SyntaxBlocker;
import me.versache.core.hcf.listener.fixes.VoidGlitchFixListener;
import me.versache.core.hcf.listener.fixes.WeatherFixListener;
import me.versache.core.hcf.scoreboard.ScoreboardHandler;
import me.versache.core.hcf.signs.EventSignListener;
import me.versache.core.hcf.signs.KitSignListener;
import me.versache.core.hcf.tab.HcfMapSupplier;
import me.versache.core.hcf.timer.TimerExecutor;
import me.versache.core.hcf.timer.TimerManager;
import me.versache.core.special.key.sale.KeySaleCommand;
import me.versache.core.special.key.sale.KeySaleListener;
import me.versache.core.special.reboot.RebootCommand;
import me.versache.core.special.sale.SaleCommand;
import me.versache.core.special.sale.SaleListener;
import me.versache.core.special.sotw.SotwCommand;
import me.versache.core.special.sotw.SotwListener;
import me.versache.core.special.sotw.SotwTimer;
import me.versache.core.special.stattracker.StatTrackListener;
import me.versache.core.utilities.config.PlayerData;
import me.versache.core.utilities.config.PotionLimiterData;
import me.versache.core.utilities.config.WorldData;
import me.versache.core.utilities.random.BasePlugins;
import me.versache.core.utilities.random.Cooldowns;
import me.versache.core.utilities.random.DateTimeFormats;
import me.versache.core.utilities.random.Message;
import me.versache.core.utilities.random.ServerHandler;
import me.versache.core.utilities.user.ConsoleUser;
import me.versache.core.utilities.user.FactionUser;
import me.versache.core.utilities.user.UserManager;
import me.versache.core.utilities.util.SignHandler;
import me.versache.core.utilities.util.itemdb.ItemDb;
import me.versache.core.utilities.util.itemdb.SimpleItemDb;
import me.versache.core.utilities.visualise.ProtocolLibHook;
import me.versache.core.utilities.visualise.VisualiseHandler;
import me.versache.core.utilities.visualise.WallBorderListener;
import me.vertises.aztec.tablist.TablistManager;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.util.com.google.common.base.Joiner;


public class Base<ReclaimManager> extends JavaPlugin implements CommandExecutor, Listener {

	private CombatLogListener combatLogListener;
	private me.versache.core.special.reclaim.ReclaimManager reclaimManager1;
    public static List<KillStreaks> killStreaks;
	public CombatLogListener getCombatLogListener() {
		return this.combatLogListener;
	}
	

	
	
	
    static {
        Base.killStreaks = new ArrayList<KillStreaks>();
    }
	
	public void onEnable() {
		plugin = this;

		BasePlugins.getPlugin().init(this);
		config = getConfig();
		config.options().copyDefaults(true);
		saveConfig();
		conf = new File(getDataFolder(), "config.yml");
		WorldData.getInstance().setup(this);
		PlayerData.getInstance().setup(this);
		PotionLimiterData.getInstance().setup(this);
	    Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + ("--------------------------------------------------------"));
	    Bukkit.getConsoleSender().sendMessage(ConfigurationService.PRIMARY_COLOR + ("cLife has been enabled"));
	    Bukkit.getConsoleSender().sendMessage(ConfigurationService.PRIMARY_COLOR + (" "));
	    Bukkit.getConsoleSender().sendMessage(ConfigurationService.PRIMARY_COLOR + ("Name: " + ChatColor.WHITE + getPlugin().getDescription().getName()));
	    Bukkit.getConsoleSender().sendMessage(ConfigurationService.PRIMARY_COLOR + ("Author: " + ChatColor.WHITE + getPlugin().getDescription().getAuthors()));
	    Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + ("--------------------------------------------------------"));
		ProtocolLibHook.hook(this);
		CustomEntityRegistration.registerCustomEntities();
		Plugin wep = Bukkit.getPluginManager().getPlugin("WorldEdit");
		this.craftBukkitVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		this.worldEdit = (((wep instanceof WorldEditPlugin)) && (wep.isEnabled()) ? (WorldEditPlugin) wep : null);
		
		registerConfiguration();
		registerCommands();
		registerManagers();
		registerListeners();
		
		  Bukkit.getPluginManager().registerEvents(this, this);
		  
		  if (getPlugin().getConfig().getBoolean("Tab.Enabled")) {
		new TablistManager(this, new HcfMapSupplier(this), TimeUnit.SECONDS.toMillis(1L));
		  }
        for (final String item : Base.plugin.getConfig().getConfigurationSection("killstreaks.items").getKeys(false)) {
            final String name = Base.plugin.getConfig().getString("killstreaks.items." + item + ".name");
            final int kills = Base.plugin.getConfig().getInt("killstreaks.items." + item + ".kills");
            final String command = Base.plugin.getConfig().getString("killstreaks.items." + item + ".command");
            final KillStreaks killsAdd = new KillStreaks(name, kills, command);
            Base.killStreaks.add(killsAdd);
        }
		Cooldowns.createCooldown("diamond_cooldown");
		Cooldowns.createCooldown("emerald_cooldown");
		Cooldowns.createCooldown("ruby_cooldown");
		Cooldowns.createCooldown("gold_cooldown");
		Cooldowns.createCooldown("archer_speed_cooldown");
		Cooldowns.createCooldown("archer_jump_cooldown");
		Cooldowns.createCooldown("rogue_speed_cooldown");
		Cooldowns.createCooldown("rogue_jump_cooldown");
		Cooldowns.createCooldown("rogue_cooldown");
		Cooldowns.createCooldown("lff_cooldown");
		

		
		
		
		new BukkitRunnable() {
	         public void run() {
	             long current = System.currentTimeMillis();
	             Bukkit.broadcastMessage(Base.plugin.getConfig().getString("general.save.start").replace('&', '§'));
	             System.gc();
					Base.this.saveData();
	          }

		}.runTaskTimerAsynchronously(this, TimeUnit.SECONDS.toMillis(1L), TimeUnit.SECONDS.toMillis(1L));
		
	    new BukkitRunnable() {

	        @Override
	        public void run() {
	          String players = Arrays.stream(Bukkit.getOnlinePlayers())
	              .filter(player -> player.hasPermission("command.toprank") && !player.isOp() && !player.hasPermission("*"))
					.map(Player::getName)
					.collect(Collectors.joining(", "));
	          
	          if (!players.isEmpty()) {
		      getServer().broadcastMessage("");
		      getServer().broadcastMessage(ConfigurationService.RANK_COLOR.toString() + ConfigurationService.TOP_RANK + " Users " + ChatColor.GOLD + "» " + ChatColor.WHITE + players);
	          getServer().broadcastMessage(ChatColor.GRAY + "You can purchase this rank at " + ConfigurationService.DONATE_URL + ".");
	          getServer().broadcastMessage("");
	          
	        }
	          
	        
	        }
	      }.runTaskTimer(this, 60L, 20 * 60 * 5);
	
	 new BukkitRunnable() {
		 
		 
	        public void run() {
          final World world = Base.this.getServer().getWorld("world");
          final List<Entity> entList = (List<Entity>)world.getEntities();
          for (final Entity current : entList) {
              if (current instanceof Item) {
                  current.remove();
              }
          }
      }
  }.runTaskTimer((Plugin)this, 10000L, 10000L);
}
	
	  
	private void saveData() {
		this.combatLogListener.removeCombatLoggers();
		this.deathbanManager.saveDeathbanData();
		this.economyManager.saveEconomyData();
		this.factionManager.saveFactionData();
		this.playTimeManager.savePlaytimeData();
		this.userManager.saveUserData();
		this.signHandler.cancelTasks(null);
		

		PlayerData.getInstance().saveConfig();
	}

	public void onDisable() {
		this.pvpClassManager.onDisable();
		this.scoreboardHandler.clearBoards();
		this.deathbanManager.saveDeathbanData();
		this.economyManager.saveEconomyData();
		this.factionManager.saveFactionData();
		this.playTimeManager.savePlaytimeData();
		this.userManager.saveUserData();
		StaffModeCommand.onDisableMod();
		plugin = null;
			Base.this.saveData();
	}
	

	private void registerConfiguration() {
		ConfigurationSerialization.registerClass(CaptureZone.class);
		ConfigurationSerialization.registerClass(Deathban.class);
		ConfigurationSerialization.registerClass(Claim.class);
		ConfigurationSerialization.registerClass(ConsoleUser.class);
		ConfigurationSerialization.registerClass(Subclaim.class);
		ConfigurationSerialization.registerClass(FactionUser.class);
		ConfigurationSerialization.registerClass(ClaimableFaction.class);
		ConfigurationSerialization.registerClass(ConquestFaction.class);
		ConfigurationSerialization.registerClass(CapturableFaction.class);
		ConfigurationSerialization.registerClass(KothFaction.class);
		ConfigurationSerialization.registerClass(GlowstoneFaction.class);
		ConfigurationSerialization.registerClass(EndPortalFaction.class);
		ConfigurationSerialization.registerClass(Faction.class);
		ConfigurationSerialization.registerClass(FactionMember.class);
		ConfigurationSerialization.registerClass(PlayerFaction.class);
		ConfigurationSerialization.registerClass(RoadFaction.class);
		ConfigurationSerialization.registerClass(SpawnFaction.class);
		ConfigurationSerialization.registerClass(RoadFaction.NorthRoadFaction.class);
		ConfigurationSerialization.registerClass(RoadFaction.EastRoadFaction.class);
		ConfigurationSerialization.registerClass(RoadFaction.SouthRoadFaction.class);
		ConfigurationSerialization.registerClass(RoadFaction.WestRoadFaction.class);
	}

	private void registerListeners() {
		PluginManager manager = getServer().getPluginManager();
		manager.registerEvents(new OreStatsCommand(), this);
		manager.registerEvents(new GodListener(), this);
		manager.registerEvents(new VanishListener(), this);
		manager.registerEvents(new ArcherClass(this), this);
		manager.registerEvents(new RogueClass(this), this);
		manager.registerEvents(new PotionLimitListener(this), this);
		manager.registerEvents(new LoginEvent(), this);
		manager.registerEvents(new DupeGlitchFix(), this);
		manager.registerEvents(new PortalListener(this), this);
		manager.registerEvents(new WeatherFixListener(), this);
		manager.registerEvents(this.combatLogListener = new CombatLogListener(this), this);
		manager.registerEvents(new NaturalMobSpawnFixListener(), this);
		manager.registerEvents(new AutoSmeltOreListener(), this);
		manager.registerEvents(new BlockHitFixListener(), this);
		manager.registerEvents(new BlockJumpGlitchFixListener(), this);
		manager.registerEvents(new CommandBlocker(), this);
		manager.registerEvents(new BoatGlitchFixListener(), this);
		manager.registerEvents(new BookDeenchantListener(), this);
		manager.registerEvents(new PexCrashFixListener(this), this);
		manager.registerEvents(new BookQuillFixListener(this), this);
		manager.registerEvents(new BorderListener(), this);
		manager.registerEvents(new ChatListener(this), this);
		manager.registerEvents(new ClaimWandListener(this), this);
		manager.registerEvents(new BottledExpListener(), this);
		manager.registerEvents(new CoreListener(this), this);
		manager.registerEvents(new CrowbarListener(this), this);
		manager.registerEvents(new DeathListener(this), this);
		manager.registerEvents(new ElevatorListener(this), this);
		manager.registerEvents(new DeathMessageListener(this), this);
		manager.registerEvents(new DeathbanListener(this), this);
		manager.registerEvents(new EnchantLimitListener(), this);
		manager.registerEvents(new EnderChestRemovalListener(), this);
		manager.registerEvents(new FlatFileFactionManager(this), this);
		manager.registerEvents(new StrengthListener(), this);
		manager.registerEvents(new KillMoneyListener(), this);

		manager.registerEvents(new ArmorFixListener(), this);
		manager.registerEvents(new EotwListener(this), this);
		manager.registerEvents(new EventSignListener(), this);
		manager.registerEvents(new ExpMultiplierListener(), this);
		manager.registerEvents(new FactionListener(this), this);
		manager.registerEvents(new FoundDiamondsListener(this), this);
		manager.registerEvents(new FurnaceSmeltSpeederListener(), this);
		manager.registerEvents(new KitMapListener(this), this);
		manager.registerEvents(new InfinityArrowFixListener(), this);
		manager.registerEvents(new HungerFixListener(), this);
		manager.registerEvents(new PearlGlitchListener(this), this);
		manager.registerEvents(new FactionsCoreListener(this), this);
		manager.registerEvents(new PearlGlitchListener(this), this);

		manager.registerEvents(new SignSubclaimListener(this), this);
		manager.registerEvents(new EndPortalCommand(getPlugin()), this);
		manager.registerEvents(new ShopSignListener(this), this);
		manager.registerEvents(new SkullListener(), this);
		manager.registerEvents(new BeaconStrengthFixListener(this), this);
		manager.registerEvents(new VoidGlitchFixListener(), this);
		manager.registerEvents(new WallBorderListener(this), this);
		manager.registerEvents(this.playTimeManager, this);
		manager.registerEvents(new WorldListener(this), this);
		manager.registerEvents(new UnRepairableListener(), this);
		manager.registerEvents(new StaffModeListener(), this);
		manager.registerEvents(new SyntaxBlocker(), this);
		manager.registerEvents(new SotwListener(this), this);
		manager.registerEvents(new KitSignListener(), this);
		manager.registerEvents(new SaleListener(), this);
		manager.registerEvents(new KeySaleListener(), this);
		manager.registerEvents(new EntityLimitListener(this), this);
		manager.registerEvents(new StatTrackListener(), this);
	    if(ConfigurationService.KIT_MAP == true) {
            manager.registerEvents((Listener)new KillstreakListener(this), (Plugin)this);
        }
	    //if(ConfigurationService.KIT_MAP == true) {
	    	//manager.registerEvents(new SelectorListener(), this);
	    }


	private void registerCommands() {

		getCommand("top").setExecutor(new TopCommand());
		getCommand("list").setExecutor(new ListCommand());
		getCommand("setborder").setExecutor(new SetBorderCommand());
		getCommand("hat").setExecutor(new HatCommand());
		getCommand("world").setExecutor(new WorldCommand());
		getCommand("endportal").setExecutor(new EndPortalCommand(getPlugin()));
		getCommand("fix").setExecutor(new FixCommand());
		getCommand("enchant").setExecutor(new EnchantCommand());
		getCommand("freeze").setExecutor(new FreezeCommand(this));
		getCommand("staffrevive").setExecutor(new StaffReviveCommand(this));
		getCommand("lag").setExecutor(new LagCommand());
		getCommand("broadcast").setExecutor(new BroadcastCommand());
		getCommand("togglemessage").setExecutor(new ToggleMessageCommand());
		getCommand("reply").setExecutor(new ReplyCommand());
		getCommand("message").setExecutor(new MessageCommand());
		getCommand("feed").setExecutor(new FeedCommand());
		getCommand("pv").setExecutor(new PlayerVaultCommand(this));
		getCommand("setspawn").setExecutor(new SpawnCommand());
		getCommand("medic").setExecutor(new MedicCommand(this));
		getCommand("togglemessage").setExecutor(new ToggleMessageCommand());
		getCommand("teleportall").setExecutor(new TeleportAllCommand());
		getCommand("teleporthere").setExecutor(new TeleportHereCommand());
		getCommand("give").setExecutor(new GiveCommand());
		getCommand("gamemode").setExecutor(new GameModeCommand());
		getCommand("item").setExecutor(new ItemCommand());
		getCommand("rawcast").setExecutor(new RawcastCommand());
		getCommand("lockdown").setExecutor(new LockdownCommand(this));
		getCommand("fly").setExecutor(new FlyCommand());
		getCommand("invsee").setExecutor(new InvSeeCommand(this));
		getCommand("god").setExecutor(new GodCommand());
		getCommand("gms").setExecutor(new GMSCommand());
		getCommand("gmc").setExecutor(new GMCCommand());
		getCommand("vanish").setExecutor(new VanishCommand());
		getCommand("sotw").setExecutor(new SotwCommand(this));
		getCommand("random").setExecutor(new RandomCommand(this));
		getCommand("conquest").setExecutor(new ConquestExecutor(this));
		getCommand("crowbar").setExecutor(new CrowbarCommand());
		getCommand("economy").setExecutor(new EconomyCommand(this));
		getCommand("eotw").setExecutor(new EotwCommand(this));
		getCommand("event").setExecutor(new EventExecutor(this));
		getCommand("faction").setExecutor(new FactionExecutor(this));
		getCommand("playtime").setExecutor(new PlayTimeCommand(this));
		getCommand("gopple").setExecutor(new GoppleCommand(this));
		getCommand("cobble").setExecutor(new CobbleCommand());
		getCommand("koth").setExecutor(new KothExecutor(this));
		getCommand("lives").setExecutor(new LivesExecutor(this));
		getCommand("logout").setExecutor(new LogoutCommand(this));
		getCommand("more").setExecutor(new MoreCommand());
		getCommand("panic").setExecutor(new PanicCommand());
		getCommand("heal").setExecutor(new HealCommand());
		getCommand("pay").setExecutor(new PayCommand(this));
		getCommand("pvptimer").setExecutor(new PvpTimerCommand(this));
		getCommand("LFF").setExecutor(new LFFCommand());
		getCommand("refund").setExecutor(new RefundCommand());
		getCommand("spawn").setExecutor(new SpawnCommand());
		getCommand("timer").setExecutor(new TimerExecutor(this));
		getCommand("kill").setExecutor(new KillCommand());
		getCommand("fight").setExecutor(new FightCommand());
		getCommand("ores").setExecutor(new OreStatsCommand());
		getCommand("help").setExecutor(new HelpCommand());
		getCommand("rename").setExecutor(new RenameCommand());
		getCommand("dev").setExecutor(new DevCommand());
		getCommand("teamspeak").setExecutor(new TeamspeakCommand());
		getCommand("discord").setExecutor(new DiscordCommand());
		getCommand("reboot").setExecutor(new RebootCommand(plugin));
		getCommand("coords").setExecutor(new CoordsCommand());
		getCommand("fsay").setExecutor(new MiscCommands());
		getCommand("mapkit").setExecutor(new MapKitCommand(this));
		getCommand("staffmode").setExecutor(new StaffModeCommand());
		getCommand("spawner").setExecutor(new SpawnerCommand());
		getCommand("set").setExecutor(new SetCommand(this));
		getCommand("ci").setExecutor(new ClearCommand());
		getCommand("staffguide").setExecutor(new StaffGuideCommand());
		getCommand("drop").setExecutor(new MiscCommands());
		getCommand("copyinv").setExecutor(new MiscCommands());
		getCommand("teleport").setExecutor(new TeleportCommand());
		getCommand("skull").setExecutor(new SkullCommand());
		getCommand("reset").setExecutor(new ResetCommand());
		getCommand("whois").setExecutor(new WhoisCommand(this));
		getCommand("tl").setExecutor(new TLCommand());
		getCommand("sale").setExecutor(new SaleCommand(plugin));
		getCommand("keysale").setExecutor(new KeySaleCommand(plugin));
		getCommand("stats").setExecutor(new StatsCommand());
		getCommand("glowstone").setExecutor(new GlowstoneMountain(this));

		Map<String, Map<String, Object>> map = getDescription().getCommands();
		for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
			PluginCommand command = getCommand((String) entry.getKey());
			command.setPermission("command." + (String) entry.getKey());
			command.setPermissionMessage(ConfigurationService.PRIMARY_COLOR + ChatColor.BOLD.toString() + ConfigurationService.NAME + ChatColor.GOLD + " » " + ChatColor.RED + "No Permission.");
		}
		
   	}

	private void registerManagers() {
		this.claimHandler = new ClaimHandler(this);
		this.deathbanManager = new FlatFileDeathbanManager(this);
		this.economyManager = new FlatFileEconomyManager(this);
		this.eotwHandler = new EOTWHandler(this);
		this.eventScheduler = new EventScheduler(this);
		this.factionManager = new FlatFileFactionManager(this);
		this.itemDb = new SimpleItemDb(this);
		this.playTimeManager = new PlayTimeManager(this);
		this.pvpClassManager = new PvpClassManager(this);
		this.timerManager = new TimerManager(this);
		this.scoreboardHandler = new ScoreboardHandler(this);
		this.userManager = new UserManager(this);
		this.visualiseHandler = new VisualiseHandler();
		this.sotwTimer = new SotwTimer();
		this.reclaimManager1 = new me.versache.core.special.reclaim.ReclaimManager(this);
		this.message = new Message(this);
		this.signHandler = new SignHandler(this);
		new BardRestorer(this);
	}

	public Message getMessage() {
		return this.message;
	}

	public ItemDb getItemDb() {
		return this.itemDb;
	}

	public Random getRandom() {
		return this.random;
	}

	public PlayTimeManager getPlayTimeManager() {
		return this.playTimeManager;
	}

	public WorldEditPlugin getWorldEdit() {
		return this.worldEdit;
	}

	public ClaimHandler getClaimHandler() {
		return this.claimHandler;
	}

	public SotwTimer getSotwTimer() {
		return this.sotwTimer;
	}

	public SignHandler getSignHandler() {
		return this.signHandler;
	}

	public ConfigurationService getConfiguration() {
		return this.configuration;
	}

	public DeathbanManager getDeathbanManager() {
		return this.deathbanManager;
	}

	public VanishListener getVanish() {
		return this.vanish;
	}

	public EconomyManager getEconomyManager() {
		return this.economyManager;
	}

	public EOTWHandler getEotwHandler() {
		return this.eotwHandler;
	}

	public FactionManager getFactionManager() {
		return this.factionManager;
	}

	public PvpClassManager getPvpClassManager() {
		return this.pvpClassManager;
	}

	public ScoreboardHandler getScoreboardHandler() {
		return this.scoreboardHandler;
	}

	public TimerManager getTimerManager() {
		return this.timerManager;
	}

	public UserManager getUserManager() {
		return this.userManager;
	}

	public VisualiseHandler getVisualiseHandler() {
		return this.visualiseHandler;
	}

	public Base() {
		this.random = new Random();
	}

	public ServerHandler getServerHandler() {
		return this.serverHandler;
	}

	public static Base getPlugin() {
		return plugin;
	}

	public static Base getInstance() {
		return instance;
	}

	public static String getReaming(long millis) {
		return getRemaining(millis, true, true);
	}

	public String getCraftBukkitVersion() {
		return this.craftBukkitVersion;
	}

	public static String getRemaining(long millis, boolean milliseconds) {
		return getRemaining(millis, milliseconds, true);
	}

	public static String getRemaining(long duration, boolean milliseconds, boolean trail) {
		if ((milliseconds) && (duration < MINUTE)) {
			return ((DecimalFormat) (trail ? DateTimeFormats.REMAINING_SECONDS_TRAILING
					: DateTimeFormats.REMAINING_SECONDS).get()).format(duration * 0.001D) + 's';
		}
		return DurationFormatUtils.formatDuration(duration, (duration >= HOUR ? "HH:" : "") + "mm:ss");
	}

	public static File conf;
	public static FileConfiguration config;
	private String craftBukkitVersion;
	public static Base instance;
	private ConfigurationService configuration;
	private static final long MINUTE = TimeUnit.MINUTES.toMillis(1L);
	private static final long HOUR = TimeUnit.HOURS.toMillis(1L);
	private static Base plugin;
	public static Plugin pl;
	private ServerHandler serverHandler;
	public BukkitRunnable clearEntityHandler;
	public BukkitRunnable announcementTask;
	private Message message;
	@Getter private ReclaimManager reclaimManager;

	public EventScheduler eventScheduler;
	public static final Joiner SPACE_JOINER = Joiner.on(' ');
	public static final Joiner COMMA_JOINER = Joiner.on(", ");
	private Random random;
	private PlayTimeManager playTimeManager;
	private WorldEditPlugin worldEdit;
	private ClaimHandler claimHandler;
	private ItemDb itemDb;

	private DeathbanManager deathbanManager;
	private EconomyManager economyManager;
	private EOTWHandler eotwHandler;
	private FactionManager factionManager;
	private PvpClassManager pvpClassManager;
	private VanishListener vanish;
	private ScoreboardHandler scoreboardHandler;
	private SotwTimer sotwTimer;
	private TimerManager timerManager;
	private UserManager userManager;
	private VisualiseHandler visualiseHandler;
	private SignHandler signHandler;

	public Object getVanishListener() {
		// TODO Auto-generated method stub
		return null;
	}


	}


