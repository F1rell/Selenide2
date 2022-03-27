import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class SelenidTest {

    String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    String planningDate = generateDate(3);

    @BeforeEach
    void setupTest() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $x("//span[@data-test-id='date']//input[@type='tel']").doubleClick().sendKeys(Keys.chord(Keys.CONTROL, "A"), Keys.DELETE);
    }

    @Test
    public void allFieldsAndCheckboxFilledUp() {
        $x("//span[@data-test-id='city']//input[@type='text']").val("Томск");
        $x("//span[@data-test-id='date']//input[@type='tel']").val(planningDate);
        $x("//span[@data-test-id='name']//input[@name='name']").val("Иванов Иван");
        $x("//span[@data-test-id='phone']//input[@name='phone']").val("+79520000000");
        $x("//label[@data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x("//div[@class='notification__title']").shouldHave(Condition.text("Успешно!"), Duration.ofSeconds(15));
        $x("//div[@class='notification__content']").shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15));
    }
}