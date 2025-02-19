package org.chatgpt.tests.exercice1;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.By;
// import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
// import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
// import java.util.Set;

public class Login {
    public static void main(String[] args) throws InterruptedException {
        // 1. Définir le chemin du rapport
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("target/reports/login.html");

        // 2. Créer un objet ExtentReports
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        // 3. Ajouter un test
        ExtentTest test = extent.createTest("Test eBay Login", "tester la fonctionnalité login sur eBay");

        // déclaration de web driver et ouverture un navigateur web Google Chrome
        WebDriver driver = new ChromeDriver();
        // acccéder à la page web de site e-commerce "e-bay"
        driver.navigate().to("https://signin.ebay.fr/ws/eBayISAPI.dll?SignIn&sgfl=gh&ru=https%3A%2F%2Fwww.ebay.fr%2F");
        driver.manage().window().maximize();

        /* localisations & interactions des web élèments */
        // saisir Adresse email ou pseudo
        WebElement email = driver.findElement(By.xpath("//input[@data-testid=\"userid\"]"));
        email.sendKeys("bchaker28@yahoo.com");

        // cliquer sur le bouton Continuer
        WebElement Continuer = driver.findElement(By.xpath("//button[@data-testid=\"signin-continue-btn\"]"));
        Continuer.click();

        //driver.manage().deleteAllCookies();

        // Utilisation de WebDriverWait pour interagir avec les éléments et des actions avancées
        // ajouter une attente explicite pour que l'élément devienne visible et cliquable :
        // cliquer sur le bouton Tout Accepter en bannière
        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement ToutAccepter = wait2.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"gdpr-banner-accept\"]")));
        ToutAccepter.click();
        // WebElement ToutAccepter = driver.findElement(By.xpath("//*[@id=\"gdpr-banner-accept\"]"));
        // ToutAccepter.click();

        Thread.sleep(2000);

        // saisir un mot de passe
        WebElement password = driver.findElement(By.xpath("//*[@id=\"pass\"]"));
        password.sendKeys("Azerty1234");

        // cliquer sur le bouton Se connecter
        /* WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(20));
         WebElement SeConnecter = wait3.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"sgnBt\"]")));
        SeConnecter.click(); */
        WebElement SeConnecter = driver.findElement(By.xpath("//*[@id=\"sgnBt\"]"));
        SeConnecter.click();

        // cliquer sur lien ignorer pour le moment
        // WebElement ignorer = driver.findElement(By.id("passkeys-cancel-btn"));
        // ignorer.click();

        // attendre un temps pour execute les instructions suivantes
        Thread.sleep(4000);

        // vérifier la connexion au site web e-bay
        String currentURL = driver.getCurrentUrl();
        // System.out.println(currentURL);
        if (currentURL.equals("https://www.ebay.fr/")) {
            test.pass("connexion ebay avec succès");
        } else {
            test.fail("échec de connexion ebay");
        }

        // fermer le navigateur
        driver.quit();
        test.info("Test terminé et navigateur fermé");

        // générer le rapport
        extent.flush();

        System.out.println("✅ Rapport généré : target/testReport.html");
    }
}