package com.example.composelazycolumn.utils

data class Feature(
        val id: Int, val name: String, val desc: String
)

var featureList = listOf(
    Feature(
        id = 0,
        name = "Lazy column with pull to refresh",
        desc = "This is shows how to \nbuild scrollable content with pull to refresh, \nsort list in alphabetic order of title, \nshow collapsing toolbar"
    ), Feature(
        id = 1,
        name = "Permission requests and Google map",
        desc = "This shows how to \nrequest for permission using compose \nshow map in background"
    )
)