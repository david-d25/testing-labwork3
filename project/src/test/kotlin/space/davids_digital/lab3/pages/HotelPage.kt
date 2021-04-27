package space.davids_digital.lab3.pages

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait
import space.davids_digital.lab3.countElements
import space.davids_digital.lab3.hasElement
import java.time.Duration

private val LIKE_BUTTON = By.xpath("//button[.//*[contains(@class, '-iconset-heart')]]")
private val LIKE_BUTTON_ACTIVE = By.xpath("//button[.//*[contains(@class, '-iconset-heart')] and contains(@class, 'saved_in_wl')]")
private val SHARE_BUTTON = By.xpath("//button[contains(@class, 'hp_share_center_button')]")
private val SHARE_POPUP_VISIBLE = By.xpath("//div[@class='bui-dropdown__content' and @aria-hidden='false']/div[@id='dropdown_secondary_menu']")
private val SHARE_ON_FACEBOOK = By.xpath("//div[@id='dropdown_secondary_menu']//a[@data-channel='facebook']")
private val SHARE_ON_TWITTER = By.xpath("//div[@id='dropdown_secondary_menu']//a[@data-channel='twitter']")
private val TITLE_ASIDE_RESERVE_BUTTON = By.xpath("//button[@id='hp_book_now_button']")
private val ROOMS_SELECTORS = By.xpath("//select[contains(@class, 'hprt-nos-select ')]")
private val RESERVE_BUTTON = By.xpath("//button[contains(@class, 'js-reservation-button') and @type='submit']")
private val MORE_QUESTIONS_BUTTON = By.xpath("//div[@class='guest-questions-more-cta-wrapper']/button")
private val QUESTIONS_POPUP_VISIBLE = By.xpath("//div[@role='dialog' and contains(@class, 'sliding-panel-widget') and contains(@class, 'is-shown')]")
private val QUESTIONS_POPUP_CLOSE_BUTTON = By.xpath("//div[@role='dialog' and contains(@class, 'sliding-panel-widget') and contains(@class, 'is-shown')]//div[@class='sliding-panel-widget-close-button']")

class HotelPage(private val driver: WebDriver): CommonPage(driver, Regex("https://www\\.booking\\.com/hotel.*")) {
    fun isLiked() = driver.hasElement(LIKE_BUTTON_ACTIVE)
    fun clickLike() {
        val wasLiked = isLiked()
        driver.findElement(LIKE_BUTTON).click()
        WebDriverWait(driver, 5)
            .pollingEvery(Duration.ofMillis(250))
            .until { isLiked() xor wasLiked }
    }

    fun clickShare() = driver.findElement(SHARE_BUTTON).click()
    fun clickShareOnFacebook() = driver.findElement(SHARE_ON_FACEBOOK).click()
    fun clickShareOnTwitter() = driver.findElement(SHARE_ON_TWITTER).click()
    fun isSharePopupOpened() = driver.hasElement(SHARE_POPUP_VISIBLE)

    fun clickReserveButton() = driver.findElement(RESERVE_BUTTON).submit()
    fun clickTitleAsideReserveButton() = driver.findElement(TITLE_ASIDE_RESERVE_BUTTON).click()
    fun getRoomSelectorsCount()  = driver.countElements(ROOMS_SELECTORS)
    fun getRoomSelectorValue(index: Int) = Select(driver.findElements(ROOMS_SELECTORS)[index]).firstSelectedOption.getAttribute("value").toInt()
    fun getRoomSelectorMaxValue(index: Int) = Select(driver.findElements(ROOMS_SELECTORS)[index]).options.maxOf { it.getAttribute("value").toInt() }
    fun setRoomSelectorValue(index: Int, value: Int) = Select(driver.findElements(ROOMS_SELECTORS)[index]).selectByValue(value.toString())
    fun waitForSelectorValue(index: Int, value: Int) {
        WebDriverWait(driver, 10)
            .pollingEvery(Duration.ofMillis(500))
            .until { getRoomSelectorValue(index) == value }
    }

    fun clickMoreQuestionsButton() = driver.findElement(MORE_QUESTIONS_BUTTON).click()
    fun isQuestionsPopupOpen() = driver.hasElement(QUESTIONS_POPUP_VISIBLE)
    fun clickCloseQuestionsPopup() = driver.findElement(QUESTIONS_POPUP_CLOSE_BUTTON).click()
}