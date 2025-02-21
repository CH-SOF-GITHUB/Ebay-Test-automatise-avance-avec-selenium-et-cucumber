package org.chatgpt.Exercice1;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SearchByCategoryAndFilter {
    static List<WebElement> listes = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        // déclaration de Web driver: initialisation et ouverture un navigateur google chrome
        WebDriver driver = new ChromeDriver();
        driver.get("http://www.ebay.com");
        driver.manage().window().maximize();

        /* déclaration et interaction avec des web éléments */
        // Navigate to Search by category > Electronics > Cell Phones & accessories
        WebElement category = driver.findElement(By.className("gh-search-categories"));
        Select catg = new Select(category);
        catg.selectByValue("15032");

        WebElement Search = driver.findElement(By.xpath("//button[@value=\"Search\"]"));
        Search.click();

        Thread.sleep(2000);

        String url = driver.getCurrentUrl();
        System.out.println("url search by category: " + url);

        // vérifier le texte "Search by category > Electronics > Cell Phones & accessories"
        String msg = driver.findElement(By.xpath("/html/body/div[2]/div[2]/section[1]/h1")).getText();
        if (msg.equals("Cell Phones, Smart Watches & Accessories")) {
            System.out.println("message search by category s'affiche correctement");
        } else {
            System.out.println("message search by category ne s'affiche pas");
        }
        // cliquer sur section in sidebar "Cell Phones & accessories"
        WebElement section = driver.findElement(By.xpath("//a[@class=\"textual-display brw-category-nav__link\"]"));
        section.click();

        // cliquer sur "All listings" pour afficher toute la liste des produits
        WebElement AllListings = driver.findElement(By.xpath("//*[@id=\"brw-controls-root\"]/div[1]/div[1]/div[1]/ul/li[1]/a"));
        AllListings.click();

        // scroll down
        JavascriptExecutor jsExecuter = (JavascriptExecutor) driver;
        jsExecuter.executeScript("window.scrollBy(0,26225)");

        // pagination la page suivante en cliquant sur le fléche suivante
        WebElement next = driver.findElement(By.xpath("/html/body/div[2]/div[2]/section[3]/section[3]/div/nav/a"));
        next.click();

        // cliquer pour filter
        WebElement BtnSort = driver.findElement(By.xpath("//button[@aria-label=\"Sort Best Match\"]"));
        BtnSort.click();

        // bloquer le systéme un temps 5s puis exécuter
        Thread.sleep(2000);

        // filter par option "Time: ending soonest"
        WebElement option2 = driver.findElement(By.linkText("Time: newly listed"));
        option2.click();

        Thread.sleep(2000);

        // afficher tous les données end time des produits
        listes = driver.findElements(By.xpath("//h3[contains(@class, 'textual-display bsig__title__text')]"));

        for (int i = 0; i < listes.size(); i++) {
            System.out.println(listes.get(i).getText().substring(11));
        }
    }
}