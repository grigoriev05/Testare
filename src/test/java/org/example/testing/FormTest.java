package org.example.testing;

import org.example.pom.FormPom;
import org.example.utils.Driver;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FormTest {

    WebDriver driver;

    static public String URL = "https://demoqa.com/automation-practice-form";
    static public String FIRSTNAME = "Gabriela";
    static public String LASTNAME = "Grigoriev";
    static public String EMAIL = "gabigrigoriev.06@gmail.com";
    static public String GENDER = "Female";
    static public String MOBILE = "0689548832";
    static public String DATEOFBIRTH = "20 Dec 2005";
    static public String SUBJECTS = "Maths";
    static public String HOBBIES = "Sports";
    static public String STATE = "Rajasthan";
    static public String CITY = "Jaipur";

    @BeforeClass
    public void beforeTest() {
        driver = Driver.setLocalDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void fillFormTest() {
        driver.get(URL);
        FormPom form = new FormPom(driver);

        form.setFirstName(FIRSTNAME);
        form.setLastName(LASTNAME);
        form.closeAdvert();
        form.setUserEmail(EMAIL);
        form.setGender(GENDER);
        form.setUserNumber(MOBILE);
        form.scrollToDateOfBirth();
        form.setDateOfBirth(DATEOFBIRTH);
        form.setSubjects(SUBJECTS);
        form.setHobbies(HOBBIES);
        form.setState(STATE);
        form.setCity(CITY);
        form.clickSubmit();

        boolean result = form.verifySubmissionData(
                FIRSTNAME, LASTNAME, EMAIL, GENDER, MOBILE,
                DATEOFBIRTH, SUBJECTS, HOBBIES, STATE, CITY
        );

        if (result) {
            System.out.println("Verificare reusita: toate datele coincid.");
        } else {
            System.out.println("Verificare esuata: unele date nu coincid.");
        }
    }

    @AfterClass
    public void closeTest() {
         //driver.quit();
    }
}
