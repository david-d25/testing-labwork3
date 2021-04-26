package space.davids_digital.lab3

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.openqa.selenium.WebDriver
import space.davids_digital.lab3.pages.CommonSignInRegisterPage
import space.davids_digital.lab3.pages.MainPage

private const val MIN_ADULTS_NUM = 1
private const val MAX_ADULTS_NUM = 30
private const val MIN_CHILDREN_NUM = 0
private const val MAX_CHILDREN_NUM = 10
private const val MIN_ROOMS_NUM = 0
private const val MAX_ROOMS_NUM = 10

class MainPageTest {
    private lateinit var driver: WebDriver

    @ParameterizedTest
    @ProvideWebDrivers
    fun `language changing`(driver: WebDriver) {
        this.driver = driver

        with(MainPage(driver)) {
            changeLanguage("de")
            assertEquals("de", getPageLangCode())
        }
    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun `currency changing`(driver: WebDriver) {
        this.driver = driver

        with(MainPage(driver)) {
            changeCurrency("BGN")
            assertEquals("BGN", getCurrentCurrency())
        }
    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun `guests filter setting`(driver: WebDriver) {
        this.driver = driver

        // todo test adults/children/rooms buttons and label
    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun `going to register`(driver: WebDriver) {
        this.driver = driver

        MainPage(driver).clickHeaderRegisterButton()
        assertDoesNotThrow { CommonSignInRegisterPage(driver) } // Gone to the right page
    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun `going to sign in`(driver: WebDriver) {
        this.driver = driver

        MainPage(driver).clickHeaderSignInButton()
        assertDoesNotThrow { CommonSignInRegisterPage(driver) } // Gone to the right page
    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun `going to customer service`(driver: WebDriver) {
        this.driver = driver

        // todo
    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun `going to property listing`(driver: WebDriver) {
        this.driver = driver

        // todo
    }

    @AfterEach
    fun tearDown() {
        driver.quit()
    }
}