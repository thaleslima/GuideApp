package com.guideapp.model

data class Local(var id: Long? = null,
                 var description: String? = null,
                 var site: String? = null,
                 var phone: String? = null,
                 var address: String? = null,
                 var isWifi: Boolean = false,
                 var detail: String? = null,
                 var latitude: Double = 0.toDouble(),
                 var longitude: Double = 0.toDouble(),
                 var imagePath: String? = null,
                 var idCity: Long? = null,
                 var idCategory: Long = 0,
                 var idCategories: List<Long>? = null,
                 var idSubCategories: List<Long>? = null,
                 var timestamp: Long = 0,
                 var subCategories: List<SubCategory>? = null,
                 var descriptionSubCategories: String? = null,
                 var isFavorite: Boolean = false
)
