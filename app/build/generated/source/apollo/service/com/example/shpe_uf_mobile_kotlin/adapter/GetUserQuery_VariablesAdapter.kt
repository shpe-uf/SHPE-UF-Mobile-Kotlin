//
// AUTO-GENERATED FILE. DO NOT MODIFY.
//
// This class was automatically generated by Apollo GraphQL version '4.0.0-beta.5'.
//
package com.example.shpe_uf_mobile_kotlin.adapter

import com.apollographql.apollo3.api.CustomScalarAdapters
import com.apollographql.apollo3.api.StringAdapter
import com.apollographql.apollo3.api.json.JsonWriter
import com.example.shpe_uf_mobile_kotlin.GetUserQuery
import kotlin.Boolean
import kotlin.Suppress

public object GetUserQuery_VariablesAdapter {
  @Suppress(
    "UNUSED_PARAMETER",
    "UNUSED_VARIABLE",
  )
  public fun serializeVariables(
    writer: JsonWriter,
    `value`: GetUserQuery,
    customScalarAdapters: CustomScalarAdapters,
    withDefaultValues: Boolean,
  ) {
    writer.name("userId")
    StringAdapter.toJson(writer, customScalarAdapters, value.userId)
  }
}
