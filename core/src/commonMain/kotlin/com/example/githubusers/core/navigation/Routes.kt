package com.example.githubusers.core.navigation

import kotlinx.serialization.Serializable

@Serializable
data object UsersRouteData

@Serializable
data class DetailRouteData(
    val userName: String
)
