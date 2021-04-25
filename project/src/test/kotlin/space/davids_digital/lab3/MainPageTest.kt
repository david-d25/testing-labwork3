package space.davids_digital.lab3

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.params.ParameterizedTest
import org.openqa.selenium.WebDriver

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
        // todo
    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun `currency changing`(driver: WebDriver) {
        this.driver = driver
        // todo
    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun `guests filter setting`(driver: WebDriver) {
        this.driver = driver
        // todo test adults/children/rooms buttons and label
    }

    @AfterEach
    fun tearDown() {
        driver.quit()
    }
}