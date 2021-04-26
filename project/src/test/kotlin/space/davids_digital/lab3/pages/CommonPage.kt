package space.davids_digital.lab3.pages

import org.openqa.selenium.WebDriver

open class CommonPage(driver: WebDriver, expectedPageUrlRegex: Regex) {
    init {
        if (!driver.currentUrl.matches(expectedPageUrlRegex))
            throw IllegalStateException("Expected url to match regex '$expectedPageUrlRegex', but url is '${driver.currentUrl}'")
    }
}