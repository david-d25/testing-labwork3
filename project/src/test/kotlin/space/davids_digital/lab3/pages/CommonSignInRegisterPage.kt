package space.davids_digital.lab3.pages

import org.openqa.selenium.By
import org.openqa.selenium.ElementNotInteractableException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

private val FACEBOOK_BUTTON = By.xpath("//div[@class='access-panel__social-buttons']/a[@data-ga-label='facebook']")
private val GOOGLE_BUTTON = By.xpath("//div[@class='access-panel__social-buttons']/a[@data-ga-label='google']")
private val APPLE_BUTTON = By.xpath("//div[@class='access-panel__social-buttons']/a[@data-ga-label='apple']")
private val EMAIL_INPUT = By.xpath("//input[@type='email']")
private val CONTINUE_BUTTON = By.xpath("//button[@type='submit']")
private val REGISTRATION_PASSWORD_INPUT = By.xpath("//input[@name='new_password']")
private val REGISTRATION_PASSWORD_CONFIRMATION_INPUT = By.xpath("//input[@name='confirmed_password']")
private val CREATE_ACCOUNT_BUTTON = By.xpath("//button[@type='submit']")
private val SIGN_IN_PASSWORD_INPUT = By.xpath("//input[@name='password']")
private val SIGN_IN_BUTTON = By.xpath("//button[@type='submit']")

class CommonSignInRegisterPage(private val driver: WebDriver): CommonPage(driver, Regex("https://account\\.booking\\.com.*")) {
    fun clickFacebookButton() = driver.findElement(FACEBOOK_BUTTON).click()
    fun clickGoogleButton() = driver.findElement(GOOGLE_BUTTON).click()
    fun clickAppleButton() = driver.findElement(APPLE_BUTTON).click()
    fun enterEmail(email: String) = driver.findElement(EMAIL_INPUT).sendKeys(email)
    fun clickContinue() = driver.findElement(CONTINUE_BUTTON).click()
    fun enterRegistrationPassword(password: String) {
        driver.findElement(REGISTRATION_PASSWORD_INPUT).sendKeys(password)
        driver.findElement(REGISTRATION_PASSWORD_CONFIRMATION_INPUT).sendKeys(password)
    }
    fun clickCreateAccount() = driver.findElement(CREATE_ACCOUNT_BUTTON).click()
    fun enterLoginPassword(password: String) {
        WebDriverWait(driver, 2)
            .pollingEvery(Duration.ofMillis(100))
            .ignoring(ElementNotInteractableException::class.java)
            .until { driver.findElement(SIGN_IN_PASSWORD_INPUT).sendKeys(password) }
    }
    fun clickSignIn() = driver.findElement(SIGN_IN_BUTTON).click()
}