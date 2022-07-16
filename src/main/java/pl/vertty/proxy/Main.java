package pl.vertty.proxy;

import dev.waterdog.waterdogpe.event.defaults.PlayerLoginEvent;
import dev.waterdog.waterdogpe.plugin.Plugin;
import lombok.Getter;
import lombok.SneakyThrows;
import pl.vertty.proxy.command.WhitelistCommand;
import pl.vertty.proxy.database.Database;
import pl.vertty.proxy.listener.PlayerLoginListener;
import pl.vertty.proxy.whitelist.WhitelistManager;

@Getter
public class Main extends Plugin {

    @Getter
    private static Main instance;

    private Database store;

    private WhitelistManager whitelistManager;

    @Override
    public void onEnable() {
        instance = this;

        initDatabase();

        whitelistManager = new WhitelistManager(store);

        whitelistManager.loadWhiteList();
        whitelistManager.loadWhiteListReason();
        whitelistManager.loadWhiteListStatus();

        getProxy().getCommandMap().registerCommand(new WhitelistCommand(whitelistManager));
        getProxy().getEventManager().subscribe(PlayerLoginEvent.class, PlayerLoginListener::onLoginEvent);
    }

    @Override
    public void onDisable() {
        if (store != null) {
            store.close();
        }
    }

    @SneakyThrows
    private void initDatabase() {
        Class.forName("com.mysql.jdbc.Driver");
        store = new Database();

        store.executeUpdate("CREATE TABLE IF NOT EXISTS `whitelist` (`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT, `name` varchar(32) NOT NULL);");
        store.executeUpdate("CREATE TABLE IF NOT EXISTS `whitelist-reason` (`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT, `name` longtext NOT NULL, `numer` int NOT NULL);");
        store.executeUpdate("CREATE TABLE IF NOT EXISTS `whitelist-status` (`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT, `name` int NOT NULL);");
    }
}
