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

public class RecipeTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void recipe_instantiates_correctly() {
    Recipe testRecipe = new Recipe("Spaghetti Carbonara");
    assertTrue(testRecipe instanceof Recipe);
  }

  @Test
  public void getName_recipeInstantiatesWithName_String() {
    Recipe testRecipe = new Recipe("Spaghetti Carbonara");
    assertEquals("Spaghetti Carbonara", testRecipe.getName());
  }

  @Test
  public void equals_returnsTrueIfRecipesAreTheSame_true() {
    Recipe firstRecipe = new Recipe("Spaghetti Carbonara");
    Recipe secondRecipe = new Recipe("Spaghetti Carbonara");
    assertTrue(firstRecipe.equals(secondRecipe));
  }

  @Test
  public void save_returnsTrueIfSavedRecipeEqualsOriginalRecipe_true() {
    Recipe testRecipe = new Recipe("Strawberry Rhubarb Pie");
    testRecipe.save();
    assertTrue(Recipe.all().get(0).equals(testRecipe));
  }

  @Test
  public void save_assignsIdToRecipe() {
    Recipe testRecipe = new Recipe("Salade Nicoise");
    testRecipe.save();
    Recipe savedRecipe = Recipe.all().get(0);
    assertEquals(testRecipe.getId(), savedRecipe.getId());
  }

  @Test
  public void all_returnsAllInstancesOfRecipe_true(){
    Recipe firstRecipe = new Recipe("Salade Nicoise");
    firstRecipe.save();
    Recipe secondRecipe = new Recipe("Strawberry Rhubarb Pie");
    secondRecipe.save();
    assertEquals(true, Recipe.all().get(0).equals(firstRecipe));
    assertEquals(true, Recipe.all().get(1).equals(secondRecipe));
  }

  @Test
  public void find_returnsRecipeWithSameId_secondRecipe() {
    Recipe firstRecipe = new Recipe("Salade Nicoise");
    firstRecipe.save();
    Recipe secondRecipe = new Recipe("Strawberry Rhubarb Pie");
    secondRecipe.save();
    assertEquals(Recipe.find(secondRecipe.getId()), secondRecipe);
  }

  @Test
  public void getIngredients_returnsAllIngredientsForThisRecipe_ListIngredient() {
    Recipe firstRecipe = new Recipe("Salade Nicoise");
    firstRecipe.save();
    Ingredient firstIngredient = new Ingredient(firstRecipe.getId(), "8 oz", "salmon");
    firstIngredient.save();
    Ingredient secondIngredient = new Ingredient(firstRecipe.getId(), "1 cup", "green beans");
    secondIngredient.save();
    Recipe secondRecipe = new Recipe("Rhubarb Pie");
    secondRecipe.save();
    Ingredient thirdIngredient = new Ingredient(secondRecipe.getId(), "8 oz", "salmon");
    thirdIngredient.save();
    assertTrue(firstRecipe.getIngredients().get(0).equals(firstIngredient));
    assertTrue(firstRecipe.getIngredients().get(1).equals(secondIngredient));
    assertFalse(firstRecipe.getIngredients().contains(thirdIngredient));
  }

}
