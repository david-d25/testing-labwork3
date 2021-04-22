package space.davids_digital.lab3

import org.junit.jupiter.api.fail
import org.junit.jupiter.params.provider.MethodSource
import org.openqa.selenium.By
import org.openqa.selenium.Cookie
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxBinary
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxDriverLogLevel
import org.openqa.selenium.firefox.FirefoxOptions
import java.io.File
import java.time.Instant
import java.util.*
import java.util.stream.Stream

fun WebDriver.hasElement(by: By) = this.findElements(by).size > 0

const val DRIVERS_PROVIDER_NAME = "space.davids_digital.lab3.WebDriverHelpers#provideWebDrivers"

@MethodSource(DRIVERS_PROVIDER_NAME)
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ProvideWebDrivers

class WebDriverHelpers {
    companion object {
        private const val SETTINGS_FILE_PATH = "/settings.properties"

        var props: Properties = Properties()

        init {
            try {
                props.load(this::class.java.getResourceAsStream(SETTINGS_FILE_PATH))
            } catch (e: NullPointerException) {
                fail("Settings file '$SETTINGS_FILE_PATH' not found")
            }
            System.setProperty("webdriver.gecko.driver", props.getProperty("browsers.firefox.driver"))
            System.setProperty("webdriver.chrome.driver", props.getProperty("browsers.chrome.driver"))
        }

        @JvmStatic
        @Suppress("unused") // Used in @MethodSource-annotated testing functions
        fun provideWebDrivers(): Stream<WebDriver> {
            val browsers = props.getProperty("bot.use_browsers").split(",").map { it.strip() }
            val builder = Stream.builder<WebDriver>()
            if (browsers.contains("chrome"))
                builder.add(makeChromeDriver())
            if (browsers.contains("firefox"))
                builder.add(makeFirefoxDriver())
            return builder.build()
        }

        private fun makeChromeDriver(): ChromeDriver {
            val options = ChromeOptions()
            options.setBinary(props.getProperty("browsers.chrome.binary"))
            return addCookiesPromptCookie(ChromeDriver(options))
        }

        private fun makeFirefoxDriver(): FirefoxDriver {
            val options = FirefoxOptions()
            options.binary = FirefoxBinary(File(props.getProperty("browsers.firefox.binary")))
            options.setLogLevel(FirefoxDriverLogLevel.ERROR)
            return addCookiesPromptCookie(FirefoxDriver(options))
        }

        private fun <T : WebDriver> addCookiesPromptCookie(driver: T): T {
            driver.get("https://booking.com")
            driver.manage().addCookie(Cookie("OptanonAlertBoxClosed", Instant.now().toString()))
            return driver
        }
    }
}