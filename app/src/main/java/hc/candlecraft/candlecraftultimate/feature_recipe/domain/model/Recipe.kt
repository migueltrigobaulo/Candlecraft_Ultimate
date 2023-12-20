package hc.candlecraft.candlecraftultimate.feature_recipe.domain.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Recipe(
    @PrimaryKey var id: Int? = null,
    val name: String,
    val notes: String,
    val lastEditTimestamp: Long,
    val icon: Int,
    val waxType: String,
    val fragranceName: String,
    val fragrancePercentage: Int,
    val recipePicture: Uri?
)

class InvalidRecipeNameException(): Exception()