package space.davids_digital.lab3.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class FavouritesPage(private val driver: WebDriver): CommonPage(driver, Regex("https://www\\.booking\\.com/mywishlist.*")) {
    private val SAVED_ELEMENT_TITLE = By.xpath("//div[contains(@class, 'listview-hotel-pob')]//a[@class='js-listview-book js-listview-hotel-title']")
    private val DELETE_BUTTON = By.xpath("//div[contains(@class, 'listview-hotel-pob')]//a[@class='js-remove-hotel listview__remove_hotel_icon']")

    fun isSavedElementPresented(name: String): Boolean {
        val find: WebElement
        return try {
            find = driver.findElement(By.xpath("//div[contains(@class, 'listview-hotel-pob')]//a[@class='js-listview-book js-listview-hotel-title' and @title='$name']"))
            find != null
        } catch (e: org.openqa.selenium.NoSuchElementException) {
            false
        }
    }

    fun deleteFavourites() {
        Thread.sleep(2000) //todo if remove, org.openqa.selenium.StaleElementReferenceException: stale element reference: element is not attached to the page document appears

        return driver.findElements(DELETE_BUTTON).forEach { it.click() }
    }

}