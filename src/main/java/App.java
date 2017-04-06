import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.List;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/recipes/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/recipe-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/recipes", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      Recipe recipe = new Recipe(name);
      recipe.save();
      String url = String.format("/recipes/%d", recipe.getId());
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/recipes/:recipe_id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Recipe recipe = Recipe.find(Integer.parseInt(request.params(":recipe_id")));
      List<Ingredient> ingredients = recipe.getIngredients();
      List<Instruction> instructions = recipe.getInstructions();
      List<Tag> recipeTags = recipe.getTags();
      List<Tag> tags = Tag.all();
      model.put("ingredients", ingredients);
      model.put("instructions", instructions);
      model.put("recipeTags", recipeTags);
      model.put("tags", tags);
      model.put("recipe", recipe);
      model.put("template", "templates/recipe.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/recipes/ingredients/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Integer recipeId = Integer.parseInt(request.queryParams("recipeId"));
      String measure = request.queryParams("measure");
      String ingredientText = request.queryParams("ingredientText");
      Ingredient ingredient = new Ingredient(recipeId, measure, ingredientText);
      ingredient.save();
      String url = String.format("/recipes/%d", recipeId);
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/recipes/instructions/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Integer recipeId = Integer.parseInt(request.queryParams("recipeId"));
      String instructionText = request.queryParams("instructionText");
      Instruction instruction = new Instruction(recipeId, instructionText);
      instruction.save();
      String url = String.format("/recipes/%d", recipeId);
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("recipes/tags/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Integer recipeId = Integer.parseInt(request.queryParams("recipeId"));
      String tagName = request.queryParams("tagName");
      Tag tag = new Tag(tagName);
      tag.save();
      Recipe recipe = Recipe.find(recipeId);
      recipe.addTag(tag);
      String url = String.format("/recipes/%d", recipeId);
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  //   post("recipes/tag/addExisting" (request, response))

  }
}
