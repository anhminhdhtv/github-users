package com.example.githubuser.data.di

import com.example.githubuser.data.local.IUserLocal
import com.example.githubuser.data.local.UserLocalImpl
import com.example.githubuser.data.local.database.createDatabase
import com.example.githubuser.data.local.database.sqlDriverFactory
import com.example.githubuser.data.network.IUserApi
import com.example.githubuser.data.network.UserApiImpl
import com.example.githubuser.data.network.infrastructure.ApiClient
import com.example.githubuser.data.repo.local.IUserLocalRepo
import com.example.githubuser.data.repo.local.UserLocalRepoImpl
import com.example.githubuser.data.repo.remote.IUserRemoteRepo
import com.example.githubuser.data.repo.remote.UserRemoteRepoImpl
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

const val APP_HOST = "api.github.com"
const val GITHUB_APIKEY = "" // Replace with your own GitHub API key to increase rate limit
const val AUTHENTICATION_KEY = "Authorization"

val dataModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
            coerceInputValues = true
        }
    }
    // HTTP client
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(get<Json>())
            }
            install(HttpCache)
            install(DefaultRequest) {
                url {
                    protocol = URLProtocol.HTTPS
                    host = APP_HOST
                    headers.append(
                        AUTHENTICATION_KEY,
                        GITHUB_APIKEY
                    )
                }
            }
        }
    }

    single<ApiClient> { ApiClient(get()) }

    // Remote data
    single<IUserApi> { UserApiImpl(get()) }
    single<IUserRemoteRepo> { UserRemoteRepoImpl(get(), get()) }

    // Local data
    factory { sqlDriverFactory() }
    single { createDatabase(get()) }

    single<IUserLocal> { UserLocalImpl(get()) }
    single<IUserLocalRepo> { UserLocalRepoImpl(get()) }


}
