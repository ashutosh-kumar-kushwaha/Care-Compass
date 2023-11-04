package me.ashutoshkk.carecompass.models

data class GetRecordsResponse(
    val content: List<Record>,
    val lastPage: Boolean,
    val pageNumber: Int,
    val pageSize: Int,
    val totalElements: Int,
    val totalPage: Int
)