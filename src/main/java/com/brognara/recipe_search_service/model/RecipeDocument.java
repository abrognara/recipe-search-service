package com.brognara.recipe_search_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "recipe")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeDocument {
    @JsonProperty("dish_name")
    private String dishName;
    private String name;
    private String cuisine;
    private String course;
    @JsonProperty("dish_type")
    private String dishType;
    private List<Ingredient> ingredients;
    private List<String> instructions;
    @JsonProperty("preparation_method")
    private String preparationMethod;
    @JsonProperty("cooking_time")
    private int cookingTime;
    private int servings;
    private String author;
    private String description;
    private String difficulty;
    @JsonProperty("created_ts")
    private String createdTimestamp;
    @JsonProperty("thumbnail_uri")
    private String thumbnailUri;

    // properties init service-side
    private int totalReviews;
    private int reviewScore;

    // ##################################################
    //      Optional properties (tag with nullable?)
    // ##################################################

    @JsonProperty("diet_tags")
    private List<String> dietTags;
    @JsonProperty("diet_restriction_tags")
    private List<String> dietRestrictionTags;
    @JsonProperty("other_tags")
    private List<String> otherTags;
//    @JsonProperty("nutritional_contents")
//    private List<NutritionalContent> nutritionalContents;
}
