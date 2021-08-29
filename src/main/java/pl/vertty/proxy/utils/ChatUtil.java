package pl.vertty.proxy.utils;

import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;

public class ChatUtil {


    public static boolean sendMessage(CommandSender sender, String message) {
        if (sender instanceof ProxiedPlayer) {
            if (message != null || message != "")
                sender.sendMessage(fixColor(message));
        } else {
            sender.sendMessage(fixColor(message));
        }
        return true;
    }

    public static String fixColor(String s) {
        return (s == null) ? "" : s.replace("&", "§");
    }

}
