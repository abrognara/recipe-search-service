package com.brognara.recipe_search_service.model;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

// TODO validate inputs for this class
@ToString
@Getter
public class RecipePublishRequest {
    private String dishName; // FK connecting to dish table (WIP)
    private String recipeName;
    private String cuisine;
    private String course; // i.e. breakfast, lunch, dinner, desert
    private String dishType; // i.e. main dish, side dish, beverage
    private List<Ingredient> ingredients;
    private List<String> instructions;
    private String preparationMethod;
    private int cookingTime;
    private int servings;
    private String author; // TODO user id will come from oauth token or identity token
    private String description;
    private String difficulty;
    private String timestamp;
    private String thumbnailLocation;

    // ##################################################
    //      Optional properties (tag with nullable?)
    // ##################################################

    // tags for if meal is part of a diet (NOT the same as diet restrictions)
    // i.e. low-carb, paleo, etc.
    private List<String> dietTags;
    // tags for if meal restricts an ingredient or ingredients
    // i.e. vegan, vegetarian, gluten-free, peanut-free, etc.
    private List<String> dietRestrictionTags;
    // generic user-provided tags to help with search, should eventually consider restricting
    // i.e. holiday-related (thanksgiving, christmas), occasions (large-party, football-tailgate)
    private List<String> otherTags;
    // TODO if user cant submit nutritional contents we should estimate?
    private List<NutritionalContent> nutritionalContents;
}
