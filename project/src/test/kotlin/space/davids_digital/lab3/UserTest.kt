package space.davids_digital.lab3

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import space.davids_digital.lab3.pages.*
import java.time.Duration

class UserTest {
    private lateinit var driver: WebDriver

    @ParameterizedTest
    @ProvideWebDrivers
    fun `check favourites`(driver: WebDriver) {
        this.driver = driver
        val mainPage = MainPage(driver)
        mainPage.clickHeaderRegisterButton()
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

        with(mainPage) {
            typeIntoSearchBox("Ivanovo")
            setAdultsNumber(1)
            pickSearchDates()
            clickSearchButton()
        }

        val resultsPage = SearchResultsPage(driver)
        val expected = resultsPage.addToFavourite(5)
        resultsPage.showFavourites()

        val favouritesPage = FavouritesPage(driver)
        Assertions.assertTrue(favouritesPage.isSavedElementPresented(expected))


    }

    @AfterEach
    fun tearDown() {
        driver.quit()
    }
}