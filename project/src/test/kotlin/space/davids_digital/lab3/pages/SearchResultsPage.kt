package space.davids_digital.lab3.pages

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import java.text.MessageFormat

private const val EXPECTED_PAGE_URL_PREFIX = "https://www.booking.com/searchresults"

private val SEARCH_RESULTS = By.xpath("//*[@data-hotelid]")
private const val RESULT_LOCATION_PATH = "//*[@data-hotelid=''{0}'']//div[@class=''sr_item_main_block'']//a[@class=''bui-link'']"
private const val RESULT_LOCATION_SHOW_ON_MAP_PATH = "//*[@data-hotelid=''{0}'']//div[@class=''sr_item_main_block'']//a[@class=''bui-link'']/span"

class SearchResultsPage(private val driver: WebDriver) {
    init {
        if (!   driver.currentUrl.startsWith(EXPECTED_PAGE_URL_PREFIX))
            throw IllegalStateException("Expected url starting with '$EXPECTED_PAGE_URL_PREFIX', but it's '${driver.currentUrl}'")
    }

    fun getSearchResults(): List<Result> {
        return driver.findElements(SEARCH_RESULTS).map { Result(driver, it) }
    }

    class Result(private val driver: WebDriver, private val el: WebElement) {
        fun getLocation(): String {
            val hotelId = el.getAttribute("data-hotelid")
            return (driver as JavascriptExecutor).executeScript("return arguments[0].childNodes[0]", el.findElement(By.xpath(MessageFormat.format(RESULT_LOCATION_PATH, hotelId)))).toString()
        }
    }
}