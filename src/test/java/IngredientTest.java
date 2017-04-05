import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class IngredientTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void ingredient_instantiates_correctly() {
    Ingredient testIngredient = new Ingredient(1, "2 tbsp", "olive oil");
    assertTrue(testIngredient instanceof Ingredient);
  }

  @Test
  public void getMeasure_ingredientInstantiatesWithMeasure_String() {
    Ingredient testIngredient = new Ingredient(1, "2 tbsp", "olive oil");
    assertEquals("2 tbsp", testIngredient.getMeasure());
  }

  @Test
  public void getIngredient_ingredientInstantiatesWithIngredient_String() {
    Ingredient testIngredient = new Ingredient(1, "2 tbsp", "olive oil");
    assertEquals("olive oil", testIngredient.getIngredient());
  }

  @Test
  public void getRecipeId_instantiatesWithRecipeId_int() {
    Ingredient testIngredient = new Ingredient(1, "2 tbsp", "olive oil");
    assertEquals(1, testIngredient.getRecipeId());
  }

  @Test
  public void equals_returnsTrueIfIngredientsAreTheSame_true() {
    Ingredient firstIngredient = new Ingredient(1, "2 tbsp", "olive oil");
    Ingredient secondIngredient = new Ingredient(1, "2 tbsp", "olive oil");
    assertTrue(firstIngredient.equals(secondIngredient));
  }

  @Test
  public void save_returnsTrueIfSavedIngredientEqualsOriginalIngredient_true() {
    Ingredient testIngredient = new Ingredient(1, "2 tbsp", "olive oil");
    testIngredient.save();
    assertTrue(Ingredient.all().get(0).equals(testIngredient));
  }

  @Test
  public void save_assignsIdToIngredient() {
    Ingredient testIngredient = new Ingredient(1, "2 tbsp", "olive oil");
    testIngredient.save();
    Ingredient savedIngredient = Ingredient.all().get(0);
    assertEquals(testIngredient.getId(), savedIngredient.getId());
  }

  @Test
  public void all_returnsAllInancesOfIngredient_true() {
    Ingredient firstIngredient = new Ingredient(1, "2 tbsp", "olive oil");
    firstIngredient.save();
    Ingredient secondIngredient = new Ingredient(1, "1 cup", "sugar");
    secondIngredient.save();
    assertTrue(Ingredient.all().get(0).equals(firstIngredient));
    assertTrue(Ingredient.all().get(1).equals(secondIngredient));
  }

  @Test
  public void find_returnsIngredientWithSameId_secondIngredient() {
    Ingredient firstIngredient = new Ingredient(1, "2 tbsp", "olive oil");
    firstIngredient.save();
    Ingredient secondIngredient = new Ingredient(1, "1 cup", "sugar");
    secondIngredient.save();
    assertEquals(Ingredient.find(secondIngredient.getId()), secondIngredient);
  }
}
