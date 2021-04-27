package space.davids_digital.lab3

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_METHOD
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.openqa.selenium.WebDriver
import space.davids_digital.lab3.pages.HotelPage
import space.davids_digital.lab3.pages.MainPage
import space.davids_digital.lab3.pages.SearchResultsPage

@TestInstance(PER_METHOD)
class SearchPageTest {

    private lateinit var driver: WebDriver

    @ParameterizedTest
    @ProvideWebDrivers
    fun `search with filters`(driver: WebDriver) {
        this.driver = driver

        driver.get("https://booking.com")
        with(MainPage(driver)) {
            typeIntoSearchBox("Massachusetts")
            pickSearchDates()
            setAdultsNumber(5)
            setChildrenNumber(3)
            setChildAge(0, 10)
            setChildAge(1, 8)
            setChildAge(2, 12)
            setRoomsNumber(2)
            clickSearchButton()
        }

        val resultsPage = SearchResultsPage(driver)

        // Checking that search options applied correctly
        assertTrue(resultsPage.isAsideFormPresent())
        assertEquals("Massachusetts", resultsPage.getAsideFilterDestination())
        assertEquals(5, resultsPage.getAsideFilterAdultsNumber())
        assertEquals(3, resultsPage.getAsideFilterChildrenNumber())
        assertEquals(2, resultsPage.getAsideFilterRoomsNumber())

        // Children ages may change order, so it's needed to sort them before checking
        val ages = listOf(
            resultsPage.getAsideFilterChildAge(0),
            resultsPage.getAsideFilterChildAge(1),
            resultsPage.getAsideFilterChildAge(2)
        ).sorted()
        assertEquals(listOf(8, 10, 12), ages)

        assertTrue(resultsPage.isFilterBoxContainerPresent())

        // As filter elements re-build on each filter update, getFilterBoxes must be invoked instead of storing its result
        // As filters number may change on filter update, it's required to use 'while' loop
        while (resultsPage.getUnusedFilterBoxes().isNotEmpty()) {
            // Always choose the most popular option so there will be more results
            resultsPage.expandAllFilters()
            resultsPage.getUnusedFilterBoxes()[0].getOptions()
                .maxByOrNull { it.getFilterCount() }
                .let { it?.toggleSelection() }
        }

        with(resultsPage) {
            setAsideFilterDestination("Ivanovo")
            selectAsideFilterAdultsNumber(2)
            selectAsideFilterChildrenNumber(4)
            selectAsideFilterRoomsNumber(1)
            selectAsideFilterChildAge(0, 5)
            selectAsideFilterChildAge(1, 6)
            selectAsideFilterChildAge(2, 7)
            selectAsideFilterChildAge(3, 8)
            clickTravellingForWorkCheckbox()
            clickAsideFilterSearchButton()
        }

        // Checking that aside form changes applied correctly
        assertTrue(resultsPage.isAsideFormPresent())
        assertEquals("Ivanovo", resultsPage.getAsideFilterDestination())
        assertEquals(2, resultsPage.getAsideFilterAdultsNumber())
        assertEquals(4, resultsPage.getAsideFilterChildrenNumber())
        assertEquals(1, resultsPage.getAsideFilterRoomsNumber())
        assertEquals(5, resultsPage.getAsideFilterChildAge(0))
        assertEquals(6, resultsPage.getAsideFilterChildAge(1))
        assertEquals(7, resultsPage.getAsideFilterChildAge(2))
        assertEquals(8, resultsPage.getAsideFilterChildAge(3))
        assertTrue(resultsPage.isFilterBoxContainerPresent())

        resultsPage.clickMenubarItem(2)
        assertTrue(resultsPage.getSearchResults().isNotEmpty())
        resultsPage.getSearchResults()[0].go()
        assertEquals(2, driver.windowHandles.size) // Opened in new tab
        driver.switchTo().window(driver.windowHandles.toList()[1]) // Switching to new tab
        assertDoesNotThrow { HotelPage(driver) } // Gone to right page
    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun `going to result page without setting any filters`(driver: WebDriver) {
        this.driver = driver

        driver.get("https://booking.com")

        with (MainPage(driver)) {
            typeIntoSearchBox("Buzuluk")
            pickSearchDates()
            clickSearchButton()
        }
        with (SearchResultsPage(driver)) {
            assertTrue(getSearchResults().isNotEmpty()) // There should be at least one hotel in Buzuluk city
            getSearchResults()[0].go()
        }
        assertEquals(2, driver.windowHandles.size) // Opened in new tab
        driver.switchTo().window(driver.windowHandles.toList()[1])
        assertDoesNotThrow { HotelPage(driver) } // Gone to the right url
        // todo test title
    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun `show on map buttons`(driver: WebDriver) {
        this.driver = driver

        driver.get("https://booking.com")

        with (MainPage(driver)) {
            typeIntoSearchBox("Podolsk")
            pickSearchDates()
            clickSearchButton()
        }
        with (SearchResultsPage(driver)) {
            assertEquals(2, getShowOnMapElementsCount())
            clickShowOnMap(0)
            assertTrue(isMapShowed())
            closeMap()
            assertFalse(isMapShowed())
            clickShowOnMap(1)
            assertTrue(isMapShowed())
            closeMap()
            assertFalse(isMapShowed())
        }
    }

    @AfterEach
    fun tearDown() {
        driver.quit()
    }
}