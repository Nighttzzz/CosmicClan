package net.sacredlabyrinth.phaed.simpleclans.commands;

import net.sacredlabyrinth.phaed.simpleclans.*;
import net.sacredlabyrinth.phaed.simpleclans.uuid.UUIDMigration;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.MessageFormat;

public class PlaceCommand {
    public PlaceCommand() {
    }

    /**
     * Execute the command
     *
     * @param sender
     * @param arg
     */
    public void execute(CommandSender sender, String[] arg) {
        SimpleClans plugin = SimpleClans.getInstance();

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!plugin.getPermissionsManager().has(player, "simpleclans.mod.place")) {
                ChatBlock.sendMessage(sender, ChatColor.RED + plugin.getLang("insufficient.permissions"));
                return;
            }
        }

        if (arg.length != 2) {
            ChatBlock.sendMessage(sender, ChatColor.RED + MessageFormat.format(plugin.getLang("usage.0.place"), plugin.getSettingsManager().getCommandClan()));
            return;
        }

        Player player = Helper.getPlayer(arg[0]);

        if (player == null) {
            ChatBlock.sendMessage(sender, ChatColor.RED + plugin.getLang("no.player.matched"));
            return;
        }

        Clan newClan = plugin.getClanManager().getClan(arg[1]);

        if (newClan == null) {
            ChatBlock.sendMessage(sender, ChatColor.RED + plugin.getLang("the.clan.does.not.exist"));
            return;
        }

        ClanPlayer oldCp = plugin.getClanManager().getClanPlayer(player);

        if (oldCp != null) {
            Clan oldClan = oldCp.getClan();

            if (oldClan.isLeader(player) && oldClan.getLeaders().size() <= 1) {
                oldClan.clanAnnounce(player.getName(), "§7O clan foi desfeito.");
                oldClan.disband();
            } else {
                oldClan.addBb(player.getName(), "§7O jogador '§f" + player.getName() + "§7' saiu do clan.");

                if (SimpleClans.getInstance().hasUUID()) {
                    oldClan.removePlayerFromClan(player.getUniqueId());
                } else {
                    oldClan.removePlayerFromClan(player.getName());
                }
            }
        }

        ClanPlayer cp = plugin.getClanManager().getCreateClanPlayerUUID(player.getName());

        if (cp == null) {
            return;
        }

        newClan.addBb("§7O jogador '§f" + cp.getName() + "§7' entrou para o clan.");
        newClan.addPlayerToClan(cp);

    }
}

