package hc.candlecraft.candlecraftultimate.feature_recipe.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import hc.candlecraft.candlecraftultimate.common.MeasuringUnit

@Entity(indices = [Index(value = ["recipeId", "id"], unique = true)],
    foreignKeys = [ForeignKey(
            entity = Recipe::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("recipeId"),
            onDelete = CASCADE
        )]
)

data class Ingredient(
    @PrimaryKey var id: Int? = null,
    var recipeId: Int? = null,
    val name: String,
    val amount: Float
) {}

class InvalidIngredientNameException() : Exception()
class InvalidIngredientAmountException() : Exception()