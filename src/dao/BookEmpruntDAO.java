package dao;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import bookpackage.Book;
import bookpackage.Emprunt;
import bookpackage.StatutEmprunt;
import systemusers.User;

public class BookEmpruntDAO {

    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "Moub";
    private static final String PASS = "souzixx56";

    // Ajouter un nouvel emprunt
    public static void insertEmprunt(Emprunt e) throws SQLException {
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

    // Retourner un livre (mettre à jour la date de retour réelle et le statut)
    public static void retournerLivre(int idEmprunt) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "UPDATE EMPRUNT SET DATE_RETOUR_REELLE = SYSDATE, STATUT='RETOURNE' WHERE EMPRUNT_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idEmprunt);
            ps.executeUpdate();
        }
    }
    public static String searchBookAvailable(int id) throws SQLException
    {
    	try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS))
    	{
    		String sql = "SELECT * FROM EMPRUNT WHERE BOOK_ID=? AND STATUT= ?";
    		PreparedStatement ps = conn.prepareStatement(sql);
    		ps.setInt(1, id);
    		ps.setString(2, "EN_COURS");
    		ResultSet rs = ps.executeQuery();
    		if (rs.next())
    		{
    			return "Indisponible";
    		}
    	}
		return "Disponible";
    	
    }
    public static int searchEmpruntByUserandBook(int user_id,int book_id, LocalDate localdate) throws SQLException 
    {
    	try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT * FROM EMPRUNT WHERE USER_ID=? AND BOOK_ID=? AND DATE_EMPRUNT=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, user_id);
            ps.setInt(2, book_id);
            
            ps.setDate(3, Date.valueOf(localdate));
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                     rs.getInt("EMPRUNT_ID");
                    
            }
        }
       return 0 ;
    }
    // Rechercher un e       ar ID
    public static Emprunt searchEmprunt(int id) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT * FROM EMPRUNT WHERE EMPRUNT_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Book book = BookDAO.searchBook(rs.getInt("BOOK_ID"));
                User user = UserDAO.searchUser(rs.getInt("USER_ID"));
                LocalDate dateEmprunt = rs.getDate("DATE_EMPRUNT").toLocalDate();
                LocalDate dateRetourPrevue = rs.getDate("DATE_RETOUR_PREVUE").toLocalDate();
             
                String statut = rs.getString("STATUT");
                return new Emprunt(dateEmprunt, dateRetourPrevue, statut, book, user);
            }
        }
        return null;
    }

    // Lister tous les emprunts
    public static List<Emprunt> getAllEmprunts() throws SQLException {
        List<Emprunt> emprunts = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT * FROM EMPRUNT";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Book book = BookDAO.searchBook(rs.getInt("BOOK_ID"));
                User user = UserDAO.searchUser(rs.getInt("USER_ID"));
                LocalDate dateEmprunt = rs.getDate("DATE_EMPRUNT").toLocalDate();
                LocalDate dateRetourPrevue = rs.getDate("DATE_RETOUR_PREVUE").toLocalDate();
                String statut = rs.getString("STATUT");
                emprunts.add(new Emprunt(dateEmprunt, dateRetourPrevue, statut, book, user));
            }
        }
        return emprunts;
    }

    // Lister tous les emprunts d'un utilisateur
    public static List<Emprunt> getEmpruntsByUser(int userId) throws SQLException {
        List<Emprunt> emprunts = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT * FROM EMPRUNT WHERE USER_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	int id = rs.getInt("EMPRUNT_ID");
                Book book = BookDAO.searchBook(rs.getInt("BOOK_ID"));
                User user = UserDAO.searchUser(userId);
                LocalDate dateEmprunt = rs.getDate("DATE_EMPRUNT").toLocalDate();
                LocalDate dateRetourPrevue = rs.getDate("DATE_RETOUR_PREVUE").toLocalDate();
                Date drr = rs.getDate("DATE_RETOUR_REELLE");
                LocalDate dateRetourReelle = (drr != null) ? drr.toLocalDate() : null;
                StatutEmprunt statut = StatutEmprunt.valueOf(rs.getString("STATUT"));
                emprunts.add(new Emprunt(id,dateEmprunt, dateRetourPrevue,dateRetourReelle,statut, book, user));
            }
        }
        return emprunts;
    }
}
