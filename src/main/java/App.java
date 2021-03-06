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
      List<Recipe> recipes = Recipe.all();
      List<Tag> tags = Tag.all();
      model.put("tags", tags);
      model.put("recipes", recipes);
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

    get("/tags/:name", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Tag tag = Tag.findByName(request.params(":name"));
      List<Recipe> recipes = tag.getRecipes();
      model.put("tag", tag);
      model.put("recipes", recipes);
      model.put("template", "templates/tag.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/recipes/:recipe_id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Recipe recipe = Recipe.find(Integer.parseInt(request.params(":recipe_id")));
      List<Ingredient> ingredients = recipe.getIngredients();
      List<Instruction> instructions = recipe.getInstructions();
      List<Tag> recipeTags = recipe.getTags();
      List<Tag> unusedTags = recipe.getUnusedTags();
      model.put("ingredients", ingredients);
      model.put("instructions", instructions);
      model.put("recipeTags", recipeTags);
      model.put("unusedTags", unusedTags);
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

    post("recipes/tag/addExisting", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Integer recipeId = Integer.parseInt(request.queryParams("recipeId"));
      Tag tag = Tag.find(Integer.parseInt(request.queryParams("tag_id")));
      Recipe recipe = Recipe.find(recipeId);
      recipe.addTag(tag);
      String url = String.format("/recipes/%d", recipeId);
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("recipes/:recipe_id/tags/:tag_id/remove", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Recipe recipe = Recipe.find(Integer.parseInt(request.params("recipe_id")));
      Tag tag = Tag.find(Integer.parseInt(request.params("tag_id")));
      recipe.removeTag(tag);
      String url = String.format("/recipes/%d", recipe.getId());
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("recipes/:recipe_id/ingredients/:ingredient_id/remove", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Recipe recipe = Recipe.find(Integer.parseInt(request.params("recipe_id")));
      Ingredient ingredient = Ingredient.find(Integer.parseInt(request.params("ingredient_id")));
      ingredient.delete();
      String url = String.format("/recipes/%d", recipe.getId());
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("recipes/:recipe_id/instructions/:instruction_id/remove", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Recipe recipe = Recipe.find(Integer.parseInt(request.params("recipe_id")));
      Instruction instruction = Instruction.find(Integer.parseInt(request.params("instruction_id")));
      instruction.delete();
      String url = String.format("/recipes/%d", recipe.getId());
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("recipes/:recipe_id/ingredients/:ingredient_id/update", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Recipe recipe = Recipe.find(Integer.parseInt(request.params("recipe_id")));
      Ingredient ingredient = Ingredient.find(Integer.parseInt(request.params("ingredient_id")));
      model.put("recipe", recipe);
      model.put("ingredient", ingredient);
      model.put("template", "templates/ingredient-update.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("recipes/:recipe_id/ingredients/:ingredients_id/update", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Recipe recipe = Recipe.find(Integer.parseInt(request.params("recipe_id")));
      Ingredient ingredient = Ingredient.find(Integer.parseInt(request.params("ingredient_id")));
      ingredient.setMeasure(request.queryParams("measure"));
      ingredient.setIngredientText(request.queryParams("ingredient_text"));
      ingredient.update();
      String url = String.format("recipes/%d", recipe.getId());
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
