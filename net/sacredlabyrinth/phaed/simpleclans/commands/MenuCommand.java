package net.sacredlabyrinth.phaed.simpleclans.commands;

import net.sacredlabyrinth.phaed.simpleclans.*;
import net.sacredlabyrinth.phaed.simpleclans.inventories.ClanMarket;
import net.sacredlabyrinth.phaed.simpleclans.inventories.ClansInventory;
import net.sacredlabyrinth.phaed.simpleclans.inventories.MembersInventory;
import net.sacredlabyrinth.phaed.simpleclans.inventories.RelationsInventory;
import net.sacredlabyrinth.phaed.simpleclans.util.Alphabetic;
import net.sacredlabyrinth.phaed.simpleclans.util.ItemBuilder;
import net.sacredlabyrinth.phaed.simpleclans.util.messageCenter;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author phaed
 */
public class MenuCommand implements Listener {
    private List<String> menuItems = new LinkedList<>();

    public MenuCommand() {
    }

    /**
     * Execute the command
     *
     * @param player
     */
    public void execute(Player player) {
        SimpleClans plugin = SimpleClans.getInstance();

        ClanPlayer cp = plugin.getClanManager().getClanPlayer(player);
        Clan clan = cp == null ? null : cp.getClan();
        if (clan == null && plugin.getPermissionsManager().has(player, "simpleclans.leader.create")) {
		    player.sendMessage("");
		    messageCenter.sendCenteredMessage(player, "§7Comando de Clans");
		    player.sendMessage("");
		       	    		
		    String precmd = "§7  /clan ";
		    String desc = "§8 - §7";
		       	    		
		    player.sendMessage(precmd + "criar <tag> <nome>" + desc + "Criar um novo clan");
		    player.sendMessage(precmd + "listar" + desc + "Ver os clans online");
		    player.sendMessage(precmd + "info <tag>" + desc + "Ver as infos de um clan");
		    player.sendMessage(precmd + "perfil <jogador>" + desc + "Ver o perfil de um jogador");
		    player.sendMessage(precmd + "aliancas" + desc + "Ver a lista de clans com alianças");
		    player.sendMessage(precmd + "rivais" + desc + "Ver a lista de clans com rivais");
		    player.sendMessage(precmd + "membros <tag>" + desc + "Ver os membros de um clan");
		    player.sendMessage("");    
        } else {
		    player.sendMessage("");
		    messageCenter.sendCenteredMessage(player, "§7Comando de Clans");
		    player.sendMessage("");
		    
		    String precmd = "§7  /clan ";
		    String desc = "§8 - §7";
		       	    		
		    player.sendMessage(precmd + "listar" + desc + "Ver os clans online.");
		    player.sendMessage(precmd + "info <tag>" + desc + "Ver as infos de um clan.");
		    player.sendMessage(precmd + "perfil <jogador>" + desc + "Ver o perfil de um jogador.");
		    player.sendMessage(precmd + "aliancas" + desc + "Ver a lista de clans com alianças.");
		    player.sendMessage(precmd + "rivais" + desc + "Ver a lista de clans com rivais.");
		    player.sendMessage(precmd + "membros <tag>" + desc + "Ver os membros de um clan.");
		    player.sendMessage(precmd + "relacao <tag>" + desc + "Alterar relação com um clan.");
		    player.sendMessage(precmd + "setbase" + desc + "Definir a base do seu clan.");
		    player.sendMessage(precmd + "base" + desc + "Ir até a base do seu clan.");
		    player.sendMessage(precmd + "motd" + desc + "Ver a motd do seu clan.");
		    player.sendMessage(precmd + "convidar <jogador>" + desc + "Convidar um jogador.");
		    player.sendMessage(precmd + "top" + desc + "Ver melhores usuários.");
		    player.sendMessage(precmd + "expulsar <jogador>" + desc + "Expulsar um jogador do clan.");
		    player.sendMessage(precmd + "confiar <jogador>" + desc + "Confiar em um membro do clan.");
		    player.sendMessage(precmd + "naoconfiar <jogador>" + desc + "Revogar confiança.");
		    player.sendMessage(precmd + "rebaixar <jogador>" + desc + "Rebaixar um líder do clan.");
		    player.sendMessage(precmd + "ff <on/off>" + desc + "Alternar entre o fogo amigo.");
		    player.sendMessage(precmd + "sair" + desc + "Sair do seu clan.");
		    player.sendMessage("");
	    }
    }

    /**
     * Create a menu gui to then /clan
     */
    
	public void menuGui(Player player) {
        NumberFormat formatter = new DecimalFormat("#.#");
        SimpleClans plugin = SimpleClans.getInstance();

        ClanPlayer cplayer = plugin.getClanManager().getClanPlayer(player);
        String prefix = PermissionsEx.getUser(player.getName()).getPrefix();
        
		Inventory inv;
		
    	if (cplayer == null) {
    		inv = Bukkit.createInventory(null, 3 * 9, player.getName());
    		
        	boolean online = player.isOnline();
        	String rank = "Membro";
    		
    		inv.setItem(10, new ItemBuilder(Material.SKULL_ITEM)
    				.durability(3)
    				.owner(player.getName())
        			.name(prefix + "§7" + player.getName())
        			.lore("§7Moedas: §f" + plugin.econ.getBalance(player.getName()),
        					"§7Cargo: §f" + rank,
        					"§7KDR: §f" + 0.0,
        					"§7Abates: §8[Rival §f" + 0 + "§8 Neutro: §f" + 0 + "§8 Civil: §f" + 0 + "§8]",
        					"§7Mortes: §f" + 0,
        					"§7Status: §f" + (online ? "§aOnline" : "§cOffline"))
        			.build());
    		inv.setItem(11, new ItemBuilder(Material.BANNER)
    				.durability(15)
    				.name("§eCriar um clan")
    				.lore("§7Clique para criar seu clan.")
    				.build());
    		inv.setItem(14, new ItemBuilder(Material.SKULL_ITEM)
    				.durability(3)
    				.owner("821")
    				.name("§aAjuda")
    				.lore("§7Todas opções exibidas aqui também estão disponíveis",
    						"§7Utilizando o comando '§f/clan ajuda§7'.")
    				.build());
    		inv.setItem(15, new ItemBuilder(Material.NETHER_STAR)
    				.name("§aClans online")
    				.lore("§7Listar os clans online.")
    				.build());
    		
    		player.openInventory(inv);
    	} else {
            Clan clan = cplayer == null ? null : cplayer.getClan();
    		inv = Bukkit.createInventory(null, 5 * 9, "[" + clan.getTag() + "] " + cplayer.getName());
    		
        	Player p = (Player) cplayer.toPlayer();
        	boolean online = p.isOnline();
        	String rank = "Membro";
        	
        	if (cplayer.isLeader()) {
        		rank = "Líder";
        	}
        	if (cplayer.isTrusted() && !cplayer.isLeader()) {
        		rank = "Oficial";
        	}
        	if (!cplayer.isLeader() && !cplayer.isTrusted()) {
        		rank = "Membro";
        	}
        	
    		inv.setItem(10, new ItemBuilder(Material.SKULL_ITEM)
    				.durability(3)
    				.owner(player.getName())
        			.name(prefix + "§7" + player.getName())
        			.lore("§7Moedas: §f" + plugin.econ.getBalance(player.getName()),
        					"§7Cargo: §f" + rank,
        					"§7KDR: §f" + formatter.format(cplayer.getKDR()),
        					"§7Abates: §8[Rival §f" + cplayer.getRivalKills() + "§8 Neutro: §f" + cplayer.getNeutralKills() + "§8 Civil: §f" + cplayer.getCivilianKills() + "§8]",
        					"§7Mortes: §f" + cplayer.getDeaths(),
        					"§7Status: §f" + (online ? "§aOnline" : "§cOffline"))
        			.build());
    		
            List<ClanPlayer> members = clan.getAllMembers();
            plugin.getClanManager().sortClanPlayersByLastSeen(members);
			List<String> lore = new ArrayList<>();
			
			lore.add("§7Status: §f" + (clan.isVerified() ? "§aVerificado" : "§cNão verificado"));
			lore.add("§7Líderes: §f" + clan.getLeaders().toString().replaceAll("\\[", "").replaceAll("]", ""));
			lore.add("§7KDR: §f" + clan.getTotalKDR());
			lore.add("§7Abates: §8[Rival: §f" + clan.getTotalRival() + "§8 Neutro: §f" + clan.getTotalNeutral() + "§8 Civil: §f" + clan.getTotalCivilian() + "§8]");
			lore.add("§7Mortes: §f" + clan.getTotalDeaths());
			lore.add("§7Membros: §f" + clan.getMembers().size() + "/15");
			lore.add("§7Membros Online: §f" + clan.getOnlineMembers().size());
			
			for (ClanPlayer cp : members) {
				lore.add("  §7" + cp.toString().replaceAll("\\[", "").replaceAll("]", ""));
			}
    		inv.setItem(11, Alphabetic.getAlphabet(new ItemBuilder(Material.BANNER)
    				.name("§7[" + clan.getTag() + "] - " + clan.getName())
    				.durability(15)
    				.removeAttributes()
    				.listLore(lore)
    				.build(), clan.getTag(), DyeColor.WHITE, DyeColor.BLACK));
    		inv.setItem(14, new ItemBuilder(Material.SKULL_ITEM)
    				.durability(3)
    				.owner("821")
    				.name("§aAjuda")
    				.lore("§7Todas opções exibidas aqui também estão disponíveis",
    						"§7Utilizando o comando '§f/clan ajuda§7'.")
    				.build());
    		inv.setItem(15, new ItemBuilder(Material.NETHER_STAR)
    				.name("§aClans online")
    				.lore("§7Listar os clans online.")
    				.build());
    		inv.setItem(16, new ItemBuilder(Material.EMPTY_MAP)
    				.name("§aRelações")
    				.lore("§7Listar as relações do seu clan.")
    				.build());
    		inv.setItem(28, new ItemBuilder(Material.SKULL_ITEM)
    				.durability(3)
    				.owner(player.getName())
    				.name("§aMembros")
    				.lore("§7Listar os membros do seu clan.")
    				.build());
    		inv.setItem(30, new ItemBuilder(Material.FIREBALL)
    				.name("§aMercado do clan")
    				.lore("§7Abrir mercado de clans.")
    				.build());
    		if (plugin.getConfig().getBoolean("use_mcmmo") == true) {
    			inv.setItem(32, new ItemBuilder(Material.BOOK_AND_QUILL)
    					.name("§eRanking de Habilidades")
    					.lore("§7Clique para abrir o ranking de habilidades.")
    					.build());
    			inv.setItem(33, new ItemBuilder(Material.EXP_BOTTLE)
    					.name("§eHabilidades")
    					.lore("§7Clique para ver suas habilidades.")
    					.build());
    		}
    		inv.setItem(34, new ItemBuilder(Material.DARK_OAK_DOOR_ITEM)
    				.removeAttributes()
    				.name((cplayer.isLeader() ? "§cDesfazer" : "§cSair"))
    				.lore((cplayer.isLeader() ? "§7Desfazer seu clan." : "§7Sair do seu clan."))
    				.build());
    		
    		player.openInventory(inv);
    	}
    }
    
    /**
     * Inventory events
     */
	
	@EventHandler
    public void onClick(InventoryClickEvent e ) {
    	Player p = (Player) e.getWhoClicked();
    	Inventory inv = e.getInventory();
    	ItemStack item = e.getCurrentItem();
    	
    	if (inv.getName().equalsIgnoreCase(p.getName()) && item != null && item.getTypeId() != 0) {
    		e.setCancelled(true);
    		
        	boolean hasmeta = item.hasItemMeta();
    		
        	if (hasmeta && item.getItemMeta().getDisplayName().equalsIgnoreCase("§eCriar um clan")) {
    			p.closeInventory();
    			p.sendMessage("§aDigite /clan criar <tag> <nome>");
    		}
        	if (hasmeta && item.getItemMeta().getDisplayName().equalsIgnoreCase("§aAjuda")) {
        		p.closeInventory();
        		p.performCommand("clan ajuda");
    			return;
        	}
        	if (hasmeta && item.getItemMeta().getDisplayName().equalsIgnoreCase("§aClans online")) {
        		ClansInventory.invClans(p, 1);
    			return;
        	}
    	}
        SimpleClans plugin = SimpleClans.getInstance();

        ClanPlayer cplayer = plugin.getClanManager().getClanPlayer(p);
        Clan clan = cplayer == null ? null : cplayer.getClan();
        if (cplayer != null && inv.getName().equalsIgnoreCase("[" + clan.getTag() + "] " + cplayer.getName()) && item != null && item.getTypeId() != 0) {
        	e.setCancelled(true);
        	
        	boolean hasmeta = item.hasItemMeta();
        	
        	if (hasmeta && item.getItemMeta().getDisplayName().equalsIgnoreCase("§aMembros")) {
        		MembersInventory.invMembers(p, clan, 1);
    			return;
        	}
        	if (hasmeta && item.getItemMeta().getDisplayName().equalsIgnoreCase("§aAjuda")) {
        		p.closeInventory();
        		p.performCommand("clan ajuda");
    			return;
        	}
        	if (hasmeta && item.getItemMeta().getDisplayName().equalsIgnoreCase("§aClans online")) {
        		ClansInventory.invClans(p, 1);
    			return;
        	}
        	if (hasmeta && item.getItemMeta().getDisplayName().equalsIgnoreCase("§cDesfazer")) {
        		if (cplayer.isLeader()) {
        			p.closeInventory();
        			p.performCommand("clan desfazer");
        			return;
        		}
        	}
        	if (hasmeta && item.getItemMeta().getDisplayName().equalsIgnoreCase("§cSair")) {
        		if (cplayer.isLeader()) {
        			p.sendMessage("§cVocê é líder do clan '§f" + clan.getTag() + "§c' você precisa desfazê-lo!");
        			return;
        		} else {
        			p.closeInventory();
        			p.sendMessage("§cVocê saiu do clan '§f" + clan.getTag() + "§c'.");
        			clan.removePlayerFromClan(p.getName());
        			return;
        		}
        	}
        	if (hasmeta && item.getItemMeta().getDisplayName().equalsIgnoreCase("§aRelações")) {
        		RelationsInventory relationsInventory = new RelationsInventory();
        		relationsInventory.relationGui(p, 1);
        		return;
        	}
        	if (hasmeta && item.getItemMeta().getDisplayName().equalsIgnoreCase("§aMercado do clan")) {
        		ClanMarket clanMarket = new ClanMarket();
        		clanMarket.invMarket(p);
        		return;
        	}
        }
    }
    
    /**
     * Adds a menu item to the /clan menu
     *
     * @param syntax
     * @param description
     */
    public void addMenuItem(String syntax, String description) {
        addMenuItem(syntax, description, ChatColor.AQUA);
    }

    /**
     * Adds a menu item to the /clan menu, specifying syntax color
     * [color] /[syntax] - [description]
     *
     * @param syntax
     * @param description
     * @param color
     */
    public void addMenuItem(String syntax, String description, ChatColor color) {
        menuItems.add(color + "/" + syntax + ChatColor.WHITE + " - " + description);
    }
}
