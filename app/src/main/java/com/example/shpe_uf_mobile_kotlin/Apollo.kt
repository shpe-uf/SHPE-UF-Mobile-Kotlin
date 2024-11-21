package com.example.shpe_uf_mobile_kotlin

import com.apollographql.apollo3.ApolloClient

val apolloClient = ApolloClient.Builder()
    .serverUrl("https://635c-128-227-1-14.ngrok-free.app/")
    .build()