# Homework 3

Aplicatie Spring Boot pentru gestionarea unei biblioteci. Aplicatia permite imprumutul si returnarea cartilor si foloseste configuratii diferite pentru limitele de imprumut.

## Cum functioneaza

- `Member` si `Book` reprezinta membrii si cartile din biblioteca.
- `MemberRepository` si `BookRepository` pastreaza datele in memorie.
- `BorrowingService` se ocupa de imprumutul si returnarea cartilor.
- `BorrowingPolicy` defineste politica de imprumut folosita de aplicatie.
- `FixedBorrowingPolicy` foloseste o limita fixa de carti.
- `RoleBasedBorrowingPolicy` stabileste limita in functie de rolul membrului.

## Configuratie

Politica folosita este aleasa din `application.yml`, prin proprietatea `library.policy.type`.

- `fixed` foloseste aceeasi limita pentru toti membrii.
- `role-based` foloseste limite diferite pentru studenti, membri regular si membri premium.
- Limitele pot fi modificate din fisierul de configurare.

## Aspects

- `BorrowingValidationAspect` verifica daca imprumutul este permis.
- Aspectul de logging afiseaza informatii despre operatii si timpul de executie.
- Aspectul de exceptii inregistreaza erorile aparute in timpul operatiilor.

## Events

Cand un membru imprumuta un numar de carti care atinge pragul configurat, `BorrowingService` publica un eveniment.

`BorrowingEventListener` asculta acest eveniment si afiseaza o notificare in consola.

Pragul este configurat prin `library.borrowing.event-threshold` in `application.yml`.

## Testare

`LibraryTestRunner` adauga date de exemplu si demonstreaza imprumutul unei carti, returnarea acesteia si cateva cazuri in care operatia poate esua.

Aplicatia foloseste dependency injection prin constructor pentru a conecta serviciile, repository-urile si aspectele.
