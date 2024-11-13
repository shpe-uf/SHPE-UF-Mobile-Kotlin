package com.example.shpe_uf_mobile_kotlin

import com.apollographql.apollo3.ApolloClient

private const val url =  "https://807e-128-227-1-18.ngrok-free.app" // BuildConfig.SERVER_URL

val apolloClient = ApolloClient.Builder()
    .serverUrl(url)
    .build()