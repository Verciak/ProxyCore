package pl.vertty.proxy.commands;

import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.command.CommandSettings;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import org.apache.commons.lang3.StringUtils;
import pl.vertty.proxy.Main;
import pl.vertty.proxy.managers.WhiteList;
import pl.vertty.proxy.managers.WhitelistManager;
import pl.vertty.proxy.utils.ChatUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class WhitelistCommand extends Command {

    public WhitelistCommand() {
        super("whitelist", CommandSettings.builder()
                .setDescription("Dodawanie osob do whitelist")
                .setPermission("proxycore.whitelsit")
                .setAliases(new String[]{"wl", "pwl"})
                .build());
    }

    @Override
    public boolean onExecute(CommandSender sender, String alias, String[] args) {
        if (args.length < 1) {
            ChatUtil.sendMessage(sender, "/wl <add|remove|list|reason|on|off> [gracz]");
            return false;
        }
        final String s3;
        final String s4;
        final String s2 = s4 = (s3 = args[0]);
        switch (s4) {
            case "wlacz":
            case "on": {
                if (WhitelistManager.getWhiteListStatus(1) != null) {
                    return ChatUtil.sendMessage(sender, "&4Blad: &cWhitelist jest juz on!");
                }
                WhitelistManager.deleteWhitelistStatus(0);
                WhitelistManager.addWhiteListStatus(1);
                return ChatUtil.sendMessage(sender, "&8>> &cWhitelist zostala wlaczona!");
            }
            case "wylacz":
            case "off": {
                if (WhitelistManager.getWhiteListStatus(0) != null) {
                    return ChatUtil.sendMessage(sender, "&4Blad: &cWhitelist jest off!");
                }
                WhitelistManager.deleteWhitelistStatus(1);
                WhitelistManager.addWhiteListStatus(0);
                return ChatUtil.sendMessage(sender, "&8>> &cWhitelist zostala wylaczona!");
            }
            case "dodaj":
            case "add": {
                if (args.length < 2) {
                    return ChatUtil.sendMessage(sender, "/wl add <gracz>");
                }
                final String nick = args[1];
                if (WhitelistManager.getWhiteList(nick) != null) {
                    return ChatUtil.sendMessage(sender, "&4Blad: &c" + nick + " jest juz na whitelist!");
                }
                WhitelistManager.addWhiteList(nick);
                return ChatUtil.sendMessage(sender, "&cGracz &6" + nick + " &czostal dodany do whitelist!");
            }
            case "usun":
            case "remove": {
                if (args.length < 2) {
                    return ChatUtil.sendMessage(sender, "/wl remove <gracz>");
                }
                final String nick = args[1];
                if (WhitelistManager.getWhiteList(nick) == null) {
                    return ChatUtil.sendMessage(sender, "&4Blad: &c" + nick + " nie jest na whitelist!");
                }
                WhitelistManager.deleteWhitelist(nick);
                return ChatUtil.sendMessage(sender, "&cGracz &6" + nick + " &czostal usuniety z whitelist!");
            }
            case "list": {
                return ChatUtil.sendMessage(sender, "&8>> &7Lista graczy na whitelist: &9" + StringUtils.join(WhitelistManager.getWhiteLists().keySet().iterator(), "&8, &9"));
            }
            case "powod":
            case "reason": {
                if (args.length < 2) {
                    return ChatUtil.sendMessage(sender, "/wl reason <numer> <powod>");
                }
                final String reason = StringUtils.join(args, " ", 2, args.length);
                WhitelistManager.setWl_reason(reason, Integer.parseInt(args[1]));
                return ChatUtil.sendMessage(sender, "&8>> &cUstawiles powod whitelist na: &r" + reason);
            }
            default: {
                return ChatUtil.sendMessage(sender, "/wl <add|remove|list|reason|on|off> [gracz]");
            }
        }
    }

}