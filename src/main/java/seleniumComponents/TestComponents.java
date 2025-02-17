package seleniumComponents;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;


public class TestComponents {

    private WebDriver browser;

    @BeforeEach
    public void startUp(){
        browser = new ChromeDriver();
        browser.manage().window().maximize();
        browser.get("file:///"+ System.getProperty("user.dir")+ "/resources/componentes.html"); //abrir o arquivo local
    }

    @AfterEach
    public void tearDown(){
        //browser.quit();
    }

    @Test
    public void TextField(){
        browser.findElement(By.id("elementosForm:nome")).sendKeys("teste");
        Assertions.assertEquals("teste", browser.findElement(By.id("elementosForm:nome")).getDomProperty("value"));// vereficar automaticamente que o texto foi escrito no campo
    }

    @Test
    public void TextArea(){
        browser.findElement(By.id("elementosForm:sugestoes")).sendKeys("teste\nteste");
        Assertions.assertEquals("teste\nteste",browser.findElement(By.id("elementosForm:sugestoes")).getDomProperty("value"));
    }

    @Test
    public void RadioButton(){
        browser.findElement(By.id("elementosForm:sexo:0")).click();
        Assertions.assertTrue(browser.findElement(By.id("elementosForm:sexo:0")).isSelected());
    }

    @Test
    public void CheckBox(){
        browser.findElement(By.id("elementosForm:comidaFavorita:2")).click();
        Assertions.assertTrue(browser.findElement(By.id("elementosForm:comidaFavorita:2")).isSelected());
    }

    @Test
    public void ComboBox(){
        WebElement element = browser.findElement(By.id("elementosForm:escolaridade"));
        Select combo = new Select(element);
        //combo.selectByIndex(3); // clicar pela posição no array
        //combo.selectByValue("superior");//clicar pelo  value
        combo.selectByVisibleText("Mestrado");// clicar pelo texto visível na tela (visão do usuário)
    }

    @Test
    public void CheckComboBoxValues(){
        WebElement element = browser.findElement(By.id("elementosForm:escolaridade"));
        Select combo = new Select(element);
        List<WebElement> options = combo.getOptions(); //retorna uma lista de todas as opções disponíveis do Select
        Assertions.assertEquals(8,options.size());// validar a quantidade de opções

        //validar se existe uma determinada opção
        boolean encontrou = false;
        for(WebElement option: options){
            if(option.getText().equals("Mestrado")){
                encontrou = true;
                break;
            }
        }
        Assertions.assertTrue(encontrou);
    }

    @Test
    public void MultipleComboBox(){
        WebElement element = browser.findElement(By.id("elementosForm:esportes"));
        Select combo = new Select(element);
        combo.selectByVisibleText("Natacao");
        combo.selectByVisibleText("Corrida");

        //verificação das opções marcadas
        List<WebElement> allSelectedOptions = combo.getAllSelectedOptions();
        Assertions.assertEquals(2, allSelectedOptions.size());
    }

    @Test
    public void Button(){
        WebElement button = browser.findElement(By.id("buttonSimple"));
        button.click();

        //validar mudança de texto
        Assertions.assertEquals("Obrigado!",button.getDomAttribute("value"));
    }

    @Test
    public void ValidateText(){
        // Validar texto na tela (Div e spam) without Xpath

        // não perfomático
        Assertions.assertTrue(browser.findElement(By.tagName("body")).getText().contains("Campo de Treinamento"));

        Assertions.assertEquals("Campo de Treinamento", browser.findElement(By.tagName("h3")).getText());
    }

    @Test
    public void Link(){
        browser.findElement(By.linkText("Voltar")).click();

        // Validar texto na tela (Div e spam)
        Assertions.assertEquals("Voltou!", browser.findElement(By.id("resultado")).getText());
    }

    @Test
    public void SimpleAlert(){
        browser.findElement(By.id("alert")).click();

        Alert alert = browser.switchTo().alert();
        String text = alert.getText();
        Assertions.assertEquals("Alert Simples", text); // pegar texto do alert
        alert.accept(); //fechar o alert
    }

    @Test
    public void ConfirmAlert(){
        browser.findElement(By.id("confirm")).click();

        //Confirm
        Alert alert = browser.switchTo().alert();
        String text = alert.getText();
        Assertions.assertEquals("Confirm Simples", text); // pegar texto do alert
        alert.accept(); //fechar o alert
        text = alert.getText();
        Assertions.assertEquals("Confirmado", text);
        alert.accept();

        //Dismiss
        browser.findElement(By.id("confirm")).click();
        alert = browser.switchTo().alert();
        alert.dismiss();
        text = alert.getText();
        Assertions.assertEquals("Negado", text);
        alert.dismiss(); //último é indiferente se é dismiss ou accept
    }

    @Test
    public void PromptAlert(){
        browser.findElement(By.id("prompt")).click();

        Alert alert = browser.switchTo().alert();
        alert.sendKeys("12");
        alert.accept();
        String text = alert.getText();
        Assertions.assertEquals("Era 12?", text);
        alert.accept();
        text = alert.getText();
        Assertions.assertEquals(":D", text);
        alert.accept();
    }

    @Test
    public void Frames(){
        browser.switchTo().frame("frame1");
        browser.findElement(By.id("frameButton")).click();

        Alert alert = browser.switchTo().alert();
        Assertions.assertEquals("Frame OK!", alert.getText());
    }

    @Test
    public void PopupWithMethodTitle(){
        browser.findElement(By.id("buttonPopUpEasy")).click();

        browser.switchTo().window("Popup");
        browser.findElement(By.tagName("textarea")).sendKeys("Deu certo?");
        browser.close();

        browser.switchTo().window("");
        browser.findElement(By.tagName("textarea")).sendKeys("e agora?");
    }

    @Test
    public void PopupWithoutMethodTitle(){
        // working simultaneously in the two windows (there is no need to close the popup)
        browser.findElement(By.id("buttonPopUpHard")).click();
        System.out.println(browser.getWindowHandle()); // main page
        System.out.println(browser.getWindowHandles()); //array[main page, popup page]
        browser.switchTo().window((String) browser.getWindowHandles().toArray()[1]);
        browser.findElement(By.tagName("textarea")).sendKeys("Deu certo?");
        browser.switchTo().window((String) browser.getWindowHandles().toArray()[0]);
        browser.findElement(By.tagName("textarea")).sendKeys("E agora?");
    }

    // How to interact with table elements
    @Test
    public void TableClick(){
        WebElement table = browser.findElement(By.xpath("//*[@id='elementosForm:tableUsuarios']")); // Find table

        // Find all headers
        List<WebElement> columns = table.findElements(By.xpath(".//th"));
        int idColumnName = -1;
        int idColumnButton = -1;

        // find id of "Nome" and "Botao" columns
        for (int i = 0; i < columns.size(); i++) {
            String columnName = columns.get(i).getText().trim();
            if (columnName.equalsIgnoreCase("Nome")) {
                idColumnName = i + 1; // XPath starts in 1
            }
            if (columnName.equalsIgnoreCase("Botao")) {
                idColumnButton = i + 1;
            }
        }

        if (idColumnName == -1 || idColumnButton == -1) {
            System.out.println("Error: Column 'Nome' or 'Botao' not found!");
            return;
        }

        // find all rows of column "Nome"
        List<WebElement> rows = table.findElements(By.xpath("./tbody/tr/td[" + idColumnName + "]"));
        int idRow = -1;
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i).getText().trim().equalsIgnoreCase("Maria")) { // find row
                idRow = i + 1;
                break;
            }
        }

        if (idRow == -1) {
            System.out.println("Erro: Linha com o nome 'Maria' não encontrada!");
            return;
        }


        WebElement button = table.findElement(By.xpath("./tbody/tr[" + idRow + "]/td[" + idColumnButton + "]/input"));
        button.click();


    }


    // How to use JavaScript (AVOID)
    @Test
    public void javascript(){
        JavascriptExecutor js = (JavascriptExecutor) browser;
        //js.executeScript("alert('Test js with selenium')"); //open alert and send keys
        //js.executeScript("document.getElementById('elementosForm:nome').value = 'Write on the input with js'");
        //js.executeScript("document.getElementById('elementosForm:sobrenome').type = 'radio'"); //change the type of the field

        //WebElement element  = browser.findElement(By.id("elementosForm:nome"));
        //js.executeScript("arguments[0].style.border = arguments[1]",element,"solid 4px red");
        // ----------------- scroll -----------------
        //js.executeScript("window.scrollBy(0,arguments[0]",element.getLocation().y);

    }





}
