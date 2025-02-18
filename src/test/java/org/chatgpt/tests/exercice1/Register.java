package org.chatgpt.tests.exercice1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Register {
    public static void main(String[] args) throws InterruptedException {
        // déclaration de web driver et ouverture un navigateur web Google Chrome
        WebDriver driver = new ChromeDriver();

        // acccéder à la page web de site e-commerce "e-bay"
        driver.get("https://signin.ebay.com/signin?sgn=reg&siteid=0&co_partnerId=0&UsingSSL=1&rv4=1&pageType=2556586&ru=https%3A%2F%2Fwww.ebay.com%2F&regUrl=https%3A%2F%2Fsignup.ebay.com%2Fpa%2Fcrte%3Fsiteid%3D0%26co_partnerId%3D0%26UsingSSL%3D1%26rv4%3D1%26pageType%3D2556586%26ru%3Dhttps%253A%252F%252Fwww.ebay.com%252F%26_trksid%3Dp2487285.m5021.l46827&sgfl=reg");
        driver.manage().window().maximize();
        Thread.sleep(4000);

        /* localisations & interactions des web élèments */
        WebElement create_account = driver.findElement(By.xpath("//*[@id=\"create-account-link\"]"));
        create_account.click();
        // histoire des actions navigate
        // driver.navigate().back();
        // Thread.sleep(4000);
        // driver.navigate().forward();

        // vérifier le message "Create an account"
        // String texte = driver.findElement(By.className("giant-text-2")).getText();
        // if (texte.equals("Create an account")) {
        //    System.out.println("message création compte ebay s'affiche");
        // } else {
        //     System.out.println("message création compte ebay ne s'affiche pas");
        // }

        // saisir First name
        WebElement firstname = driver.findElement(By.xpath("//input[@id=\"firstname\"]"));
        firstname.sendKeys("Ben Said");

        // saisir Last name
        WebElement lastname = driver.findElement(By.xpath("//input[@id=\"lastname\"]"));
        lastname.sendKeys("Chaker");

        // saisir un email
        WebElement email = driver.findElement(By.xpath("//input[@id=\"Email\"]"));
        email.sendKeys("chaker.nehos@gmail.com");

        // saisir un mot de passe
        WebElement password = driver.findElement(By.xpath("//input[@id=\"password\"]"));
        password.sendKeys("Azerty1234");

        Thread.sleep(4000);

        // cliquer sur le bouton Create personal account
        WebElement button = driver.findElement(By.xpath("//button[@name=\"EMAIL_REG_FORM_SUBMIT\"]"));
        button.click();
    }
}