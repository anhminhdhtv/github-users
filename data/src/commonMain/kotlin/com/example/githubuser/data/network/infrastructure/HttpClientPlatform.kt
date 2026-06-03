package com.example.githubuser.data.network.infrastructure

import io.ktor.client.HttpClient

expect fun platformHttpClient(timeout: Long = 60_000L): HttpClient