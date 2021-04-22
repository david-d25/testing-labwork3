package space.davids_digital.lab3

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.TestInfo
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_METHOD
import org.junit.jupiter.params.ParameterizedTest
import org.openqa.selenium.Cookie
import org.openqa.selenium.WebDriver
import space.davids_digital.lab3.pages.MainPage
import java.time.Instant

@TestInstance(PER_METHOD)
class BookingTest {

    private lateinit var driver: WebDriver

    @ParameterizedTest
    @ProvideWebDrivers
    fun `search apartment`(driver: WebDriver) {
        this.driver = driver

        driver.get("https://booking.com")
        val mainPage = MainPage(driver)
        mainPage.typeIntoSearchBox("Massachusetts")
        mainPage.pickSearchDates()
        Thread.sleep(999999)
//        mainPage.clickSearchButton()
//        val resultsPage = SearchResultsPage(driver)
    }

    @AfterEach
    fun tearDown() {
        driver.quit()
    }
}