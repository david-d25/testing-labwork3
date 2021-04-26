package space.davids_digital.lab3.pages

import org.openqa.selenium.*
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait
import space.davids_digital.lab3.hasElement
import java.text.MessageFormat
import java.time.Duration

private val SEARCH_RESULTS = By.xpath("//*[@data-hotelid]")
private val SEARCH_RESULT_TITLE_LINK = By.xpath(".//h3/a")
private val FILTER_BOX_CONTAINER = By.xpath("//*[@id='filterbox_options']")
private val FILTER_BOXES_UNUSED = By.xpath("//*[@id='filterbox_options']/*[@data-block-id='filter_options']/*[contains(concat(' ', normalize-space(@class), ' '), ' filterbox ') and not(contains(@class, 'g-hidden')) and not(contains(@class, 'filterbox_hide-in-list')) and not(contains(@style, 'display: none;'))][.//div[contains(@class, 'filteroptions') and not(contains(@class, 'filter_selected')) and count(.//a[contains(@class, 'active')])=0]]")
private val FILTER_BOX_OPTIONS = By.xpath("./*[contains(@class, 'filteroptions')]/a[not(contains(@class, 'collapsed_partly'))]")
private val FILTER_BOX_OPTION_FILTER_COUNT = By.xpath(".//*[@class='filter_count']")
private val FILTER_BOX_SHOW_MORE_BUTTON = By.xpath("//*[@data-block-id='filter_options']/*[contains(concat(' ', normalize-space(@class), ' '), ' filterbox_limited ') and not(contains(@class, 'g-hidden')) and not(contains(@class, 'filterbox_hide-in-list')) and not(contains(@style, 'display: none;'))]//button[contains(@class, 'collapsed_partly_more')]")
private val FILTER_UPDATE_POPUP_CONTAINER = By.xpath("//*[@class='sr-usp-overlay__container']")
private val ASIDE_FORM = By.xpath("//form[@id='frm']")
private val ASIDE_FORM_ADULTS_SELECT = By.xpath("//form[@id='frm']//select[@name='group_adults']")
private val ASIDE_FORM_CHILDREN_SELECT = By.xpath("//form[@id='frm']//select[@name='group_children']")
private val ASIDE_FORM_ROOMS_SELECT = By.xpath("//form[@id='frm']//select[@name='no_rooms']")
private val ASIDE_FORM_TRAVELLING_FOR_WORK_CHECKBOX = By.xpath("//form[@id='frm']//label[.//*[@name='sb_travel_purpose']]")
private val ASIDE_FORM_SEARCH = By.xpath("//form[@id='frm']//button[@type='submit']")
private val ASIDE_FORM_DESTINATION = By.xpath("//form[@id='frm']//input[@name='ss']")
private val ASIDE_FORM_DESTINATION_AUTOCOMPLETE_VISIBLE = By.xpath("//form[@id='frm']//ul[@role='listbox' and contains(@class, 'sb-autocomplete__list') and contains(@class, '-visible')]")
private val ASIDE_FORM_DESTINATION_AUTOCOMPLETE_ITEM = By.xpath("//form[@id='frm']//ul[@role='listbox' and contains(@class, 'sb-autocomplete__list')]/li")
private val MENUBAR_ITEM = By.xpath("//ul[@role='menubar']/li/a")

private val ASIDE_FORM_CHILD_AGE_PATTERN = MessageFormat("//form[@id=''frm'']//select[@name=''age'' and @data-group-child-age=''{0}'']")

class SearchResultsPage(private val driver: WebDriver): CommonPage(driver, Regex("https://www\\.booking\\.com/searchresults.*")) {

    fun isAsideFormPresent() = driver.hasElement(ASIDE_FORM)
    fun isFilterBoxContainerPresent() = driver.hasElement(FILTER_BOX_CONTAINER)

    /**
     * Wait for popup to appear and then disappear
     */
    fun waitForFiltersToUpdate() {
        try {
            // Trying to wait for popup to show up
            WebDriverWait(driver, 2)
                .pollingEvery(Duration.ofMillis(100))
                .until { driver.hasElement(FILTER_UPDATE_POPUP_CONTAINER) }
        } catch (ignored: TimeoutException) {
            // Popup did not show or hidden already
        }
        WebDriverWait(driver, 10)
            .pollingEvery(Duration.ofMillis(100))
            .until { !driver.hasElement(FILTER_UPDATE_POPUP_CONTAINER) }
    }

    fun getAsideFilterDestination()
            = driver.findElement(ASIDE_FORM_DESTINATION).getAttribute("value")!!
    fun getAsideFilterAdultsNumber()
            = Select(driver.findElement(ASIDE_FORM_ADULTS_SELECT)).firstSelectedOption.getAttribute("value").toInt()
    fun getAsideFilterChildrenNumber()
            = Select(driver.findElement(ASIDE_FORM_CHILDREN_SELECT)).firstSelectedOption.getAttribute("value").toInt()
    fun getAsideFilterRoomsNumber()
            = Select(driver.findElement(ASIDE_FORM_ROOMS_SELECT)).firstSelectedOption.getAttribute("value").toInt()
    fun getAsideFilterChildAge(index: Int)
            = Select(driver.findElement(By.xpath(ASIDE_FORM_CHILD_AGE_PATTERN.format(arrayOf(index))))).firstSelectedOption.getAttribute("value").toInt()

    fun setAsideFilterDestination(value: String) {
        driver.findElement(ASIDE_FORM_DESTINATION).clear()
        driver.findElement(ASIDE_FORM_DESTINATION).sendKeys(value)
        WebDriverWait(driver, 10).pollingEvery(Duration.ofSeconds(1)).until { driver.hasElement(ASIDE_FORM_DESTINATION_AUTOCOMPLETE_VISIBLE) }
        driver.findElements(ASIDE_FORM_DESTINATION_AUTOCOMPLETE_ITEM)[0].click()
    }
    fun selectAsideFilterAdultsNumber(number: Int)
            = Select(driver.findElement(ASIDE_FORM_ADULTS_SELECT)).selectByValue(number.toString())
    fun selectAsideFilterChildrenNumber(number: Int)
            = Select(driver.findElement(ASIDE_FORM_CHILDREN_SELECT)).selectByValue(number.toString())
    fun selectAsideFilterRoomsNumber(number: Int)
            = Select(driver.findElement(ASIDE_FORM_ROOMS_SELECT)).selectByValue(number.toString())
    fun selectAsideFilterChildAge(index: Int, value: Int)
            = Select(driver.findElement(By.xpath(ASIDE_FORM_CHILD_AGE_PATTERN.format(arrayOf(index))))).selectByValue(value.toString())

    fun clickTravellingForWorkCheckbox() {
        val el = driver.findElement(ASIDE_FORM_TRAVELLING_FOR_WORK_CHECKBOX)
        WebDriverWait(driver, 10)
            .pollingEvery(Duration.ofMillis(500))
            .until(ExpectedConditions.elementToBeClickable(el))
            .click()
    }

    fun clickAsideFilterSearchButton() = driver.findElement(ASIDE_FORM_SEARCH).click()
    fun expandAllFilters() {
        while (driver.hasElement(FILTER_BOX_SHOW_MORE_BUTTON)) {
            WebDriverWait(driver, 10)
                .pollingEvery(Duration.ofMillis(250))
                .until(ExpectedConditions.elementToBeClickable(FILTER_BOX_SHOW_MORE_BUTTON))
                .click()
        }
    }

    fun getUnusedFilterBoxes() = driver.findElements(FILTER_BOXES_UNUSED).map { FilterBox(it) }
    fun getSearchResults() = driver.findElements(SEARCH_RESULTS).map { Result(it) }

    fun clickMenubarItem(index: Int) {
        driver.findElements(MENUBAR_ITEM)[index].click()
        waitForFiltersToUpdate()
    }

    inner class Result(private val el: WebElement) {
        fun go() = el.findElement(SEARCH_RESULT_TITLE_LINK).click()
    }

    inner class FilterBox(private val el: WebElement) {
        fun getOptions(): List<Option> {
            return try {
                el.findElements(FILTER_BOX_OPTIONS).map { Option(it) }
            } catch (e: StaleElementReferenceException) {
                throw RuntimeException(
                    "The element is not attached to DOM. Please, re-invoke getFilterBoxes() ang get new references")
            }
        }

        inner class Option(private val el: WebElement) {
            fun getFilterCount(): Int {
                return if (el.findElements(FILTER_BOX_OPTION_FILTER_COUNT).size > 0) {
                    val text = el.findElement(FILTER_BOX_OPTION_FILTER_COUNT).text
                    if (text.isNotBlank()) text.toInt() else 0
                } else 0
            }

            fun toggleSelection() {
                // https://stackoverflow.com/a/62137766/8881863
                (driver as JavascriptExecutor).executeScript("arguments[0].scrollIntoView(true)", el)
                WebDriverWait(driver, 10)
                    .pollingEvery(Duration.ofMillis(500))
                    .until(ExpectedConditions.elementToBeClickable(el))
                    .click()
                waitForFiltersToUpdate()
            }
        }
    }
}