package com.example.tobyspring3.db;

import java.sql.*;
import java.util.Map;

import static java.lang.System.getenv;

public class ConnectChecker {
    Connection conn = null;
    PreparedStatement pstmt = null;
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Map<String, String> env = getenv();
        String dbHost = env.get("DB_HOST");
        String dbUser = env.get("DB_USER");
        String dbPassword = env.get("DB_PASSWORD");

        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(
                dbHost,
                dbUser,
                dbPassword);

        return conn;
    }

    public void check() throws ClassNotFoundException, SQLException {
        conn = getConnection();

        // Statement -> 완성된 쿼리를 넣어야 함
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("show databases");

        while(rs.next()) {
            String line = rs.getString(1);
            System.out.println(line);
        }
    }

    public void add() throws SQLException, ClassNotFoundException {
        conn = getConnection();

        pstmt = conn.prepareStatement("insert into users(name, password) values(?, ?)");
        pstmt.setString(1, "lion");
        pstmt.setString(2, "230417");
        pstmt.executeUpdate();

        select();
        System.out.println("\ninsert 성공");
    }

    public void select() throws SQLException, ClassNotFoundException {
        conn = getConnection();

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from users");
        rs = stmt.getResultSet();

        while(rs.next()) {
            String col1 = rs.getString(1);
            String col2 = rs.getString(2);
            String col3 = rs.getString(3);
            System.out.printf("id:%s name:%s pw:%s\n", col1, col2, col3);
        }
    }

    public void update() throws SQLException, ClassNotFoundException {
        conn = getConnection();

        pstmt = conn.prepareStatement("update users set name = ?, password = ? where id = ?");
        pstmt.setString(1, "likelion");
        pstmt.setString(2, "230417");
        pstmt.setString(3, "1");
        pstmt.executeUpdate();

        select();
        System.out.println("\nupdate 성공");
    }

    public void delete() throws SQLException, ClassNotFoundException {
        conn = getConnection();

        pstmt = conn.prepareStatement("delete from users where id = ?");
        pstmt.setString(1, "1");
        pstmt.executeUpdate();

        select();
        System.out.println("\ndelete 성공");
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ConnectChecker cc = new ConnectChecker();
        //cc.check();
        //cc.add();
        //cc.select();
        //cc.update();
        //cc.delete();
    }
}

