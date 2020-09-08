package net.sacredlabyrinth.phaed.simpleclans.executors;

import net.sacredlabyrinth.phaed.simpleclans.ChatBlock;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.Helper;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class AllyCommandExecutor implements CommandExecutor {
    SimpleClans plugin;

    public AllyCommandExecutor() {
        plugin = SimpleClans.getInstance();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;

        if (!plugin.getSettingsManager().isAllyChatEnable()) {
            return false;
        }

        if (strings.length == 0) {
            return false;
        }
        ClanPlayer cp = plugin.getClanManager().getClanPlayer(player);

        if (cp == null) {
            return false;
        }
        
        boolean isleader = cp.isLeader();
        
        boolean istrusted = cp.isTrusted() && !cp.isLeader();
        
        boolean ismember = !cp.isLeader() && !cp.isTrusted();
        
        String message = "§9[ALIADOS] §7" + cp.getClan().getTag() + " " + "§9" + (isleader ? "**" : "") + (istrusted ? "*" : "") + (ismember ? "+" : "") + cp.getName() + ": " +  Helper.toMessage(strings);

         Player self = cp.toPlayer();
        ChatBlock.sendMessage(self, message);

        Set<ClanPlayer> allies = cp.getClan().getAllAllyMembers();
        allies.addAll(cp.getClan().getMembers());

        for (ClanPlayer ally : allies) {
            if (ally.isMutedAlly()) {
                continue;
            }
            Player member = ally.toPlayer();
            if (SimpleClans.getInstance().hasUUID()) {
                if (player.getUniqueId().equals(ally.getUniqueId())) {
                    continue;
                }
            } else {
                if (player.getName().equalsIgnoreCase(ally.getName())) {
                    continue;
                }
            }
            
            ChatBlock.sendMessage(member, message);
        }
        return false;
    }
}
