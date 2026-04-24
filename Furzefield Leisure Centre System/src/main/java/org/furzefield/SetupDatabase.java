package org.furzefield;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.Statement;

public class SetupDatabase {

     void createTables() {

        createUserTable();

    }

    void createUserTable() {

        String sql = """
            CREATE TABLE IF NOT EXISTS members (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                email TEXT UNIQUE NOT NULL,
                password TEXT NOT NULL,
                type TEXT NOT NULL
            );
        """;

        try {
            Connection conn = Database.connect();
            if(conn != null) {
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
            }

        } catch (Exception e) {
            System.out.println("Exception : "+e);
        }
    }
}
