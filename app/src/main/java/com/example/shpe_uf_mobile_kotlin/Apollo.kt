package com.example.shpe_uf_mobile_kotlin

import com.apollographql.apollo3.ApolloClient
// hello

val apolloClient = ApolloClient.Builder()
    .serverUrl("https://7a14-128-227-1-13.ngrok-free.app")
    .build()