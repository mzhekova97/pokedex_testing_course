package test.java;

import static org.junit.Assert.*;

import org.junit.*;
import org.sql2o.*;

import main.java.Move;
import main.java.Pokemon;

public class PokemonTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();


  // Here we test if the Pokemon instance is succesfully created
  // Unit Test - because it tels us where the mistake is - in the instance of  Pokemon 
  @Test
  public void Pokemon_instantiatesCorrectly_true() {
    Pokemon myPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
    assertEquals(true, myPokemon instanceof Pokemon);
  }


  // Here we test if the name of the instantiated Pokemon is Squirtle
  // Unit test
  @Test
  public void getName_pokemonInstantiatesWithName_String() {
    Pokemon myPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
    assertEquals("Squirtle", myPokemon.getName());
  }


// Here we test if there are any pokemons at the start
// Unit test
  @Test
  public void all_emptyAtFirst() {
    assertEquals(Pokemon.all().size(), 0);
  }


// Here we test if the pokemons are equal 
// Unit Test
  @Test
  public void equals_returnsTrueIfPokemonAreTheSame_true() {
    Pokemon firstPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
    Pokemon secondPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
    assertTrue(firstPokemon.equals(secondPokemon));
  }

  //Here we test if the Pokemon is saved correctly (should give 1 because at the beginning there are no pokemons, so with this on 0+1)
  //Integration Test - because when we have created one Pokemon the database should increase - two pieces of code need to work together
  @Test
  public void save_savesPokemonCorrectly_1() {
    Pokemon newPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
    newPokemon.save();
    assertEquals(1, Pokemon.all().size());
  }

  // Here we test that we can retrieve a pokemon only by its ID 
  // Integration test - same reason as before
  @Test
  public void find_findsPokemonInDatabase_true() {
    Pokemon myPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
    myPokemon.save();
    Pokemon savedPokemon = Pokemon.find(myPokemon.getId());
    assertTrue(myPokemon.equals(savedPokemon));
  }

//Here we test if the created move, later adding to the pokemon's move is the same as the move "extracted" from the pokemon
//Integration test- same as before (checking if all the data is up to date and working together)
  @Test
  public void addMove_addMoveToPokemon() {
    Move myMove = new Move("Punch", "Normal", 50.0, 100);
    myMove.save();
    Pokemon myPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
    myPokemon.save();
    myPokemon.addMove(myMove);
    Move savedMove = myPokemon.getMoves().get(0);
    assertTrue(myMove.equals(savedMove));
  }

  // Here we test if the created Pokemon and its move are deleted after removing the mokemon
  // Integration test - same as before, the move should be removed with the pokemon because it was created specifically for this pokemon (because we add it to the pokemon)

  @Test
  public void delete_deleteAllPokemonAndMovesAssociations() {
    Pokemon myPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
    myPokemon.save();
    Move myMove = new Move("Bubble", "Water", 50.0, 100);
    myMove.save();
    myPokemon.addMove(myMove);
    myPokemon.delete();
    assertEquals(0, Pokemon.all().size());
    assertEquals(0, myPokemon.getMoves().size());
  }


  // Testing if by creating a pokemon we can find it only with a part of its name
  // Integration Test
  @Test
  public void searchByName_findAllPokemonWithSearchInputString_List() {
    Pokemon myPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
    myPokemon.save();
    assertEquals(myPokemon, Pokemon.searchByName("squir").get(0));
  }

  // Testing if the HP changes after the 4 attacks
  // Integration test 
  @Test
  public void fighting_damagesDefender() {
    Pokemon myPokemon = new Pokemon("Squirtle", "Water", "Normal", "A cute turtle", 50.0, 12, 16, false);
    myPokemon.save();
    myPokemon.hp = 500;
    Move myMove = new Move("Bubble", "Water", 50.0, 100);
    myMove.attack(myPokemon);
    System.out.println(myPokemon.hp);
    myMove.attack(myPokemon);
        System.out.println(myPokemon.hp);
    myMove.attack(myPokemon);
        System.out.println(myPokemon.hp);
    myMove.attack(myPokemon);
    assertEquals(400, myPokemon.hp);
  }

}
