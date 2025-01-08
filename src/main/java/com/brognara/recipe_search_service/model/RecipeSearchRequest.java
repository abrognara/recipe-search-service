package com.brognara.recipe_search_service.model;

import lombok.Getter;
import lombok.ToString;

// TODO validate inputs for this class
@ToString
@Getter
public class RecipeSearchRequest {
    private String recipeName;
    private String cuisine;
    private String course;
    private String dishType;
}
