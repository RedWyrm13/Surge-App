package com.example.surge_app.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//This class holds the info for the places autocomplete query from the google places API
@Serializable
data class Prediction(
    val description: String,
    val matchedSubstrings: List<MatchedSubstring>,
    val structuredFormatting: StructuredFormatting,
    val terms: List<Term>,
    @SerialName("place_id")
    val placeId: String
)

@Serializable
data class MatchedSubstring(
    val length: Int,
    val offset: Int
)

@Serializable
data class StructuredFormatting(
    @SerialName("main_text")
    val mainText: String,
    @SerialName("main_text_matched_substrings")
    val mainTextMatchedSubstrings: List<MatchedSubstring>,
    @SerialName("secondary_text")
    val secondaryText: String,
    @SerialName("secondary_text_matched_substrings")
    val secondaryTextMatchedSubstrings: List<MatchedSubstring>
)

@Serializable
data class Term(
    val offset: Int,
    val value: String
)

@Serializable
data class AutocompleteResponse(
    val predictions: List<Prediction>,
    val status: String
)
