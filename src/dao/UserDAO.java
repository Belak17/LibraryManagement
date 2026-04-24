package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import bookpackage.Emprunt;
import systemusers.Librarian;
import systemusers.User;

public class UserDAO {

    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "YOUR-USERNAME";
    private static final String PASS = "YOUR-PASSWORD";

    public static void insertUser(User user) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "INSERT INTO APP_USERS( USERNAME, PASSWORD,ROLE) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3,user.getClass().getSimpleName().toUpperCase());
            ps.executeUpdate();
        }
    }

    public static void insertLibrarian(Librarian lib) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "INSERT INTO APP_USERS(USERNAME, PASSWORD ,ROLE) VALUES (?, ?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, lib.getUsername());
            ps.setString(2, lib.getPassword());
            ps.setString(3, "LIBRARIAN");
            ps.executeUpdate();
        }
    }
    public static void insertKaleb() throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "INSERT INTO APP_USERS(USERNAME, PASSWORD ,ROLE) VALUES (?, ?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "Kaleb");
            ps.setString(2, "souzixx56");
            ps.setString(3, "ADMIN");
            ps.executeUpdate();
        }
    }

    public static User login(String name, String password) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT * FROM APP_USERS WHERE USERNAME=? AND PASSWORD=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("ID"), rs.getString("NAME"), rs.getString("PASSWORD"),rs.getString("ROLE"));
            }
        }
        return null;
    }

    public static User searchUser(String name) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT * FROM APP_USERS WHERE USERNAME=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("USER_ID"), rs.getString("USERNAME"), rs.getString("PASSWORD"),rs.getString("ROLE"));
            }
        }
        return null;
    }
    
    
    public static void deleteUser(int id) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "DELETE FROM APP_USERS WHERE USER_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    public static User searchUser(int id) throws SQLException {
        User user = null;

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT * FROM APP_USERS WHERE USER_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String username = rs.getString("USERNAME");
                String password = rs.getString("PASSWORD");

                user = new User(username, password);
                user.setId(id); // Assigner l'ID récupéré
            }
        }

        return user;
    }
    public static void emprunterLivre(Emprunt e) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "INSERT INTO EMPRUNT(DATE_EMPRUNT, DATE_RETOUR_PREVUE, STATUT, BOOK_ID, USER_ID) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(e.getDateEmprunt()));
            ps.setDate(2, Date.valueOf(e.getDateRetourPrevue()));
            ps.setString(3, e.getStatut().toString());
            ps.setInt(4, e.getBook().getId());
            ps.setInt(5, e.getUser().getId());
            ps.executeUpdate();
        }
    }
    public static List<Librarian> ListLibrarian () throws SQLException 
    {
    	 
    	 List<Librarian> ListLib = new ArrayList<Librarian>();
         try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
             String sql = "SELECT * FROM APP_USERS WHERE ROLE=?";
             PreparedStatement ps = conn.prepareStatement(sql);
             ps.setString(1,"LIBRARIAN");

             ResultSet rs = ps.executeQuery();
             while (rs.next()) {
            	 ListLib.add(new Librarian(rs.getInt("USER_ID"),rs.getString("USERNAME"),rs.getString("PASSWORD"),rs.getString("ROLE")));
                 
             }
         }
         return ListLib ; 
    }
    public static List<User> ListUser () throws SQLException 
    {
    	 
    	 List<User> ListUser = new ArrayList<User>();
         try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
             String sql = "SELECT * FROM APP_USERS WHERE ROLE=?";
             PreparedStatement ps = conn.prepareStatement(sql);
             ps.setString(1,"USER");

             ResultSet rs = ps.executeQuery();
             while (rs.next()) {
            	 ListUser.add(new Librarian(rs.getInt("USER_ID"),rs.getString("USERNAME"),rs.getString("PASSWORD"),rs.getString("ROLE")));
                 
             }
         }
         return ListUser ; 
    }
    public static void retournerLivre(int idEmprunt) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "UPDATE EMPRUNT SET DATE_RETOUR_REELLE = SYSDATE, STATUT='TERMINE' WHERE EMPRUNT_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idEmprunt);
            ps.executeUpdate();
        }
    }
}
