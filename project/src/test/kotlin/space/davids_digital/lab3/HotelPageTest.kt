package space.davids_digital.lab3

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_METHOD
import org.junit.jupiter.params.ParameterizedTest
import org.openqa.selenium.WebDriver
import space.davids_digital.lab3.pages.BookingPage
import space.davids_digital.lab3.pages.HotelPage
import space.davids_digital.lab3.pages.MainPage
import space.davids_digital.lab3.pages.SearchResultsPage

@TestInstance(PER_METHOD)
class HotelPageTest {

    private lateinit var driver: WebDriver

    /**
     * Мы отправляемся за бутылкой изысканного Château Haut-Batailley из региона Бордо, Франция,
     * а заодно тестируем, насколько легко на booking.com оценить отель по достоинству (поставить
     * "Like", что в переводе с английского означает "Нравится")
     */
    @ParameterizedTest
    @ProvideWebDrivers
    fun `go to France and test like button`(driver: WebDriver) {
        this.driver = driver

        driver.get("https://booking.com")
        with (MainPage(driver)) {
            typeIntoSearchBox("Bordeaux")
            pickSearchDates()
            clickSearchButton()
        }
        with (SearchResultsPage(driver)) {
            assertTrue(getSearchResults().isNotEmpty())
            getSearchResults().first().go()
        }
        assertEquals(2, driver.windowHandles.size)
        driver.switchTo().window(driver.windowHandles.toList()[1])
        with (HotelPage(driver)) {
            assertFalse(isLiked())
            clickLike()
            assertTrue(isLiked())
            driver.navigate().refresh()
            assertTrue(isLiked()) // Like saved after page reload
            clickLike()
            assertFalse(isLiked())
            driver.navigate().refresh()
            assertFalse(isLiked())
        }
    }

    /**
     * Наше путешествие прокладывается через Никарагуа, где мы останемся на день, чтобы
     * понаблюдать за природой этой республики и найти там красивого Бурого момота.
     * Заодно протестируем кнопку "Поделиться".
     */
    @ParameterizedTest
    @ProvideWebDrivers
    fun `to go Nicaragua and test share button`(driver: WebDriver) {
        this.driver = driver

        driver.get("https://booking.com")
        with (MainPage(driver)) {
            typeIntoSearchBox("Nicaragua")
            pickSearchDates()
            clickSearchButton()
        }
        with (SearchResultsPage(driver)) {
            assertTrue(getSearchResults().isNotEmpty())
            getSearchResults().first().go()
        }
        assertEquals(2, driver.windowHandles.size)
        driver.switchTo().window(driver.windowHandles.toList()[1])
        with (HotelPage(driver)) {
            clickShare()
            assertTrue(isSharePopupOpened())
            clickShareOnFacebook()
            assertEquals(3, driver.windowHandles.size) // Opened in new window, so there are 3 windows opened
            driver.switchTo().window(driver.windowHandles.toList()[2])
            assertTrue(driver.currentUrl.startsWith("https://www.facebook.com/"))
            driver.close()
            driver.switchTo().window(driver.windowHandles.toList()[1])

            clickShare()
            clickShareOnTwitter()
            assertEquals(3, driver.windowHandles.size)
            driver.switchTo().window(driver.windowHandles.toList()[2])
            assertTrue(driver.currentUrl.startsWith("https://twitter.com/"))
            driver.close()
        }
    }

    /**
     * Насмотревшись японской мультипликации, мы решили отправиться в Японию,
     * чтобы своими глазами увидеть ту самую сакуру и узнать, действительно
     * ли там есть отдельные вагоны для женщин.
     */
    @ParameterizedTest
    @ProvideWebDrivers
    fun `go to Tokyo, see sakura and test hotel booking`(driver: WebDriver) {
        this.driver = driver

        driver.get("https://booking.com")
        with (MainPage(driver)) {
            typeIntoSearchBox("Tokyo")
            pickSearchDates()
            clickSearchButton()
        }
        with (SearchResultsPage(driver)) {
            assertTrue(getSearchResults().isNotEmpty())
            getSearchResults().first().go()
        }
        assertEquals(2, driver.windowHandles.size)
        driver.switchTo().window(driver.windowHandles.toList()[1])
        with (HotelPage(driver)) {
            assertTrue(getRoomSelectorsCount() > 0)
            assertEquals(0, getRoomSelectorValue(0))
            clickTitleAsideReserveButton()
            waitForSelectorValue(0, 1)
            assertEquals(1, getRoomSelectorValue(0))

            for (i in 0 until getRoomSelectorsCount())
                setRoomSelectorValue(i, getRoomSelectorMaxValue(i))

            clickReserveButton()

            assertDoesNotThrow { BookingPage(driver) } // Gone to the right page
        }
    }

    @AfterEach
    fun tearDown() {
        driver.quit()
    }
}