package me.lorenzo0111.pluginslib.database.connection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Connection handler
 */
public interface IConnectionHandler {
    /**
     * @return a database connection
     * @throws SQLException if something went wrong
     */
    Connection getConnection() throws SQLException;
    void close() throws SQLException;
}
