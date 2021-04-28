
package test.java;

import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @BeforeEach
  public void setUp(){
	  this.database = new DatabaseRule();
  }

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @BeforeAll
  public static void setUp(){ 
  	AppTest.server = new ServerRule();
  }
// Here we test that the page is displayed with the Pokedex
// Acceptance test
  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Pokedex");
  }


// Here we test if the Pokemons Ivysaur and Charizard are displayed
// Acceptance Test
  @Test
  public void allPokemonPageIsDisplayed() {
    goTo("http://localhost:4567/");
    click("#viewDex");
    assertThat(pageSource().contains("Ivysaur"));
    assertThat(pageSource().contains("Charizard"));
  }


  // Here we test if the page for the Charizard pokemon is displayed
  // Acceptance test
  @Test
  public void individualPokemonPageIsDisplayed() {
    goTo("http://localhost:4567/pokepage/6");
    assertThat(pageSource().contains("Charizard"));
  }

  // Here it is tested that by clicking on the specific place that the Pokemon Squirtle should be displayed.
  // Integration Test - because the pieces of code must work together

  @Test
  public void arrowsCycleThroughPokedexCorrectly() {
    goTo("http://localhost:4567/pokepage/6");
    click(".glyphicon-triangle-right");
    assertThat(pageSource().contains("Squirtle"));
  }

// Testing if the action about searching for Charizard is working
// Integration Test
  @Test
  public void searchResultsReturnMatches() {
    goTo("http://localhost:4567/pokedex");
    fill("#name").with("char");
    assertThat(pageSource().contains("Charizard"));
  }


// Same as above - Integration test
  @Test
  public void searchResultsReturnNoMatches() {
    goTo("http://localhost:4567/pokedex");
    fill("#name").with("x");
    assertThat(pageSource().contains("No matches for your search results"));
  }

}
