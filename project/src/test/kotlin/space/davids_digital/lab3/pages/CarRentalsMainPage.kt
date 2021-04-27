package space.davids_digital.lab3.pages

import org.openqa.selenium.WebDriver

class CarRentalsMainPage(private val driver: WebDriver): CommonPage(driver, Regex("https://www\\.booking\\.com/cars.*"))