package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import bookpackage.Book;

public class BookDAO {

    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "Moub";
    private static final String PASS = "souzixx56";

    public static void insertBook(Book book) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "INSERT INTO BOOKS(TITLE, AUTHOR, LANGUE, DESCRIPTION, ANNEE) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, book.getTitre());
            ps.setString(2, book.getAuteur());
            ps.setString(3, book.getLangue());
            ps.setString(4, book.getDescription());
            ps.setInt(5, book.getAnnee());
            ps.executeUpdate();
        }
    }

    public static void deleteBook(int id) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "DELETE FROM BOOKS WHERE BOOK_ID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public static void updateBook(Book book) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "UPDATE BOOKS SET TITLE=?, AUTHOR=?, LANGUE=?, DESCRIPTION=?, ANNEE=? WHERE BOOK_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, book.getTitre());
            ps.setString(2, book.getAuteur());
            ps.setString(3, book.getLangue());
            ps.setString(4, book.getDescription());
            ps.setInt(5, book.getAnnee());
            ps.setInt(6, searchBook(book.getTitre()).getId());
            ps.executeUpdate();
        }
    }
    public static Book searchBook(int id) throws SQLException {
        Book book = null;

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT * FROM BOOKS WHERE BOOK_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String title = rs.getString("TITLE");
                String author = rs.getString("AUTHOR");
                String language = rs.getString("LANGUE");
                String description = rs.getString("DESCRIPTION");
                int year = rs.getInt("ANNEE");

                book = new Book(title, author, language, description, year);
                book.setId(id); // Assigner l'ID récupéré
            }
        }

        return book;
    }
    public static Book searchBook(String title) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT * FROM BOOKS WHERE TITLE=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, title);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Book(
                        rs.getInt("BOOK_ID"),
                        rs.getString("TITLE"),
                        rs.getString("AUTHOR"),
                        rs.getString("LANGUE"),
                        rs.getString("DESCRIPTION"),
                        rs.getInt("ANNEE")
                );
            }
        }
        return null;
    }

    public static List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT * FROM BOOKS";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                books.add(new Book(
                        rs.getInt("BOOK_ID"),
                        rs.getString("TITLE"),
                        rs.getString("AUTHOR"),
                        rs.getString("LANGUE"),
                        rs.getString("DESCRIPTION"),
                        rs.getInt("ANNEE")
                ));
            }
        }
        return books;
    }
}
