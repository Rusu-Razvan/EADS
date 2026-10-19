package org.example.homework3.runner;

import org.example.homework3.domainModel.Book;
import org.example.homework3.domainModel.Member;
import org.example.homework3.domainModel.MemberRole;
import org.example.homework3.repository.BookRepository;
import org.example.homework3.repository.MemberRepository;
import org.example.homework3.services.BorrowingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class LibraryTestRunner implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final BorrowingService borrowingService;

    public LibraryTestRunner(BookRepository bookRepository,
                             MemberRepository memberRepository,
                             BorrowingService borrowingService) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.borrowingService = borrowingService;
    }  //Aici trebuia sa instantiez repository-urile?? Nu ar fi trebuit sa instantiez numai serviciile?

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n==================================================");
        System.out.println(">>> PORNIRE TESTE SISTEM BIBLIOTECĂ (AOP & SERVICES) <<<");
        System.out.println("==================================================");

        // 1. Populăm datele de test în repository-uri
        Book b1 = new Book("Maitreyi", "Mircea Eliade", false);
        Book b2 = new Book("Ion", "Liviu Rebreanu", false);
        bookRepository.addBook(b1);
        bookRepository.addBook(b2);

        Member m1 = new Member("Andrei Popescu", MemberRole.STUDENT, new ArrayList<>());
        memberRepository.addMember(m1);

        System.out.println("-> Date inițiale încărcate: Membru ID=" + m1.getId() +
                " | Cărți disponibile: ID=" + b1.getId() + " și ID=" + b2.getId());

        // --------------------------------------------------
        // TEST 1: Împrumut cu succes
        // --------------------------------------------------
        System.out.println("\n--- TEST 1: Împrumut carte cu succes ---");
        try {
            borrowingService.borrowBook(m1.getId(), b1.getId());
            System.out.println("[TEST REUȘIT] Cartea a fost împrumutată.");
        } catch (Exception e) {
            System.out.println("[TEST EȘUAT] Eroare neașteptată: " + e.getMessage());
        }

        // --------------------------------------------------
        // TEST 2: Încercare de împrumut pentru o carte deja împrumutată
        // (Va declanșa Aspectul de Validare -> BookAlreadyBorrowedException -> Aspectul de excepții)
        // --------------------------------------------------
        System.out.println("\n--- TEST 2: Împrumut carte deja împrumutată (Eroare așteptată) ---");
        try {
            borrowingService.borrowBook(m1.getId(), b1.getId());
        } catch (Exception e) {
            System.out.println("[TEST REUȘIT] Excepția a fost prinsă cu succes în test: " + e.getMessage());
        }

        // --------------------------------------------------
        // TEST 3: Returnare carte cu succes
        // --------------------------------------------------
        System.out.println("\n--- TEST 3: Returnare carte cu succes ---");
        try {
            borrowingService.returnBook(m1.getId(), b1.getId());
            System.out.println("[TEST REUȘIT] Cartea a fost returnată.");
        } catch (Exception e) {
            System.out.println("[TEST EȘUAT] Eroare neașteptată: " + e.getMessage());
        }

        // --------------------------------------------------
        // TEST 4: Împrumut cu un membru care nu există
        // (Va declanșa MemberNotFoundException)
        // --------------------------------------------------
        System.out.println("\n--- TEST 4: Împrumut cu membru inexistent (Eroare așteptată) ---");
        try {
            borrowingService.borrowBook(999, b1.getId());
        } catch (Exception e) {
            System.out.println("[TEST REUȘIT] Excepția a fost prinsă cu succes în test: " + e.getMessage());
        }

        System.out.println("\n==================================================");
        System.out.println(">>> TOATE TESTELE AU FOST RULATE CU SUCCES <<<");
        System.out.println("==================================================");
    }
}