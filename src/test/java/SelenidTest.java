import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class SelenidTest {

    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $x("//span[@data-test-id='date']//input[@type='tel']").doubleClick().sendKeys(Keys.chord(Keys.CONTROL, "A"), Keys.DELETE);
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void allFieldsAndCheckboxFilledUp() {
        $x("//span[@data-test-id='city']//input[@type='text']").val("Томск");
        $x("//span[@data-test-id='date']//input[@type='tel']").val("30.03.2022");
        $x("//span[@data-test-id='name']//input[@name='name']").val("Иванов Иван");
        $x("//span[@data-test-id='phone']//input[@name='phone']").val("+79520000000");
        $x("//label[@data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x("//*[contains(text(), 'Успешно')]").should(visible, Duration.ofSeconds(15));
    }
}