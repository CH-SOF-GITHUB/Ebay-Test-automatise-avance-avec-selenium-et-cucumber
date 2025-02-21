package org.chatgpt.Exercice2.bestbuytunisie.tn;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class Inscription {
    // fonction main pour éxecuter le programme
    public static void main(String[] args) {
        // 1. Définir le chemin du rapport
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("target/reports/BestbuyTunisie/testInscription.html");

        // 2. Créer un objet ExtentReports
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        // 3. Ajouter un test
        ExtentTest test = extent.createTest("Test BestBuy Tunisie Inscription", "tester la fonctionnalité inscription sur BestBuy Tunisie");

        // déclaration d'un web driver: initialisation et ouverture d'un navigateur
        WebDriver driver = new ChromeDriver();
        driver.get("https://bestbuytunisie.tn/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // déclaration et interaction avec les web éléments
        WebElement ConnexionInscriptionLink = driver.findElement(By.xpath("/html/body/div[1]/header/div/div[1]/div/div/div[3]/div[1]/a"));
        ConnexionInscriptionLink.click();

        WebElement BtnCreerCompte = driver.findElement(By.xpath("/html/body/div[4]/div[3]/a"));
        BtnCreerCompte.click();

        test.warning("TC01: affichage de la page d'inscription sur le site web e-commerce bestbuy");
        String url = driver.getCurrentUrl();
        if (url.equals("https://bestbuytunisie.tn/mon-compte/")) {
            test.pass("la page d'inscription sur le site e-commerce Bestbuy Tunisie s'affiche avec succès");
        } else {
            test.fail("échec affichage de la page d'inscription sur le site e-commerce Bestbuy Tunisie");
        }

        //fermer le navigateur
        driver.quit();
        test.info("Test terminé et navigateur fermé");

        // générer le rapport
        extent.flush();

        System.out.println("✅ Rapport généré : target/reports/BestbuyTunisie/testInscription.html");
    }
}
