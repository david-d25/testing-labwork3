package space.davids_digital.lab3.pages

import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

open class CommonPage(driver: WebDriver, expectedPageUrlRegex: Regex) {
    init {
        try {
            WebDriverWait(driver, 10)
                .pollingEvery(Duration.ofMillis(100))
                .until { driver.currentUrl.matches(expectedPageUrlRegex) }
        } catch (e: TimeoutException) {
            throw IllegalStateException("Expected url to match regex '$expectedPageUrlRegex', but url is '${driver.currentUrl}'")
        }
    }
}