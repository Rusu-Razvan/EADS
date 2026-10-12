# Homework 2

Aplicație Spring Boot care citește date din fișiere diferite în funcție de profilul activ (`dev` sau `prod`).

## Cum funcționează

Configurația fișierului este definită în `application-dev.yml` și `application-prod.yml`. În funcție de profilul activ, aplicația folosește un fișier CSV pentru `dev` sau un fișier JSON pentru `prod`.

Clasa `FileProperties` folosește `@ConfigurationProperties` pentru a prelua calea și formatul fișierului din configurație.

Interfața `DataReader` definește operațiile comune pentru citirea datelor. Implementările `DevDataReader` și `ProdDataReader` se ocupă de citirea fișierelor CSV, respectiv JSON. Selecția implementării se face folosind `@Profile` și `@ConditionalOnExpression`, în funcție de profilul activ și formatul configurat.

La pornirea aplicației, `DataRunner` citește datele folosind implementarea selectată și afișează informațiile în consolă.

## Actuator

Clasa `DataSourceEndpoint` definește un endpoint Actuator care oferă informații despre sursa de date configurată, precum calea fișierului, formatul și numărul de înregistrări încărcate.

## Configurație

Valorile pot fi configurate prin fișierele de configurare Spring Boot. De asemenea, proprietățile pot fi suprascrise prin variabile de mediu sau argumente din linia de comandă, argumentele CLI având prioritate față de celelalte surse de configurare.
