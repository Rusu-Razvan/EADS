package org.example.homework3.events;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BorrowingEventListener {

    @EventListener
    public void handleBookBorrowEvent(BookBorrowEvent event) {
        System.out.println("\n [SPRING EVENT DETECTAT] ---------------------------------------------");
        System.out.println("-> Notificare: Membrul " + event.getMember().getName() +
                " a împrumutat o carte! Total cărți deținute: " +
                event.getTotalBorrowedBooks());
        System.out.println("------------------------------------------------------------------------\n");
    }
}