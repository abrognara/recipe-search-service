package com.brognara.recipe_search_service.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NutritionalContent {
    private String name; // i.e. Total Fat, Protein, Vitamin C
    private int amount;
    private String measuringUnit; // grams, milligrams
}
