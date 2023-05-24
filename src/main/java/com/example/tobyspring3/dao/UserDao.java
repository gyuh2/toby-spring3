package com.example.tobyspring3.dao;

import com.example.tobyspring3.domain.User;

import java.sql.*;

public class UserDao {
    Connection conn = null;
    PreparedStatement pstmt = null;

    private ConnectionMaker connectionMaker;

    public UserDao() {
        this.connectionMaker = new DConnectionMaker();
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        conn = connectionMaker.makeConnection();

        pstmt = conn.prepareStatement("insert into users(id, name, password) values(?, ?, ?)");
        pstmt.setString(1, user.getId());
        pstmt.setString(2, user.getName());
        pstmt.setString(3, user.getPassword());
        pstmt.executeUpdate();

        pstmt.close();
        conn.close();
    }

    public User get(String id) throws SQLException, ClassNotFoundException {
        conn = connectionMaker.makeConnection();

        pstmt = conn.prepareStatement("select id, name, password from users where id = ?");
        pstmt.setString(1, id);
        ResultSet rs = pstmt.executeQuery();
        rs.next(); // ctrl + Enter

        User user = new User();
        user.setId(rs.getString("id")); // db에 있는 칼럼명
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        pstmt.close();
        conn.close();

        return user;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();
        User user = new User();
        user.setId("4");
        user.setName("rabbit");
        user.setPassword("111111");
        userDao.add(user);

        User selectedUser = userDao.get("4");
        System.out.println(selectedUser.getId());
        System.out.println(selectedUser.getName());
        System.out.println(selectedUser.getPassword());

    }
}
