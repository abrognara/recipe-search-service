package com.brognara.recipe_search_service.repository;

import com.brognara.recipe_search_service.model.RecipeDocument;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryFavoriteRecipesRepository {

    public static Map<String, List<RecipeDocument>> table = new ConcurrentHashMap<>();

}
