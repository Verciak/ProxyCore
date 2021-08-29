package pl.vertty.proxy.managers;

import pl.vertty.proxy.Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class WhitelistManager {

    public static WhiteList getWhiteList(String name) {
        for (WhiteList w : wl.values()) {
            if (name.equalsIgnoreCase(w.getName()))
                return w;
        }
        return null;
    }

    public static WhiteListReason getWhiteListReason(Integer name) {
        for (WhiteListReason w : wl_reason.values()) {
            if (name.equals(w.getNumer()))
                return w;
        }
        return null;
    }

    public static WhiteListStatus getWhiteListStatus(Integer name) {
        for (WhiteListStatus w : wl_status.values()) {
            if (name.equals(w.getName()))
                return w;
        }
        return null;
    }

    public static void addWhiteList(String name) {
        wl.put(name, new WhiteList(name));
    }

    public static void deleteWhitelist(String name) {
        wl.remove(name);
        Main.getStore().executeUpdate("DELETE FROM `whitelist` WHERE `name` ='" + name + "';");
    }

    public static void addWhiteListStatus(Integer name) {
        wl_status.put(name, new WhiteListStatus(name));
    }

    public static void deleteWhitelistStatus(Integer name) {
        wl_status.remove(name);
        Main.getStore().executeUpdate("DELETE FROM `whitelist-status` WHERE `name` ='" + name + "';");
    }


    public static void loadWhiteList() {
        try {
            ResultSet rs = Main.getStore().query("SELECT * FROM `whitelist`");
            while (rs.next()) {
                WhiteList w = new WhiteList(rs);
                wl.put(w.getName(), w);
            }
            rs.close();
            System.out.println("Loaded " + wl.size() + " whitelist" );
        } catch (SQLException var2) {
            System.out.println("Can not load whitelist Error " + var2.getMessage());
            var2.printStackTrace();
        }
    }

    public static void loadWhiteListReason() {
        try {
            ResultSet rs = Main.getStore().query("SELECT * FROM `whitelist-reason`");
            while (rs.next()) {
                WhiteListReason w = new WhiteListReason(rs);
                wl_reason.put(w.getName(), w);
            }
            rs.close();
            System.out.println("Loaded " + wl_reason.size() + " whitelist-reason" );
        } catch (SQLException var2) {
            System.out.println("Can not load whitelist-reason Error " + var2.getMessage());
            var2.printStackTrace();
        }
    }

    public static void loadWhiteListStatus() {
        try {
            ResultSet rs = Main.getStore().query("SELECT * FROM `whitelist-status`");
            while (rs.next()) {
                WhiteListStatus w = new WhiteListStatus(rs);
                wl_status.put(w.getName(), w);
            }
            rs.close();
            System.out.println("Loaded " + wl_status.size() + " whitelist-status" );
        } catch (SQLException var2) {
            System.out.println("Can not load whitelist-status Error " + var2.getMessage());
            var2.printStackTrace();
        }
    }

    public static ConcurrentHashMap<String, WhiteList> getWhiteLists() {
        return wl;
    }

    public static void setWl_reason(String name, int numer) {
        wl_reason.clear();
        wl_reason.put(name, new WhiteListReason(name, numer));
        Main.getStore().executeUpdate("UPDATE `whitelist-reason` SET `name`='" + name + "' WHERE `numer`='" + numer + "'");
    }

    public static ConcurrentHashMap<String, WhiteList> wl = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Integer, WhiteListStatus> wl_status = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, WhiteListReason> wl_reason = new ConcurrentHashMap<>();
}

