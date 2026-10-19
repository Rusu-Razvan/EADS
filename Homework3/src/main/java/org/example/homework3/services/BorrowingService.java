package org.example.homework3.services;

import org.example.homework3.domainModel.Book;
import org.example.homework3.domainModel.Member;
import org.example.homework3.borrowingPolicies.BorrowingPolicy;
import org.example.homework3.repository.BookRepository;
import org.example.homework3.repository.MemberRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.example.homework3.events.BookBorrowEvent;
import org.springframework.beans.factory.annotation.Value;

@Service
public class BorrowingService {

    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final ApplicationEventPublisher eventPublisher;

    private final int borrowingEventThreshold;


    public BorrowingService(MemberRepository memberRepository,
                            BookRepository bookRepository,
                            ApplicationEventPublisher eventPublisher,
                            @Value("${library.borrowing.event-threshold:2}") int borrowingEventThreshold) {

        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
        this.eventPublisher = eventPublisher;
        this.borrowingEventThreshold = borrowingEventThreshold;
    }


    public void borrowBook(int memberId, int bookId) {

        Member member = memberRepository.findMemberById(memberId);
        Book book = bookRepository.findBookById(bookId);

        book.setBorrowed(true);
        member.getBorrowedBooks().add(book);


        if (member.getBorrowedBooks().size() >= borrowingEventThreshold) {
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



