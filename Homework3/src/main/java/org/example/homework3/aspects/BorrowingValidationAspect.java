package org.example.homework3.aspects;

import org.example.homework3.borrowingPolicies.BorrowingPolicy;
import org.example.homework3.domainModel.*;
import org.example.homework3.exceptions.*;
import org.example.homework3.repository.*;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BorrowingValidationAspect {

    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final BorrowingPolicy borrowingPolicy;

    public BorrowingValidationAspect(MemberRepository memberRepository,
                                     BookRepository bookRepository,
                                     BorrowingPolicy borrowingPolicy) {
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
        this.borrowingPolicy = borrowingPolicy;
    }

    @Before("execution(* org.example.homework3.services.BorrowingService.borrowBook(..))")
    public void validateBorrowing(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        if (args.length >= 2 && args[0] instanceof Integer && args[1] instanceof Integer) {
            int memberId = (Integer) args[0];
            int bookId = (Integer) args[1];

            //Verificam dacă membrul exista
            Member member = memberRepository.findMemberById(memberId);
            if (member == null) {
                throw new MemberNotFoundException("Membrul cu ID-ul " + memberId + " nu a fost gasit.");
            }

            //Verificam daca cartea exista
            Book book = bookRepository.findBookById(bookId);
            if (book == null) {
                throw new BookNotFoundException("Cartea cu ID-ul " + bookId + " nu a fost gasita.");
            }

            //Verificam daca este deja imprumutata
            if (book.isBorrowed()) {
                throw new BookAlreadyBorrowedException("Cartea '" + book.getTitle() + "' este deja imprumutata.");
            }

            //Verificam daca a depasit limita de carti
            if (!borrowingPolicy.canBorrow(member)) {
                throw new BorrowingLimitExceededException("Membrul " + member.getName() + " a depasit limita de carti permise.");
            }
        }
    }
}
