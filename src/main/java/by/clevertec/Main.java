package by.clevertec;

import by.clevertec.model.Animal;
import by.clevertec.model.Car;
import by.clevertec.model.Examination;
import by.clevertec.model.Flower;
import by.clevertec.model.House;
import by.clevertec.model.Person;
import by.clevertec.model.Student;
import by.clevertec.util.Util;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        task1();
        task2();
        task3();
        task4();
        task5();
        task6();
        task7();
        task8();
        task9();
        task10();
        task11();
        task12();
        task13();
        task14();
        task15();
        task16();
        task17();
        task18();
        task19();
        task20();
        task21();
        task22();
    }

    public static void task1() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getAge() >= 10 && animal.getAge() < 20)
                .sorted(Comparator.comparingInt(Animal::getAge))
                .skip(7 * 2)
                .limit(7)
                .forEach(System.out::println);
    }

    public static void task2() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> "Japanese".equals(animal.getOrigin()))
                .peek(animal -> animal.setBread(animal.getBread().toUpperCase()))
                .filter(animal -> "Female".equals(animal.getGender()))
                .map(Animal::getBread)
                .forEach(System.out::println);
    }

    public static void task3() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getAge() > 30)
                .map(Animal::getOrigin)
                .filter(s -> s.startsWith("A"))
                .distinct()
                .forEach(System.out::println);
    }

    public static void task4() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(
                animals.stream()
                        .filter(animal -> "Female".equals(animal.getGender()))
                        .count()
        );
    }

    public static void task5() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(
                animals.stream()
                        .filter(animal -> animal.getAge() >= 20 && animal.getAge() <= 30)
                        .anyMatch(animal -> "Hungarian".equals(animal.getOrigin()))
        );
    }

    public static void task6() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(
                animals.stream()
                        .allMatch(animal -> "Male".equals(animal.getGender()) || "Female".equals(animal.getGender()))
        );
    }

    public static void task7() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(
                animals.stream()
                        .noneMatch(animal -> animal.getOrigin().contains("Oceania"))
        );
    }

    public static void task8() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .sorted(Comparator.comparing(Animal::getBread))
                .limit(100)
                .map(Animal::getAge)
                .max(Integer::compare)
                .ifPresent(System.out::println);
    }

    public static void task9() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .map(Animal::getBread)
                .map(String::toCharArray)
                .map(chars -> chars.length)
                .min(Integer::compare)
                .ifPresent(System.out::println);
    }

    public static void task10() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(
                animals.stream()
                        .mapToInt(Animal::getAge)
                        .sum()
        );
    }

    public static void task11() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> "Indonesian".equals(animal.getOrigin()))
                .mapToInt(Animal::getAge)
                .average()
                .ifPresent(System.out::println);
    }

    public static void task12() {
        List<Person> persons = Util.getPersons();
        persons.stream()
                .filter(person -> "Male".equals(person.getGender()))
                .filter(person -> Period.between(person.getDateOfBirth(), LocalDate.now()).getYears() >= 18)
                .filter(person -> Period.between(person.getDateOfBirth(), LocalDate.now()).getYears() < 27)
                .sorted(Comparator.comparing(Person::getRecruitmentGroup))
                .limit(200)
                .forEach(System.out::println);
    }

    public static void task13() {
        List<House> houses = Util.getHouses();
        Predicate<House> peopleFromHospital = house -> house.getBuildingType()
                .equals("Hospital");
        Predicate<Person> children = person -> person.getDateOfBirth()
                .isAfter(LocalDate.now().minusYears(18));
        Predicate<Person> oldPeople = person -> person.getDateOfBirth()
                .isBefore(LocalDate.now().minusYears(65));
        Stream<Person> sickAndWounded = houses.stream()
                .filter(peopleFromHospital)
                .flatMap(house -> house.getPersonList().stream());
        Stream<Person> childrenAndOldPeople = houses.stream()
                .filter(peopleFromHospital.negate())
                .flatMap(house -> house.getPersonList().stream())
                .filter(children.or(oldPeople));
        Stream<Person> remainingPeople = houses.stream()
                .filter(peopleFromHospital.negate())
                .flatMap(house -> house.getPersonList().stream())
                .filter(children.negate().and(oldPeople.negate()));
        Stream.concat(sickAndWounded, Stream.concat(childrenAndOldPeople, remainingPeople))
                .limit(500)
                .forEach(System.out::println);
    }

    public static void task14() {
        List<Car> cars = Util.getCars();
        List<List<Car>> echelons = new ArrayList<>();

        Predicate<Car> isGoingToTurkmenistan =
                (car) -> "Jaguar".equals(car.getCarMake()) || "White".equals(car.getColor());
        Predicate<Car> isGoingToUzbekistan =
                (car) -> car.getMass() < 1500 || List.of("BMW", "Lexus", "Chrysler", "Toyota").contains(car.getCarMake());
        Predicate<Car> isGoingToKazakhstan =
                (car) -> ("Black".equals(car.getColor()) && car.getMass() >= 4000) || List.of("GMC", "Dodge").contains(car.getCarMake());
        Predicate<Car> isGoingToKyrgyzstan =
                (car) -> car.getReleaseYear() < 1982 || List.of("Civic", "Cherokee").contains(car.getCarModel());
        Predicate<Car> isGoingToRussia =
                (car) -> !List.of("Yellow", "Red", "Green", "Blue").contains(car.getColor()) || car.getPrice() > 40000;
        Predicate<Car> isGoingToMongolia =
                (car) -> car.getVin().contains("59");

        cars.stream()
                .collect(Collectors.partitioningBy(isGoingToTurkmenistan))
                .forEach((key1, value1) -> {
                    if (key1) echelons.add(value1);
                    else value1.stream()
                            .collect(Collectors.partitioningBy(isGoingToUzbekistan))
                            .forEach((key2, value2) -> {
                                if (key2) echelons.add(value2);
                                else value2.stream()
                                        .collect(Collectors.partitioningBy(isGoingToKazakhstan))
                                        .forEach((key3, value3) -> {
                                            if (key3) echelons.add(value3);
                                            else value3.stream()
                                                    .collect(Collectors.partitioningBy(isGoingToKyrgyzstan))
                                                    .forEach((key4, value4) -> {
                                                        if (key4) echelons.add(value4);
                                                        else value4.stream()
                                                                .collect(Collectors.partitioningBy(isGoingToRussia))
                                                                .forEach((key5, value5) -> {
                                                                    if (key5) echelons.add(value5);
                                                                    else value5.stream()
                                                                            .collect(Collectors.partitioningBy(isGoingToMongolia))
                                                                            .forEach((key6, value6) -> {
                                                                                if (key6) echelons.add(value6);
                                                                            });
                                                                });
                                                    });
                                        });
                            });
                });

        Collections.reverse(echelons);
        echelons.stream()
                .map(carsInEchelons -> carsInEchelons.stream()
                        .map(Car::getMass)
                        .reduce(0, Integer::sum)
                        .doubleValue() * 7.14)
                .forEach(aDouble -> System.out.printf("%.2f\n", aDouble));

        System.out.printf("%.2f\n", echelons.stream()
                .map(carsInEchelons -> carsInEchelons.stream()
                        .map(car -> (car.getPrice() - car.getMass() * 7.14))
                        .reduce(0.0, Double::sum))
                .reduce(0.0, Double::sum)
        );
    }

    public static void task15() {
        List<Flower> flowers = Util.getFlowers();
        double totalPrice = flowers.stream()
                .sorted(Comparator.comparing(Flower::getOrigin).reversed()
                        .thenComparing(Flower::getPrice)
                        .thenComparing(Flower::getWaterConsumptionPerDay).reversed())
                .filter(flower -> flower.getCommonName().charAt(0) >= 'C' && flower.getCommonName().charAt(0) <= 'S')
                .filter(Flower::isShadePreferred)
                .filter(flower -> flower.getFlowerVaseMaterial().contains("Glass") ||
                        flower.getFlowerVaseMaterial().contains("Aluminium") ||
                        flower.getFlowerVaseMaterial().contains("Steel"))
                .mapToDouble(flower -> flower.getPrice() + flower.getWaterConsumptionPerDay() * 365 * 5 * 1.39 / 1000)
                .sum();
        System.out.printf("%.2f", totalPrice);
    }

    public static void task16() {
        List<Student> students = Util.getStudents();
//        students.stream() Продолжить ...
    }

    public static void task17() {
        List<Student> students = Util.getStudents();
//        students.stream() Продолжить ...
    }

    public static void task18() {
        List<Student> students = Util.getStudents();
        List<Examination> examinations = Util.getExaminations();
//        students.stream() Продолжить ...
    }

    public static void task19() {
        List<Student> students = Util.getStudents();
//        students.stream() Продолжить ...
    }

    public static void task20() {
        List<Student> students = Util.getStudents();
//        students.stream() Продолжить ...
    }

    public static void task21() {
        List<Student> students = Util.getStudents();
//        students.stream() Продолжить ...
    }

    public static void task22() {
        List<Student> students = Util.getStudents();
//        students.stream() Продолжить ...
    }
}
