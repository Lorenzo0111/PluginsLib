/*
 * This file is part of PluginsLib, licensed under the MIT License.
 *
 * Copyright (c) Lorenzo0111
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.lorenzo0111.pluginslib.database.objects;

import me.lorenzo0111.pluginslib.StringUtils;
import me.lorenzo0111.pluginslib.database.DatabaseSerializable;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * SQL Table
 */
@SuppressWarnings("unused")
public final class Table {
    private final JavaPlugin plugin;
    private final Connection connection;
    private final String name;
    private final List<Column> columns;

    /**
     * @param plugin Owner of the database
     * @param connection SQL Connection
     * @param name Name of the table
     * @param columns List of columns
     * @see Column
     */
    public Table(JavaPlugin plugin, Connection connection, String name, List<Column> columns) {
        this.plugin = plugin;
        this.connection = connection;
        this.name = name;
        this.columns = columns;
    }

    /**
     * Create the table in the database
     */
    public void create() {
        this.run(new BukkitRunnable() {
            @Override
            public void run() {
                StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS `" + name + "` (");

                columns.forEach(column -> query.append(String.format("`%s` %s,",column.getName(),column.getType())));

                try {
                    connection.createStatement().executeUpdate(StringUtils.removeLastChar(query.toString()) + ");");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    /**
     * Add a {@link DatabaseSerializable} to the table
     * @param serializable Item to add to the table
     */
    public void add(DatabaseSerializable serializable) {
        this.run(new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    StringBuilder builder = new StringBuilder("INSERT INTO " + name + "(");

                    Map<String,Object> map = serializable.serialize();

                    for (String column : map.keySet()) {
                        builder.append(column).append(",");
                    }

                    builder = new StringBuilder(StringUtils.removeLastChar(builder.toString()));

                    builder.append(")");

                    builder.append(" VALUES (");

                    for (int i = 0; i < map.size(); i++) {
                        builder.append("?,");
                    }

                    builder = new StringBuilder(StringUtils.removeLastChar(builder.toString()));

                    builder.append(");");

                    final PreparedStatement statement = connection.prepareStatement(builder.toString());

                    int i = 1;
                    for (Object obj : map.values()) {
                        statement.setObject(i,obj);
                        i++;
                    }

                    statement.executeUpdate();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    /**
     * Clear the table
     */
    public void clear() {
        this.run(new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    connection.createStatement().executeUpdate("DELETE FROM " + name);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    /**
     * Run something async
     * @param runnable Runnable to run
     */
    private void run(BukkitRunnable runnable) {
        runnable.runTaskAsynchronously(plugin);
    }

    /**
     * @return Name of the table
     */
    public String getName() {
        return name;
    }
}