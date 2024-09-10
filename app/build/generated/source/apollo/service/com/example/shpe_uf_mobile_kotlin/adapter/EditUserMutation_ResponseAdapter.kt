//
// AUTO-GENERATED FILE. DO NOT MODIFY.
//
// This class was automatically generated by Apollo GraphQL version '4.0.0-beta.5'.
//
package com.example.shpe_uf_mobile_kotlin.adapter

import com.apollographql.apollo3.api.Adapter
import com.apollographql.apollo3.api.CustomScalarAdapters
import com.apollographql.apollo3.api.StringAdapter
import com.apollographql.apollo3.api.json.JsonReader
import com.apollographql.apollo3.api.json.JsonWriter
import com.apollographql.apollo3.api.missingField
import com.apollographql.apollo3.api.obj
import com.example.shpe_uf_mobile_kotlin.EditUserMutation
import kotlin.String
import kotlin.collections.List

public object EditUserMutation_ResponseAdapter {
  public object Data : Adapter<EditUserMutation.Data> {
    public val RESPONSE_NAMES: List<String> = listOf("editUserProfile")

    override fun fromJson(reader: JsonReader, customScalarAdapters: CustomScalarAdapters):
        EditUserMutation.Data {
      var _editUserProfile: EditUserMutation.EditUserProfile? = null

      while (true) {
        when (reader.selectName(RESPONSE_NAMES)) {
          0 -> _editUserProfile = EditUserProfile.obj().fromJson(reader, customScalarAdapters)
          else -> break
        }
      }

      return EditUserMutation.Data(
        editUserProfile = _editUserProfile ?: missingField(reader, "editUserProfile")
      )
    }

    override fun toJson(
      writer: JsonWriter,
      customScalarAdapters: CustomScalarAdapters,
      `value`: EditUserMutation.Data,
    ) {
      writer.name("editUserProfile")
      EditUserProfile.obj().toJson(writer, customScalarAdapters, value.editUserProfile)
    }
  }

  public object EditUserProfile : Adapter<EditUserMutation.EditUserProfile> {
    public val RESPONSE_NAMES: List<String> = listOf("id")

    override fun fromJson(reader: JsonReader, customScalarAdapters: CustomScalarAdapters):
        EditUserMutation.EditUserProfile {
      var _id: String? = null

      while (true) {
        when (reader.selectName(RESPONSE_NAMES)) {
          0 -> _id = StringAdapter.fromJson(reader, customScalarAdapters)
          else -> break
        }
      }

      return EditUserMutation.EditUserProfile(
        id = _id ?: missingField(reader, "id")
      )
    }

    override fun toJson(
      writer: JsonWriter,
      customScalarAdapters: CustomScalarAdapters,
      `value`: EditUserMutation.EditUserProfile,
    ) {
      writer.name("id")
      StringAdapter.toJson(writer, customScalarAdapters, value.id)
    }
  }
}