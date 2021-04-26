package space.davids_digital.lab3.pages

import org.openqa.selenium.WebDriver

class CommonSignInRegisterPage(driver: WebDriver): CommonPage(driver, Regex("https://account\\.booking\\.com.*"))