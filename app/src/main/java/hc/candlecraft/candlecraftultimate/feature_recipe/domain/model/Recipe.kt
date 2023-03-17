package hc.candlecraft.candlecraftultimate.feature_recipe.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Recipe(
    @PrimaryKey var id: Int? = null,
    val name: String,
    val description: String,
    val lastEditTimestamp: Long,
    val colorId: Int,
    val icon: Int
){}

class InvalidRecipeNameException(): Exception()