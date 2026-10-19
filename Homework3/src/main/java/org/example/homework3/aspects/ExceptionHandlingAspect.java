package org.example.homework3.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionHandlingAspect {

    //Intercepteaza orice excepție aruncata de metodele din BorrowingService
    @AfterThrowing(
            pointcut = "execution(* org.example.homework3.services.BorrowingService.*(..))",
            throwing = "ex"
    )
    public void handleBorrowingExceptions(JoinPoint joinPoint, Throwable ex) {

        //Extragem numele metodei care a esuat ("borrowBook" sau "returnBook")
        String failedOperation = joinPoint.getSignature().getName();

        //Extragem mesajul de eroare din exceptia aruncata
        String errorMessage = ex.getMessage();

        //Logam informațiile
        System.err.println("================================ EROARE INTERCEPTATĂ (AOP) ================================");
        System.err.println("-> Operația eșuată : " + failedOperation);
        System.err.println("-> Mesaj de eroare : " + errorMessage);
        System.err.println("-> Tipul excepției : " + ex.getClass().getSimpleName());
        System.err.println("-----------------------------------------------------------------------------------------");
    }
}