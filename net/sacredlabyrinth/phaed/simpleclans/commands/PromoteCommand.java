package net.sacredlabyrinth.phaed.simpleclans.commands;

import net.sacredlabyrinth.phaed.simpleclans.*;
import net.sacredlabyrinth.phaed.simpleclans.uuid.UUIDMigration;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.MessageFormat;

/**
 * @author phaed
 */
public class PromoteCommand {
    public PromoteCommand() {
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
        	player.sendMessage("§cVocê não é um líder para usar este comando.");
            return;
        }
        if (arg.length != 1) {
        	player.sendMessage("§cUtilize /clan promover <jogador>");
            return;
        }

        Player promoted = Helper.getPlayer(arg[0]);

        if (promoted == null) {
        	player.sendMessage("§cO jogador '§f" + arg[0] + "§c' não foi encontrado.");
            return;
        }
        if (promoted.getName().equals(player.getName())) {
        	player.sendMessage("§cVocê não pode se promover!");
            return;
        }
        if (!clan.isMember(promoted)) {
        	player.sendMessage("§cO jogador '§f" + promoted.getName() + "§c' não é membro do seu clan.");
            return;
        }
        if (clan.isLeader(promoted) && plugin.getSettingsManager().isConfirmationForPromote()) {
        	player.sendMessage("§cO jogador '§f" + promoted.getName() + "§c' já é um líder.");
            return;
        }
        promoted.sendMessage("§aVocê foi promovido ao cargo de líder");
        player.sendMessage("§aO jogador '§f" + promoted.getName() + "§a' foi promovido a líder.");
        clan.addBb(player.getName(), "O jogador '" + promoted.getName() + "' foi promovido a lider.");
        if (SimpleClans.getInstance().hasUUID()) {
            clan.promote(promoted.getUniqueId());
        } else {
            clan.promote(promoted.getName());
        }
    }
}

