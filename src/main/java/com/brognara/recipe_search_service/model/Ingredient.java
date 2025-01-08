package com.brognara.recipe_search_service.model;

import lombok.*;

@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    private String name;
    private float quantity;
    private String unit;
}
