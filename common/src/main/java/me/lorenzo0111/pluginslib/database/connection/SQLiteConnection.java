package me.lorenzo0111.pluginslib.database.connection;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection extends JavaConnection {
    /**
     * @param folder Data folder
     * @throws SQLException if something went wrong
     * @throws IOException if the file won't created for an error
     */
    public SQLiteConnection(Path folder) throws SQLException, IOException {
        this(new File(folder.toFile(),"database.db"));
    }

    /**
     * @param file SQLite database file
     * @throws SQLException if something went wrong
     * @throws IOException if the file won't created for an error
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
