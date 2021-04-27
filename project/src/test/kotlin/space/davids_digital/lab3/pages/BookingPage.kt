package space.davids_digital.lab3.pages

import org.openqa.selenium.WebDriver

class BookingPage(driver: WebDriver): CommonPage(driver, Regex("https://secure\\.booking\\.com/book.*"))