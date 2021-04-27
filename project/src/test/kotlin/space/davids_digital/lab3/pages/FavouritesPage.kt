package space.davids_digital.lab3.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class FavouritesPage(private val driver: WebDriver) {
    private val SAVED_ELEMENT_TITLE = By.xpath("//div[contains(@class, 'listview-hotel-pob')]//a[@class='js-listview-book js-listview-hotel-title']")

    fun isSavedElementPresented(name: String): Boolean {
        return driver.findElement(By.xpath("//div[contains(@class, 'listview-hotel-pob')]//a[@class='js-listview-book js-listview-hotel-title' and @title='$name']")) != null
    }
}