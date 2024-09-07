//
// AUTO-GENERATED FILE. DO NOT MODIFY.
//
// This class was automatically generated by Apollo GraphQL version '4.0.0-beta.4'.
//
package com.example.shpe_uf_mobile_kotlin.adapter

import com.apollographql.apollo3.api.Adapter
import com.apollographql.apollo3.api.BooleanAdapter
import com.apollographql.apollo3.api.CustomScalarAdapters
import com.apollographql.apollo3.api.json.JsonReader
import com.apollographql.apollo3.api.json.JsonWriter
import com.apollographql.apollo3.api.missingField
import com.apollographql.apollo3.api.obj
import com.example.shpe_uf_mobile_kotlin.PointsMutation
import kotlin.Boolean
import kotlin.String
import kotlin.collections.List

public object PointsMutation_ResponseAdapter {
  public object Data : Adapter<PointsMutation.Data> {
    public val RESPONSE_NAMES: List<String> = listOf("redeemPoints")

    override fun fromJson(reader: JsonReader, customScalarAdapters: CustomScalarAdapters):
        PointsMutation.Data {
      var _redeemPoints: PointsMutation.RedeemPoints? = null

      while (true) {
        when (reader.selectName(RESPONSE_NAMES)) {
          0 -> _redeemPoints = RedeemPoints.obj().fromJson(reader, customScalarAdapters)
          else -> break
        }
      }

      return PointsMutation.Data(
        redeemPoints = _redeemPoints ?: missingField(reader, "redeemPoints")
      )
    }

    override fun toJson(
      writer: JsonWriter,
      customScalarAdapters: CustomScalarAdapters,
      `value`: PointsMutation.Data,
    ) {
      writer.name("redeemPoints")
      RedeemPoints.obj().toJson(writer, customScalarAdapters, value.redeemPoints)
    }
  }

  public object RedeemPoints : Adapter<PointsMutation.RedeemPoints> {
    public val RESPONSE_NAMES: List<String> = listOf("confirmed")

    override fun fromJson(reader: JsonReader, customScalarAdapters: CustomScalarAdapters):
        PointsMutation.RedeemPoints {
      var _confirmed: Boolean? = null

      while (true) {
        when (reader.selectName(RESPONSE_NAMES)) {
          0 -> _confirmed = BooleanAdapter.fromJson(reader, customScalarAdapters)
          else -> break
        }
      }

      return PointsMutation.RedeemPoints(
        confirmed = _confirmed ?: missingField(reader, "confirmed")
      )
    }

    override fun toJson(
      writer: JsonWriter,
      customScalarAdapters: CustomScalarAdapters,
      `value`: PointsMutation.RedeemPoints,
    ) {
      writer.name("confirmed")
      BooleanAdapter.toJson(writer, customScalarAdapters, value.confirmed)
    }
  }
}
