package com.example.shpe_uf_mobile_kotlin

import com.apollographql.apollo3.ApolloClient

private const val url = BuildConfig.SERVER_URL

val apolloClient = ApolloClient.Builder()
    .serverUrl("https://59eb-2600-387-15-3814-00-2.ngrok-free.app")
    .build()