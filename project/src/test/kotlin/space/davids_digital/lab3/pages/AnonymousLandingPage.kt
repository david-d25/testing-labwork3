package space.davids_digital.lab3.pages

import org.openqa.selenium.WebDriver
import java.lang.IllegalStateException

private const val EXPECTED_PAGE_URL = "https://www.google.com/intl/ru/drive/"

class AnonymousLandingPage(private val driver: WebDriver) {
    init {
        if (driver.currentUrl != EXPECTED_PAGE_URL)
            throw IllegalStateException("Expected url '$EXPECTED_PAGE_URL', but it's '${driver.currentUrl}'")
    }


    // todo actions
}