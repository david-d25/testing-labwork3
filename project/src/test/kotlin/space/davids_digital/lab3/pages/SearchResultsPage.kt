package space.davids_digital.lab3.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

private const val EXPECTED_PAGE_URL_PREFIX = "https://www.booking.com/searchresults"

private val SEARCH_RESULTS = By.xpath("//*[@data-hotelid]")

class SearchResultsPage(private val driver: WebDriver) {
    init {
        if (!   driver.currentUrl.startsWith(EXPECTED_PAGE_URL_PREFIX))
            throw IllegalStateException("Expected url starting with '$EXPECTED_PAGE_URL_PREFIX', but it's '${driver.currentUrl}'")
    }

    fun getSearchResults(): List<Result> {
        return driver.findElements(SEARCH_RESULTS).map { Result(driver, it) }
    }

    class Result(private val driver: WebDriver, private val el: WebElement) {

    }
}