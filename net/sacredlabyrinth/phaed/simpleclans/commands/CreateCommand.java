package net.sacredlabyrinth.phaed.simpleclans.commands;

import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.Helper;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import org.bukkit.entity.Player;

/**
 * @author phaed
 */
public class CreateCommand {
    public CreateCommand() {
    }

    /**
     * Execute the command
     *
     * @param player
     * @param arg
     */
    public void execute(Player player, String[] arg) {
        SimpleClans plugin = SimpleClans.getInstance();

        if (arg.length > 1) {
	        String tag = arg[0];
	        String cleanTag = Helper.cleanTag(arg[0]);
	
	        String name = Helper.toMessage(Helper.removeFirst(arg));
	        
	        if (!cleanTag.matches("[0-9a-zA-Z]*")) {
	        	player.sendMessage("§cA tag do seu clan não pode conter caracteres especiais.");
	            return;
	        }
	        if (tag.contains("&")) {
	        	player.sendMessage("§cA tag do seu clan não pode conter o caractere '§f&§c'.");
	        	return;
	        }
	        if (tag.length() > 3) {
	        	player.sendMessage("§cA tag do seu clan não pode ser maior que 3 caracteres.");
	        	return;
	        }
	        if (tag.length() < 3) {
	        	player.sendMessage("§cA tag do seu clan não pode ser menor que 3 caracteres.");
	        	return;
	        }
	        if (name.contains("&")) {
	        	player.sendMessage("§cO nome do seu clan não pode conter o caractere '§f&§c'.");
	        	return;
	        }
	        ClanPlayer cplayer = plugin.getClanManager().getClanPlayer(player);
	        
	        if (cplayer != null) {
	        	player.sendMessage("§cVocê já pertence ao clan '§f" + cplayer.getClan().getTag() + "§c'.");
	        	return;
	        }
	        if (plugin.getClanManager().isClan(tag)) {
	        	player.sendMessage("§cJá existe um clan com esta tag.");
	        	return;
	        }
	       	plugin.getClanManager().createClan(player, tag, name);
	       	Clan clan = plugin.getClanManager().getClan(tag);
	       	clan.verifyClan();
	       	clan.addBb(player.getName());
	       	plugin.getStorageManager().updateClan(clan);
	       	player.sendMessage("§aYay! Seu clan foi criado com sucesso!");
        } else {
            player.sendMessage("§cUtilize /clan criar <tag> <nome>.");
            return;
        }
    }
}
