package by.clevertec;

import by.clevertec.model.*;
import by.clevertec.util.Util;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {
    @Test
    @DisplayName("Should run task1 correctly")
    void task1_test() {
        List<Animal> animals = Util.getAnimals();
        List<Animal> expected = animals.stream()
                .filter(animal -> animal.getAge() >= 10 && animal.getAge() < 20)
                .sorted(Comparator.comparingInt(Animal::getAge))
                .skip(7 * 2)
                .limit(7)
                .collect(Collectors.toList());

        List<Animal> actual = Main.task1(animals);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should run task2 correctly")
    void task2_test() {
        List<Animal> animals = Util.getAnimals();
        List<String> expected = animals.stream()
                .filter(animal -> "Japanese".equals(animal.getOrigin()) && "Female".equals(animal.getGender()))
                .map(Animal::getBread)
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        List<String> actual = Main.task2(animals);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should run task3 correctly")
    void task3_test() {
        List<Animal> animals = Util.getAnimals();
        List<String> expected = animals.stream()
                .filter(animal -> animal.getAge() > 30)
                .map(Animal::getOrigin)
                .filter(s -> s.startsWith("A"))
                .distinct()
                .collect(Collectors.toList());

        List<String> actual = Main.task3(animals);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should run task4 correctly")
    void task4_test() {
        List<Animal> animals = Util.getAnimals();
        long expected = animals.stream()
                .filter(animal -> "Female".equals(animal.getGender()))
                .count();

        long actual = Main.task4(animals);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should run task5 correctly")
    void task5_test() {
        List<Animal> animals = Util.getAnimals();
        boolean expected = animals.stream()
                .anyMatch(animal -> animal.getAge() >= 20 && animal.getAge() <= 30 && "Hungarian".equals(animal.getOrigin()));

        boolean actual = Main.task5(animals);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should run task6 correctly")
    void task6_test() {
        List<Animal> animals = Util.getAnimals();
        boolean expected = animals.stream()
                .allMatch(animal -> "Male".equals(animal.getGender()) || "Female".equals(animal.getGender()));

        boolean actual = Main.task6(animals);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should run task7 correctly")
    void task7_test() {
        List<Animal> animals = Util.getAnimals();
        boolean expected = animals.stream()
                .noneMatch(animal -> animal.getOrigin().contains("Oceania"));

        boolean actual = Main.task7(animals);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should run task12 correctly")
    void task12_test() {
        List<Person> persons = Util.getPersons();
        List<Person> expected = persons.stream()
                .filter(person -> "Male".equals(person.getGender()))
                .filter(person -> Period.between(person.getDateOfBirth(), LocalDate.now()).getYears() >= 18)
                .filter(person -> Period.between(person.getDateOfBirth(), LocalDate.now()).getYears() < 27)
                .sorted(Comparator.comparing(Person::getRecruitmentGroup))
                .limit(200)
                .collect(Collectors.toList());

        List<Person> actual = Main.task12(persons);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should run task16 correctly")
    void task16_test() {
        List<Student> students = Util.getStudents();
        List<String> expected = students.stream()
                .filter(student -> student.getAge() < 18)
                .sorted(Comparator.comparing(Student::getSurname))
                .map(student -> student.getSurname() + " - " + student.getAge() + " years old")
                .collect(Collectors.toList());

        List<Student> actual = Main.task16(students);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should run task17 correctly")
    void task17_test() {
        List<Student> students = Util.getStudents();
        List<String> expected = students.stream()
                .map(Student::getGroup)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        List<String> actual = Main.task17(students);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should run task19 correctly")
    void task19_test() {
        List<Student> students = Util.getStudents();
        List<Examination> examinations = Util.getExaminations();
        List<String> expected = students.stream()
                .filter(student -> student.getGroup().equals("C-3"))
                .filter(student -> {
                    Examination examination = examinations.stream()
                            .filter(exam -> exam.getStudentId() == student.getId())
                            .findFirst()
                            .orElse(null);
                    return examination != null && examination.getExam1() > 4 && examination.getExam2() > 4 && examination.getExam3() > 4;
                })
                .map(Student::getSurname)
                .collect(Collectors.toList());

        List<String> actual = Main.task19(students, examinations);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should run task20 correctly")
    void task20_test() {
        List<Student> students = Util.getStudents();
        List<Examination> examinations = Util.getExaminations();
        String expected = examinations.stream()
                .collect(Collectors.groupingBy(e -> students.stream()
                                .filter(s -> s.getId() == e.getStudentId())
                                .findFirst()
                                .map(Student::getFaculty)
                                .orElse("Unknown"),
                        Collectors.averagingInt(Examination::getExam1)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .get();

        String actual = Main.task20(students, examinations);

        assertEquals(expected, actual);
    }
}