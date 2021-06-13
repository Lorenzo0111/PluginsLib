package me.lorenzo0111.pluginslib.database.connection;

import java.sql.Connection;
import java.sql.SQLException;

public interface IConnectionHandler {
    Connection getConnection() throws SQLException;
}
