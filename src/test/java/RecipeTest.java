import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

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
  public void getRating_recipeInstantiatesWithRating_int() {
    Recipe testRecipe = new Recipe("Spaghetti Carbonara");
    assertEquals(0, testRecipe.getRating());
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
    assertEquals(2, firstRecipe.getIngredients().size());
    assertFalse(firstRecipe.getIngredients().contains(thirdIngredient));
  }

  @Test
  public void getInstructions_returnsAllInstructionsForThisRecipe_ListInstruction(){
    Recipe firstRecipe = new Recipe("Lemon Meringue Pie");
    firstRecipe.save();
    Instruction firstInstruction = new Instruction(firstRecipe.getId(), "Beat eggs until they form soft peaks.");
    firstInstruction.save();
    Instruction secondInstruction = new Instruction(firstRecipe.getId(), "Beat eggs until they form soft peaks.");
    secondInstruction.save();
    Recipe secondRecipe = new Recipe("Rhubarb Pie");
    secondRecipe.save();
    Instruction thirdInstruction = new Instruction(secondRecipe.getId(), "Chop the rhubarb in 1 inch pieces and macerate with the sugar and cornstarch.");
    thirdInstruction.save();
    assertTrue(firstRecipe.getInstructions().get(0).equals(firstInstruction));
    assertTrue(firstRecipe.getInstructions().get(1).equals(secondInstruction));
    assertEquals(2, firstRecipe.getInstructions().size());
    assertFalse(firstRecipe.getInstructions().contains(thirdInstruction));
  }

  @Test
  public void addTag_addsTagToRecipe() {
    Recipe testRecipe = new Recipe("Lemon Meringue Pie");
    testRecipe.save();
    Tag testTag = new Tag("yummy");
    testTag.save();
    testRecipe.addTag(testTag);
    Tag savedTag = testRecipe.getTags().get(0);
    assertTrue(testTag.equals(savedTag));
  }

  @Test
  public void delete_deletesRecipe_true() {
    Recipe testRecipe = new Recipe("Lemon Meringue Pie");
    testRecipe.save();
    testRecipe.delete();
    assertEquals(0, Recipe.all().size());
  }

  @Test
  public void delete_deletesAllTagsAndRecipesAssociations() {
    Recipe testRecipe = new Recipe("Lemon Meringue Pie");
    testRecipe.save();
    Tag testTag = new Tag("yummy");
    testTag.save();
    testRecipe.addTag(testTag);
    testRecipe.delete();
    assertEquals(0, testTag.getRecipes().size());
  }

  @Test
  public void delete_deletesIngredientsAndInstructionsWhenRecipeIsDeleted() {
    Recipe testRecipe = new Recipe("Lemon Meringue Pie");
    testRecipe.save();
    Ingredient testIngredient = new Ingredient(testRecipe.getId(), "1 cup", "sugar");
    testIngredient.save();
    Instruction testInstruction = new Instruction(testRecipe.getId(), "Whip it. Whip it good.");
    testInstruction.save();
    testRecipe.delete();
    assertEquals(0, testRecipe.getInstructions().size());
    assertEquals(0, testRecipe.getIngredients().size());
  }
}
