package net.sacredlabyrinth.phaed.simpleclans.commands;

import net.sacredlabyrinth.phaed.simpleclans.*;
import net.sacredlabyrinth.phaed.simpleclans.util.UltimateFancy;
import net.sacredlabyrinth.phaed.simpleclans.uuid.UUIDMigration;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.MessageFormat;

/**
 * @author phaed
 */
public class InviteCommand {
    public InviteCommand() {
    }

    /**
     * Ultimate Json message
     */
    public UltimateFancy message (Player playe, Clan clan) {
    	
    	UltimateFancy msg = new UltimateFancy();
    	
    	msg.next()
    	.text("§7Você foi convidado a entrar no clan '§f" + clan.getTag() + "§7'.")
    	.next()
    	.text("\n")
    	.next()
    	.text("§7Clique ")
    	.next()
    	.text("§a§lAQUI")
    	.clickRunCmd("/aceitar")
    	.next()
    	.text("§r§7 para aceitar ou ")
    	.next()
    	.text("§c§lAQUI")
    	.clickRunCmd("/rejeitar")
    	.next()
    	.text("§r§7 Para rejeitar")
    	.next()
    	.text("\n");
    	
    	return msg;
    }
    
    /**
     * Execute the command
     *
     * @param player
     * @param arg
     */
    
    public void execute(Player player, String[] arg) {
        SimpleClans plugin = SimpleClans.getInstance();
        ClanPlayer cp = plugin.getClanManager().getClanPlayer(player);

        if (arg.length == 0) {
        	player.sendMessage("§cUtilize /clan convidar <jogador>");
        	return;
        }
        
        Player invited = Bukkit.getServer().getPlayer(arg[0]);
        
        if (invited == null) {
        	player.sendMessage("§cO jogador '§f" + arg[0] + "§c' não foi localizado.");
        	return;
        }
        if (cp == null) {
        	player.sendMessage("§cVocê não pertence a um clan.");
            return;
        } else {
        	Clan clan = cp.getClan();
        	ClanPlayer cpInv = plugin.getClanManager().getClanPlayer(invited);
        	
        	if (cpInv != null) {
        		player.sendMessage("§cO jogador já pertence ao clan '§f" + cpInv.getClan().getTag() + "§c'.");
        		return;
        	}
        	if (clan.getSize() >= 15) {
        		player.sendMessage("§cSeu clan já atingiu o limite máximo '§f" + 15 + "§c'.");
        		return;
        	}
        	plugin.getRequestManager().addInviteRequest(cp, invited.getName(), clan);
        	player.sendMessage("§aVocê convidou o jogador '§f" + invited.getName() + "§a'.");
        	UltimateFancy nm = message(invited, clan);
        	nm.send(invited);
        }
    }
}
