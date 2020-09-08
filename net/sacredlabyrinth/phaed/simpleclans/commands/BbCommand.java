package net.sacredlabyrinth.phaed.simpleclans.commands;

import net.sacredlabyrinth.phaed.simpleclans.*;
import org.bukkit.entity.Player;

/**
 * @author phaed
 */
public class BbCommand {

    public BbCommand() {
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
        	player.sendMessage("§cVocê não pertence a um clan.");
            return;
        }

        Clan clan = cp.getClan();

        if (arg.length == 0) {
            clan.displayBb(player);
            return;
        }

        if (arg.length == 1 && arg[0].equalsIgnoreCase("clear")) {
            if (!cp.isTrusted() || !cp.isLeader()) {
            	player.sendMessage("§cVocê precisa do cargo oficial ou superior para executar este comando.");
                return;
            }
            cp.getClan().clearBb();
            player.sendMessage("§aA motd do seu clan foi deletada!");
            return;
        }

        if (!cp.isTrusted() || !cp.isLeader()) {
        	player.sendMessage("§cVocê precisa do cargo oficial ou superior para executar este comando.");
            return;
        }

        String msg = Helper.toMessage(arg);
        clan.addBb(player.getName(), "§7" + player.getName() + ": §f" + msg);
        plugin.getStorageManager().updateClan(clan);
    }
}