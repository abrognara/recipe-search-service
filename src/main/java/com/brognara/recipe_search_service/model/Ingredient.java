package com.brognara.recipe_search_service.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder
@Getter
public class Ingredient {
    private String name;
    private float amount;
    private String unit;
}
