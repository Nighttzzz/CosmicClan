package net.sacredlabyrinth.phaed.simpleclans.executors;

import net.sacredlabyrinth.phaed.simpleclans.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AcceptCommandExecutor implements CommandExecutor {
    SimpleClans plugin;

    public AcceptCommandExecutor() {
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
            	player.sendMessage("§cVocê precisa do cargo Líder para executar este comando.");
                return false;
            }
            if (!plugin.getRequestManager().hasRequest(clan.getTag())) {
            	player.sendMessage("§cNão há nada para aceitar/rejeitar");
                return false;
            }
            if (cp.getVote() != null) {
            	player.sendMessage("§cVocê já votou!");
                return false;
            }
            clan.addBb("§7O jogador '§f" + cp.getName() +  "§7' entrou para o clan.");
            plugin.getRequestManager().accept(cp);
            clan.leaderAnnounce("§aO líder '§f" + cp.getName() + "§a' votou para aceitar.");
        } else {
            if (!plugin.getRequestManager().hasRequest(player.getName().toLowerCase())) {
            	player.sendMessage("§cNão há nenhum convite.");
                return false;
            }
            if (SimpleClans.getInstance().hasUUID()) {
                cp = plugin.getClanManager().getCreateClanPlayer(player.getUniqueId());
                cp.setName(player.getName());
            } else {
                cp = plugin.getClanManager().getCreateClanPlayer(player.getName());
            }
            player.sendMessage("§aVocê aceitou o convite.");
            plugin.getRequestManager().accept(cp);
        }

        return true;
    }
}
