package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;
import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryTest {

    private Faker faker;


    @BeforeEach
    void setUpAll() {
        open("http://localhost:9999/");
        faker = new Faker(new Locale("ru"));

    }


    @Test
    void shouldSendDataForDelivery() {
        String date1 = DataGenerator.Registration.getDate(4);
        String date2 = DataGenerator.Registration.getDate(20);
        String city = DataGenerator.Registration.cityForCard("ru");
        String name = DataGenerator.Registration.nameForCard("ru");
        String phone = DataGenerator.Registration.phoneForCard("ru");

        $("[data-test-id='city'] input").setValue(city);
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date1);
        $("[name='name']").setValue(name);
        $("[name='phone']").setValue(phone);
        $("[data-test-id=agreement] .checkbox__box").click();
        $("button.button").click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(exactText("Встреча успешно запланирована на " + date1));

        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date2);
        $("button.button").click();
        $("[data-test-id= 'replan-notification']").shouldHave(Condition.text("Необходимо подтверждение У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $(byText("Перепланировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(exactText("Встреча успешно запланирована на " + date2));

    }


    @Test
    void shouldTestInvalidName() {
        String date = DataGenerator.Registration.getDate(4);
        String date1 = DataGenerator.Registration.getDate(20);
        $("[data-test-id='city'] input").setValue(DataGenerator.Registration.cityForCard("ru"));
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id=name] input").setValue("Ivan Ivanov");
        $("[name='phone']").setValue(DataGenerator.Registration.phoneForCard("ru"));
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldBe(visible, Duration.ofSeconds(15)).
                shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }


    @Test
    void shouldTestInvalidPhone() {
        String date = DataGenerator.Registration.getDate(4);
        String date1 = DataGenerator.Registration.getDate(20);
        $("[data-test-id='city'] input").setValue(DataGenerator.Registration.cityForCard("ru"));
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date);
        $("[name='name']").setValue(DataGenerator.Registration.nameForCard("ru"));
        $("[data-test-id=phone] input").setValue("7123456789");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='phone'] .input__sub").shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("На указанный номер моб. тел. будет отправлен смс-код для подтверждения заявки на карту. Проверьте, что номер ваш и введен корректно."));
    }

    @Test
    void shouldTestEmptyName() {
        String date = DataGenerator.Registration.getDate(4);
        String date1 = DataGenerator.Registration.getDate(20);
        $("[data-test-id='city'] input").setValue(DataGenerator.Registration.cityForCard("ru"));
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id=name] input").setValue("");
        $("[name='phone']").setValue(DataGenerator.Registration.phoneForCard("ru"));
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldBe(visible, Duration.ofSeconds(15)).
                shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestEmptyPhone() {
        String date = DataGenerator.Registration.getDate(4);
        String date1 = DataGenerator.Registration.getDate(20);
        $("[data-test-id='city'] input").setValue(DataGenerator.Registration.cityForCard("ru"));
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date);
        $("[name='name']").setValue(DataGenerator.Registration.nameForCard("ru"));
        $("[data-test-id=phone] input").setValue("");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldBe(visible, Duration.ofSeconds(15)).
                shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestNoAgreement() {
        String date = DataGenerator.Registration.getDate(4);
        String date1 = DataGenerator.Registration.getDate(20);
        $("[data-test-id='city'] input").setValue(DataGenerator.Registration.cityForCard("ru"));
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date);
        $("[name='name']").setValue(DataGenerator.Registration.nameForCard("ru"));
        $("[name='phone']").setValue(DataGenerator.Registration.phoneForCard("ru"));
        $("button.button").click();
        $("[data-test-id='agreement'].input_invalid .checkbox__text").shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}