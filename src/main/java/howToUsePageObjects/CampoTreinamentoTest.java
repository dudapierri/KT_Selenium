package howToUsePageObjects;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.jupiter.api.Assertions;

public class CampoTreinamentoTest {
    private WebDriver browser;
    private Dsl dsl;
    private CampoTreinamentoPage page;

    @BeforeEach
    public void startUp(){
        browser = new ChromeDriver();
        browser.manage().window().maximize();
        browser.get("file:///"+ System.getProperty("user.dir")+ "/resources/componentes.html"); //abrir o arquivo local
        dsl = new Dsl(browser);
        page = new CampoTreinamentoPage(browser);
    }

    @AfterEach
    public void tearDown(){
        browser.quit();
    }

    @Test
    public void SendFormSuccessfully(){
        page.setName("Test");
        page.setLastName("Testtest");
        page.setGenderFem();
        page.setFoodPizza();
        page.setDegree();
        page.setSport("Natacao");
        page.setSport("Futebol");
        page.sendForm();

        //Validation
        Assertions.assertEquals("Cadastrado!", page.getResult());
        Assertions.assertEquals("Test",page.getName());
        Assertions.assertEquals("Testtest", page.getLastName());
        Assertions.assertEquals("Feminino", page.getGender());
        Assertions.assertEquals("Pizza", page.getFood());
        Assertions.assertEquals("superior", page.getDegree());
        Assertions.assertEquals("Natacao Futebol", page.getSport());

    }

    @Test
    public void MandatoryFieldName(){
        page.sendForm();
        Assertions.assertEquals("Nome eh obrigatorio",dsl.getTextAlert());
    }

    @Test
    public void MandatoryFieldLastName(){
        page.setName("Test");
        page.sendForm();
        Assertions.assertEquals("Sobrenome eh obrigatorio",dsl.getTextAlert());

    }

    @Test
    public void MandatoryFieldGender(){
        page.setName("Test");
        page.setLastName("Testest");
        page.sendForm();

        Assertions.assertEquals("Sexo eh obrigatorio",dsl.getTextAlert());

    }


    @Test
    public void InvalidOptionFavoriteFood(){
        page.setName("Test");
        page.setLastName("Testtest");
        page.setGenderFem();
        page.setFoodMeat();
        page.setFoodVegetarian();
        page.sendForm();

        Assertions.assertEquals("Tem certeza que voce eh vegetariano?", dsl.getTextAlert());

    }

    @Test
    public void InvalidOptionForSports(){
        page.setName("Test");
        page.setLastName("Testtest");
        page.setGenderFem();
        page.setSport("O que eh esporte?", "Futebol");
        page.sendForm();

        Assertions.assertEquals("Voce faz esporte ou nao?",dsl.getTextAlert());

    }

}
