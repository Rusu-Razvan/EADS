package org.example.homework3.events;

import org.example.homework3.domainModel.Member;

public class BookBorrowEvent {
    private final Member member;
    private final int totalBorrowedBooks;

    public BookBorrowEvent(Member member, int totalBorrowedBooks) {
        this.member = member;
        this.totalBorrowedBooks = totalBorrowedBooks;
    }

    public Member getMember() {
        return member;
    }

    public int getTotalBorrowedBooks() {
        return totalBorrowedBooks;
    }
}