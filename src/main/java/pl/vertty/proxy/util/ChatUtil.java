package pl.vertty.proxy.util;

import dev.waterdog.waterdogpe.command.CommandSender;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ChatUtil {

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(fixColor(message));
    }

    public static String fixColor(String message) {
        return (message == null) ? "" : message.replace("&", "§");
    }
}
