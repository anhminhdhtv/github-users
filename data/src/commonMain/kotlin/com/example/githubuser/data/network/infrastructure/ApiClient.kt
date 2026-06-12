package com.example.githubuser.data.network.infrastructure

import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import io.ktor.client.request.host
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.json.Json

class ApiClient(val httpClient: HttpClient) {
    companion object {
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            coerceInputValues = true
        }
    }

    suspend inline fun request(
        requestConfig: RequestConfig,
        auth: Map<String, String> = mapOf(),
        bodyData: Any? = null
    ): HttpResponse {
        val functionPath = requestConfig.path

        val requestData = buildRequestData {
            url("$host$functionPath")

            this.method = requestConfig.method.toKtorMethod()
            headers {
                auth.forEach { (key, value) ->
                    this[key] = value
                }
                requestConfig.headers.forEach { (key, value) ->
                    append(key, value)
                }
            }

            requestConfig.query.forEach { (key, values) ->
                values.forEach { value ->
                    parameter(key, value)
                }
            }

            if (bodyData != null) {
                setBody(bodyData)
            }
        }

        return httpClient.request(requestData)
    }

    fun buildRequestData(block: HttpRequestBuilder.() -> Unit) =
        HttpRequestBuilder().apply(block)
}
