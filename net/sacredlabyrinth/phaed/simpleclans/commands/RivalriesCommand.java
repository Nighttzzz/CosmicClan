package net.sacredlabyrinth.phaed.simpleclans.commands;

import net.sacredlabyrinth.phaed.simpleclans.ChatBlock;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.Helper;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import net.sacredlabyrinth.phaed.simpleclans.util.messageCenter;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.MessageFormat;
import java.util.List;

/**
 * @author phaed
 */
public class RivalriesCommand {
    public RivalriesCommand() {
    }

    /**
     * Execute the command
     *
     * @param player
     * @param arg
     */
    public void execute(Player player, String[] arg) {
        SimpleClans plugin = SimpleClans.getInstance();

        if (arg.length != 0) {
            ChatBlock.sendMessage(player, ChatColor.RED + MessageFormat.format(plugin.getLang("usage.0.rivalries"), plugin.getSettingsManager().getCommandClan()));
            return;
        }

        List<Clan> clans = plugin.getClanManager().getClans();
        plugin.getClanManager().sortClansByKDR(clans);

        player.sendMessage("");
        messageCenter.sendCenteredMessage(player, "§7Lista de rivais");
        player.sendMessage("");
        for (Clan clan : clans) {
            if (!clan.isVerified()) {
                continue;
            }
            player.sendMessage("§7  Clan: §f" + clan.getName() + " §7Rivais: §f" + clan.getRivalString(", "));
        }
        player.sendMessage("");
    }
}

