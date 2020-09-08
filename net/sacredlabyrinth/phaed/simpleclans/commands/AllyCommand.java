package net.sacredlabyrinth.phaed.simpleclans.commands;

import net.sacredlabyrinth.phaed.simpleclans.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.MessageFormat;
import java.util.List;

/**
 * @author phaed
 */
public class AllyCommand {
    public AllyCommand() {
    }

    /**
     * Execute the command
     *
     * @param player
     * @param arg
     */
    public void execute(Player player, String[] arg) {
        SimpleClans plugin = SimpleClans.getInstance();

        if (!plugin.getPermissionsManager().has(player, "simpleclans.leader.ally")) {
        	player.sendMessage("§cPermissões insuficientes.");
            return;
        }

        ClanPlayer cp = plugin.getClanManager().getClanPlayer(player);

        if (cp == null) {
        	player.sendMessage("§cVocê não pertence a um clan.");
            return;
        }

        Clan clan = cp.getClan();

        if (!cp.isLeader() && !cp.isTrusted()) {
			player.sendMessage("§cVocê precisa ser oficial ou superior para executar esta a��o.");
            return;
        }

        if (arg.length != 2) {
        	player.sendMessage("§cUtilize /clan aliado <aceitar/rejeitar> <tag>");
            return;
        }

        String action = arg[0];
        Clan ally = plugin.getClanManager().getClan(arg[1]);

        if (ally == null) {
        	player.sendMessage("§cO clan '§f" + arg[1] + "§c' não foi localizado.");
            return;
        }

        if (action.equalsIgnoreCase("aceitar")) {
        	if (clan.isAlly(ally.getTag())) {
        		player.sendMessage("§cSeu clan já é aliado de '§f" + ally.getTag() + "§c'.");
        		return;
        	}
        	if (plugin.getRequestManager().hasRequest(ally.getTag())) {
        		clan.addAlly(ally);
        		clan.addBb("§aO clan '" + "§7[" + ally.getTag() + "] §f" + ally.getName() + "§a' agora é um aliado.");
        		ally.addBb("§aO clan '" + "§7[" + clan.getTag() + "] §f" + clan.getName() + "§a' agora é um aliado.");
        		player.sendMessage("§aO clan '§f" + ally + "§a' agora é aliado.");
        		for (ClanPlayer on : ally.getOnlineMembers()) {
        			Player ons = (Player) on.toPlayer();
        			ons.sendMessage("§aO clan '§f" + clan.getTag() + "§a' agora é um aliado.");
        		}
        		for (ClanPlayer on : clan.getOnlineMembers()) {
        			Player ons = (Player) on.toPlayer();
        			ons.sendMessage("§aO clan '§f" + ally.getTag() + "§a' agora é um aliado.");
        		}
        		return;
        	} else {
        		player.sendMessage("§cO clan '§f" + arg[1] + "§c' não lhe enviou pedido de aliado.");
        		return;
        	}
        } else if (action.equalsIgnoreCase("rejeitar")) {
            if (clan.isAlly(ally.getTag())) {
            	player.sendMessage("§cO clan '§f" + ally + "§c' já é um liado.");
                return;
            }
            List<ClanPlayer> onlineLeaders = Helper.stripOffLinePlayers(clan.getLeaders());
            
            if (onlineLeaders.isEmpty()) {
            	return; 
            } else {
            	for (ClanPlayer leaders : clan.getLeaders()) {
            		Player leadersp = (Player) leaders.toPlayer();
            		leadersp.sendMessage("§cA aliança com o clan '§f" + ally.getTag() + "§c' foi recusada.");
            	}
            }
            player.sendMessage("§cVocê rejeitou o pedido de aliança do clan '§f" + arg[1] + "§c'.");
            clan.removeAlly(ally);
        } else {
        	player.sendMessage("§cUtilize /clan aliado <aceitar/rejeitar> <tag>");
        	return;
        }
    }
}
