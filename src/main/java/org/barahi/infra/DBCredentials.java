package org.barahi.infra;

import java.io.FileInputStream;
import java.util.Properties;

public class DBCredentials {
    private static final String DEFAULT_FILENAME = "db.properties";

    private final String user;
    private final String password;
    private final String url;

    public static DBCredentials fromDefaultFile() {
        return DBCredentials.fromFile(DEFAULT_FILENAME);
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }


    private DBCredentials(String user, String password, String url) {
        this.user = user;
        this.password = password;
        this.url = url;
    }

    private static DBCredentials fromFile(String filename) {
        try (FileInputStream fis = new FileInputStream(filename)) {
            Properties prop = new Properties();
            prop.load(fis);

            String user = prop.getProperty("jdbc.user");
            String password = prop.getProperty("jdbc.password");
            String url = prop.getProperty("jdbc.url");

            return new DBCredentials(user, password, url);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }
}
