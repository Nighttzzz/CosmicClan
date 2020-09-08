package net.sacredlabyrinth.phaed.simpleclans.commands;

import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import org.bukkit.entity.Player;

/**
 * @author phaed
 */
public class DemoteCommand {
    public DemoteCommand() {
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
        	player.sendMessage("§cVocê deve pertencer a um clan.");
            return;
        }

        Clan clan = cp.getClan();

        if (!clan.isLeader(player)) {
        	player.sendMessage("§cVocê não tem permissão de líder para isto.");
            return;
        }
        if (arg.length != 1) {
        	player.sendMessage("§cUtilize /clan rebaixar <jogador>");
            return;
        }

        String demotedName = arg[0];
        boolean allOtherLeadersOnline;

        if (demotedName == null) {
        	player.sendMessage("§cO jogador '§f" + arg[0] + "§c' não foi localizado.a");
            return;
        }
        allOtherLeadersOnline = clan.allOtherLeadersOnline(demotedName);

        if (!allOtherLeadersOnline) {
        	player.sendMessage("§cÉ preciso outros líderes estarem online para votarem.");
            return;
        }
        if (!clan.isLeader(demotedName)) {
        	player.sendMessage("§cO jogador '§f" + demotedName + "§c' não é líder do seu clan.");
        	return;
        }
        if (clan.getLeaders().size() != 1 && plugin.getSettingsManager().isConfirmationForDemote()) {
        	plugin.getRequestManager().addDemoteRequest(cp, demotedName, clan);
        	player.sendMessage("§cUm pedido de rebaixamento foi enviado para os líderes.");
            return;
        }
        player.sendMessage("§aO jogador '§f" + demotedName + "§a' foi rebaixado com sucesso!");
        clan.addBb(player.getName(), "§7O jogador '§f" + demotedName + "§7' foi rebaixado.");
        clan.demote(demotedName);
    }
}
