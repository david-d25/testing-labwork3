package space.davids_digital.lab3

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_METHOD
import org.junit.jupiter.api.fail
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxBinary
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import space.davids_digital.lab3.pages.AnonymousLandingPage
import java.io.File
import java.util.*
import java.util.stream.Stream


@TestInstance(PER_METHOD)
class GoogleDriveTest {
    companion object {
        private const val SETTINGS_FILE = "/settings.properties"
        lateinit var props: Properties

        @BeforeAll
        @JvmStatic
        fun initEverything() {
            props = Properties()
            try {
                props.load(this::class.java.getResourceAsStream(SETTINGS_FILE))
            } catch (e: NullPointerException) {
                fail("Settings file '$SETTINGS_FILE' not found")
            }
            System.setProperty("webdriver.gecko.driver", props.getProperty("browsers.firefox.driver"))
            System.setProperty("webdriver.chrome.driver", props.getProperty("browsers.chrome.driver"))
        }

        @JvmStatic
        fun provideWebDrivers(): Stream<WebDriver> {
            // todo make configurable
            return Stream.of(
                makeFirefoxDriver(),
                makeChromeDriver()
            )
        }

        private fun makeChromeDriver(): ChromeDriver {
            val options = ChromeOptions()
            options.setBinary(props.getProperty("browsers.chrome.binary"))
            return ChromeDriver(options)
        }

        private fun makeFirefoxDriver(): FirefoxDriver {
            val options = FirefoxOptions()
            options.binary = FirefoxBinary(File(props.getProperty("browsers.firefox.binary")))
            return FirefoxDriver(options)
        }
    }

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun dummyTest(driver: WebDriver) {
        driver.get("https://drive.google.com")
        val page = AnonymousLandingPage(driver)
        Thread.sleep(8000)
        driver.quit()
    }

//    @ParameterizedTest
//    @MethodSource("provideWebDrivers")
//    fun dummyTest2(driver: WebDriver) {
//        driver.get("https://ya.ru")
//        Thread.sleep(10000)
//        driver.quit()
//    }
}