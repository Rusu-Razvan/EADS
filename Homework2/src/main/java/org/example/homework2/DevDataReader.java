package org.example.homework2;

import org.example.homework2.FileProperties;
import org.example.homework2.Person;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


@Service
@Profile("dev")
@ConditionalOnExpression("'${app.file.format}'.equalsIgnoreCase('CSV')")
public class DevDataReader implements DataReader {

    private final FileProperties fileProperties;
    private final ResourceLoader resourceLoader;

    public DevDataReader(FileProperties fileProperties, ResourceLoader resourceLoader) {
        this.fileProperties = fileProperties;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public List<Person> readData() {

        List<Person> persons = new ArrayList<>();
        try {
            Resource resource = resourceLoader.getResource("classpath:" + fileProperties.getPath());
            if (!resource.exists()) return persons;

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                boolean isFirstLine = true;
                while ((line = reader.readLine()) != null) {
                    if (isFirstLine) { // Ignorăm antetul (person_number, name...)
                        isFirstLine = false;
                        continue;
                    }
                    if (line.isBlank()) continue;

                    String[] parts = line.split(",");
                    if (parts.length >= 4) {
                        int number = Integer.parseInt(parts[0].trim());
                        String name = parts[1].trim();
                        int age = Integer.parseInt(parts[2].trim());
                        int weight = Integer.parseInt(parts[3].trim());
                        persons.add(new Person(number, name, age, weight));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return persons;

    }

    @Override
    public String getFormat() {
        return fileProperties.getFormat();
    }
}
