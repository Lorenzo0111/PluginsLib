package me.lorenzo0111.pluginslib.database.connection;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariConnection implements IConnectionHandler {
    private final HikariDataSource dataSource;

    /**
     * @param dataSource Hikari data source
     */
    public HikariConnection(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void close() {
        dataSource.close();
    }
}
