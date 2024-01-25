package data.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductModel(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: RatingModel,
    val title: String
)

@Serializable
data class RatingModel(
    val count: Int,
    val rate: Double
)