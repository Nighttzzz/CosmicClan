package net.sacredlabyrinth.phaed.simpleclans.commands;

import org.bukkit.entity.Player;

import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import net.sacredlabyrinth.phaed.simpleclans.events.PlayerHomeSetEvent;

public class SetHomeCommand {
	
    public SetHomeCommand() {
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
        } else {
        	if (!cp.isLeader() && !cp.isTrusted()) {
        		player.sendMessage("§cVocê precisa do cargo Oficial ou superior para executar este comando.");
        	}
        	Clan clan = cp.getClan();

            PlayerHomeSetEvent homeSetEvent = new PlayerHomeSetEvent(clan, cp, player.getLocation());
            SimpleClans.getInstance().getServer().getPluginManager().callEvent(homeSetEvent);

            if (homeSetEvent.isCancelled() || !plugin.getClanManager().purchaseHomeTeleportSet(player)) {
                return;
            }

            clan.setHomeLocation(player.getLocation());
            player.sendMessage("§aA base do seu clan foi definida com sucesso!");
            return;
        }
    }
}
