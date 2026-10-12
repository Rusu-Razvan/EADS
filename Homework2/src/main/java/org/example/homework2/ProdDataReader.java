package org.example.homework2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.homework2.FileProperties;
import org.example.homework2.Person;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Service
@Profile("prod")
@ConditionalOnExpression("'${app.file.format}'.equalsIgnoreCase('JSON')")
public class ProdDataReader implements DataReader {

    private final FileProperties fileProperties;
    private final ResourceLoader resourceLoader;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public ProdDataReader(FileProperties fileProperties, ResourceLoader resourceLoader) {
        this.fileProperties = fileProperties;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public List<Person> readData() {
        List<Person> persons = new ArrayList<>();
        try {
            Resource resource = resourceLoader.getResource("classpath:" + fileProperties.getPath());
            if (!resource.exists()) return persons;

            // Citim nodul rădăcină din JSON
            JsonNode rootNode = objectMapper.readTree(resource.getInputStream());
            JsonNode personsNode = rootNode.get("persons");

            if (personsNode != null && personsNode.isObject()) {
                Iterator<Map.Entry<String, JsonNode>> fields = personsNode.fields();
                while (fields.hasNext()) {
                    Map.Entry<String, JsonNode> entry = fields.next();
                    JsonNode node = entry.getValue();

                    int number = node.get("number").asInt();
                    String name = node.get("name").asText();
                    int age = node.get("age").asInt();
                    // Atenție la ortografia "weigth" din fișierul tău JSON
                    int weight = node.has("weigth") ? node.get("weigth").asInt() : node.get("weight").asInt();

                    persons.add(new Person(number, name, age, weight));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return persons;
    }

    public String getFormat(){
        return fileProperties.getFormat();
    }

}
