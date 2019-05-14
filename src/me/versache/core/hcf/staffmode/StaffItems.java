package me.versache.core.hcf.staffmode;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.versache.core.hcf.command.StaffModeCommand;




public class StaffItems {

	@SuppressWarnings("deprecation")
	public static void modItems(Player p) {
		Inventory inv = p.getInventory();

		inv.clear();

		ItemStack compass = new ItemStack(Material.COMPASS);
		ItemStack book = new ItemStack(Material.BOOK);
		ItemStack tp = new ItemStack(Material.ENDER_PEARL);
		ItemStack vanish = new ItemStack(Material.FEATHER);
		ItemStack freeze = new ItemStack(Material.PACKED_ICE);
		ItemStack staffguide = new ItemStack(Material.CHEST);
		ItemStack carpet = new ItemStack(Material.CARPET);
		
		ItemMeta compassMeta = compass.getItemMeta();
		ItemMeta bookMeta = book.getItemMeta();
		ItemMeta eggMeta = tp.getItemMeta();
		ItemMeta carpetMeta = carpet.getItemMeta();
		ItemMeta vanishMeta = vanish.getItemMeta();
		ItemMeta freezeMeta = freeze.getItemMeta();
		ItemMeta staffguideMeta = staffguide.getItemMeta();
		
		compassMeta.setDisplayName("§ePhaser");
	    compassMeta.setLore(Arrays.asList(new String[] { "§7Right click to phase through walls" }));
		bookMeta.setDisplayName("§eInventory Inspector");
	    bookMeta.setLore(Arrays.asList(new String[] { "§7Right click to check a players inventory" }));
		eggMeta.setDisplayName("§eFind Player");
	    eggMeta.setLore(Arrays.asList(new String[] { "§7Right click to teleport to a random player" }));
		vanishMeta.setDisplayName("§eVanish: §aOn");
	    vanishMeta.setLore(Arrays.asList(new String[] { "§7Right click to set vanish mode" }));
		freezeMeta.setDisplayName("§eFreeze");
	    freezeMeta.setLore(Arrays.asList(new String[] { "§7Right click to freeze a player" }));
		staffguideMeta.setDisplayName("§eStaff Guide");
	    staffguideMeta.setLore(Arrays.asList(new String[] { "§7Right click to see the staff guide" }));
		carpetMeta.setDisplayName("§eCarpet");
		
		compass.setItemMeta(compassMeta);
		book.setItemMeta(bookMeta);
		tp.setItemMeta(eggMeta);
		carpet.setItemMeta(eggMeta);
		vanish.setItemMeta(vanishMeta);
		freeze.setItemMeta(freezeMeta);		
		staffguide.setItemMeta(staffguideMeta);

		inv.setItem(0, compass);
		inv.setItem(1, book);
		inv.setItem(7, tp);
		inv.setItem(2, carpet);
		inv.setItem(8, vanish);
		inv.setItem(4, freeze);
		inv.setItem(6, staffguide);
	}
	
	private static void getItemMeta() {
		// TODO Auto-generated method stub
		
	}

	public static void saveInventory(Player p) {
		StaffModeCommand.armorContents.put(p.getName(), p.getInventory().getArmorContents());
		StaffModeCommand.inventoryContents.put(p.getName(), p.getInventory().getContents());
	}

	public static void loadInventory(Player p) {
		p.getInventory().clear();

		p.getInventory().setContents((ItemStack[]) StaffModeCommand.inventoryContents.get(p.getName()));
		p.getInventory().setArmorContents((ItemStack[]) StaffModeCommand.armorContents.get(p.getName()));

		StaffModeCommand.inventoryContents.remove(p.getName());
		StaffModeCommand.armorContents.remove(p.getName());
	}

}
