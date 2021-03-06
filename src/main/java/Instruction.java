import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class Instruction implements DatabaseManagement{
  private int id;
  private int recipe_id;
  private int step;
  private String instruction_text;

  public Instruction(int recipe_id, String instruction_text) {
    this.recipe_id = recipe_id;
    step = Recipe.find(recipe_id).getInstructions().size() + 1;
    this.instruction_text = instruction_text;
  }

  @Override
  public int getId(){
    return id;
  }

  public int getRecipeId(){
    return recipe_id;
  }

  public int getStep(){
    return step;
  }

  public void setStep(int step){
    this.step = step;
  }

  public String getInstructionText(){
    return instruction_text;
  }

  public void setInstructionText(String instruction_text){
    this.instruction_text = instruction_text;
  }

  @Override
  public boolean equals(Object otherInstruction) {
    if (!(otherInstruction instanceof Instruction)) {
      return false;
    } else {
      Instruction newInstruction = (Instruction) otherInstruction;
      return this.getRecipeId() == newInstruction.getRecipeId() &&
        this.getStep() == newInstruction.getStep() &&
        this.getInstructionText().equals(newInstruction.getInstructionText());
    }
  }

  @Override
  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO instructions (recipe_id, step, instruction_text) VALUES (:recipe_id, :step, :instruction_text)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("recipe_id", recipe_id)
        .addParameter("step", step)
        .addParameter("instruction_text", instruction_text)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Instruction> all() {
    String sql = "SELECT * FROM instructions";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Instruction.class);
    }
  }

  public static Instruction find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM instructions WHERE id=:id";
      Instruction instruction = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Instruction.class);
      return instruction;
    }
  }

  @Override
  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM instructions WHERE id=:id";
      con.createQuery(sql)
        .addParameter("id", this.getId())
        .executeUpdate();
    }
  }

  @Override
  public void update() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE instructions SET step = :step, instruction_text = :instruction_text WHERE id = :id";
      con.createQuery(sql)
      .addParameter("step", this.step)
      .addParameter("instruction_text", this.instruction_text)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }
}
