//
// AUTO-GENERATED FILE. DO NOT MODIFY.
//
// This class was automatically generated by Apollo GraphQL version '4.0.0-beta.5'.
//
package com.example.shpe_uf_mobile_kotlin.selections

import com.apollographql.apollo3.api.CompiledArgument
import com.apollographql.apollo3.api.CompiledField
import com.apollographql.apollo3.api.CompiledSelection
import com.apollographql.apollo3.api.CompiledVariable
import com.apollographql.apollo3.api.list
import com.apollographql.apollo3.api.notNull
import com.example.shpe_uf_mobile_kotlin.type.GraphQLString
import com.example.shpe_uf_mobile_kotlin.type.User
import kotlin.collections.List

public object GetUserQuerySelections {
  private val __getUser: List<CompiledSelection> = listOf(
        CompiledField.Builder(
          name = "firstName",
          type = GraphQLString.type.notNull()
        ).build(),
        CompiledField.Builder(
          name = "lastName",
          type = GraphQLString.type.notNull()
        ).build(),
        CompiledField.Builder(
          name = "username",
          type = GraphQLString.type.notNull()
        ).build(),
        CompiledField.Builder(
          name = "email",
          type = GraphQLString.type.notNull()
        ).build(),
        CompiledField.Builder(
          name = "sex",
          type = GraphQLString.type.notNull()
        ).build(),
        CompiledField.Builder(
          name = "ethnicity",
          type = GraphQLString.type.notNull()
        ).build(),
        CompiledField.Builder(
          name = "country",
          type = GraphQLString.type.notNull()
        ).build(),
        CompiledField.Builder(
          name = "year",
          type = GraphQLString.type.notNull()
        ).build(),
        CompiledField.Builder(
          name = "graduating",
          type = GraphQLString.type.notNull()
        ).build(),
        CompiledField.Builder(
          name = "classes",
          type = GraphQLString.type.list()
        ).build(),
        CompiledField.Builder(
          name = "internships",
          type = GraphQLString.type.list()
        ).build(),
        CompiledField.Builder(
          name = "socialMedia",
          type = GraphQLString.type.list()
        ).build(),
        CompiledField.Builder(
          name = "photo",
          type = GraphQLString.type.notNull()
        ).build()
      )

  public val __root: List<CompiledSelection> = listOf(
        CompiledField.Builder(
          name = "getUser",
          type = User.type
        ).arguments(listOf(
          CompiledArgument.Builder("userId").value(CompiledVariable("userId")).build()
        ))
        .selections(__getUser)
        .build()
      )
}
