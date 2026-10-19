package org.example.homework3.aspects;

import org.example.homework3.domainModel.Book;
import org.example.homework3.domainModel.Member;
import org.example.homework3.repository.BookRepository;
import org.example.homework3.repository.MemberRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;


    public LoggingAspect(MemberRepository memberRepository, BookRepository bookRepository) {
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
    }

    //Pointcut care vizeaza metodele borrowBook si returnBook din BorrowingService
    @Around("execution(* org.example.homework3.services.BorrowingService.borrowBook(..)) || " +
            "execution(* org.example.homework3.services.BorrowingService.returnBook(..))")
    public Object logBorrowAndReturn(ProceedingJoinPoint joinPoint) throws Throwable {

        //Pornim cronometrul pentru a masura timpul de executie
        long startTime = System.currentTimeMillis();

        //Extragem numele metodei care a fost apelata
        String methodName = joinPoint.getSignature().getName();

        //Extragem argumentele transmise metodei
        Object[] args = joinPoint.getArgs();
        String memberName = "Necunoscut";
        String bookTitle = "Necunoscuta";

        if (args.length >= 2 && args[0] instanceof Integer && args[1] instanceof Integer) {
            int memberId = (Integer) args[0];
            int bookId = (Integer) args[1];

            //Cautam membrul in repository dupa ID
            Member member = memberRepository.findMemberById(memberId);
            if (member != null) {
                memberName = member.getName();
            }

            //Cautam cartea in repository dupa ID
            Book book = bookRepository.findBookById(bookId);
            if (book != null) {
                bookTitle = book.getTitle();
            }
        }

        Object result;
        try {
            //Lasam metoda reala din Service sa isi continuie executia
            result = joinPoint.proceed();
        } finally {
            //Oprim cronometrul (folosim blocul finally ca timpul sa se calculeze chiar daca apare o exceptie)
            long executionTime = System.currentTimeMillis() - startTime;

            //Afișam log-ul
            System.out.println("--------------------------------INSPECȚIE AOP LOG--------------------------------");
            System.out.println("-> Metodă apelată : " + methodName);
            System.out.println("-> Nume membru    : " + memberName);
            System.out.println("-> Titlu carte    : " + bookTitle);
            System.out.println("-> Timp execuție  : " + executionTime + " ms");
            System.out.println("---------------------------------------------------------------------------------");
        }

        return result;
    }
}