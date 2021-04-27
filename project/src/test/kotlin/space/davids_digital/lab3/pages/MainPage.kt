package space.davids_digital.lab3.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.Select
import space.davids_digital.lab3.hasElement
import java.text.MessageFormat

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
private val LANGUAGE_BUTTON = By.xpath("//button[@data-modal-id='language-selection']")
private val LANGUAGE_OPTIONS = By.xpath("//a[contains(@class, 'bui-list-item')]")
private val CURRENCY_BUTTON = By.xpath("//header//button[@data-modal-header-async-type='currencyDesktop']")
private val CURRENCY_BUTTON_CURRENCY_CODE = By.xpath("//header//button[@data-modal-header-async-type='currencyDesktop']/span[@class='bui-button__text']/span[1]")
private val HEADER_REGISTER_SIGN_IN_BUTTONS = By.xpath("//header//a[contains(@class, 'js-header-login-link')]")
private val HEADER_CUSTOMER_SERVICE_BUTTON = By.xpath("//header//a[contains(@class, 'bui-button')][.//*[contains(@class, '-streamline-question_mark_circle')]]")
private val HEADER_LIST_PROPERTY_BUTTON = By.xpath("//div[@class='bui-group__item' and .//*[contains(@class, '-streamline-property_add')]]/a")
private val HEADER_ACCOUNT_DROPDOWN = By.xpath("//nav//div[@class='bui-group__item']/div[contains(@class, 'bui-dropdown')]")

private val CURRENCIES_PATTERN = MessageFormat("//div[@role=''dialog'']//a[normalize-space(.//div[@class=''bui-traveller-header__currency'']/text()) = ''{0}'']")
private val CHILD_AGE_SELECT_PATTERN = MessageFormat("//*[@id=''xp__guests__inputs-container'']//*[contains(@class, ''sb-group__children__field'')]//select[@name=''age'' and @data-group-child-age=''{0}'']")

class MainPage(private val driver: WebDriver): CommonPage(driver, Regex("https://www\\.booking\\.com.*")) {
    fun typeIntoSearchBox(text: String) {
        driver.findElement(SEARCH_INPUT).clear()
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

    fun setAdultsNumber(value: Int) {
        if (!isGuestsToggleExpanded())
            toggleGuestsToggle()
        val diff = value - getAdultsNumber()
        if (diff < 0)
            repeat(-diff) { decrementAdultsNumber() }
        else
            repeat(diff) { incrementAdultsNumber() }
    }

    fun setChildrenNumber(value: Int) {
        if (!isGuestsToggleExpanded())
            toggleGuestsToggle()
        val diff = value - getChildrenNumber()
        if (diff < 0)
            repeat(-diff) { decrementChildrenNumber() }
        else
            repeat(diff) { incrementChildrenNumber() }
    }

    fun setRoomsNumber(value: Int) {
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

    fun changeCurrency(currencyCode: String) {
        driver.findElement(CURRENCY_BUTTON).click()
        driver.findElement(By.xpath(CURRENCIES_PATTERN.format(arrayOf(currencyCode)))).click()
    }

    fun getCurrentCurrency() = driver.findElement(CURRENCY_BUTTON_CURRENCY_CODE).text.trim() // May have newlines

    fun changeLanguage(langCode: String) {
        driver.findElement(LANGUAGE_BUTTON).click()
        driver.findElements(LANGUAGE_OPTIONS).find { it.getAttribute("data-lang") == langCode }!!.click()
    }

    fun getPageLangCode() = driver.findElement(By.tagName("html")).getAttribute("lang")!!

    fun clickHeaderRegisterButton() = driver.findElements(HEADER_REGISTER_SIGN_IN_BUTTONS)[0].click()
    fun clickHeaderSignInButton() = driver.findElements(HEADER_REGISTER_SIGN_IN_BUTTONS)[1].click()
    fun clickCustomerServiceButton() = driver.findElement(HEADER_CUSTOMER_SERVICE_BUTTON).click()
    fun clickListPropertyButton() = driver.findElement(HEADER_LIST_PROPERTY_BUTTON).click()

    fun isUserLoggedIn() = driver.hasElement(HEADER_ACCOUNT_DROPDOWN)
}