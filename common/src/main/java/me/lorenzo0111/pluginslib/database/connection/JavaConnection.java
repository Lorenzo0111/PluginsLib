package me.lorenzo0111.pluginslib.database.connection;

import java.sql.Connection;
import java.sql.SQLException;

public class JavaConnection implements IConnectionHandler {
    private final Connection connection;

    /**
     * Default java.sql connection handler
     * @param connection Connection
     */
    public JavaConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }
}
