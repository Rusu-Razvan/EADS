package org.example.homework3.services;

import org.example.homework3.domainModel.Book;
import org.example.homework3.domainModel.Member;
import org.example.homework3.borrowingPolicies.BorrowingPolicy;
import org.example.homework3.repository.BookRepository;
import org.example.homework3.repository.MemberRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.example.homework3.events.BookBorrowEvent;

@Service
public class BorrowingService {

    private final BorrowingPolicy borrowingPolicy; // S-ar putea sa n-am nevoie de asta. Vad ca policy-ul nu este folosit nicaieri. E corect asa?
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final ApplicationEventPublisher eventPublisher;


    public BorrowingService(BorrowingPolicy borrowingPolicy,
                            MemberRepository memberRepository,
                            BookRepository bookRepository,
                            ApplicationEventPublisher eventPublisher) {

        this.borrowingPolicy = borrowingPolicy;
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
        this.eventPublisher = eventPublisher;
    }


    public void borrowBook(int memberId, int bookId) {

        Member member = memberRepository.findMemberById(memberId);
        Book book = bookRepository.findBookById(bookId);

        book.setBorrowed(true);
        member.getBorrowedBooks().add(book);

        //as adauga pragul peste care sa se declanseze evenimentul in app.yml si sa iau valoare cu @Value sau
        // @Congig(prefix = _).
        if (member.getBorrowedBooks().size() >= 1) {
            eventPublisher.publishEvent(new BookBorrowEvent(member, member.getBorrowedBooks().size()));
        }
    }

    public void returnBook ( int memberId, int bookId) {

        Member member = memberRepository.findMemberById(memberId);
        if (member == null) {
            throw new IllegalArgumentException("Membrul nu a fost găsit.");
        }

        Book book = bookRepository.findBookById(bookId);
        if (book == null) {
            throw new IllegalArgumentException("Cartea nu a fost găsită.");
        }

        if (!book.isBorrowed()) {
            throw new IllegalArgumentException("Cartea nu era împrumutată, deci nu poate fi returnată.");
        }

         book.setBorrowed(false);
         member.getBorrowedBooks().remove(book);
        }
}



