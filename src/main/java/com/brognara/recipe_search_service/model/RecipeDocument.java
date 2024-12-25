package com.brognara.recipe_search_service.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Getter
@Setter
@Builder
@Document(indexName = "recipe")
public class RecipeDocument {
    private String dishName;
    private String name;
    private String cuisine;
    private String course;
    private String dishType;
    private List<Ingredient> ingredients;
    private List<String> instructions;
    private String preparationMethod;
    private int cookingTime;
    private int servings;
    private String author;
    private String description;
    private String difficulty;
    private long createdTimestamp;
    private String thumbnailUri;

    // properties init service-side
    private int totalReviews;
    private int reviewScore;

    // ##################################################
    //      Optional properties (tag with nullable?)
    // ##################################################

    private List<String> dietTags;
    private List<String> dietRestrictionTags;
    private List<String> otherTags;
    private List<NutritionalContent> nutritionalContents;
}
