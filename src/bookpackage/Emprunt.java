package bookpackage;

import java.time.LocalDate;


import systemusers.User;

import java.time.LocalDate;
import bookpackage.Book;
import systemusers.User;

public class Emprunt {

    private int idEmprunt;
    private LocalDate dateEmprunt;
    private LocalDate dateRetourPrevue;
    private LocalDate dateRetourReelle;
    private StatutEmprunt statut;
    private Book livre;
    private User user;

    // ===== Constructeur pour création =====
    public Emprunt(LocalDate dateEmprunt,
                   LocalDate dateRetourPrevue,
                   Book livre,
                   User user) {

        this.dateEmprunt = dateEmprunt;
        this.dateRetourPrevue = dateRetourPrevue;
        this.livre = livre;
        this.user = user;
        this.statut = StatutEmprunt.EN_COURS;
    }
    public Emprunt(LocalDate dateEmprunt,
            LocalDate dateRetourPrevue,
            String  statut ,
            Book livre,
            User user) {

 this.dateEmprunt = dateEmprunt;
 this.dateRetourPrevue = dateRetourPrevue;
 this.livre = livre;
 this.user = user;
 this.statut=StatutEmprunt.valueOf(statut) ; 
 
}
    // ===== Constructeur depuis la BDD =====
    public Emprunt(int idEmprunt,
                   LocalDate dateEmprunt,
                   LocalDate dateRetourPrevue,
                   LocalDate dateRetourReelle,
                   StatutEmprunt statut,
                   Book livre,
                   User user) {

        this.idEmprunt = idEmprunt;
        this.dateEmprunt = dateEmprunt;
        this.dateRetourPrevue = dateRetourPrevue;
        this.dateRetourReelle = dateRetourReelle;
        this.statut = statut;
        this.livre = livre;
        this.user = user;
    }

    // ===== Getters / Setters =====
    public int getIdEmprunt() {
        return idEmprunt;
    }

    public void setIdEmprunt(int idEmprunt) {
        this.idEmprunt = idEmprunt;
    }

    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(LocalDate dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public LocalDate getDateRetourPrevue() {
        return dateRetourPrevue;
    }

    public void setDateRetourPrevue(LocalDate dateRetourPrevue) {
        this.dateRetourPrevue = dateRetourPrevue;
    }

    public LocalDate getDateRetourReelle() {
        return dateRetourReelle;
    }

    public void setDateRetourReelle(LocalDate dateRetourReelle) {
        this.dateRetourReelle = dateRetourReelle;
    }

    public StatutEmprunt getStatut() {
        return statut;
    }

    public void setStatut(StatutEmprunt statut) {
        this.statut = statut;
    }

    public Book getBook() {
        return livre;
    }

    public void setLivre(Book livre) {
        this.livre = livre;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
