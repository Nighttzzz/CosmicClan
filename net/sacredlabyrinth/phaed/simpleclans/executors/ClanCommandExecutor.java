package net.sacredlabyrinth.phaed.simpleclans.executors;

import net.sacredlabyrinth.phaed.simpleclans.*;
import net.sacredlabyrinth.phaed.simpleclans.commands.*;
import net.sacredlabyrinth.phaed.simpleclans.util.messageCenter;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.MessageFormat;

/**
 * @author phaed
 */
public final class ClanCommandExecutor implements CommandExecutor {
    private SimpleClans plugin;
    private CreateCommand createCommand;
    private ListCommand listCommand;
    private ProfileCommand profileCommand;
    private RosterCommand rosterCommand;
    private LookupCommand lookupCommand;
    private AlliancesCommand alliancesCommand;
    private RivalriesCommand rivalriesCommand;
    private AllyCommand allyCommand;
    private BbCommand bbCommand;
    private ToggleCommand toggleCommand;
    private InviteCommand inviteCommand;
    private KickCommand kickCommand;
    private TrustCommand trustCommand;
    private UntrustCommand untrustCommand;
    private PromoteCommand promoteCommand;
    private DemoteCommand demoteCommand;
    private RelationCommand relationCommand;
    private FfCommand ffCommand;
    private ResignCommand resignCommand;
    private DisbandCommand disbandCommand;
    private VerifyCommand verifyCommand;
    private BanCommand banCommand;
    private UnbanCommand unbanCommand;
    private ReloadCommand reloadCommand;
    private GlobalffCommand globalffCommand;
    private MenuCommand menuCommand;
    private WarCommand warCommand;
    private HomeCommand homeCommand;
    private BankCommand bankCommand;
    private PlaceCommand placeCommand;
    private ResetKDRCommand resetKDRCommand;
    private SetHomeCommand setHomeCommand;

    /**
     *
     */
    public ClanCommandExecutor() {
        plugin = SimpleClans.getInstance();
        menuCommand = new MenuCommand();
        createCommand = new CreateCommand();
        listCommand = new ListCommand();
        profileCommand = new ProfileCommand();
        rosterCommand = new RosterCommand();
        lookupCommand = new LookupCommand();
        alliancesCommand = new AlliancesCommand();
        rivalriesCommand = new RivalriesCommand();
        allyCommand = new AllyCommand();
        relationCommand = new RelationCommand();
        bbCommand = new BbCommand();
        toggleCommand = new ToggleCommand();
        inviteCommand = new InviteCommand();
        kickCommand = new KickCommand();
        trustCommand = new TrustCommand();
        untrustCommand = new UntrustCommand();
        promoteCommand = new PromoteCommand();
        demoteCommand = new DemoteCommand();
        ffCommand = new FfCommand();
        resignCommand = new ResignCommand();
        disbandCommand = new DisbandCommand();
        verifyCommand = new VerifyCommand();
        banCommand = new BanCommand();
        unbanCommand = new UnbanCommand();
        reloadCommand = new ReloadCommand();
        globalffCommand = new GlobalffCommand();
        warCommand = new WarCommand();
        homeCommand = new HomeCommand();
        bankCommand = new BankCommand();
        placeCommand = new PlaceCommand();
        resetKDRCommand = new ResetKDRCommand();
        setHomeCommand = new SetHomeCommand();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        new BukkitRunnable(){
            @Override
            public void run() {
                try {
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        if (args.length == 0) {
                            menuCommand.menuGui(player);
                        } else {
                            String subcommand = args[0];
                            String[] subargs = Helper.removeFirst(args);

                            if (subcommand.equalsIgnoreCase(plugin.getLang("create.command"))) {
                                createCommand.execute(player, subargs);
                            } else if (subcommand.equalsIgnoreCase("ajuda") || subcommand.equalsIgnoreCase("help")) {
                                menuCommand.execute(player);
                            } else if (subcommand.equalsIgnoreCase(plugin.getLang("list.command")) || subcommand.equalsIgnoreCase("list")) {
                                listCommand.execute(player, subargs);
                            } else if (subcommand.equalsIgnoreCase(plugin.getLang("bank.command")) || subcommand.equalsIgnoreCase("bank")) {
                                bankCommand.execute(player, subargs);
                            } else if (subcommand.equalsIgnoreCase(plugin.getLang("profile.command")) || subcommand.equalsIgnoreCase("profile")) {
                                profileCommand.execute(player, subargs);
                            } else if (subcommand.equalsIgnoreCase(plugin.getLang("roster.command")) || subcommand.equalsIgnoreCase("roster")) {
                                rosterCommand.execute(player, subargs);
                            } else if (subcommand.equalsIgnoreCase(plugin.getLang("lookup.command")) || subcommand.equalsIgnoreCase("lookup")) {
                                lookupCommand.execute(player, subargs);
                            } else if (subcommand.equalsIgnoreCase("base") || subcommand.equalsIgnoreCase("home")) {
                                homeCommand.execute(player, subargs);
                            }else if (subcommand.equalsIgnoreCase(plugin.getLang("alliances.command")) || subcommand.equalsIgnoreCase("alliances")) {
                                alliancesCommand.execute(player, subargs);
                            } else if (subcommand.equalsIgnoreCase(plugin.getLang("rivalries.command")) || subcommand.equalsIgnoreCase("rivalries")) {
                                rivalriesCommand.execute(player, subargs);
                            } else if (subcommand.equalsIgnoreCase(plugin.getLang("ally.command")) || subcommand.equalsIgnoreCase("ally")) {
                                allyCommand.execute(player, subargs);
                            } else if (subcommand.equalsIgnoreCase(plugin.getLang("bb.command")) || subcommand.equalsIgnoreCase("bb")) {
                                bbCommand.execute(player, subargs);
                            } else if (subcommand.equalsIgnoreCase(plugin.getLang("toggle.command")) || subcommand.equalsIgnoreCase("toggle")) {
                                toggleCommand.execute(player, subargs);
                            } else if (subcommand.equalsIgnoreCase(plugin.getLang("invite.command")) || subcommand.equalsIgnoreCase("invite")) {
                                inviteCommand.execute(player, subargs);
                            } else if (subcommand.equalsIgnoreCase(plugin.getLang("kick.command")) || subcommand.equalsIgnoreCase("kick")) {
                                kickCommand.execute(player, subargs);
                            } else if (subcommand.equalsIgnoreCase(plugin.getLang("trust.command")) || subcommand.equalsIgnoreCase("trust")) {
                                trustCommand.execute(player, subargs);
                            } else if (subcommand.equalsIgnoreCase(plugin.getLang("untrust.command")) || subcommand.equalsIgnoreCase("unstrust")) {
                                untrustCommand.execute(player, subargs);
                            } else if (subcommand.equalsIgnoreCase(plugin.getLang("promote.command")) || subcommand.equalsIgnoreCase("promote")) {
                                promoteCommand.execute(player, subargs);
                            } else if (subcommand.equalsIgnoreCase(plugin.getLang("demote.command")) || subcommand.equalsIgnoreCase("demote")) {
                                demoteCommand.execute(player, subargs);
                            } else if (subcommand.equalsIgnoreCase(plugin.getLang("ff.command")) || subcommand.equalsIgnoreCase("ff")) {
                                ffCommand.execute(player, subargs);
                            } else if (subcommand.equalsIgnoreCase(plugin.getLang("resign.command")) || subcommand.equalsIgnoreCase("resign")) {
                                resignCommand.execute(player, subargs);
                            } else if (subcommand.equalsIgnoreCase(plugin.getLang("disband.command")) || subcommand.equalsIgnoreCase("disband")) {
                                disbandCommand.execute(player, subargs);
                            } else if (subcommand.equalsIgnoreCase(plugin.getLang("verify.command")) || subcommand.equalsIgnoreCase("verify")) {
                                verifyCommand.execute(player, subargs);
                            } else if (subcommand.equalsIgnoreCase(plugin.getLang("ban.command")) || subcommand.equalsIgnoreCase("ban")) {
                                banCommand.execute(player, subargs);
                            } else if (subcommand.equalsIgnoreCase(plugin.getLang("unban.command")) || subcommand.equalsIgnoreCase("unban")) {
                                unbanCommand.execute(player, subargs);
                            } else if (subcommand.equalsIgnoreCase(plugin.getLang("reload.command")) || subcommand.equalsIgnoreCase("reload")) {
                                reloadCommand.execute(player, subargs);
                            } else if (subcommand.equalsIgnoreCase("relacao") || subcommand.equalsIgnoreCase("relation")) {
                                relationCommand.execute(player, subargs);
                            } else if (subcommand.equalsIgnoreCase(plugin.getLang("globalff.command")) || subcommand.equalsIgnoreCase("globalff")) {
                                globalffCommand.execute(player, subargs);
                            } else if (subcommand.equalsIgnoreCase(plugin.getLang("war.command")) || subcommand.equalsIgnoreCase("war")) {
                                warCommand.execute(player, subargs);
                            } else if (subcommand.equalsIgnoreCase(plugin.getLang("place.command")) || subcommand.equalsIgnoreCase("place")) {
                                placeCommand.execute(player, subargs);
                            } else if (subcommand.equalsIgnoreCase(plugin.getLang("resetkdr.command")) || subcommand.equalsIgnoreCase("resetkdr")) {
                                resetKDRCommand.execute(player, subargs);
                            } else if (subcommand.equalsIgnoreCase("v") || subcommand.equalsIgnoreCase("v")) {
                                messageCenter.sendCenteredMessage(sender, "§7SimpleClans");
                                sender.sendMessage("");
                                sender.sendMessage("§7  Autor da Edição: §f@SrGutyerrez");
                                sender.sendMessage("§7  Versão do plugin: §f" + plugin.getDescription().getVersion());
                                sender.sendMessage("");
                            } else if (subcommand.equalsIgnoreCase("setbase")) {
                                setHomeCommand.execute(player, subargs);
                            } else {
                                sender.sendMessage("§aUtilize '§f/clan§a' e veja a lista de comandos.");
                            }
                        }
                    } else {
                        if (args.length == 0) {
                            return;
                        } else {
                            String subcommand = args[0];
                            String[] subargs = Helper.removeFirst(args);

                            if (subcommand.equalsIgnoreCase(plugin.getLang("verify.command")) || subcommand.equalsIgnoreCase("verify")) {
                                verifyCommand.execute(sender, subargs);
                            } else if (subcommand.equalsIgnoreCase(plugin.getLang("reload.command")) || subcommand.equalsIgnoreCase("reload")) {
                                reloadCommand.execute(sender, subargs);
                            } else if (subcommand.equalsIgnoreCase(plugin.getLang("place.command")) || subcommand.equalsIgnoreCase("place")) {
                                placeCommand.execute(sender, subargs);
                            } else if (subcommand.equalsIgnoreCase(plugin.getLang("globalff.command")) || subcommand.equalsIgnoreCase("globalff")) {
                                globalffCommand.execute(sender, subargs);
                            }
                            else {
                                ChatBlock.sendMessage(sender, ChatColor.RED + plugin.getLang("does.not.match"));
                            }
                        }
                    }
                } catch (Exception ex) {
                    SimpleClans.getInstance().getServer().getConsoleSender().sendMessage(ChatColor.RED + MessageFormat.format(plugin.getLang("simpleclans.command.failure"), ex.getMessage()));
                    for (StackTraceElement el : ex.getStackTrace()) {
                        System.out.print(el.toString());
                    }
                }
            }
        }.runTaskAsynchronously(SimpleClans.getInstance());
        return false;
    }
}
