package domain.mappers

import data.model.ProductDTO
import domain.model.Product

fun List<ProductDTO>.toDomain(): List<Product> = map {
    Product(
        image = it.image,
        title = it.title,
        id = it.id
    )
}