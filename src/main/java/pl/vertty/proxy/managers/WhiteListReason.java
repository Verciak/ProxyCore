package pl.vertty.proxy.managers;

import pl.vertty.proxy.Main;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WhiteListReason {
    private String name;
    private Integer numer;

    public WhiteListReason(String name, int numer) {
        this.name = name;
        this.numer = numer;
//        insert();
    }

    public WhiteListReason(ResultSet rs) throws SQLException {
        this.name = rs.getString("name");
        this.numer = rs.getInt("numer");
    }

    private void insert() {
        Main.getStore().executeUpdate("INSERT INTO `whitelist-reason`(`id`, `name`, `numer`) VALUES (NULL, '" + getName() + "', '" + getNumer() + "');");
    }

    public String getName() {
        return this.name;
    }

    public Integer getNumer() {
        return this.numer;
    }

    public void setName(String name) {
        this.name = name;
    }
}

