package space.davids_digital.lab3

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.params.ParameterizedTest
import org.openqa.selenium.WebDriver

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