package com.brognara.recipe_search_service.model;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class ToggleFavoriteRecipeRequest {
    private RecipeDocument favoriteRecipe;
    private boolean add;
}
