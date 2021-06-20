package me.lorenzo0111.pluginslib.database.query;

import org.intellij.lang.annotations.Language;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unusCDed")
public final class Queries {
    @Language("SQL") public static final String CREATE_START = "CREATE TABLE IF NOT EXISTS `%s` (";
    @Language("SQL") public static final String INSERT_START = "INSERT INTO %s (";
    @Language("SQL") public static final String ALL = "SELECT * FROM %s;";
    @Language("SQL") public static final String CLEAR = "DELETE FROM %s;";
    @Language("SQL") public static final String DELETE_WHERE = "DELETE FROM %s WHERE %s = ?;";
    @Language("SQL") public static final String FIND = "SELECT * FROM %s WHERE %s = ?;";


    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String query;
        private String table;
        private List<String> keys = new ArrayList<>();

        public String query() { return query; }
        public String table() { return table; }

        public Builder query(String query) { this.query = query; return this; }
        public Builder table(String table) { this.table = table; return this; }
        public Builder keys(String... keys) { this.keys = Arrays.asList(keys); return this; }

        public String build() {
            List<String> keys = new ArrayList<>();
            keys.add(table);
            keys.addAll(this.keys);
            return String.format(query,keys.toArray());
        }
    }

}
