package net.sacredlabyrinth.phaed.simpleclans.commands;

import net.sacredlabyrinth.phaed.simpleclans.ChatBlock;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import net.sacredlabyrinth.phaed.simpleclans.util.messageCenter;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.List;


/**
 * @author phaed
 */
public class ListCommand {
    public ListCommand() {
    }

    /**
     * Execute the command
     *
     * @param player
     * @param arg
     */
    public void execute(Player player, String[] arg) {
        SimpleClans plugin = SimpleClans.getInstance();
        NumberFormat formatter = new DecimalFormat("#.#");

        if (!plugin.getPermissionsManager().has(player, "simpleclans.anyone.list")) {
            ChatBlock.sendMessage(player, ChatColor.RED + plugin.getLang("insufficient.permissions"));
            return;
        }
        if (arg.length != 0) {
            ChatBlock.sendMessage(player, ChatColor.RED + MessageFormat.format(plugin.getLang("usage.list"), plugin.getSettingsManager().getCommandClan()));
            return;
        }

        List<Clan> clans = plugin.getClanManager().getClans();
        plugin.getClanManager().sortClansByKDR(clans);

        if (clans.isEmpty()) {
            ChatBlock.sendMessage(player, ChatColor.RED + plugin.getLang("no.clans.have.been.created"));
            return;
        }

        player.sendMessage("");
        messageCenter.sendCenteredMessage(player, "§7Lista de clans");
        player.sendMessage("");
        for (Clan clan : clans) {
            if (!plugin.getSettingsManager().isShowUnverifiedOnList() && !clan.isVerified()) {
                continue;
            }
            if (clan.getOnlineMembers().size() > 0) {
            	String name = clan.getName();
            	String tag = clan.getTag();
            	int size = clan.getOnlineMembers().size();
            	String kdr = formatter.format(clan.getTotalKDR());
            	ChatBlock.sendMessage(player, "  §7[" + tag + "] " + name + " §8- §7" + size + "/15 Online §8- §7" + kdr + " KDR");
            }
        }
        player.sendMessage("");
    }
}



