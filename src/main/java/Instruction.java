import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class Instruction {
  private int id;
  private int recipe_id;
  private int step;
  private String instruction;

  public Instruction(int recipe_id, String instruction) {
    this.recipe_id = recipe_id;
    // Recipe recipe = Recipe.find(recipe_id);
    // step = recipe.getInstructions().size() + 1;
    this.instruction = instruction;
  }

  public int getId(){
    return id;
  }

  public int getRecipeId(){
    return recipe_id;
  }

  public int getStep(){
    return step;
  }

  public String getInstruction(){
    return instruction;
  }

  @Override
  public boolean equals(Object otherInstruction) {
    if (!(otherInstruction instanceof Instruction)) {
      return false;
    } else {
      Instruction newInstruction = (Instruction) otherInstruction;
      return this.getRecipeId() == newInstruction.getRecipeId() &&
        this.getStep() == newInstruction.getStep() &&
        this.getInstruction().equals(newInstruction.getInstruction());
    }
  }

}
