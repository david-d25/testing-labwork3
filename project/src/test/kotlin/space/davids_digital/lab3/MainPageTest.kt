package space.davids_digital.lab3

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.openqa.selenium.WebDriver
import space.davids_digital.lab3.pages.CommonSignInRegisterPage
import space.davids_digital.lab3.pages.HelpCenterPage
import space.davids_digital.lab3.pages.JoinLandingPage
import space.davids_digital.lab3.pages.MainPage

private const val MIN_ADULTS_NUM = 1
private const val MAX_ADULTS_NUM = 30
private const val MIN_CHILDREN_NUM = 0
private const val MAX_CHILDREN_NUM = 10
private const val MIN_ROOMS_NUM = 1
private const val MAX_ROOMS_NUM = 30

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

        with(MainPage(driver)) {
            setChildrenNumber(4)
            assertEquals(4, getChildrenNumber())
            setAdultsNumber(5)
            assertEquals(5, getAdultsNumber())
            setRoomsNumber(6)
            assertEquals(6, getRoomsNumber())

            setChildrenNumber(MIN_CHILDREN_NUM - 1)
            assertEquals(MIN_CHILDREN_NUM, getChildrenNumber())
            setChildrenNumber(MAX_CHILDREN_NUM + 1)
            assertEquals(MAX_CHILDREN_NUM, getChildrenNumber())

            setAdultsNumber(MIN_ADULTS_NUM - 1)
            assertEquals(MIN_ADULTS_NUM, getAdultsNumber())
            setAdultsNumber(MAX_ADULTS_NUM + 1)
            assertEquals(MAX_ADULTS_NUM, getAdultsNumber())

            setRoomsNumber(MIN_ROOMS_NUM - 1)
            assertEquals(MIN_ROOMS_NUM, getRoomsNumber())
            setRoomsNumber(MAX_ROOMS_NUM + 1)
            assertEquals(MAX_ROOMS_NUM, getRoomsNumber())
        }
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

        MainPage(driver).clickCustomerServiceButton()
        assertDoesNotThrow { HelpCenterPage(driver) } // Gone to the right page
    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun `going to property listing`(driver: WebDriver) {
        this.driver = driver

        MainPage(driver).clickListPropertyButton()
        assertEquals(2, driver.windowHandles.size) // Opened in new tab
        driver.switchTo().window(driver.windowHandles.toList()[1])
        assertDoesNotThrow { JoinLandingPage(driver) }
    }

    @AfterEach
    fun tearDown() {
        driver.quit()
    }
}