package com.brognara.recipe_search_service.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class NutritionalContent {
    private String name; // i.e. Total Fat, Protein, Vitamin C
    private int amount;
    private String measuringUnit; // grams, milligrams
}
