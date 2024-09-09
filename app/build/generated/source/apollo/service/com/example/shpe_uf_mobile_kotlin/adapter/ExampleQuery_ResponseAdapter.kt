//
// AUTO-GENERATED FILE. DO NOT MODIFY.
//
// This class was automatically generated by Apollo GraphQL version '4.0.0-beta.4'.
//
package com.example.shpe_uf_mobile_kotlin.adapter

import com.apollographql.apollo3.api.Adapter
import com.apollographql.apollo3.api.CustomScalarAdapters
import com.apollographql.apollo3.api.IntAdapter
import com.apollographql.apollo3.api.json.JsonReader
import com.apollographql.apollo3.api.json.JsonWriter
import com.apollographql.apollo3.api.missingField
import com.apollographql.apollo3.api.nullable
import com.apollographql.apollo3.api.obj
import com.example.shpe_uf_mobile_kotlin.ExampleQuery
import kotlin.Int
import kotlin.String
import kotlin.collections.List

public object ExampleQuery_ResponseAdapter {
  public object Data : Adapter<ExampleQuery.Data> {
    public val RESPONSE_NAMES: List<String> = listOf("getUser")

    override fun fromJson(reader: JsonReader, customScalarAdapters: CustomScalarAdapters):
        ExampleQuery.Data {
      var _getUser: ExampleQuery.GetUser? = null

      while (true) {
        when (reader.selectName(RESPONSE_NAMES)) {
          0 -> _getUser = GetUser.obj().nullable().fromJson(reader, customScalarAdapters)
          else -> break
        }
      }

      return ExampleQuery.Data(
        getUser = _getUser
      )
    }

    override fun toJson(
      writer: JsonWriter,
      customScalarAdapters: CustomScalarAdapters,
      `value`: ExampleQuery.Data,
    ) {
      writer.name("getUser")
      GetUser.obj().nullable().toJson(writer, customScalarAdapters, value.getUser)
    }
  }

  public object GetUser : Adapter<ExampleQuery.GetUser> {
    public val RESPONSE_NAMES: List<String> = listOf("points", "fallPoints", "fallPercentile",
        "springPoints", "springPercentile", "summerPoints", "summerPercentile")

    override fun fromJson(reader: JsonReader, customScalarAdapters: CustomScalarAdapters):
        ExampleQuery.GetUser {
      var _points: Int? = null
      var _fallPoints: Int? = null
      var _fallPercentile: Int? = null
      var _springPoints: Int? = null
      var _springPercentile: Int? = null
      var _summerPoints: Int? = null
      var _summerPercentile: Int? = null

      while (true) {
        when (reader.selectName(RESPONSE_NAMES)) {
          0 -> _points = IntAdapter.fromJson(reader, customScalarAdapters)
          1 -> _fallPoints = IntAdapter.fromJson(reader, customScalarAdapters)
          2 -> _fallPercentile = IntAdapter.fromJson(reader, customScalarAdapters)
          3 -> _springPoints = IntAdapter.fromJson(reader, customScalarAdapters)
          4 -> _springPercentile = IntAdapter.fromJson(reader, customScalarAdapters)
          5 -> _summerPoints = IntAdapter.fromJson(reader, customScalarAdapters)
          6 -> _summerPercentile = IntAdapter.fromJson(reader, customScalarAdapters)
          else -> break
        }
      }

      return ExampleQuery.GetUser(
        points = _points ?: missingField(reader, "points"),
        fallPoints = _fallPoints ?: missingField(reader, "fallPoints"),
        fallPercentile = _fallPercentile ?: missingField(reader, "fallPercentile"),
        springPoints = _springPoints ?: missingField(reader, "springPoints"),
        springPercentile = _springPercentile ?: missingField(reader, "springPercentile"),
        summerPoints = _summerPoints ?: missingField(reader, "summerPoints"),
        summerPercentile = _summerPercentile ?: missingField(reader, "summerPercentile")
      )
    }

    override fun toJson(
      writer: JsonWriter,
      customScalarAdapters: CustomScalarAdapters,
      `value`: ExampleQuery.GetUser,
    ) {
      writer.name("points")
      IntAdapter.toJson(writer, customScalarAdapters, value.points)

      writer.name("fallPoints")
      IntAdapter.toJson(writer, customScalarAdapters, value.fallPoints)

      writer.name("fallPercentile")
      IntAdapter.toJson(writer, customScalarAdapters, value.fallPercentile)

      writer.name("springPoints")
      IntAdapter.toJson(writer, customScalarAdapters, value.springPoints)

      writer.name("springPercentile")
      IntAdapter.toJson(writer, customScalarAdapters, value.springPercentile)

      writer.name("summerPoints")
      IntAdapter.toJson(writer, customScalarAdapters, value.summerPoints)

      writer.name("summerPercentile")
      IntAdapter.toJson(writer, customScalarAdapters, value.summerPercentile)
    }
  }
}
