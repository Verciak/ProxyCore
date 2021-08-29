package pl.vertty.proxy.managers;

import pl.vertty.proxy.Main;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WhiteList {
    private String name;


    public WhiteList(String name) {
        this.name = name;
        insert();
    }

    public WhiteList(ResultSet rs) throws SQLException {
        this.name = rs.getString("name");
    }

    private void insert() {
        Main.getStore().executeUpdate("INSERT INTO `whitelist`(`id`, `name`) VALUES (NULL, '" + getName() + "');");
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

