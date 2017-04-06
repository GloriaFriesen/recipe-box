import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class Recipe {
  private int id;
  private String name;
  private int rating;


  public Recipe(String name) {
    this.name = name;
  }

  public int getId(){
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  @Override
  public boolean equals(Object otherRecipe) {
    if (!(otherRecipe instanceof Recipe)) {
      return false;
    } else {
      Recipe newRecipe = (Recipe) otherRecipe;
      return this.getName().equals(newRecipe.getName());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO recipes (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", name)
      .executeUpdate()
      .getKey();
    }
  }

  public void update() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE recipes SET name = :name, rating = :rating WHERE id = :id";
      con.createQuery(sql)
      .addParameter("name", name)
      .addParameter("rating", rating)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public static List<Recipe> all() {
    String sql = "SELECT * FROM recipes";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Recipe.class);
    }
  }

  public static Recipe find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM recipes where id=:id";
      Recipe recipe = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Recipe.class);
      return recipe;
    }
  }

  public List<Ingredient> getIngredients() {
    String sql = "SELECT * FROM ingredients WHERE recipe_id=:id";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Ingredient.class);
    }
  }

  public List<Instruction> getInstructions() {
    String sql = "SELECT * FROM instructions WHERE recipe_id=:id";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Instruction.class);
    }
  }

  public List<Tag> getTags() {
    try(Connection con = DB.sql2o.open()) {
      String joinQuery = "SELECT tag_id FROM recipes_tags WHERE recipe_id=:recipe_id";
      List<Integer> tagIds = con.createQuery(joinQuery)
      .addParameter("recipe_id", this.getId())
      .executeAndFetch(Integer.class);

      List<Tag> tags = new ArrayList<Tag>();

      for (Integer tagId:tagIds) {
        String tagQuery = "SELECT * FROM tags WHERE id=:tag_id";
        Tag tag = con.createQuery(tagQuery)
          .addParameter("tag_id", tagId)
          .executeAndFetchFirst(Tag.class);
        tags.add(tag);
      }
      return tags;
    }
  }

  public void addTag(Tag tag) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO recipes_tags (recipe_id, tag_id) VALUES (:recipe_id, :tag_id)";
      con.createQuery(sql)
        .addParameter("recipe_id", this.getId())
        .addParameter("tag_id", tag.getId())
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String ingredientDeleteQuery = "DELETE FROM ingredients WHERE recipe_id=:recipe_id";
      con.createQuery(ingredientDeleteQuery)
        .addParameter("recipe_id", this.getId())
        .executeUpdate();
      String instructionDeleteQuery = "DELETE FROM instructions WHERE recipe_id=:recipe_id";
      con.createQuery(instructionDeleteQuery)
        .addParameter("recipe_id", this.getId())
        .executeUpdate();
      String joinDeleteQuery = "DELETE FROM recipes_tags WHERE recipe_id=:recipe_id";
      con.createQuery(joinDeleteQuery)
        .addParameter("recipe_id", this.getId())
        .executeUpdate();
      String sql = "DELETE FROM recipes WHERE id=:id";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }
}
