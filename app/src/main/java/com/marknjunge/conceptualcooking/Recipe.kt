package com.marknjunge.conceptualcooking

data class Recipe(val image: String, val name: String, val description: String)

val listOfRecipes = listOf(
    Recipe("grilled.jpg", "Grilled sea bass", "8 min | 3 servings"),
    Recipe("sous_vide.jpg", "Sous vide sea bass with mash", "8 min | 3 servings"),
    Recipe("creamed.jpg", "Creamed sea bass", "8 min | 3 servings"),

    Recipe("grilled.jpg", "Grilled sea bass", "8 min | 3 servings"),
    Recipe("sous_vide.jpg", "Sous vide sea bass with mash", "8 min | 3 servings"),
    Recipe("creamed.jpg", "Creamed sea bass", "8 min | 3 servings"),
    Recipe("grilled.jpg", "Grilled sea bass", "8 min | 3 servings"),
    Recipe("sous_vide.jpg", "Sous vide sea bass with mash", "8 min | 3 servings"),
    Recipe("creamed.jpg", "Creamed sea bass", "8 min | 3 servings"),
    Recipe("grilled.jpg", "Grilled sea bass", "8 min | 3 servings"),
    Recipe("sous_vide.jpg", "Sous vide sea bass with mash", "8 min | 3 servings"),
    Recipe("creamed.jpg", "Creamed sea bass", "8 min | 3 servings")
)