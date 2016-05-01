import java.util.*;
import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.ArrayList;

public class Dictionary {
  public static void main(String[] args) {

    String layout = "templates/Layout.vtl";
    staticFileLocation("/public");

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/home.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/words/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/AddWords.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/words/:id/definition/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Words lexicon = Words.locate(Integer.parseInt(request.params(":id")));
      model.put("lexicon", lexicon);
      model.put("template", "templates/CreateDefine.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/words", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("words", Words.all());
      model.put("template", "templates/word.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/definitions", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("definitions", Definition.all());
      model.put("template", "templates/define.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/words", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      Words newWords = new Words(name);
      model.put("template", "templates/define.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/definitionPost", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Words lexicon = Words.locate(Integer.parseInt(request.queryParams("lexiconId")));
      String description = request.queryParams("description");
      Definition definition = new Definition(description);
      lexicon.addDefine(definition);
      model.put("lexicon", lexicon);
      model.put("template", "templates/AddWords.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/words/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Words lexicon = Words.locate(Integer.parseInt(request.params(":id")));
      model.put("lexicon", lexicon);
      model.put("template", "templates/word.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/definitions/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Definition definition = Definition.locate(Integer.parseInt(request.params(":id")));
      model.put("definition", definition);
      model.put("template", "templates/definition.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
