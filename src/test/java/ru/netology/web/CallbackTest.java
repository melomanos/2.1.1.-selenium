package ru.netology.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CallbackTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "./driver/win/chromedriver.exe");
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Users\\Meloman\\AppData\\Local\\Google\\Chrome SxS\\Application\\chrome.exe");
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
        elements.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий");
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
        elements.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Oleg");
        elements.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79270000000");
        elements.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        elements.findElement(By.className("button")).click();
        List<WebElement> elementsInvalid = driver.findElements(By.className("input__sub"));
        String text = elementsInvalid.get(0).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void shouldTestEmptyName() {
        driver.get("http://localhost:9999");
        WebElement elements = driver.findElement(By.cssSelector("form"));
        elements.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("");
        elements.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79270000000");
        elements.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        elements.findElement(By.className("button")).click();
        List<WebElement> elementsInvalid = driver.findElements(By.className("input__sub"));
        String text = elementsInvalid.get(0).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldTestInvalidPhone() {
        driver.get("http://localhost:9999");
        WebElement elements = driver.findElement(By.cssSelector("form"));
        elements.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий");
        elements.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("79270000000");
        elements.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        elements.findElement(By.className("button")).click();
        List<WebElement> elementsInvalid = driver.findElements(By.className("input__sub"));
        String text = elementsInvalid.get(1).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void shouldTestEmptyPhone() {
        driver.get("http://localhost:9999");
        WebElement elements = driver.findElement(By.cssSelector("form"));
        elements.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий");
        elements.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("");
        elements.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        elements.findElement(By.className("button")).click();
        List<WebElement> elementsInvalid = driver.findElements(By.className("input__sub"));
        String text = elementsInvalid.get(1).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }
}

