package com.example.shpe_uf_mobile_kotlin

import com.apollographql.apollo3.ApolloClient

val apolloClient = ApolloClient.Builder()
    .serverUrl("https://274d-128-227-39-26.ngrok-free.app")
    .build()