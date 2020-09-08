package net.sacredlabyrinth.phaed.simpleclans.commands;

import net.sacredlabyrinth.phaed.simpleclans.ChatBlock;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.Helper;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.MessageFormat;

import net.sacredlabyrinth.phaed.simpleclans.uuid.UUIDMigration;

/**
 * @author phaed
 */
public class KickCommand {
    public KickCommand() {
    }

    /**
     * Execute the command
     *
     * @param player
     * @param arg
     */
    public void execute(Player player, String[] arg) {
        SimpleClans plugin = SimpleClans.getInstance();

        if (!plugin.getPermissionsManager().has(player, "simpleclans.leader.kick")) {
            ChatBlock.sendMessage(player, ChatColor.RED + plugin.getLang("insufficient.permissions"));
            return;
        }

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
        if (arg.length != 1) {
        	player.sendMessage("§cUtilize /clan expulsar <jogador>");
            return;
        }

        String kicked = arg[0];

        if (kicked == null) {
        	player.sendMessage("§cO jogador '§f" + arg[0] + "§c' não existe.");
            return;
        }
        if (kicked.equalsIgnoreCase(player.getName())) {
        	player.sendMessage("§cVocê não pode expulsar a si mesmo.");
            return;
        }
        if (!clan.isMember(kicked)) {
        	player.sendMessage("§cO jogador '§f" + arg[0] + "§c' não é membro do seu clan.");
            return;
        }
        if (clan.isLeader(kicked)) {
        	player.sendMessage("§cVocê não pode expulsar outro líder.");
        	return;
        }

        clan.addBb(player.getName(), "§7O jogador '§f" + arg[0] + "§7' foi expulso por: '§f" + player.getName() + "§7'.");

        if (SimpleClans.getInstance().hasUUID()) {
            clan.removePlayerFromClan(UUIDMigration.getForcedPlayerUUID(kicked));
        } else {
            clan.removePlayerFromClan(kicked);
        }
    }
}
