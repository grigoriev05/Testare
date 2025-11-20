package org.example.pom;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class FormPom {

    WebDriver driver;
    JavascriptExecutor js;
    WebDriverWait wait;

    @FindBy(xpath = "//*[@id='firstName']")
    WebElement firstName;

    @FindBy(xpath = "//*[@id='lastName']")
    WebElement lastName;

    @FindBy(xpath = "//*[@id='userEmail']")
    WebElement userEmail;

    @FindBy(xpath = "//*[@id='userNumber']")
    WebElement userNumber;

    @FindBy(xpath = "//*[@id='dateOfBirthInput']")
    WebElement dateOfBirthInput;

    @FindBy(xpath = "//*[@id='subjectsInput']")
    WebElement subjectsInput;

    @FindBy(xpath = "//*[@id='state']")
    WebElement state;

    @FindBy(xpath = "//*[@id='city']")
    WebElement city;

    @FindBy(xpath = "//*[@id='submit']")
    WebElement submit;

    public FormPom(WebDriver driver) {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // ------------------ SET METHODS --------------------

    public void setFirstName(String firstNameParam) {
        firstName.clear();
        firstName.sendKeys(firstNameParam);
    }

    public void setLastName(String lastNameParam) {
        lastName.clear();
        lastName.sendKeys(lastNameParam);
    }

    public void setUserEmail(String userEmailParam) {
        userEmail.clear();
        userEmail.sendKeys(userEmailParam);
    }

    public void setUserNumber(String userNumberParam) {
        userNumber.clear();
        userNumber.sendKeys(userNumberParam);
    }

    public void setGender(String genderParam) {
        WebElement gender = driver.findElement(By.xpath("//label[text()='" + genderParam + "']"));
        js.executeScript("arguments[0].scrollIntoView(true);", gender);
        gender.click();
    }

    public void setDateOfBirth(String dateOfBirthParam) {
        dateOfBirthInput.sendKeys(Keys.CONTROL, "a");
        dateOfBirthInput.sendKeys(dateOfBirthParam);
        dateOfBirthInput.sendKeys(Keys.ENTER);
    }

    public void setSubjects(String subjectsParam) {
        subjectsInput.sendKeys(subjectsParam);
        subjectsInput.sendKeys(Keys.ENTER);
    }

    public void setHobbies(String hobbiesParam) {
        WebElement hobbies = driver.findElement(By.xpath("//label[text()='" + hobbiesParam + "']"));
        js.executeScript("arguments[0].scrollIntoView(true);", hobbies);
        hobbies.click();
    }

    public void setState(String stateParam) {
        js.executeScript("arguments[0].scrollIntoView(true);", state);
        state.click();
        WebElement option = driver.findElement(By.xpath("//div[contains(@id,'react-select-3-option') and text()='" + stateParam + "']"));
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    public void setCity(String cityParam) {
        js.executeScript("arguments[0].scrollIntoView(true);", city);
        city.click();
        WebElement option = driver.findElement(By.xpath("//div[contains(@id,'react-select-4-option') and text()='" + cityParam + "']"));
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    // ------------------ REMOVE ADVERTS --------------------

    public void closeAdvert() {
        try {
            js.executeScript(
                    "var elem = document.evaluate(\"//*[@id='adplus-anchor']\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;" +
                            "if(elem){ elem.remove(); }"
            );
        } catch (Exception ignored) {}

        try {
            js.executeScript(
                    "var elem = document.evaluate(\"//footer\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;" +
                            "if(elem){ elem.remove(); }"
            );
        } catch (Exception ignored) {}
    }

    // ------------------ SCROLL --------------------

    public void scrollToDateOfBirth() {
        scrollToElement(dateOfBirthInput);
    }

    public void scrollToElement(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    // ------------------ SUBMIT --------------------

    public void clickSubmit() {
        scrollToElement(submit);
        js.executeScript("arguments[0].click();", submit);
    }

    // ------------------ VERIFY --------------------

    public boolean verifySubmissionData(
            String firstNameParam,
            String lastNameParam,
            String emailParam,
            String genderParam,
            String mobileParam,
            String dateOfBirthParam,
            String subjectsParam,
            String hobbiesParam,
            String stateParam,
            String cityParam) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-content")));

        String fullNameExpected = firstNameParam + " " + lastNameParam;

        List<WebElement> rows = driver.findElements(By.xpath("//div[@class='modal-body']//table//tr"));

        boolean allMatch = true;

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() < 2) continue;

            String label = cells.get(0).getText().trim();
            String value = cells.get(1).getText().trim();

            switch (label) {
                case "Student Name":
                    if (!value.equals(fullNameExpected)) allMatch = false;
                    break;
                case "Student Email":
                    if (!value.equals(emailParam)) allMatch = false;
                    break;
                case "Gender":
                    if (!value.equals(genderParam)) allMatch = false;
                    break;
                case "Mobile":
                    if (!value.equals(mobileParam)) allMatch = false;
                    break;
                case "Date of Birth":
                    String normalized = value.replace(",", "").replace(" ", "").toLowerCase();
                    String normalizedExpected = dateOfBirthParam.replace(",", "").replace(" ", "").toLowerCase();
                    if (!normalized.equals(normalizedExpected)) allMatch = false;
                    break;
                case "Subjects":
                    if (!value.equals(subjectsParam)) allMatch = false;
                    break;
                case "Hobbies":
                    if (!value.equals(hobbiesParam)) allMatch = false;
                    break;
                case "State and City":
                    String expectedSC = stateParam + " " + cityParam;
                    if (!value.equals(expectedSC)) allMatch = false;
                    break;
            }
        }
        return allMatch;
    }
}
