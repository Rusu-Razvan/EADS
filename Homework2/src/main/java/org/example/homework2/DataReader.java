package org.example.homework2;

import org.example.homework2.Person;
import java.util.List;

public interface DataReader {
    List<Person> readData();
    String getFormat();
}
