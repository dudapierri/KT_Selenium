package howToUsePageObjects;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class Dsl {

    private WebDriver browser;

    public Dsl(WebDriver browser){
        this.browser = browser;
    }


    public void write(By by, String text){
        browser.findElement(by).clear();//limpar texto
        browser.findElement(by).sendKeys(text);
    }

    public void write(String id, String text){
        write(By.id(id),text);
    }

    public void click(String id){
        browser.findElement(By.id(id)).click();
    }

    public void selectCombo(String id, String opcao){
        WebElement element = browser.findElement(By.id(id));
        Select combo = new Select(element);
        combo.selectByVisibleText(opcao);
    }

    public String getText(By by){
        return browser.findElement(by).getText();
    }

    public String getText(String xpath){
        return getText(By.xpath(xpath));
    }

    public String getTextAlert(){
        Alert alert = browser.switchTo().alert();
        return alert.getText();
    }


}
