package org.example.homework3.domainModel;

import org.example.homework3.domainModel.MemberRole;
import java.util.ArrayList;

public class Member {

    private int id;
    private MemberRole role;
    private String name;
    private ArrayList<Book> borrowedBooks = new ArrayList<>();

    private static int idCounter = 1;

    public Member(String name, MemberRole role, ArrayList<Book> borrowedBooks) {
        this.id = idCounter++;
        this.name = name;
        this.role = role;
        this.borrowedBooks = borrowedBooks;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public MemberRole getRole() {
        return role;
    }
    public void setRole(MemberRole role) {
        this.role = role;
    }

   public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
   }

   public void setBorrowedBooks(ArrayList<Book> borrowedBooks) {
        this.borrowedBooks= borrowedBooks;
    }



}
