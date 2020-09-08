package net.sacredlabyrinth.phaed.simpleclans.commands;

import net.sacredlabyrinth.phaed.simpleclans.ChatBlock;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.MessageFormat;

/**
 * @author phaed
 */
public class FfCommand {
    public FfCommand() {
    }

    /**
     * Execute the command
     *
     * @param player
     * @param arg
     */
    public void execute(Player player, String[] arg) {
        SimpleClans plugin = SimpleClans.getInstance();

        if (!plugin.getPermissionsManager().has(player, "simpleclans.member.ff")) {
            ChatBlock.sendMessage(player, ChatColor.RED + plugin.getLang("insufficient.permissions"));
            return;
        }

        ClanPlayer cp = plugin.getClanManager().getClanPlayer(player);

        if (cp == null) {
        	player.sendMessage("§cVocê não pertence a um clan!");
            return;
        }
        if (arg.length != 1) {
        	player.sendMessage("§cUtilize /clan ff <on/off>");
            return;
        }

        String action = arg[0];

        if (action.equalsIgnoreCase("on")) {
        	if (!cp.isFriendlyFire()) {
	            cp.setFriendlyFire(true);
	            plugin.getStorageManager().updateClanPlayer(cp);
	            player.sendMessage("§aFogo amigo ativado.");
	            return;
        	} else {
        		player.sendMessage("§cO fogo amigo já está ativado!");
        		return;
        	}
        }
        if (action.equalsIgnoreCase("off")) {
        	if (cp.isFriendlyFire()) {
        		cp.setFriendlyFire(false);
            	plugin.getStorageManager().updateClanPlayer(cp);
            	player.sendMessage("§aFogo amigo desativado.");
            	return;
        	} else {
        		player.sendMessage("§cO fogo amigo já está desativado!");
        		return;
        	}
        }
    }
}
