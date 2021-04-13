package space.davids_digital.lab3

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

fun WebDriver.hasElement(by: By) = this.findElements(by).size > 0