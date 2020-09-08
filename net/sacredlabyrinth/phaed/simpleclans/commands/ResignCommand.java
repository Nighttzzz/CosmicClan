package net.sacredlabyrinth.phaed.simpleclans.commands;

import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import org.bukkit.entity.Player;

/**
 * @author phaed
 */
public class ResignCommand {
    public ResignCommand() {
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

        if (cp == null) {
        	player.sendMessage("§cVocê não pertence a um clan!");
            return;
        }
        Clan clan = cp.getClan();

        if (clan.getMembers().size() > 1) {
	        if (!clan.isLeader(player) || clan.getLeaders().size() > 1) {
	        	player.sendMessage("§cVocê saiu do clan '§f" + cp.getClan().getName() + "§c'.");
	            if (SimpleClans.getInstance().hasUUID()) {
	                clan.removePlayerFromClan(player.getUniqueId());
	            } else {
	                clan.removePlayerFromClan(player.getName());
	            }
	            return;
	        }
        } else {
            player.sendMessage("§aSeu clan foi desfeito devido a você ser o último membro.");
        	clan.disband();
        	return;
        }
        if (clan.isLeader(player) && clan.getMembers().size() == 1) {
            player.sendMessage("§aSeu clan foi desfeito devido a você ser o último membro.");
        	clan.disband();
            return;
        } else {
        	player.sendMessage("§cVocê não é o único no clan, promova alguém a líder ou desfaça-o.");
            return;
        }
    }
}
