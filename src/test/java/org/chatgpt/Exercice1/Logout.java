package org.chatgpt.Exercice1;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Logout {
    // fonction main pour éxecuter les code
    public static void main(String[] args) throws InterruptedException {
        // 1. Définir le chemin du rapport
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("target/reports/logout.html");

        // 2. Créer un objet ExtentReports
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        // 3. Ajouter un test
        ExtentTest test = extent.createTest("Test eBay Logout", "tester la fonctionnalité logout sur eBay");

        // déclaration Web Driver: initialisation et ouverture d'un navigateur
        WebDriver driver = new ChromeDriver();
        // ouvrir la page de connexion
        driver.get("https://signin.ebay.fr/ws/eBayISAPI.dll?SignIn&sgfl=gh&ru=https%3A%2F%2Fwww.ebay.fr%2F");
        driver.manage().window().maximize();

        /* localisations et interactions des web elements pour connecter */
        // saisir adresse email ou pseudo
        WebElement email = driver.findElement(By.xpath("//input[@data-testid=\"userid\"]"));
        email.sendKeys("bchaker28@yahoo.com");

        // cliquer sur le bouton Continuer
        WebElement Continuer = driver.findElement(By.xpath("//button[@data-testid=\"signin-continue-btn\"]"));
        Continuer.click();

        // utilisation de WebDriverWait avec explicite wait pour que l'élément cliquable et visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement ToutAccepter = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"gdpr-banner-accept\"]")));
        ToutAccepter.click();

        // saisir un mot de passe
        WebElement password = driver.findElement(By.xpath("//*[@id=\"pass\"]"));
        password.sendKeys("Azerty1234");

        // cliquer sur le bouton Se Connecter
        WebElement SeConnecter = driver.findElement(By.xpath("//*[@id=\"sgnBt\"]"));
        SeConnecter.click();

        // cliquer sur le lien ignorer pour le moment
        // WebElement ignorer = driver.findElement(By.id("passkeys-cancel-btn"));
        // ignorer.click();

        // attendre un temps de 2 seconds et vérifier la connexion
        Thread.sleep(2000);
        String currentURL = driver.getCurrentUrl();
        //System.out.println(currentURL);
        if (currentURL.equals("https://www.ebay.fr/")) {
            test.pass("connexion avec succès");
        } else {
            test.fail("échec de connexion");
        }

        // attendre loading page
        Thread.sleep(5000);

        // cliquer sur le lien sign out et redirection vers la page confirmation logout
        WebElement userMenu = driver.findElement(By.className("gh-identity"));

        // Classe Acions: actions utilisateur complexes.
        // Utilisez cette classe plutôt que d'utiliser directement le clavier ou la souris.
        // Appelez perform() à la fin de la chaîne de méthodes pour effectuer réellement les actions.
        Actions actions = new Actions(driver);
        actions.moveToElement(userMenu).perform();
        // attendre affichage lien signout
        Thread.sleep(2000);
        WebElement linksignout = driver.findElement(By.linkText("Se déconnecter"));
        linksignout.click();

        // attendre un temps et vérifier la déconnexion
        Thread.sleep(2000);
        String url = driver.getCurrentUrl();
        if (url.equals("https://signin.ebay.fr/logout/confirm?sgfl=gh")) {
            test.pass("déconnexion avec succès");
        } else {
            test.fail("échec de déconnexion et affichage un message d'erreur");
        }

        // fermer le navigateur
        driver.quit();
        test.info("Test terminé et navigateur fermé");

        // générer le rapport
        extent.flush();

        System.out.println("✅ Rapport généré : target/reports/logout.html");
    }
}