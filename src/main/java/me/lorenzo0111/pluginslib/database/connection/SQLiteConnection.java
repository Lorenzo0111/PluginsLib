package me.lorenzo0111.pluginslib.database.connection;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection extends JavaConnection {
    /**
     * @param plugin Owner of the sqlite database
     * @throws SQLException if something went wrong
     */
    public SQLiteConnection(JavaPlugin plugin) throws SQLException, IOException {
        this(new File(plugin.getDataFolder(),"database.db"));
    }

    /**
     * @param file SQLite database file
     * @throws SQLException if something went wrong
     */
    public SQLiteConnection(File file) throws SQLException, IOException {
        this(create(file));
    }

    /**
     * @param connection Java connection
     */
    public SQLiteConnection(Connection connection) {
        super(connection);
    }

    private static Connection create(File file) throws SQLException, IOException {
        file.getParentFile().mkdirs();
        file.createNewFile();
        return DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath());
    }
}
