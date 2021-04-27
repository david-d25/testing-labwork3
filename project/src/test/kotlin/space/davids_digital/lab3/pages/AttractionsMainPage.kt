package space.davids_digital.lab3.pages

import org.openqa.selenium.WebDriver

class AttractionsMainPage(private val driver: WebDriver): CommonPage(driver, Regex("https://www\\.booking\\.com/attractions.*"))