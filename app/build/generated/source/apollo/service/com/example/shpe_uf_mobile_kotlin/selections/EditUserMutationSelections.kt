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
import com.apollographql.apollo3.api.notNull
import com.example.shpe_uf_mobile_kotlin.type.GraphQLID
import com.example.shpe_uf_mobile_kotlin.type.User
import kotlin.collections.List

public object EditUserMutationSelections {
  private val __editUserProfile: List<CompiledSelection> = listOf(
        CompiledField.Builder(
          name = "id",
          type = GraphQLID.type.notNull()
        ).build()
      )

  public val __root: List<CompiledSelection> = listOf(
        CompiledField.Builder(
          name = "editUserProfile",
          type = User.type.notNull()
        ).arguments(listOf(
          CompiledArgument.Builder("editUserProfileInput").value(CompiledVariable("editUserProfileInput")).build()
        ))
        .selections(__editUserProfile)
        .build()
      )
}
