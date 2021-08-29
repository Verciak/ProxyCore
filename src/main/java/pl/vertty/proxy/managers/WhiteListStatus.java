package pl.vertty.proxy.managers;

import pl.vertty.proxy.Main;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WhiteListStatus {
    private Integer name;


    public WhiteListStatus(Integer name) {
        this.name = name;
        insert();
    }

    public WhiteListStatus(ResultSet rs) throws SQLException {
        this.name = rs.getInt("name");
    }

    private void insert() {
        Main.getStore().executeUpdate("INSERT INTO `whitelist-status`(`id`, `name`) VALUES (NULL, '" + getName() + "');");
    }

    public Integer getName() {
        return this.name;
    }

    public void setName(Integer name) {
        this.name = name;
    }
}

