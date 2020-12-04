package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CallbackTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestSuccessful() {
        driver.get("http://localhost:9999");
        WebElement elements = driver.findElement(By.cssSelector("form"));
        elements.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий Петров");
        elements.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79270000000");
        elements.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        elements.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void shouldTestInvalidName() {
        driver.get("http://localhost:9999");
        WebElement elements = driver.findElement(By.cssSelector("form"));
        elements.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Oleg Ivanov");
        elements.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79270000000");
        elements.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        elements.findElement(By.className("button")).click();
        WebElement name = elements.findElement(By.cssSelector("[data-test-id=name]"));
        name.findElement(By.className("input__sub"));
        String text = name.getText();
        assertEquals("Фамилия и имя\n" +
                "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void shouldTestEmptyName() {
        driver.get("http://localhost:9999");
        WebElement elements = driver.findElement(By.cssSelector("form"));
        elements.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("");
        elements.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79270000000");
        elements.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        elements.findElement(By.className("button")).click();
        WebElement name = elements.findElement(By.cssSelector("[data-test-id=name]"));
        name.findElement(By.className("input__sub"));
        String text = name.getText();
        assertEquals("Фамилия и имя\n" +
                "Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldTestInvalidPhone() {
        driver.get("http://localhost:9999");
        WebElement elements = driver.findElement(By.cssSelector("form"));
        elements.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий Петров");
        elements.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("79270000000");
        elements.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        elements.findElement(By.className("button")).click();
        WebElement name = elements.findElement(By.cssSelector("[data-test-id=phone]"));
        name.findElement(By.className("input__sub"));
        String text = name.getText();
        assertEquals("Мобильный телефон\n" +
                "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void shouldTestEmptyPhone() {
        driver.get("http://localhost:9999");
        WebElement elements = driver.findElement(By.cssSelector("form"));
        elements.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий Петров");
        elements.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("");
        elements.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        elements.findElement(By.className("button")).click();
        WebElement name = elements.findElement(By.cssSelector("[data-test-id=phone]"));
        name.findElement(By.className("input__sub"));
        String text = name.getText();
        assertEquals("Мобильный телефон\n" +
                "Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldTestDoNotPushRadioButton() {
        driver.get("http://localhost:9999");
        WebElement elements = driver.findElement(By.cssSelector("form"));
        elements.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий Петров");
        elements.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("");
        elements.findElement(By.className("button")).click();
        WebElement name = elements.findElement(By.cssSelector("[data-test-id=agreement]"));
        name.findElement(By.className("checkbox__text"));
        String text = name.getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных " +
                "и разрешаю сделать запрос в бюро кредитных историй", text.trim());
    }
}

