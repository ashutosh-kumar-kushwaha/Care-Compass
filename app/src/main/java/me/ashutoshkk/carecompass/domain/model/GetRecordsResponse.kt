package me.ashutoshkk.carecompass.domain.model

data class GetRecordsResponse(
    val content: List<Record>,
    val lastPage: Boolean,
    val pageNumber: Int,
    val pageSize: Int,
    val totalElements: Int,
    val totalPage: Int
)