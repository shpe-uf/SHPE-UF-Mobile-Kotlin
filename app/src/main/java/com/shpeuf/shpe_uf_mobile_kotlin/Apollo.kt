package com.shpeuf.shpe_uf_mobile_kotlin

import com.apollographql.apollo3.ApolloClient
import com.shpeuf.shpe_uf_mobile_kotlin.BuildConfig.SERVER_URL


val apolloClient = ApolloClient.Builder()
    .serverUrl(SERVER_URL)
    .build()
