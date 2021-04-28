package space.davids_digital.lab3

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
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
            WebDriverWait(driver, 5)
                .pollingEvery(Duration.ofSeconds(1))
                .until { driver.currentUrl.startsWith("https://account.booking.com/sign-in/password") }
            enterLoginPassword("BHHHHfdjg9999")
            clickSignIn()
            // In case of anti-robot check
            WebDriverWait(driver, 600)
                .pollingEvery(Duration.ofSeconds(1))
                .until { !driver.currentUrl.startsWith("https://account.booking.com/sign-in/password") }
        }

        with(mainPage) {
            typeIntoSearchBox("Tokyo")
            pickSearchDates()
            clickSearchButton()
        }

        val resultsPage = SearchResultsPage(driver)
        val expected = resultsPage.addToFavourite(1)

        WebDriverWait(driver, 10).until { ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='fly-dropdown wl-dropdown dmw-bui-wl-dropdown fly-dropdown_bottom fly-dropdown_arrow-right']")) }

        Assertions.assertTrue(resultsPage.isAddedToFavorites())


//        resultsPage.showFavourites()
//
//        val favouritesPage = FavouritesPage(driver)
//
//        Thread.sleep(2000)
//        Assertions.assertTrue(favouritesPage.isSavedElementPresented(expected))
//        favouritesPage.deleteFavourites()
//        driver.navigate().refresh()
//        Assertions.assertFalse(favouritesPage.isSavedElementPresented(expected))
    }

    @AfterEach
    fun tearDown() {
        driver.quit()
    }
}