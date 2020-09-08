package net.sacredlabyrinth.phaed.simpleclans.commands;

import net.sacredlabyrinth.phaed.simpleclans.*;
import net.sacredlabyrinth.phaed.simpleclans.util.messageCenter;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;

/**
 * @author phaed
 */
public class LookupCommand {
    public LookupCommand() {
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

        String playerName = null;

        if (arg.length == 0) {
            if (!plugin.getPermissionsManager().has(player, "simpleclans.member.lookup")) {
                ChatBlock.sendMessage(player, ChatColor.RED + plugin.getLang("insufficient.permissions"));
                return;
            }
            playerName = player.getName();
        } else if (arg.length == 1) {
            if (!plugin.getPermissionsManager().has(player, "simpleclans.anyone.lookup")) {
                ChatBlock.sendMessage(player, ChatColor.RED + plugin.getLang("insufficient.permissions"));
                return;
            }
            playerName = arg[0];
        } else {
            ChatBlock.sendMessage(player, ChatColor.RED + MessageFormat.format(plugin.getLang("usage.lookup.tag"), plugin.getSettingsManager().getCommandClan()));
            return;
        }

        ClanPlayer targetCp = plugin.getClanManager().getAnyClanPlayer(playerName);
        ClanPlayer myCp = plugin.getClanManager().getClanPlayer(player.getName());
        Clan myClan = myCp == null ? null : myCp.getClan();

        if (targetCp != null) {
            Clan targetClan = targetCp.getClan();

            String status = targetClan == null ? plugin.getLang("free.agent") : (targetCp.isLeader() ? plugin.getSettingsManager().getPageLeaderColor() + plugin.getLang("leader") : (targetCp.isTrusted() ? plugin.getSettingsManager().getPageTrustedColor() + plugin.getLang("trusted") : plugin.getSettingsManager().getPageUnTrustedColor() + plugin.getLang("untrusted")));
            int rival = targetCp.getRivalKills();
            int neutral = targetCp.getNeutralKills();
            int civilian = targetCp.getCivilianKills();
            int deaths = targetCp.getDeaths();
            String kdr = formatter.format(targetCp.getKDR());
        
            String prefix = PermissionsEx.getUser(playerName).getPrefix();
            
            player.sendMessage("");
            messageCenter.sendCenteredMessage(player, prefix + "§7" + targetClan.getTag() + " " + playerName);
            player.sendMessage("");
            player.sendMessage("§7  Clan: §f" + targetClan.getName());
            player.sendMessage("§7  Status: §f" + status);
            player.sendMessage("§7  KDR: §f" + kdr);
            player.sendMessage("§7  Abates: §8[Rivais: §f" + rival + "§8 Neutros: §f" + neutral + "§8 Civis: §f" + civilian + "§8]");
            player.sendMessage("§7  Mortes: §f" + deaths);
            player.sendMessage("");
        } else {
        	if (arg.length == 0) {
        		player.sendMessage("§cVocê não pertence a um clan.");
        	} else {
        		player.sendMessage("§cO jogador '§f" + arg[0] + "§c' não pertence a um clan.");
        	}  ChatBlock.sendMessage(player, MessageFormat.format(plugin.getLang("kill.type.civilian"), ChatColor.DARK_GRAY));
        }
    }
}
