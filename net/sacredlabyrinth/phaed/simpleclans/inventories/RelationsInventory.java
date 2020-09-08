package net.sacredlabyrinth.phaed.simpleclans.inventories;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
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
import net.sacredlabyrinth.phaed.simpleclans.commands.MenuCommand;
import net.sacredlabyrinth.phaed.simpleclans.util.Alphabetic;
import net.sacredlabyrinth.phaed.simpleclans.util.ItemBuilder;

public class RelationsInventory implements Listener {

	public void relationGui(Player p, int pagina) {
        SimpleClans plugin = SimpleClans.getInstance();

        ClanPlayer cp = plugin.getClanManager().getClanPlayer(p);
        
        if (cp == null) {
        	p.sendMessage("§cVocê não pertence a um clan.");
        	return;
        }

        Clan clan = cp.getClan();
        
		Inventory inv = Bukkit.createInventory(null, 6 * 9,  "[" + clan.getTag() + "] - Relações #" + pagina);
        
        List<Clan> clans = plugin.getClanManager().getClans();
        List<ItemStack> clansitem = new ArrayList<>();
        
        for (Clan relations : clans) {
        	if (relations.isAlly(clan.getTag())) {
        		clansitem.add(Alphabetic.getAlphabet(new ItemBuilder(Material.BANNER)
        				.removeAttributes()
        				.name("§9" + relations.getName())
        				.lore("§7Membros: §f" + relations.getAllMembers().size() + "/" + 15)
        				.color(Color.fromRGB(85, 85, 255))
        				.build(),
        				relations.getTag(),
        				DyeColor.WHITE,
        				DyeColor.BLACK));
        	}
        	if (relations.isRival(clan.getTag())) {
        		clansitem.add(Alphabetic.getAlphabet(new ItemBuilder(Material.BANNER)
        				.removeAttributes()
        				.name("§c" + relations.getName())
        				.lore("§7Membros: §f" + relations.getAllMembers().size() + "/" + 15)
        				.color(Color.fromRGB(255, 85, 85))
        				.build(),
        				relations.getTag(),
        				DyeColor.WHITE,
        				DyeColor.BLACK));
        	}
        }
        
		int limitepp = 21;
		int index = pagina * limitepp - limitepp;
		int endIndex = Math.min(index + limitepp, clansitem.size());
		
		int next;
		next = pagina +1;
		if (endIndex > 20 && endIndex != clansitem.size()) {
			inv.setItem(26, new ItemBuilder(Material.ARROW)
					.name("§aPágina " + next)
					.build());
		}
		int previus;
		previus = pagina -1;
		if (!inv.getName().equalsIgnoreCase("[" + clan.getTag() + "] - Relações #" + 1)) {
			inv.setItem(18, new ItemBuilder(Material.ARROW)
					.name("§aPágina " + previus)
					.build());
		}
		
		
		int x = 1;
		int h = 1;
		for (; index < endIndex; index++) {
			ItemStack item = clansitem.get(index);
			inv.setItem(x + 9 * h, item);
			if (++x == 8) {
				x = 1;
				++h;
			}
		}
        
        inv.setItem(49, new ItemBuilder(Material.ARROW)
        		.name("§aVoltar")
        		.build());
        
        p.openInventory(inv);
	}
	
	@EventHandler
	public void onclick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		Inventory inv = e.getInventory();
		ItemStack item = e.getCurrentItem();
		SimpleClans plugin = SimpleClans.getInstance();
		
		ClanPlayer cp = plugin.getClanManager().getClanPlayer(p);

		if (cp != null) {
			if (inv.getName().contains("[" + cp.getClan().getTag() + "] - Relações #") && item != null && item.getTypeId() != 0) {
				e.setCancelled(true);
				if (item.hasItemMeta() && item.getItemMeta().getDisplayName().equalsIgnoreCase("§aVoltar")) {
					MenuCommand menuCommand = new MenuCommand();
					menuCommand.menuGui(p);
					return;
				}
				Integer proxima = Integer.valueOf(e.getInventory().getName().split("#")[1]);
				int next;
				next = proxima +1;
				if (item.hasItemMeta() && item.getItemMeta().getDisplayName().equalsIgnoreCase("§aPágina " + next)) {
					relationGui(p, next);
					return;
				}
				int previus;
				previus = proxima -1;
				if (item.hasItemMeta() && item.getItemMeta().getDisplayName().equalsIgnoreCase("§aPágina " + previus)) {
					relationGui(p, previus);
					return;
				}
			}
		}
	}
	
}
