package space.davids_digital.lab3.pages

import org.openqa.selenium.WebDriver

private const val EXPECTED_PAGE_URL_PREFIX = "https://www.booking.com/hodel"

class HotelPage(private val driver: WebDriver) {
    init {
        if (!driver.currentUrl.startsWith(EXPECTED_PAGE_URL_PREFIX))
            throw IllegalStateException("Expected url '$EXPECTED_PAGE_URL_PREFIX', but it's '${driver.currentUrl}'")
    }
}