package pl.vertty.proxy.listeners;

import dev.waterdog.waterdogpe.event.defaults.PlayerLoginEvent;
import pl.vertty.proxy.managers.WhiteListReason;
import pl.vertty.proxy.managers.WhitelistManager;

public class PlayerLoginListener {

    public static void onLoginEvent(PlayerLoginEvent e) {
        if(WhitelistManager.getWhiteListStatus(1) != null) {
            if (WhitelistManager.getWhiteList(e.getPlayer().getName()) == null) {
                e.getPlayer().disconnect(WhitelistManager.getWhiteListReason(1).getName().replace("&", "§").replace("{N}", "\n"));
            }
        }
    }


}
