package org.example.homework2;

public class Person {
    private int number;
    private String name;
    private int age;
    private int weight;

    // Constructori, Getters, Setters și toString
    public Person() {}

    public Person(int number, String name, int age, int weight) {
        this.number = number;
        this.name = name;
        this.age = age;
        this.weight = weight;
    }

    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }

    @Override
    public String toString() {
        return "Person{număr=" + number + ", nume='" + name + "', vârstă=" + age + ", greutate=" + weight + "}";
    }
}