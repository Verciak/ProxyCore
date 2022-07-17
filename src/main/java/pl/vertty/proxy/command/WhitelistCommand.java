package pl.vertty.proxy.command;

import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.command.CommandSettings;
import org.apache.commons.lang3.StringUtils;
import pl.vertty.proxy.whitelist.WhitelistManager;
import pl.vertty.proxy.util.ChatUtil;

public class WhitelistCommand extends Command {

    private final WhitelistManager whitelistManager;
    
    public WhitelistCommand(WhitelistManager whitelistManager) {
        super("whitelist", CommandSettings.builder()
                .setDescription("Dodawanie osob do whitelist")
                .setPermission("proxycore.whitelsit")
                .setAliases(new String[]{"wl", "pwl"})
                .build());
        
        this.whitelistManager = whitelistManager;
    }

    @Override
    public boolean onExecute(CommandSender sender, String alias, String[] args) {
        if (args.length < 1) {
            ChatUtil.sendMessage(sender, "/wl <add|remove|list|reason|on|off> [gracz]");
            return false;
        }

        switch (args[0]) {
            case "wlacz":
            case "on":
                if (whitelistManager.getWhiteListStatus(1).isPresent()) {
                    ChatUtil.sendMessage(sender, "&4Blad: &cWhitelist jest juz on!");
                    break;
                }

                whitelistManager.deleteWhitelistStatus(0);
                whitelistManager.addWhiteListStatus(1);

                ChatUtil.sendMessage(sender, "&8>> &cWhitelist zostala wlaczona!");
                break;
            case "wylacz":
            case "off":
                if (whitelistManager.getWhiteListStatus(0).isPresent()) {
                    ChatUtil.sendMessage(sender, "&4Blad: &cWhitelist jest off!");
                    break;
                }

                whitelistManager.deleteWhitelistStatus(1);
                whitelistManager.addWhiteListStatus(0);

                ChatUtil.sendMessage(sender, "&8>> &cWhitelist zostala wylaczona!");
                break;
            case "dodaj":
            case "add": {
                if (args.length < 2) {
                    ChatUtil.sendMessage(sender, "/wl add <gracz>");
                    break;
                }

                String nick = args[1];

                if (whitelistManager.getWhiteList(nick).isPresent()) {
                    ChatUtil.sendMessage(sender, "&4Blad: &c" + nick + " jest juz na whitelist!");
                    break;
                }

                whitelistManager.addWhiteList(nick);
                ChatUtil.sendMessage(sender, "&cGracz &6" + nick + " &czostal dodany do whitelist!");
                break;
            }
            case "usun":
            case "remove": {
                if (args.length < 2) {
                    ChatUtil.sendMessage(sender, "/wl remove <gracz>");
                    break;
                }

                String nick = args[1];

                if (whitelistManager.getWhiteList(nick).isEmpty()) {
                    ChatUtil.sendMessage(sender, "&4Blad: &c" + nick + " nie jest na whitelist!");
                    break;
                }

                whitelistManager.deleteWhitelist(nick);
                ChatUtil.sendMessage(sender, "&cGracz &6" + nick + " &czostal usuniety z whitelist!");
                break;
            }
            case "list":
                ChatUtil.sendMessage(sender, "&8>> &7Lista graczy na whitelist: &9" + StringUtils.join(whitelistManager.getWhiteLists().iterator(), "&8, &9"));
                break;
            case "powod":
            case "reason":
                if (args.length < 2) {
                    ChatUtil.sendMessage(sender, "/wl reason <numer> <powod>");
                    break;
                }

                String reason = StringUtils.join(args, " ", 2, args.length);

                whitelistManager.setWhiteListReason(reason, Integer.parseInt(args[1]));
                ChatUtil.sendMessage(sender, "&8>> &cUstawiles powod whitelist na: &r" + reason);
                break;
            default:
                ChatUtil.sendMessage(sender, "/wl <add|remove|list|reason|on|off> [gracz]");
                break;
        }

        return true;
    }
}