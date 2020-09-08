package net.sacredlabyrinth.phaed.simpleclans.inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import net.sacredlabyrinth.phaed.simpleclans.commands.MenuCommand;
import net.sacredlabyrinth.phaed.simpleclans.util.ItemBuilder;

public class ClanMarket implements Listener {

	public void invMarket(Player p) {
		
		Inventory inv = Bukkit.createInventory(null, 3 * 9, "Mercado do Clan");
		SimpleClans plugin = SimpleClans.getInstance();
		
		inv.setItem(13, new ItemBuilder(Material.FIREBALL)
				.name("§cReset KDR")
				.lore("§7Resetar o seu KDR.",
						"§7Preço: §f" + plugin.getConfig().getInt("market.resetkdr"))
				.build());
		
		p.openInventory(inv);
	}
	
	@EventHandler
	public void onClickmarket(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		Inventory inv = e.getInventory();
		ItemStack item = e.getCurrentItem();
		if (inv.getName().equalsIgnoreCase("Mercado do Clan") && item != null && item.getTypeId() != 0) {
			e.setCancelled(true);
			if (item.hasItemMeta() && item.getItemMeta().getDisplayName().equalsIgnoreCase("§cReset KDR")) {
				SimpleClans plugin = SimpleClans.getInstance();
				if (plugin.econ.getBalance(p) >= plugin.getConfig().getInt("market.resetkdr")) {
					plugin.econ.withdrawPlayer(p, plugin.getConfig().getInt("market.resetkdr"));
					if (p.getInventory().getContents().length < -1) {
						p.getLocation().getWorld().dropItem(p.getLocation(), new ItemBuilder(Material.FIREBALL)
							.name("§cReset KDR")
							.lore("§7Resetar o seu KDR.")
							.build());
						p.sendMessage("§aVocê comprou um '" + item.getItemMeta().getDisplayName() + "§a'.");
					} else {
						p.getInventory().addItem(new ItemBuilder(Material.FIREBALL)
							.name("§cReset KDR")
							.lore("§7Resetar o seu KDR.")
							.build());
						p.sendMessage("§aVocê comprou um '" + item.getItemMeta().getDisplayName() + "§a'.");
					}
				} else {
					p.sendMessage("§cVocê não possui '§f" + plugin.getConfig().getInt("market.resetkdr") + "§c' coins.");
				}
			}
		}
	}
	
	@EventHandler
	public void interact(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		ItemStack item = p.getItemInHand();
		if (item != null) {
			if (item.getType() == Material.FIREBALL && item.hasItemMeta() && item.getItemMeta().getDisplayName().equalsIgnoreCase("�cReset KDR")) {
				e.setCancelled(true);
				SimpleClans plugin = SimpleClans.getInstance();
				ClanPlayer cp = plugin.getClanManager().getClanPlayer(p);
				cp.setNeutralKills(0);
				cp.setRivalKills(0);
				cp.setCivilianKills(0);
				cp.setDeaths(0);
				cp.updateLastSeen();
				p.sendMessage("§aVocê teve seu KDR resetado.");
				if (item.getAmount() > 1) {
					item.setAmount(item.getAmount() -1);
				} else {
					if (item.getAmount() == 1) {
						p.setItemInHand(new ItemStack(Material.AIR));
					}
				}
			}
		}
	}
}
