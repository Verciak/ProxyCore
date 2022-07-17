package pl.vertty.proxy.listener;

import dev.waterdog.waterdogpe.event.defaults.PlayerLoginEvent;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import pl.vertty.proxy.Main;
import pl.vertty.proxy.whitelist.WhitelistManager;

public class PlayerLoginListener {

    private static final WhitelistManager whitelistManager = Main.getInstance().getWhitelistManager();

    public static void onLoginEvent(PlayerLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();

        if(whitelistManager.getWhiteListStatus(1).isPresent() && whitelistManager.getWhiteList(player.getName()).isEmpty()) {
            player.disconnect(whitelistManager.getWhiteListReason(1).get().getName().replace("&", "§").replace("{N}", "\n"));
        }
    }
}