package space.davids_digital.lab3.pages

import org.openqa.selenium.WebDriver

class HelpCenterPage(driver: WebDriver): CommonPage(driver, Regex("https://secure\\.booking\\.com/help\\.html.*"))