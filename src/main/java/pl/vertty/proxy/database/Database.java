package pl.vertty.proxy.database;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

public class Database {

    private final HikariDataSource dataSource = new HikariDataSource();

    public Database() {
        String host = "localhost";
        String base = "blazepe";
        String user = "root";
        String password = "domiS2002";

        int port = 3306;

        dataSource.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + base + "?useSSL=false&serverTimezone=Europe/Warsaw");
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.addDataSourceProperty("useSSL", false);
        dataSource.addDataSourceProperty("cachePrepStmts", true);
        dataSource.addDataSourceProperty("prepStmtCacheSize", 250);
        dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        dataSource.addDataSourceProperty("useServerPrepStmts", true);
        dataSource.addDataSourceProperty("rewriteBatchedStatements", true);
        dataSource.setConnectionTimeout(15000L);
        dataSource.setMaximumPoolSize(5);
    }

    public void close() {
        dataSource.close();
    }

    public ResultSet query(String query) {
        try (Connection connection = dataSource.getConnection()) {
            return connection.prepareStatement(query).executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void executeQuery(String query) {
        try {
            Connection connection = this.dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            statement.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void executeUpdate(String query) {
        try {
            Connection connection = this.dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            if (statement == null) {
                return;
            }

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
