package net.sacredlabyrinth.phaed.simpleclans.commands;

import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import org.bukkit.entity.Player;

/**
 * @author phaed
 */
public class TrustCommand {
    public TrustCommand() {
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

        if (!clan.isLeader(player)) {
        	player.sendMessage("§cVocê precisa do cargo líder para executar este comando.");
            return;
        }
        if (arg.length != 1) {
        	player.sendMessage("§cUtilize /clan confiar <jogador>");
            return;
        }

        String trusted = arg[0];

        if (trusted == null) {
        	player.sendMessage("§cO jogador '§f" + trusted + "§c' não foi localizado.");
            return;
        }
        if (trusted.equals(player.getName())) {
        	player.sendMessage("§cVocê não pode dar confiança a você mesmo.");
            return;
        }
        if (!clan.isMember(trusted)) {
        	player.sendMessage("§cEste jogador não é membro do seu clan");
            return;
        }
        if (clan.isLeader(trusted)) {
        	player.sendMessage("§cVocê não pode confiar em alguém que já é líder.");
            return;
        }

        ClanPlayer tcp = plugin.getClanManager().getClanPlayer(trusted);

        if (tcp == null) {
        	player.sendMessage("§cO jogador '§f" + trusted + "§c' não foi localizado.");
            return;
        }

        if (tcp.isTrusted()) {
        	player.sendMessage("§cJogador já possui confiança.");
            return;
        }

        clan.addBb(player.getName(), "§7O jogador '§f" + trusted + "§7' recebeu confiança.");
        tcp.setTrusted(true);
        plugin.getStorageManager().updateClanPlayer(tcp);
    }

}







