package net.sacredlabyrinth.phaed.simpleclans.commands;

import net.sacredlabyrinth.phaed.simpleclans.*;
import net.sacredlabyrinth.phaed.simpleclans.util.ItemBuilder;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author phaed
 */
public class RosterCommand implements Listener {

    public RosterCommand() {
    }

    /**
     * Execute the command
     *
     * @param player
     * @param arg
     */
    public void execute(Player player, String[] args) {
        SimpleClans plugin = SimpleClans.getInstance();

        Clan clan = null;

        if (args.length == 0) {
        	ClanPlayer cp = plugin.getClanManager().getClanPlayer(player);
        	if (cp == null) {
        		player.sendMessage("§cVocê não pertence a um clan.");
        		return;
        	}
        	clan = cp.getClan();
        	invMembers(player, clan, 1);
        } else {
        	clan = plugin.getClanManager().getClan(args[0]);
        	if (clan == null) {
        		player.sendMessage("§cO clan '§f" + args[0] + "§c' não foi encontrado.");
        		return;
        	}
        	invMembers(player, clan, 1);
        }

    }
    
    
    public void invMembers(Player p, Clan clan, int pagina) {
        NumberFormat formatter = new DecimalFormat("#.#");
        SimpleClans plugin = SimpleClans.getInstance();
        
        Inventory inv = Bukkit.createInventory(null, 5 * 9, "[" + clan.getTag() + "] - Membros");
    	
        List<ClanPlayer> members = clan.getAllMembers();
        plugin.getClanManager().sortClanPlayersByLastSeen(members);
    	
        List<ItemStack> heads = new ArrayList<>();
        
        for (ClanPlayer cplayer : members) {
        	
        	String rank = "Membro";
        	
        	String online = "";
        	Player player = Bukkit.getServer().getPlayer(cplayer.getName());
        	if (player == null) {
        		online = "§cOffline";
        	} else {
        		online = "§aOnline";
        	}
        	
        	if (cplayer.isTrusted()) {
        		rank = "Oficial";
        	}
        	if (cplayer.isLeader()) {
        		rank = "Líder";
        	}
        	if (!cplayer.isLeader() && !cplayer.isTrusted()) {
        		rank = "Membro";
        	}
        	
        	heads.add(new ItemBuilder(Material.SKULL_ITEM)
        			.durability(3)
        			.owner(cplayer.getName())
        			.name(PermissionsEx.getUser(cplayer.getName()).getPrefix() + "§7" + cplayer.getName())
        			.lore("§7Moedas: §f" + plugin.econ.getBalance(cplayer.getName()),
        					"§7Cargo: §f" + rank,
        					"§7KDR: §f" + formatter.format(cplayer.getKDR()),
        					"§7Abates: §8[Rival §f" + cplayer.getRivalKills() + "§8 Neutro: §f" + cplayer.getNeutralKills() + "§8 Civil: §f" + cplayer.getCivilianKills() + "§8]",
        					"§7Mortes: §f" + cplayer.getDeaths(),
        					"§7Status: §f" + online)
        			.build());
        }
		
        if (heads.size() < 15) {
			int size = heads.size();
			for (int i = 0; i < 15 - size; i++) {
				heads.add(new ItemBuilder(Material.SKULL_ITEM).durability(3).name("§7Vago").build());
			}
		}
		
		int limitepp = 15;
		int index = pagina * limitepp - limitepp;
		int endIndex = Math.min(index + limitepp, heads.size());
		
		int x = 2;
		int h = 1;
		for (; index < endIndex; index++) {
			ItemStack item = heads.get(index);
			inv.setItem(x + 9 * h, item);
			if (++x == 7) {
				x = 2;
				++h;
			}
		}
		
		p.openInventory(inv);
    }
    
    @EventHandler
    public void onClick(InventoryClickEvent e) {
    	ItemStack item = e.getCurrentItem();
    	Inventory inv = e.getInventory();
    	
    	if (inv.getName().contains(" - Membros") && item != null && item.getTypeId() != 0) {
    		e.setCancelled(true);
    	}
    }
}
