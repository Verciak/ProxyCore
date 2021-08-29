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
        this.dataSource.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + base + "?useSSL=false&serverTimezone=Europe/Warsaw");
        this.dataSource.setUsername(user);
        this.dataSource.setPassword(password);
        this.dataSource.addDataSourceProperty("useSSL", Boolean.valueOf(false));
        this.dataSource.addDataSourceProperty("cachePrepStmts", Boolean.valueOf(true));
        this.dataSource.addDataSourceProperty("prepStmtCacheSize", Integer.valueOf(250));
        this.dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", Integer.valueOf(2048));
        this.dataSource.addDataSourceProperty("useServerPrepStmts", Boolean.valueOf(true));
        this.dataSource.addDataSourceProperty("rewriteBatchedStatements", Boolean.valueOf(true));
        this.dataSource.setConnectionTimeout(15000L);
        this.dataSource.setMaximumPoolSize(5);
    }

    public void close() {
        this.dataSource.close();
    }

    public ResultSet query(String query) {
        try (Connection connection = this.dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void executeQuery(String query, Consumer<ResultSet> action) {
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery()) {
            action.accept(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int executeUpdate(String query) {
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            if (statement == null)
                return 0;
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}

