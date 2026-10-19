package org.example.homework2;

import org.example.homework2.FileProperties;
import org.example.homework2.Person;
import org.example.homework2.DataReader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.core.env.Environment;
import java.util.Arrays;

import java.util.List;

@Component
public class DataRunner implements CommandLineRunner {

    private final DataReader dataReader;
    private final FileProperties fileProperties;
    private final Environment environment;

    public DataRunner(DataReader dataReader, FileProperties fileProperties, Environment environment) {
        this.dataReader = dataReader;
        this.fileProperties = fileProperties;
        this.environment = environment;
    }



    @Override
    public void run(String... args) {

        String[] activeProfiles = environment.getActiveProfiles();

        System.out.println("==================================================");
        System.out.println(">>> APLICATIA A PORNIT CU SUCCES <<<");
        System.out.println("Profiluri active: " +
                Arrays.toString(activeProfiles));
        System.out.println("Cale fisier: " + fileProperties.getPath());
        System.out.println("Format detectat: " + dataReader.getFormat());

        List<Person> persons = dataReader.readData();
        System.out.println("Persoane incarcate (" + persons.size() + "):");
        for (Person p : persons) {
            System.out.println(" -> " + p);
        }
        System.out.println("==================================================");
    }
}