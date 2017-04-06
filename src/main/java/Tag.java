import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class Tag implements DatabaseManagement {
  private int id;
  private String name;

  public Tag(String name) {
    this.name = name;
  }

  @Override
  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object otherTag) {
    if (!(otherTag instanceof Tag)) {
      return false;
    } else {
      Tag newTag = (Tag) otherTag;
      return this.getName().equals(newTag.getName());
    }
  }

  @Override
  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO tags (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", name)
      .executeUpdate()
      .getKey();
    }
  }

  @Override
  public void update() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE tags SET name = :name WHERE id = :id";
      con.createQuery(sql)
      .addParameter("name", name)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  // @Override
  public static List<Tag> all() {
    String sql = "SELECT * FROM tags";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Tag.class);
    }
  }

  // @Override
  public static Tag find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tags WHERE id=:id";
      Tag tag = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Tag.class);
      return tag;
    }
  }

  public List<Recipe> getRecipes() {
    try(Connection con = DB.sql2o.open()) {
      String joinQuery = "SELECT recipe_id FROM recipes_tags WHERE tag_id=:tag_id";
      List<Integer> recipeIds = con.createQuery(joinQuery)
        .addParameter("tag_id", this.getId())
        .executeAndFetch(Integer.class);

      List<Recipe> recipes = new ArrayList<Recipe>();

      for(Integer recipeId : recipeIds) {
        String recipeQuery = "SELECT * FROM recipes WHERE id=:recipe_id";
        Recipe recipe = con.createQuery(recipeQuery)
          .addParameter("recipe_id", recipeId)
          .executeAndFetchFirst(Recipe.class);
        recipes.add(recipe);
      }
      return recipes;
    }
  }

  @Override
  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String joinDeleteQuery = "DELETE FROM recipes_tags WHERE tag_id=:tag_id";
      con.createQuery(joinDeleteQuery)
        .addParameter("tag_id", this.getId())
        .executeUpdate();
      String sql = "DELETE FROM tags WHERE id=:id";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }
}
