package space.davids_digital.lab3.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import space.davids_digital.lab3.hasElement

private val SAVED_ELEMENT_TITLE = By.xpath("//div[contains(@class, 'listview-hotel-pob')]//a[@class='js-listview-book js-listview-hotel-title']")
private val DELETE_BUTTON = By.xpath("//div[contains(@class, 'listview-hotel-pob')]//a[@class='js-remove-hotel listview__remove_hotel_icon']")

class FavouritesPage(private val driver: WebDriver): CommonPage(driver, Regex("https://www\\.booking\\.com/mywishlist.*")) {
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
        while (driver.hasElement(DELETE_BUTTON))
            driver.findElement(DELETE_BUTTON).click()
    }

}