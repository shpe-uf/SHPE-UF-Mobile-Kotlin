package com.example.shpe_uf_mobile_kotlin

import com.apollographql.apollo3.ApolloClient

val apolloClient = ApolloClient.Builder()

    .serverUrl("https://cbab-128-227-38-193.ngrok-free.app")
    .build()