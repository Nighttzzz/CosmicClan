package net.sacredlabyrinth.phaed.simpleclans.commands;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import net.sacredlabyrinth.phaed.simpleclans.util.ItemBuilder;

public class RelationCommand implements Listener {

	public RelationCommand() {
	}

	public void execute(Player player, String[] arg) {
		SimpleClans plugin = SimpleClans.getInstance();
		
		Clan clan = null;
		
		if (arg.length == 0) {
			player.sendMessage("§cUtilize /relacao <tag>");
			return;
		} else {
			if (arg.length == 1) {
				clan = plugin.getClanManager().getClan(arg[0]);
				if (clan == null) {
					player.sendMessage("§cO clan '§f" + arg[0] + "§c' não foi localizado.");
					return;
				} else {
					ClanPlayer cp = plugin.getClanManager().getClanPlayer(player);
					
					if (cp == null) {
						player.sendMessage("§cVocê não pertence a um clan.");
						return;
					} else {
						if (cp.getTag().equalsIgnoreCase(clan.getTag())) {
							player.sendMessage("§cVocê não pode gerenciar as relações com seu próprio clan.");
							return;
						}
						invRelations(player, clan);
					}
				}
			}
		}
	}
	
	public void invRelations(Player player, Clan clan) {
		
		Inventory inv = Bukkit.createInventory(null, 3 * 9, "Relação - " + clan.getTag());
		
		inv.setItem(11, new ItemBuilder(Material.LEATHER_CHESTPLATE)
				.name("�9Aliado")
				.color(Color.fromRGB(85, 85, 255))
				.build());
		inv.setItem(13, new ItemBuilder(Material.LEATHER_CHESTPLATE)
				.name("�fNeutro")
				.color(Color.fromRGB(255, 255, 255))
				.build());
		inv.setItem(15, new ItemBuilder(Material.LEATHER_CHESTPLATE)
				.name("�cRival")
				.color(Color.fromRGB(255, 85, 85))
				.build());
		
		player.openInventory(inv);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		SimpleClans plugin = SimpleClans.getInstance();
		Player p = (Player) e.getWhoClicked();
		Inventory inv = e.getInventory();
		ItemStack item = e.getCurrentItem();
		ClanPlayer cp = plugin.getClanManager().getClanPlayer(p);
		
		if (inv.getName().contains("Relação - ") && item != null && item.getTypeId() != 0) {
			e.setCancelled(true);
			if (!cp.isLeader() && !cp.isTrusted()) {
				p.closeInventory();
				p.sendMessage("§cVocê precisa ser oficial ou superior para executar esta ação.");
				return;
			} else {
				if (item.hasItemMeta() && item.getItemMeta().getDisplayName().equalsIgnoreCase("§9Aliado")) {
					String name = inv.getName().replaceAll("Relação - ", "");
					
					Clan clan = plugin.getClanManager().getClan(name);
					
					if (cp.getClan().isAlly(clan.getTag())) {
						p.closeInventory();
						p.sendMessage("§cSeu clan já é aliado deste clan.");
						return;
					} else {
						p.closeInventory();
						p.sendMessage("§aO clan '§f" + clan.getTag() + "§a' recebeu um convite de aliado.");
						for (ClanPlayer cps : clan.getOnlineMembers()) {
							Player ps = (Player) cps.toPlayer();
							ps.sendMessage("§aO clan '§f" + cp.getClan().getTag() +  "§a' deseja tornar-se aliado.");
							ps.sendMessage("§aPara aceitar o convite utilize '§f/clan aliado aceitar " + cp.getClan().getTag() + "�a'.");
							ps.sendMessage("§aPara rejeitar o convite utilize '§f/clan aliado rejeitar " + cp.getClan().getTag() + "�a'.");
						}
						plugin.getRequestManager().addAllyRequest(cp, cp.getClan(), clan);
					}
				}
				if (item.hasItemMeta() && item.getItemMeta().getDisplayName().equalsIgnoreCase("§fNeutro")) {
					String name = inv.getName().replaceAll("Relação - ", "");
					
					Clan clan = plugin.getClanManager().getClan(name);
					
					if (cp.getClan().isAlly(clan.getTag()) || cp.getClan().isRival(clan.getTag())) {
						p.closeInventory();
						cp.getClan().removeAlly(clan);
						clan.removeAlly(cp.getClan());
						cp.getClan().removeRival(clan);
						clan.removeRival(cp.getClan());
						p.sendMessage("§cSeu clan já não tem mais relações com '§f" + clan.getTag() + "§c'.");
						return;
					} else {
						p.closeInventory();
						p.sendMessage("§cSeu clan n�o tem relações com '§f" + clan.getName() + "§c'.");
						return;
					}
				}
				if (item.hasItemMeta() && item.getItemMeta().getDisplayName().equalsIgnoreCase("§cRival")) {
					String name = inv.getName().replaceAll("Relação - ", "");
					
					Clan clan = plugin.getClanManager().getClan(name);
					
					if (cp.getClan().isRival(clan.getTag())) {
						p.closeInventory();
						p.sendMessage("§cSeu clan já é rival deste clan.");
						return;
					} else {
						p.closeInventory();
						p.sendMessage("§aO clan '§f" + clan.getTag() + "§a' agora é um rival.");
						for (ClanPlayer cps : clan.getOnlineMembers()) {
							Player ps = (Player) cps.toPlayer();
							ps.sendMessage("§aO clan '§f" + cp.getClan().getTag() +  "§a' declarou-se rival.");
						}
						cp.getClan().addRival(clan);
					}
				}
			}
		}
		
		
	}
	
}
