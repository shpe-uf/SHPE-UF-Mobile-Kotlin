package com.example.shpe_uf_mobile_kotlin

import com.apollographql.apollo3.ApolloClient

val apolloClient = ApolloClient.Builder()
    .serverUrl("https://f32e-153-33-119-30.ngrok-free.app")
    .build()