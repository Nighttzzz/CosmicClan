package net.sacredlabyrinth.phaed.simpleclans.commands;

import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import org.bukkit.entity.Player;

/**
 * @author phaed
 */
public class DisbandCommand {
    public DisbandCommand() {
    }

    /**
     * Execute the command
     *
     * @param player
     * @param arg
     */
    public void execute(Player player, String[] arg) {
        SimpleClans plugin = SimpleClans.getInstance();

        if (arg.length == 0) {
            ClanPlayer cp = plugin.getClanManager().getClanPlayer(player);

            if (cp == null) {
            	player.sendMessage("§cVocê não pertence a um clan.");
                return;
            }

            Clan clan = cp.getClan();

            if (!clan.isLeader(player)) {
            	player.sendMessage("§cVocê precisa do cargo Líder para executar este comando.");
                return;
            }
            if (clan.getLeaders().size() != 1) {
                plugin.getRequestManager().addDisbandRequest(cp, clan);
                player.sendMessage("§cUm pedido de exclusão do clan foi enviado para os outros líderes.");
                return;
            }

            clan.clanAnnounce(player.getName(), "§cO clan foi desfeito!");
            clan.disband();
            return;
        }

        if (arg.length > 1) {
        	player.sendMessage("§cUtilize /clan desfazer <tag>");
            return;
        }

        if (!plugin.getPermissionsManager().has(player, "simpleclans.mod.disband")) {
        	player.sendMessage("§cVocê precisa do cargo Administrador ou superior para usar este comando.");
            return;
        }

        Clan clan = plugin.getClanManager().getClan(arg[0]);

        if (clan == null) {
        	player.sendMessage("§cO clan '§f" + arg[0] + "§c' não existe.");
            return;
        }

        plugin.getClanManager().serverAnnounce("§cO clan '§f" + clan.getTag() + "§c' foi desfeito.");
        clan.disband();
    }
}
