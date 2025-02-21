package org.chatgpt.Exercice1;

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
    static List<WebElement> searchResultsNamesPlusieursMots;
    static List<WebElement> searchResultsNamescarspec;
    static List<WebElement> searchResultsNamesespaceDebutFin;
    static List<WebElement> searchResultsNamesNumber;
    static List<WebElement> searchResultsNamesMaj;

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

        /* déclaration et interaction des web elements */
        test.warning("TC01: recherche avec mot clé valide 'apple'");
        WebElement barre_de_recherche1 = driver.findElement(By.xpath("//input[@placeholder=\"Rechercher sur eBay\"]"));
        barre_de_recherche1.sendKeys("apple");
        WebElement BtnRecherche1 = driver.findElement(By.xpath("//button[@value=\"Rechercher\"]"));
        BtnRecherche1.click();
        // Verifier mot_clé s'affiche en titre de page
        String texte1 = driver.getTitle();
        test.info("TC01: Title of the page is : " + texte1);
        if (texte1.toLowerCase().contains("apple")) {
            test.pass("\nTC01: affichage des produits avec succès");
        } else {
            test.fail("\nTC01 échec d'affichage des produits");
        }
        // Verify all the products related to search are visible
        searchResultsNamesvalides = driver.findElements(By.xpath("//li[contains(@class,'s-item')]//div[contains(@class,'s-item__info')]//a[@class='s-item__link']//div[contains(@class,'s-item__title')]"));
        // afficher les noms des produits
        test.info("\nTC01 Produits trouvés : ");
        for (int i = 0; i < searchResultsNamesvalides.size(); i++) {
            test.info("Produit " + (i + 1) + " : " + searchResultsNamesvalides.get(i).getText());
        }

        driver.navigate().back();

        test.warning("TC02: recherche avec plusieurs mots clé 'apple watch ultra 2 49mm'");
        WebElement barre_de_recherche2 = driver.findElement(By.xpath("//input[@placeholder=\"Rechercher sur eBay\"]"));
        barre_de_recherche2.sendKeys("apple watch ultra 2 49mm");
        WebElement BtnRecherche2 = driver.findElement(By.xpath("//button[@value=\"Rechercher\"]"));
        BtnRecherche2.click();
        // Verifier mot_clé s'affiche en titre de page
        String texte2 = driver.getTitle();
        test.info("TC02: Title of the page is : " + texte1);
        if (texte2.toLowerCase().contains("apple watch ultra 2 49mm")) {
            test.pass("\nTC02: affichage des produits avec succès");
        } else {
            test.fail("\nTC02 échec d'affichage des produits");
        }

        // Verify all the products related to search are visible
        searchResultsNamesPlusieursMots = driver.findElements(By.xpath("//li[contains(@class,'s-item')]//div[contains(@class,'s-item__info')]//a[@class='s-item__link']//div[contains(@class,'s-item__title')]"));
        // afficher les noms des produits
        test.info("\nTC02 Produits trouvés : ");
        for (int i = 0; i < searchResultsNamesPlusieursMots.size(); i++) {
            test.info("Produit " + (i + 1) + " : " + searchResultsNamesPlusieursMots.get(i).getText());
        }

        driver.navigate().back();

        test.warning("TC03: recherche avec mot-clé invalide 'jhfsdfjsdfh'");
        // résultat attendu 3: échec d'affichage des produits
        WebElement barre_de_recherche3 = driver.findElement(By.xpath("//input[@placeholder=\"Rechercher sur eBay\"]"));
        barre_de_recherche3.sendKeys("jhfsdfjsdfh");
        WebElement BtnRecherche3 = driver.findElement(By.xpath("//button[@value=\"Rechercher\"]"));
        BtnRecherche3.click();

        String texte3 = driver.findElement(By.className("srp-save-null-search__heading")).getText();
        if (texte3.equals("Aucun résultat correspondant n'a été trouvé")) {
            test.pass("\nTC03: échec d'affichage des produits et affichage un message d'erreur");
        } else {
            test.fail("\nTC03: affichage des produits");
        }

        driver.navigate().back();

        test.warning("TC04: recherche avec mot-clé vide");
        WebElement BtnRecherche4 = driver.findElement(By.xpath("//button[@value=\"Rechercher\"]"));
        BtnRecherche4.click();
        // Verifier le mot_clé s'affiche en titre de page
        String texte4 = driver.getTitle();
        test.info("TC04: Title of the page is : " + texte4);
        if (!texte4.contains("en vente")) {
            test.pass("\nTC04: échec affichage des produits et la page est vide");
        } else {
            test.fail("\nTC04: affichage des produits");
        }
        driver.navigate().back();

        test.warning("TC05: recherche avec mot-clé avec des caractères spéciaux 'apple:watch;ultra@2#49mm'");
        // résultat attendu 5: affichage des produits
        WebElement barre_de_recherche5 = driver.findElement(By.xpath("//input[@placeholder=\"Rechercher sur eBay\"]"));
        barre_de_recherche5.sendKeys("apple:watch;ultra@2#49mm");
        WebElement BtnRecherche5 = driver.findElement(By.xpath("//button[@value=\"Rechercher\"]"));
        BtnRecherche5.click();

        String texte5 = driver.getTitle();
        test.info("TC05: Title of the page is : " + texte5);
        if (texte5.toLowerCase().contains("watch;ultra@2#49mm")) {
            test.pass("\nTC05: affichage des produits avec succès");
        } else {
            test.fail("\nTC05: échec d'affichage des produits");
        }
        test.info("\nTC05 Produits trouvés : ");
        searchResultsNamescarspec = driver.findElements(By.xpath("//li[contains(@class,'s-item')]//div[contains(@class,'s-item__info')]//a[contains(@class,'s-item__link')]//div[contains(@class,'s-item__title')]"));
        for (int i = 0; i < searchResultsNamescarspec.size(); i++) {
            test.info("Produit " + (i + 1) + " : " + searchResultsNamescarspec.get(i).getText());
        }

        driver.navigate().back();

        test.warning("TC06: recherche des produits par un mot clé avec un espace au début et au fin ' chaussures '");
        WebElement barre_de_recherche6 = driver.findElement(By.xpath("//input[@placeholder=\"Rechercher sur eBay\"]"));
        barre_de_recherche6.sendKeys(" chaussures ");
        WebElement BtnRecherche6 = driver.findElement(By.xpath("//button[@value=\"Rechercher\"]"));
        BtnRecherche6.click();

        String texte6 = driver.getTitle();
        test.info("TC06: Title of the page is : " + texte6);
        if (texte6.toLowerCase().contains("chaussures")) {
            test.pass("\nTC06: affichage des produits avec succès");
        } else {
            test.fail("\nTC06: échec d'affichage des produits");
        }
        test.info("\nTC06 Produits trouvés : ");
        searchResultsNamesespaceDebutFin = driver.findElements(By.xpath("//li[contains(@class,'s-item')]//div[contains(@class,'s-item__info')]//a[contains(@class,'s-item__link')]//div[contains(@class,'s-item__title')]"));
        for (int i = 0; i < searchResultsNamesespaceDebutFin.size(); i++) {
            test.info("Produit " + (i + 1) + " : " + searchResultsNamesespaceDebutFin.get(i).getText());
        }

        driver.navigate().back();

        test.warning("TC07: recherche des produits par des chiffres");
        WebElement barre_de_recherche7 = driver.findElement(By.xpath("//input[@placeholder=\"Rechercher sur eBay\"]"));
        barre_de_recherche7.sendKeys("456");
        WebElement BtnRecherche7 = driver.findElement(By.xpath("//button[@value=\"Rechercher\"]"));
        BtnRecherche7.click();

        String texte7 = driver.getTitle();
        test.info("TC07: Title of the page is : " + texte7);
        if (texte7.toLowerCase().contains("456")) {
            test.pass("\nTC07: affichage des produits avec succès");
        } else {
            test.fail("\nTC07: échec d'affichage des produits");
        }
        test.info("\nTC07 Produits trouvés : ");
        searchResultsNamesNumber = driver.findElements(By.xpath("//li[contains(@class,'s-item')]//div[contains(@class,'s-item__info')]//a[contains(@class,'s-item__link')]//div[contains(@class,'s-item__title')]"));
        for (int i = 0; i < searchResultsNamesNumber.size(); i++) {
            test.info("Produit " + (i + 1) + " : " + searchResultsNamesNumber.get(i).getText());
        }

        driver.navigate().back();

        test.warning("TC08: recherche des produits par mot clé Majuscule 'CHAUSSURES'");
        WebElement barre_de_recherche8 = driver.findElement(By.xpath("//input[@placeholder=\"Rechercher sur eBay\"]"));
        barre_de_recherche8.sendKeys("CHAUSSURES");
        WebElement BtnRecherche8 = driver.findElement(By.xpath("//button[@value=\"Rechercher\"]"));
        BtnRecherche8.click();

        // "Apple : watch;ultra@2#49mm en vente - | eBay"
        String texte8 = driver.getTitle();
        test.info("TC08: Title of the page is : " + texte8);
        if (texte8.toUpperCase().contains("CHAUSSURES")) {
            test.pass("\nTC08: affichage des produits avec succès");
        } else {
            test.fail("\nTC08: échec d'affichage des produits");
        }
        test.info("\nTC08 Produits trouvés : ");
        searchResultsNamesMaj = driver.findElements(By.xpath("//li[contains(@class,'s-item')]//div[contains(@class,'s-item__info')]//a[contains(@class,'s-item__link')]//div[contains(@class,'s-item__title')]"));
        for (int i = 0; i < searchResultsNamesMaj.size(); i++) {
            test.info("Produit " + (i + 1) + " : " + searchResultsNamesMaj.get(i).getText());
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