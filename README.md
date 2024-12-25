# Recipe Search Service

## Elasticsearch Index

### Recipe
```shell
PUT /recipe
{
  "mappings": {
    "properties": {
      "dish_name": { "type": "keyword" },
      "name": { "type": "text" },
      "cuisine": { "type": "keyword" },
      "course": { "type": "keyword" },
      "dish_type": { "type": "keyword" },
      "ingredients": { "type": "nested" },
      "instructions": { "type": "text" },
      "preparation_method": { "type": "keyword" },
      "servings": { "type": "integer" },
      "nutritional_content": {
        "properties": {
          "calories": { "type": "float" },
          "protein": { "type": "float" },
          "carbs": { "type": "float" },
          "fat": { "type": "float" }
        }
      },
      "cooking_time": { "type": "integer" },
      "difficulty": { "type": "keyword" },
      "author": { "type": "text" },
      "description": { "type": "text" },
      "total_reviews": { "type": "integer" },
      "review_score": { "type": "integer" },
      "diet_tags": { "type": "keyword" },
      "diet_restriction_tags": { "type": "keyword" },
      "other_tags": { "type": "keyword" },
      "created_ts": { "type": "date" },
      "thumbnail_uri": { "type": "text" }
    }
  }
}
```

Example doc
```shell
POST /recipe/_doc
{
  "dish_name": "Spaghetti Carbonara",
  "name": "Traditional Spaghetti Carbonara",
  "cuisine": "Italian",
  "course": "Main",
  "dish_type": "Pasta",
  "ingredients": [
    { "name": "Spaghetti", "quantity": 200, "unit": "grams" },
    { "name": "Eggs", "quantity": 2, "unit": "large piece" },
    { "name": "Parmesan Cheese (grated)", "quantity": 50, "unit": "grams" },
    { "name": "Guanciale (diced)", "quantity": 100, "unit": "grams" },
    { "name": "Black Pepper", "unit": "To taste" },
    { "name": "Salt", "unit": "To taste" }
  ],
  "instructions": [
    "Cook spaghetti in salted boiling water until al dente",
    "Meanwhile, beat eggs with grated Parmesan.",
    "In a pan, cook guanciale until crispy.",
    "Drain pasta, reserving some water, and mix with guanciale and fat.",
    "Remove pan from heat, add egg mixture, and toss to coat.",
    "Adjust consistency with reserved pasta water.",
    "Season with black pepper and serve."
  ],
  "preparation_method": "Stovetop",
  "servings": 2,
  "nutritional_content": {
    "calories": 450.0,
    "protein": 20.0,
    "carbs": 45.0,
    "fat": 15.0
  },
  "cooking_time": 25,
  "difficulty": "Easy",
  "author": "Chef John",
  "description": "A classic Italian pasta dish made with simple, high-quality ingredients.",
  "total_reviews": 125,
  "review_score": 600,
  "diet_tags": [],
  "diet_restriction_tags": [],
  "other_tags": ["Quick", "Comfort Food"],
  "created_ts": "2024-12-01T10:00:00Z",
  "thumbnail_uri": "https://images.getrecipekit.com/v1617745835_carbonara_yoxebm.jpg?aspect_ratio=1:1&quality=90&"
}
```