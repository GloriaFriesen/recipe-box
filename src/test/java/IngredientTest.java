import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

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
  public void getIngredientText_ingredientInstantiatesWithIngredientText_String() {
    Ingredient testIngredient = new Ingredient(1, "2 tbsp", "olive oil");
    assertEquals("olive oil", testIngredient.getIngredientText());
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
  public void all_returnsAllInstancesOfIngredient_true() {
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

  @Test
  public void delete_deletesIngredient_true() {
    Ingredient testIngredient = new Ingredient(1, "1 cup", "sugar");
    testIngredient.save();
    testIngredient.delete();
    assertEquals(0, Ingredient.all().size());
  }

  @Test
  public void update_updatesIngredient_true() {
    Ingredient testIngredient = new Ingredient(1, "1 cup", "sugar");
    testIngredient.save();
    testIngredient.setMeasure("1/2 cup");
    testIngredient.setIngredientText("brown sugar");
    testIngredient.update();
    Ingredient savedIngredient = Ingredient.all().get(0);
    assertEquals("1/2 cup", savedIngredient.getMeasure());
    assertEquals("brown sugar", savedIngredient.getIngredientText());    
  }
}
