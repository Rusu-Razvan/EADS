package org.example.homework2;

import org.example.homework2.FileProperties;
import org.example.homework2.Person;
import org.example.homework2.DataReader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataRunner implements CommandLineRunner {

    private final DataReader dataReader;
    private final FileProperties fileProperties;

    public DataRunner(DataReader dataReader, FileProperties fileProperties) {
        this.dataReader = dataReader;
        this.fileProperties = fileProperties;
    }

    @Override
    public void run(String... args) {
        System.out.println("==================================================");
        System.out.println(">>> APLICATIA A PORNIT CU SUCCES <<<");
        System.out.println("Cale fișier: " + fileProperties.getPath());
        System.out.println("Format detectat: " + dataReader.getFormat());

        List<Person> persons = dataReader.readData();
        System.out.println("Persoane încărcate (" + persons.size() + "):");
        for (Person p : persons) {
            System.out.println(" -> " + p);
        }
        System.out.println("==================================================");
    }
}