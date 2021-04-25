package space.davids_digital.lab3.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.Select
import java.text.MessageFormat

private const val EXPECTED_PAGE_URL_PREFIX = "https://www.booking.com/index"

private val SEARCH_INPUT = By.xpath("//input[@name='ss']")
private val SEARCH_BUTTON = By.xpath("//form[@id='frm']//button[@type='submit']")
private val CALENDAR_DAY1 = By.xpath("//div[@class='bui-calendar']//div[@class='bui-calendar__wrapper'][2]//td[@class='bui-calendar__date'][./span/span/child::text() = 1]")
private val CALENDAR_DAY2 = By.xpath("//div[@class='bui-calendar']//div[@class='bui-calendar__wrapper'][2]//td[@class='bui-calendar__date'][./span/span/child::text() = 7]")
private val CALENDAR_BUTTON = By.xpath("//div[@class='xp__dates-inner']")
private val GUESTS_TOGGLE = By.xpath("//*[@id='xp__guests__toggle']")
private val ADULTS_NUMBER_LABEL = By.xpath("//*[@id='xp__guests__inputs-container']//*[contains(@class, 'sb-group__field-adults')]//*[@data-bui-ref='input-stepper-value']")
private val ADULTS_ADD_BUTTON = By.xpath("//*[@id='xp__guests__inputs-container']//*[contains(@class, 'sb-group__field-adults')]//*[@data-bui-ref='input-stepper-add-button']")
private val ADULTS_SUBTRACT_BUTTON = By.xpath("//*[@id='xp__guests__inputs-container']//*[contains(@class, 'sb-group__field-adults')]//*[@data-bui-ref='input-stepper-subtract-button']")
private val CHILDREN_NUMBER_LABEL = By.xpath("//*[@id='xp__guests__inputs-container']//*[contains(@class, 'sb-group-children')]//*[@data-bui-ref='input-stepper-value']")
private val CHILDREN_ADD_BUTTON = By.xpath("//*[@id='xp__guests__inputs-container']//*[contains(@class, 'sb-group-children')]//*[@data-bui-ref='input-stepper-add-button']")
private val CHILDREN_SUBTRACT_BUTTON = By.xpath("//*[@id='xp__guests__inputs-container']//*[contains(@class, 'sb-group-children')]//*[@data-bui-ref='input-stepper-subtract-button']")
private val ROOMS_NUMBER_LABEL = By.xpath("//*[@id='xp__guests__inputs-container']//*[contains(@class, 'sb-group__field-rooms')]//*[@data-bui-ref='input-stepper-value']")
private val ROOMS_ADD_BUTTON = By.xpath("//*[@id='xp__guests__inputs-container']//*[contains(@class, 'sb-group__field-rooms')]//*[@data-bui-ref='input-stepper-add-button']")
private val ROOMS_SUBTRACT_BUTTON = By.xpath("//*[@id='xp__guests__inputs-container']//*[contains(@class, 'sb-group__field-rooms')]//*[@data-bui-ref='input-stepper-subtract-button']")

private val CHILD_AGE_SELECT_PATTERN = MessageFormat("//*[@id=''xp__guests__inputs-container'']//*[contains(@class, ''sb-group__children__field'')]//select[@name=''age'' and @data-group-child-age=''{0}'']")

class MainPage(private val driver: WebDriver) {
    init {
        if (!driver.currentUrl.startsWith(EXPECTED_PAGE_URL_PREFIX))
            throw IllegalStateException("Expected url '$EXPECTED_PAGE_URL_PREFIX', but it's '${driver.currentUrl}'")
    }

    fun typeIntoSearchBox(text: String) {
        driver.findElement(SEARCH_INPUT).sendKeys(text)
    }

    fun clickSearchButton() {
        driver.findElement(SEARCH_BUTTON).click()
    }

    fun pickSearchDates() {
        driver.findElement(CALENDAR_BUTTON).click()
        driver.findElement(CALENDAR_DAY1).click()
        driver.findElement(CALENDAR_DAY2).click()
    }

    fun getAdultsNumber() = driver.findElement(ADULTS_NUMBER_LABEL).text.toInt()
    fun getChildrenNumber() = driver.findElement(CHILDREN_NUMBER_LABEL).text.toInt()
    fun getRoomsNumber() = driver.findElement(ROOMS_NUMBER_LABEL).text.toInt()

    fun setAdultsNum(value: Int) {
        if (!isGuestsToggleExpanded())
            toggleGuestsToggle()
        val diff = value - getAdultsNumber()
        if (diff < 0)
            repeat(-diff) { decrementAdultsNumber() }
        else
            repeat(diff) { incrementAdultsNumber() }
    }

    fun setChildrenNum(value: Int) {
        if (!isGuestsToggleExpanded())
            toggleGuestsToggle()
        val diff = value - getChildrenNumber()
        if (diff < 0)
            repeat(-diff) { decrementChildrenNumber() }
        else
            repeat(diff) { incrementChildrenNumber() }
    }

    fun setRoomsNum(value: Int) {
        if (!isGuestsToggleExpanded())
            toggleGuestsToggle()
        val diff = value - getRoomsNumber()
        if (diff < 0)
            repeat(-diff) { decrementRoomsNumber() }
        else
            repeat(diff) { incrementRoomsNumber() }
    }

    fun setChildAge(index: Int, value: Int) {
        if (!isGuestsToggleExpanded())
            toggleGuestsToggle()
        val select = Select(driver.findElement(By.xpath(CHILD_AGE_SELECT_PATTERN.format(arrayOf(index)))))
        select.selectByValue(value.toString())
    }

    private fun isGuestsToggleExpanded() = driver.findElement(GUESTS_TOGGLE).getAttribute("aria-expanded").equals("true")
    private fun toggleGuestsToggle() = driver.findElement(GUESTS_TOGGLE).click()

    private fun incrementAdultsNumber() = driver.findElement(ADULTS_ADD_BUTTON).click()
    private fun incrementChildrenNumber() = driver.findElement(CHILDREN_ADD_BUTTON).click()
    private fun incrementRoomsNumber() = driver.findElement(ROOMS_ADD_BUTTON).click()
    private fun decrementAdultsNumber() = driver.findElement(ADULTS_SUBTRACT_BUTTON).click()
    private fun decrementChildrenNumber() = driver.findElement(CHILDREN_SUBTRACT_BUTTON).click()
    private fun decrementRoomsNumber() = driver.findElement(ROOMS_SUBTRACT_BUTTON).click()
}