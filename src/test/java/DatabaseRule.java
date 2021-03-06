import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  @Override
  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/recipe_box_test", null, null);
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteRecipesTagsQuery = "DELETE FROM recipes_tags *;";
      con.createQuery(deleteRecipesTagsQuery).executeUpdate();
      String deleteTagQuery = "DELETE FROM tags *;";
      con.createQuery(deleteTagQuery).executeUpdate();
      String deleteIngredientQuery = "DELETE FROM ingredients *;";
      con.createQuery(deleteIngredientQuery).executeUpdate();
      String deleteInstructionQuery = "DELETE FROM instructions *;";
      con.createQuery(deleteInstructionQuery).executeUpdate();
      String deleteRecipeQuery = "DELETE FROM recipes *;";
      con.createQuery(deleteRecipeQuery).executeUpdate();
    }
  }
}
