package space.davids_digital.lab3

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_METHOD
import org.junit.jupiter.params.ParameterizedTest
import org.openqa.selenium.WebDriver
import space.davids_digital.lab3.pages.MainPage

@TestInstance(PER_METHOD)
class ChooseParametersTest {

    private lateinit var driver: WebDriver

    @ParameterizedTest
    @ProvideWebDrivers
    fun `choose currency`(driver: WebDriver) {
        this.driver = driver
        driver.get("https://booking.com")

        val mainPage = MainPage(driver)
        mainPage.chooseCurrency("BGN")
        assertEquals("BGN", mainPage.getCurrentCurrency())
    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun `choose language`(driver: WebDriver) {
        this.driver = driver
        driver.get("https://booking.com")

        val mainPage = MainPage(driver)
        mainPage.chooseLanguage("Deutsch")
        assertEquals("de", mainPage.getCurrentLanguage())
    }

    @AfterEach
    fun tearDown() {
        driver.quit()
    }
}