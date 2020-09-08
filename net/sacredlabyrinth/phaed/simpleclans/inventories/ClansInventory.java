package net.sacredlabyrinth.phaed.simpleclans.inventories;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import net.sacredlabyrinth.phaed.simpleclans.commands.MenuCommand;
import net.sacredlabyrinth.phaed.simpleclans.util.Alphabetic;
import net.sacredlabyrinth.phaed.simpleclans.util.ItemBuilder;

public class ClansInventory implements Listener {

	public static void invClans(Player p, int pagina) {
        NumberFormat formatter = new DecimalFormat("#.#");
        SimpleClans plugin = SimpleClans.getInstance();
        
		Inventory inv = Bukkit.createInventory(null, 6 * 9, "Clans Online #" + pagina);
        
        List<Clan> clans = plugin.getClanManager().getClans();
        plugin.getClanManager().sortClansByKDR(clans);
        List<ItemStack> clansitem = new ArrayList<>();

        int rank = 1;
        
        for (Clan online : clans) {
        	if (online.getOnlineMembers().size() > 0) { 
				ItemStack banner = new ItemStack(Material.BANNER, 1, (short) 15);
				ItemMeta meta = banner.getItemMeta();
				meta.setDisplayName("§7[" + online.getTag() + "] " + online.getName());
				List<String> lore = new ArrayList<>();
				lore.add("§7Status: " + (online.isVerified() ? "§aVerificado"  : "§cNão verificado"));
				lore.add("§7Líderes: §f" + online.getLeaders().toString().replaceAll("\\[", "").replaceAll("]", ""));
				lore.add("§7KDR: §f" + formatter.format(online.getTotalKDR()));
				lore.add("§7Abates: §8[Rival: §f" + online.getTotalRival() + "§8 Neutro: §f" + online.getTotalNeutral() + "§8 Civil: §f" + online.getTotalCivilian());
				lore.add("§7Mortes: §f" + online.getTotalDeaths());
				lore.add("§7Membros: §f" + online.getAllMembers().size() + "/" + 15);
				lore.add("§7Membros Online: §f" + online.getOnlineMembers().size());
				for (ClanPlayer cp : online.getOnlineMembers()) {
					lore.add("§7  " + cp.getName());
				}
				meta.addItemFlags(ItemFlag.values());
				meta.setLore(lore);
				banner.setItemMeta(meta);
				clansitem.add(Alphabetic.getAlphabet(banner, online.getTag(), DyeColor.WHITE, DyeColor.BLACK));
        	rank++;
        	} else {
        		continue;
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
		if (!inv.getName().equalsIgnoreCase("Clans Online #" + 1)) {
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
    public void onClick(InventoryClickEvent e) {
    	Player p = (Player) e.getWhoClicked();
    	Inventory inv = e.getInventory();
    	ItemStack item = e.getCurrentItem();

        if (inv.getName().contains("Clans Online #") && item != null && item.getTypeId() != 0) {
        	e.setCancelled(true);
        	if (item.hasItemMeta() && item.getItemMeta().getDisplayName().equalsIgnoreCase("§aVoltar")) {
        		MenuCommand menuCommand = new MenuCommand();
        		menuCommand.menuGui(p);
        	}
			Integer proxima = Integer.valueOf(e.getInventory().getName().split("#")[1]);
			int next;
			next = proxima +1;
			if (item.hasItemMeta() && item.getItemMeta().getDisplayName().equalsIgnoreCase("§aPágina " + next)) {
				invClans(p, next);
				return;
			}
			int previus;
			previus = proxima -1;
			if (item.hasItemMeta() && item.getItemMeta().getDisplayName().equalsIgnoreCase("§aPágina " + previus)) {
				invClans(p, previus);
				return;
			}
        }
    }
}
