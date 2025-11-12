package org.barahi.infra;

import jakarta.inject.Provider;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DSLContextProvider implements Provider<DSLContext> {

    @Override
    public DSLContext get() {
        DBCredentials credentials = DBCredentials.fromDefaultFile();
        try {
            Connection connection = DriverManager.getConnection(credentials.getUrl(), credentials.getUser(), credentials.getPassword());
            return DSL.using(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
