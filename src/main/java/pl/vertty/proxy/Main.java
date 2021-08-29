package pl.vertty.proxy;

import dev.waterdog.waterdogpe.event.defaults.PlayerLoginEvent;
import dev.waterdog.waterdogpe.plugin.Plugin;
import dev.waterdog.waterdogpe.utils.Configuration;
import lombok.SneakyThrows;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;
import pl.vertty.proxy.commands.WhitelistCommand;
import pl.vertty.proxy.database.Database;
import pl.vertty.proxy.listeners.PlayerLoginListener;
import pl.vertty.proxy.managers.WhitelistManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class Main extends Plugin {

    private static Database store;
    private static Main instance;

    @SneakyThrows
    @Override
    public void onEnable() {
        Main.instance = this;
        Class.forName("com.mysql.jdbc.Driver");
        store = new Database();
        registerDatabase();
        WhitelistManager.loadWhiteList();
        WhitelistManager.loadWhiteListReason();
        WhitelistManager.loadWhiteListStatus();
        this.getProxy().getCommandMap().registerCommand(new WhitelistCommand());
        this.getProxy().getEventManager().subscribe(PlayerLoginEvent.class, PlayerLoginListener::onLoginEvent);
    }


    @Override
    public void onDisable() {
        if (store != null) {
            store.close();
        }
    }

    public static Database getStore() {
        return store;
    }
    public static Main getInstance() {
        return instance;
    }


    private void registerDatabase() {
        store.executeUpdate("CREATE TABLE IF NOT EXISTS `whitelist` (`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT, `name` varchar(32) NOT NULL);");
        store.executeUpdate("CREATE TABLE IF NOT EXISTS `whitelist-reason` (`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT, `name` longtext NOT NULL, `numer` int NOT NULL);");
        store.executeUpdate("CREATE TABLE IF NOT EXISTS `whitelist-status` (`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT, `name` int NOT NULL);");
//        store.executeUpdate("INSERT INTO `whitelist-reason`(`id`, `name`, `numer`) VALUES (NULL, test test test, 1);");

    }


}
