package com.example.githubuser.data.network

import com.example.githubuser.data.network.infrastructure.MultiValueMap
import com.example.githubuser.data.network.infrastructure.RequestConfig
import com.example.githubuser.data.network.infrastructure.RequestMethod
import com.example.githubuser.data.network.infrastructure.ApiClient
import com.example.githubuser.data.network.infrastructure.Response
import com.example.githubuser.data.network.model.UserDto
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType.Application.Json
import kotlinx.serialization.json.Json

class UserApiImpl(
    val httpClient: ApiClient
) : IUserApi {

    override suspend fun fetchUser(
        itemPerPage: Int,
        since: Int
    ): Array<UserDto> {
        val localVariableQuery: MultiValueMap = mapOf(
            "per_page" to listOfNotNull(itemPerPage.toString()),
            "since" to listOfNotNull(since.toString())
        )

        val contentHeaders: Map<String, String> = mapOf()
        val acceptsHeaders: Map<String, String> = mapOf("Content-Type" to "application/json")

        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders.putAll(contentHeaders)
        localVariableHeaders.putAll(acceptsHeaders)

        val requestConfig =
            RequestConfig(
                method = RequestMethod.GET,
                path = "/users",
                query = localVariableQuery,
                headers = localVariableHeaders
            )

        return httpClient.request(requestConfig).body<Array<UserDto>>()
    }

    override suspend fun fetchUserDetail(userName: String): UserDto {
        val localVariableQuery: MultiValueMap = mapOf()

        val contentHeaders: Map<String, String> = mapOf()
        val acceptsHeaders: Map<String, String> = mapOf("Content-Type" to "application/json")

        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders.putAll(contentHeaders)
        localVariableHeaders.putAll(acceptsHeaders)

        val requestConfig =
            RequestConfig(
                method = RequestMethod.GET,
                path = "/users/{username}".replace("{" + "username" + "}", userName),
                query = localVariableQuery,
                headers = localVariableHeaders
            )

        return httpClient.request(requestConfig).body<UserDto>()
    }
}