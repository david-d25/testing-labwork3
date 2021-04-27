package space.davids_digital.lab3

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_METHOD
import org.junit.jupiter.params.ParameterizedTest
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import space.davids_digital.lab3.pages.CommonSignInRegisterPage
import space.davids_digital.lab3.pages.MainPage
import java.time.Duration

@TestInstance(PER_METHOD)
class SignInRegisterTest {

    private lateinit var driver: WebDriver

    @ParameterizedTest
    @ProvideWebDrivers
    fun `social network buttons`(driver: WebDriver) {
        this.driver = driver

        MainPage(driver).clickHeaderSignInButton()
        with (CommonSignInRegisterPage(driver)) {
            clickFacebookButton()
            assertEquals(2, driver.windowHandles.size)
            driver.switchTo().window(driver.windowHandles.toList()[1])
            assertTrue(driver.currentUrl.startsWith("https://www.facebook.com/"))

            driver.close()
            driver.switchTo().window(driver.windowHandles.toList()[0])

            clickGoogleButton()
            assertEquals(2, driver.windowHandles.size)
            driver.switchTo().window(driver.windowHandles.toList()[1])
            assertTrue(driver.currentUrl.startsWith("https://accounts.google.com/"))

            driver.close()
            driver.switchTo().window(driver.windowHandles.toList()[0])

            clickAppleButton()
            assertEquals(2, driver.windowHandles.size)
            driver.switchTo().window(driver.windowHandles.toList()[1])
            assertTrue(driver.currentUrl.startsWith("https://appleid.apple.com/"))
        }
    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun registration(driver: WebDriver) {
        this.driver = driver

        MainPage(driver).clickHeaderRegisterButton()
        with (CommonSignInRegisterPage(driver)) {
            enterEmail("test${System.currentTimeMillis()}@example.com")
            clickContinue()
            enterRegistrationPassword("JDHRggggg398842")
            clickCreateAccount()
            // In case of anti-robot check
            WebDriverWait(driver, 600)
                .pollingEvery(Duration.ofSeconds(1))
                .until { !driver.currentUrl.startsWith("https://account.booking.com/register") }
        }
        driver.get("https://booking.com")
        assertTrue(MainPage(driver).isUserLoggedIn())
    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun login(driver: WebDriver) {
        this.driver = driver

        assertFalse(MainPage(driver).isUserLoggedIn())
        MainPage(driver).clickHeaderRegisterButton()
        with (CommonSignInRegisterPage(driver)) {
            enterEmail("rhjyhwmwjmszxpxprl@niwghx.com")
            clickContinue()
            enterLoginPassword("BHHHHfdjg9999")
            clickSignIn()
            // In case of anti-robot check
            WebDriverWait(driver, 600)
                .pollingEvery(Duration.ofSeconds(1))
                .until { !driver.currentUrl.startsWith("https://account.booking.com/sign-in/password") }
        }
        assertTrue(MainPage(driver).isUserLoggedIn())
    }

    @AfterEach
    fun tearDown() {
        driver.quit()
    }
}