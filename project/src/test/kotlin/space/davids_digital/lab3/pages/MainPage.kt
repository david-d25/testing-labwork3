package space.davids_digital.lab3.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import space.davids_digital.lab3.hasElement

private const val EXPECTED_PAGE_URL = "https://www.booking.com/"

private val ACCEPT_COOKIES_BUTTON = By.xpath("//button[@id='onetrust-accept-btn-handler']")
private val SEARCH_INPUT = By.xpath("//input[@name='ss']")
private val SEARCH_BUTTON = By.xpath("//form[@id='frm']//button[@type='submit']")
private val CALENDAR_DAY1 = By.xpath("//div[@class='bui-calendar']//div[@class='bui-calendar__wrapper'][2]//td[@class='bui-calendar__date'][./span/span/child::text() = 1]")
private val CALENDAR_DAY2 = By.xpath("//div[@class='bui-calendar']//div[@class='bui-calendar__wrapper'][2]//td[@class='bui-calendar__date'][./span/span/child::text() = 7]")
private val CALENDAR_BUTTON = By.xpath("//div[@class='xp__dates-inner']")

class MainPage(private val driver: WebDriver) {
    init {
        if (driver.currentUrl != EXPECTED_PAGE_URL)
            throw IllegalStateException("Expected url '$EXPECTED_PAGE_URL', but it's '${driver.currentUrl}'")
    }

    fun closeCookiesPromptIfShowed() {
        if (driver.hasElement(ACCEPT_COOKIES_BUTTON))
            driver.findElement(ACCEPT_COOKIES_BUTTON).click()
    }

    fun typeIntoSearchBox(text: String) {
        driver.findElement(SEARCH_INPUT).sendKeys(text)
    }

    fun clickSearchButton() {
        driver.findElement(SEARCH_BUTTON).click()
    }

    fun pickSearchDates() {
        driver.findElement(CALENDAR_BUTTON).click()
        driver.findElement(CALENDAR_DAY1).click()
        driver.findElement(CALENDAR_DAY2).click()
    }
}