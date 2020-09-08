package net.sacredlabyrinth.phaed.simpleclans.commands;

import net.sacredlabyrinth.phaed.simpleclans.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.text.MessageFormat;
import java.util.List;
import java.util.Random;
import net.sacredlabyrinth.phaed.simpleclans.events.HomeRegroupEvent;
import net.sacredlabyrinth.phaed.simpleclans.events.PlayerHomeSetEvent;

/**
 * @author phaed
 */
public class HomeCommand {

    public HomeCommand() {
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
        
        if (cp == null)
        {
        	player.sendMessage("§cVocê não pertence a um clan.");
        	return;
        } else {

        	Clan clan = cp.getClan();
        	
            Location loc = clan.getHomeLocation();

            if (loc == null) {
            	player.sendMessage("§cA base do seu clan não foi definida ainda.");
            	return;
            }
            
            player.sendMessage("§aTeleportado até a base do seu clan.");
            player.teleport(loc);
        }
    }
}
