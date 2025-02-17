package howToUsePageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class CampoTreinamentoPage {
    private Dsl dsl;

    public CampoTreinamentoPage(WebDriver browser){
       dsl = new Dsl(browser);
    }

    public void setName(String nome){
        dsl.write("elementosForm:nome",nome);
    }

    public void setLastName(String lastName){
        dsl.write("elementosForm:sobrenome",lastName);
    }

    public void setGenderFem(){
        dsl.click("elementosForm:sexo:1");
    }

    public void setGenderMas(){
        dsl.click("elementosForm:sexo:0");
    }

    public void setFoodPizza(){
        dsl.click("elementosForm:comidaFavorita:2");
    }

    public void setFoodMeat(){
        dsl.click("elementosForm:comidaFavorita:0");
    }

    public void setFoodVegetarian(){
        dsl.click("elementosForm:comidaFavorita:3");
    }

    public void setDegree(){
        dsl.selectCombo("elementosForm:escolaridade","Superior");
    }

    public void setSport(String... values){
        for(String value: values){
            dsl.selectCombo("elementosForm:esportes",value);
        }

    }

    public void sendForm(){
        dsl.click("elementosForm:cadastrar");
    }

    public String getResult(){
        return dsl.getText("//*[@id=\"resultado\"]/span");
    }

    public String getName(){
        return  dsl.getText("//*[@id=\"descNome\"]/span");
    }

    public String getLastName(){
        return dsl.getText("//*[@id=\"descSobrenome\"]/span");
    }

    public String getGender(){
        return dsl.getText("//*[@id=\"descSexo\"]/span");
    }

    public String getFood(){
        return dsl.getText("//*[@id=\"descComida\"]/span");
    }

    public String getDegree(){
        return dsl.getText("//*[@id=\"descEscolaridade\"]/span");
    }

    public String getSport(){
        return dsl.getText("//*[@id=\"descEsportes\"]/span");
    }


}
