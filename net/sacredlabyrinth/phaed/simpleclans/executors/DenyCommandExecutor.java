package net.sacredlabyrinth.phaed.simpleclans.executors;

import net.sacredlabyrinth.phaed.simpleclans.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DenyCommandExecutor implements CommandExecutor {
    SimpleClans plugin;

    public DenyCommandExecutor() {
        plugin = SimpleClans.getInstance();
    }

    @SuppressWarnings("deprecation")
	@Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        ClanPlayer cp = plugin.getClanManager().getClanPlayer(player);

        if (cp != null) {
            Clan clan = cp.getClan();

            if (!clan.isLeader(player)) {
            	player.sendMessage("§cVocê precisa co argo líder para executar este comando.");
                return false;
            }
            if (!plugin.getRequestManager().hasRequest(clan.getTag())) {
            	player.sendMessage("§cNão há nada para rejeitar.");
                return false;
            }
            if (cp.getVote() != null) {
            	player.sendMessage("§cVocê já votou.");
                return false;
            }

            plugin.getRequestManager().deny(cp);
            clan.leaderAnnounce("§cO líder '§f" + player.getName() + "§c' votou por não.");
        } else {
            if (!plugin.getRequestManager().hasRequest(player.getName().toLowerCase())) {
            	player.sendMessage("§cNão há nada para rejeitar.");
                return false;
            }
            if (SimpleClans.getInstance().hasUUID()) {
                cp = plugin.getClanManager().getCreateClanPlayer(player.getUniqueId());
            } else {
                cp = plugin.getClanManager().getCreateClanPlayer(player.getName());
            }
            player.sendMessage("§cVocê rejeitou o convite.");
            plugin.getRequestManager().deny(cp);
        }

        return true;
    }
}
