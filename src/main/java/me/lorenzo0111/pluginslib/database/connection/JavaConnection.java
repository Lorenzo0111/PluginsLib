package me.lorenzo0111.pluginslib.database.connection;

import java.sql.Connection;

public class JavaConnection implements IConnectionHandler {
    private final Connection connection;

    public JavaConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}
