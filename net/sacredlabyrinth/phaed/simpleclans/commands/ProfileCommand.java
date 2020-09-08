package net.sacredlabyrinth.phaed.simpleclans.commands;

import net.sacredlabyrinth.phaed.simpleclans.*;
import net.sacredlabyrinth.phaed.simpleclans.util.messageCenter;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author phaed
 */
public class ProfileCommand {
    public ProfileCommand() {
    }

    /**
     * Execute the command
     *
     * @param player
     * @param arg
     */
    public void execute(Player player, String[] arg) {
        SimpleClans plugin = SimpleClans.getInstance();
        String headColor = plugin.getSettingsManager().getPageHeadingsColor();
        String subColor = plugin.getSettingsManager().getPageSubTitleColor();
        NumberFormat formatter = new DecimalFormat("#.#");

        Clan clan = null;
        if (arg.length == 0) {
            if (plugin.getPermissionsManager().has(player, "simpleclans.member.profile")) {
                ClanPlayer cp = plugin.getClanManager().getClanPlayer(player);

                if (cp == null) {
                    ChatBlock.sendMessage(player, ChatColor.RED + plugin.getLang("not.a.member.of.any.clan"));
                } else {
                    if (cp.getClan().isVerified()) {
                        clan = cp.getClan();
                    } else {
                        ChatBlock.sendMessage(player, ChatColor.RED + plugin.getLang("clan.is.not.verified"));
                    }
                }
            } else {
                ChatBlock.sendMessage(player, ChatColor.RED + plugin.getLang("insufficient.permissions"));
            }
        } else if (arg.length == 1) {
            if (plugin.getPermissionsManager().has(player, "simpleclans.anyone.profile")) {
                clan = plugin.getClanManager().getClan(arg[0]);

                if (clan == null) {
                	player.sendMessage("�cClan n�o localizado.");
                }
            } else {
                ChatBlock.sendMessage(player, ChatColor.RED + plugin.getLang("insufficient.permissions"));
            }
        } else {
            ChatBlock.sendMessage(player, ChatColor.RED + MessageFormat.format(plugin.getLang("usage.0.profile.tag"), plugin.getSettingsManager().getCommandClan()));
        }

        if (clan != null) {
            if (clan.isVerified()) {
            	player.sendMessage("");
            	messageCenter.sendCenteredMessage(player, "§7" + clan);
            	player.sendMessage("");
                String name = clan.getName();
                String leaders = clan.getLeaders().toString();
                int onlineCount = Helper.stripOffLinePlayers(clan.getMembers()).size();
                
                boolean hasallies = clan.getAllies().size() > 0;
                
                String allies = (hasallies ? clan.getAllyString(subColor + ", ") : "Nenhum");

                boolean hasrivals = clan.getRivals().size() > 0;
                
                String rivals = (hasrivals ? clan.getRivalString(subColor + ", ") : "Nenhum");
                String kdr = formatter.format(clan.getTotalKDR());
                int deaths = clan.getTotalDeaths();
                int rival = clan.getTotalRival();
                int neutral = clan.getTotalNeutral();
                int civ = clan.getTotalCivilian();
                player.sendMessage("§7  Nome: §f" + name);
                player.sendMessage("§7  Status: §f" + (clan.isVerified() ? "�aVerificado" : "�cN�o verificado"));
                player.sendMessage("§7  Líderes: §c" + leaders.replaceAll("\\[", "").replaceAll("]", ""));
                player.sendMessage("§7  Membros: §f" + onlineCount);
                player.sendMessage("§7  KDR: §f" + kdr);
                player.sendMessage("§7  Abates: §8[Rival: §f" + rival + "§8 Neutro: §f" + neutral + "§8 Civil: §f" + civ + "§8]");
                player.sendMessage("§7  Mortes: §f" + deaths);
                player.sendMessage("§7  Aliados: §f");
                List<String> aliados = new ArrayList<>();
                aliados.add(allies.replaceAll("\\[", "").replaceAll("]", ""));
                player.sendMessage("§3    " + aliados.toString().replaceAll("\\[", "").replaceAll("]", ""));
                List<String> rivais = new ArrayList<>();
                rivais.add(rivals.replaceAll("\\[", "").replaceAll("]", ""));
                player.sendMessage("§7  Rivais: §f");
                player.sendMessage("§c    " + rivais.toString().replaceAll("\\[", "").replaceAll("]", ""));
                player.sendMessage("§7");
            } else {
            	player.sendMessage("§cClan não verificado.");
            }
        }
    }
}
