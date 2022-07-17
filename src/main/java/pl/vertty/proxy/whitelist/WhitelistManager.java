package pl.vertty.proxy.whitelist;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.vertty.proxy.database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class WhitelistManager {

    private final Database store;

    @Getter
    private final List<WhiteList> whiteLists = new ArrayList<>();
    private final List<WhiteListStatus> whiteListStatuses = new ArrayList<>();
    private final List<WhiteListReason> whiteListReasons = new ArrayList<>();

    public Optional<WhiteList> getWhiteList(String name) {
        return whiteLists
                .stream()
                .filter(whiteList -> whiteList.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public Optional<WhiteListReason> getWhiteListReason(int name) {
        return whiteListReasons
                .stream()
                .filter(whiteListReason -> whiteListReason.getName().equals(Integer.toString(name)))
                .findFirst();
    }

    public Optional<WhiteListStatus> getWhiteListStatus(int name) {
        return whiteListStatuses
                .stream()
                .filter(whiteListStatus -> whiteListStatus.getName() == name)
                .findFirst();
    }

    public void addWhiteList(String name) {
        store.executeUpdate("INSERT INTO `whitelist`(`id`, `name`) VALUES (NULL, '" + name + "');");
        whiteLists.add(new WhiteList(name));
    }

    public void deleteWhitelist(String name) {
        Optional<WhiteList> whiteList = getWhiteList(name);

        if (whiteList.isPresent()) {
            whiteLists.remove(whiteList.get());
            store.executeUpdate("DELETE FROM `whitelist` WHERE `name` ='" + name + "';");
        }
    }

    public void addWhiteListStatus(int name) {
        whiteListStatuses.add(new WhiteListStatus(name));
    }

    public void deleteWhitelistStatus(int name) {
        Optional<WhiteListStatus> whiteListStatus = getWhiteListStatus(name);

        if (whiteListStatus.isPresent()) {
            whiteListStatuses.remove(whiteListStatus.get());
            store.executeUpdate("DELETE FROM `whitelist-status` WHERE `name` ='" + name + "';");
        }
    }

    public void loadWhiteList() {
        try {
            ResultSet resultSet = store.query("SELECT * FROM `whitelist`");

            while (resultSet.next()) {
                WhiteList whiteList = new WhiteList(resultSet.getString("name"));
                whiteLists.add(whiteList);
            }

            resultSet.close();
            System.out.println("Loaded " + whiteLists.size() + " whitelist");
        } catch (SQLException ex) {
            System.out.println("Can not load whitelist Error " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void loadWhiteListReason() {
        try {
            ResultSet resultSet = store.query("SELECT * FROM `whitelist-reason`");

            while (resultSet.next()) {
                WhiteListReason whiteListReason = new WhiteListReason(resultSet.getString("name"), resultSet.getInt("number"));
                whiteListReasons.add(whiteListReason);
            }

            resultSet.close();
            System.out.println("Loaded " + whiteListReasons.size() + " whitelist-reason" );
        } catch (SQLException ex) {
            System.out.println("Can not load whitelist-reason Error " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void loadWhiteListStatus() {
        try {
            ResultSet resultSet = store.query("SELECT * FROM `whitelist-status`");

            while (resultSet.next()) {
                WhiteListStatus whiteListStatus = new WhiteListStatus(resultSet.getInt("name"));
                whiteListStatuses.add(whiteListStatus);
            }

            resultSet.close();
            System.out.println("Loaded " + whiteListStatuses.size() + " whitelist-status" );
        } catch (SQLException ex) {
            System.out.println("Can not load whitelist-status Error " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void setWhiteListReason(String name, int number) {
        whiteListReasons.clear();
        whiteListReasons.add(new WhiteListReason(name, number));

        store.executeUpdate("UPDATE `whitelist-reason` SET `name`='" + name + "' WHERE `numer`='" + number + "'");
    }
}