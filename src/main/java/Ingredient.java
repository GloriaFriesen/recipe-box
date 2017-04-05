import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class Ingredient {
  private int id;
  private int recipe_id;
  private String measure;
  private String ingredient_text;

  public Ingredient(int recipe_id, String measure, String ingredient_text) {
    this.recipe_id = recipe_id;
    this.measure = measure;
    this.ingredient_text = ingredient_text;
  }

  public int getId() {
    return id;
  }

  public int getRecipeId() {
    return recipe_id;
  }

  public String getMeasure() {
    return measure;
  }

  public String getIngredientText() {
    return ingredient_text;
  }

  @Override
  public boolean equals(Object otherIngredient) {
    if (!(otherIngredient instanceof Ingredient)) {
      return false;
    } else {
      Ingredient newIngredient = (Ingredient) otherIngredient;
      return this.getMeasure().equals(newIngredient.getMeasure()) &&
      this.getIngredientText().equals(newIngredient.getIngredientText()) &&
      this.getRecipeId() == newIngredient.getRecipeId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO ingredients (recipe_id, measure, ingredient_text) VALUES (:recipe_id, :measure, :ingredient_text)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("recipe_id", recipe_id)
        .addParameter("measure", measure)
        .addParameter("ingredient_text", ingredient_text)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Ingredient> all() {
    String sql = "SELECT * FROM ingredients";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Ingredient.class);
    }
  }

  public static Ingredient find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM ingredients WHERE id=:id";
      Ingredient ingredient = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Ingredient.class);
      return ingredient;
    }
  }
}
