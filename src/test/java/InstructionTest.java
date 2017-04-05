import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class InstructionTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void instruction_instantiates_correctly() {
    Recipe testRecipe = new Recipe("Lemon Meringue Pie");
    testRecipe.save();
    Instruction testInstruction = new Instruction(testRecipe.getId(), "Beat eggs until they form soft peaks.");
    assertTrue(testInstruction instanceof Instruction);
  }

  @Test
  public void getRecipeId_getsRecipeId_int() {
    Recipe testRecipe = new Recipe("Lemon Meringue Pie");
    testRecipe.save();
    Instruction testInstruction = new Instruction(testRecipe.getId(), "Beat eggs until they form soft peaks.");
    assertEquals(testRecipe.getId(), testInstruction.getRecipeId());
  }

  @Test
  public void getInstructionText_getsInstructionText_String(){
    Recipe testRecipe = new Recipe("Lemon Meringue Pie");
    testRecipe.save();
    Instruction testInstruction = new Instruction(testRecipe.getId(), "Beat eggs until they form soft peaks.");
    assertEquals("Beat eggs until they form soft peaks.", testInstruction.getInstructionText());
  }

  @Test
  public void getStep_getsInstructionStep_String(){
    Recipe testRecipe = new Recipe("Lemon Meringue Pie");
    testRecipe.save();
    Instruction firstInstruction = new Instruction(testRecipe.getId(), "Beat eggs until they form soft peaks.");
    firstInstruction.save();
    Instruction secondInstruction = new Instruction(testRecipe.getId(), "Beat eggs until they form stiff peaks.");
    secondInstruction.save();
    assertEquals(1, firstInstruction.getStep());
    assertEquals(2, secondInstruction.getStep());
  }

  @Test
  public void equals_returnsTrueIfInstructionsAreTheSame_true(){
    Recipe testRecipe = new Recipe("Lemon Meringue Pie");
    testRecipe.save();
    Instruction firstInstruction = new Instruction(testRecipe.getId(), "Beat eggs until they form soft peaks.");
    Instruction secondInstruction = new Instruction(testRecipe.getId(), "Beat eggs until they form soft peaks.");
    assertTrue(firstInstruction.equals(secondInstruction));
  }

  @Test
  public void save_returnsTrueIfSavedEqualsOriginal_true(){
    Recipe testRecipe = new Recipe("Lemon Meringue Pie");
    testRecipe.save();
    Instruction testInstruction = new Instruction(testRecipe.getId(), "Beat eggs until they form soft peaks.");
    testInstruction.save();
    assertTrue(Instruction.all().get(0).equals(testInstruction));
  }

  @Test
  public void save_assignsIdToInstruction(){
    Recipe testRecipe = new Recipe("Lemon Meringue Pie");
    testRecipe.save();
    Instruction testInstruction = new Instruction(testRecipe.getId(), "Beat eggs until they form soft peaks.");
    testInstruction.save();
    Instruction savedInstruction = Instruction.all().get(0);
    assertEquals(testInstruction.getId(), savedInstruction.getId());
  }

  @Test
  public void all_returnsAllInstancesOfInstruction_true() {
    Recipe testRecipe = new Recipe("Lemon Meringue Pie");
    testRecipe.save();
    Instruction firstInstruction = new Instruction(testRecipe.getId(), "Beat eggs until they form soft peaks.");
    firstInstruction.save();
    Instruction secondInstruction = new Instruction(testRecipe.getId(), "Beat eggs until they form soft peaks.");
    secondInstruction.save();
    assertTrue(Instruction.all().get(0).equals(firstInstruction));
    assertTrue(Instruction.all().get(1).equals(secondInstruction));
  }

  @Test
  public void find_returnsInstructionWithSameId_secondInstruction() {
    Recipe testRecipe = new Recipe("Lemon Meringue Pie");
    testRecipe.save();
    Instruction firstInstruction = new Instruction(testRecipe.getId(), "Beat eggs until they form soft peaks.");
    firstInstruction.save();
    Instruction secondInstruction = new Instruction(testRecipe.getId(), "Beat eggs until they form soft peaks.");
    secondInstruction.save();
    assertEquals(Instruction.find(secondInstruction.getId()), secondInstruction);
  }
}
