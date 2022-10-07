package ru.netology.delivery.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class DataGenerator {

    static Faker faker = new Faker(new Locale("ru"));

    public static String nameForCard(String locale) {

        return new String(
                faker.name().lastName() + " " + faker.name().firstName()
        );
    }

    public static String invalidName(String locale) {

        return new String(
                String.valueOf(faker.random().nextInt(100))
        );
    }

    public static String getDate(int shift) {
        return LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String phoneForCard(String locale) {
        return new String(
                faker.phoneNumber().phoneNumber()
        );
    }

    public static String invalidPhone(String locale) {
        return new String(
                faker.number().digits(10)
        );
    }


    public static String cityForCard() {
        String[] cities = new String[]{"Москва", "Санкт-Петербург", "Волгоград", "Саратов", "Казань"};
        int itemIndex = (int) (Math.random() * cities.length);
        return cities[itemIndex];
    }

    public static String invalidCity(String ru) {
        return new String(
                String.valueOf(faker.random().nextInt(100))
        );
    }


}
