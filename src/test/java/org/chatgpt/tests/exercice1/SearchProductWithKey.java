package org.chatgpt.tests.exercice1;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchProductWithKey {
    static List<WebElement> searchResultsNamesvalides;
    static List<WebElement> searchResultsNamescarspec;

    public static void main(String[] args) {
        // 1. Définir le chemin du rapport
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("target/reports/searchProductByKey.html");

        // 2. Créer un objet ExtentReports
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        // 3. Ajouter un test
        ExtentTest test = extent.createTest("Test eBay Recherche de produits sur un site e-commerce par mot clé", "tester la fonctionnalité recherche produits par mot clé sur eBay");

        // déclaration d'un web driver: initialisation et ouverture un navigateur chrome
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.ebay.fr/");
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        String title = driver.getTitle();
        test.info("Title of the page is : " + title);

        /* déclaration et interaction des web elements */
        // TC01: recherche avec mot-clé valide "apple watch ultra 2 49mm"
        // résultat attendu 1: affichage des produits avec succès
        WebElement barre_de_recherche1 = driver.findElement(By.xpath("//input[@placeholder=\"Rechercher sur eBay\"]"));
        barre_de_recherche1.sendKeys("apple watch ultra 2 49mm");
        WebElement BtnRecherche1 = driver.findElement(By.xpath("//button[@value=\"Rechercher\"]"));
        BtnRecherche1.click();
        // Verifier mot_clé s'affiche en titre de page
        String texte1 = driver.getTitle();
        test.info("Title of the page is : " + texte1);
        if (texte1.toLowerCase().contains("apple watch ultra 2 49mm")) {
            test.pass("\nTC01: affichage des produits avec succès");
        } else {
            test.fail("\nTC01: échec d'affichage des produits");
        }

        // Verify all the products related to search are visible
        searchResultsNamesvalides = driver.findElements(By.xpath("//li[contains(@class,'s-item')]//div[contains(@class,'s-item__info')]//a[@class='s-item__link']//div[contains(@class,'s-item__title')]"));

        // afficher les noms des produits
        test.info("\n TC01 Produits trouvés : ");
        for (int i = 0; i < searchResultsNamesvalides.size(); i++) {
            test.info("Produit " + (i + 1) + " : " + searchResultsNamesvalides.get(i).getText());
        }

        driver.navigate().back();

        // TC02: recherche avec mot-clé invalide "jhfsdfjsdfh"
        // résultat attendu 2: échec d'affichage des produits
        WebElement barre_de_recherche2 = driver.findElement(By.xpath("//input[@placeholder=\"Rechercher sur eBay\"]"));
        barre_de_recherche2.sendKeys("jhfsdfjsdfh");
        WebElement BtnRecherche2 = driver.findElement(By.xpath("//button[@value=\"Rechercher\"]"));
        BtnRecherche2.click();

        String Liste_produits2 = driver.findElement(By.className("srp-save-null-search__heading")).getText();
        if (Liste_produits2.equals("Aucun résultat correspondant n'a été trouvé")) {
            test.pass("\nTC02: échec d'affichage des produits et affichage un message d'erreur");
        } else {
            test.fail("\nTC02: affichage des produits");
        }

        driver.navigate().back();

        // TC03: recherche avec mot-clé vide
        WebElement BtnRecherche3 = driver.findElement(By.xpath("//button[@value=\"Rechercher\"]"));
        BtnRecherche3.click();
        // Verifier le mot_clé s'affiche en titre de page
        String texte3 = driver.getTitle();
        if (!texte3.contains("en vente")) {
            test.pass("\nTC03: échec affichage des produits et la page est vide");
        } else {
            test.fail("\nTC03: affichage des produits");
        }
        driver.navigate().back();

        // TC04: recherche avec mot-clé avec des caractères spéciaux "apple:watch;ultra@2#49mm"
        // résultat attendu 4: affichage des produits
        WebElement barre_de_recherche4 = driver.findElement(By.xpath("//input[@placeholder=\"Rechercher sur eBay\"]"));
        barre_de_recherche4.sendKeys("apple:watch;ultra@2#49mm");
        WebElement BtnRecherche4 = driver.findElement(By.xpath("//button[@value=\"Rechercher\"]"));
        BtnRecherche4.click();

        // "Apple : watch;ultra@2#49mm en vente - | eBay"
        String texte4 = driver.getTitle();
        if (texte4.toLowerCase().contains("watch;ultra@2#49mm")) {
            test.pass("\nTC04: affichage des produits avec succès");
        } else {
            test.fail("\nTC04: échec d'affichage des produits");
        }
        test.info("\nTC04 Produits trouvés : ");
        searchResultsNamescarspec = driver.findElements(By.xpath("//li[contains(@class,'s-item')]//div[contains(@class,'s-item__info')]//a[contains(@class,'s-item__link')]//div[contains(@class,'s-item__title')]"));
        for (int i = 0; i < searchResultsNamescarspec.size(); i++) {
            test.info("Produit " + (i + 1) + " : " + searchResultsNamescarspec.get(i).getText());
        }

        driver.navigate().back();

        // fermer le navigateur
        driver.quit();
        test.info("Test terminé et navigateur fermé");

        // générer le rapport
        extent.flush();

        System.out.println("✅ Rapport généré : target/reports/searchProductByKey.html");
        /* Get all cookies
        Set<Cookie> cookies = driver.manage().getCookies();
        List<Cookie> cookiesToRemove = new ArrayList<>();

        // Iterate over cookies and collect the ones to remove
        for (Cookie cookie : cookies) {
            System.out.println(cookie.getName() + ": " + cookie.getValue());
            if (cookie.getName().equals("__uzmf")) {
                cookiesToRemove.add(cookie);
            }
        }

        // Remove the collected cookies
        for (Cookie cookie : cookiesToRemove) {
            driver.manage().deleteCookie(cookie);
        } */

    }
}