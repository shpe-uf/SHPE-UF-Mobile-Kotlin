package com.example.shpe_uf_mobile_kotlin

import com.apollographql.apollo3.ApolloClient

val apolloClient = ApolloClient.Builder()
    .serverUrl("https://e515-153-33-119-23.ngrok-free.app")
    .build()