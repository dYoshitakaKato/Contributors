// Generated by https://quicktype.io

package com.example.contributors.model

data class Contributor(
    val login: String,
    val id: Long,
    val nodeId: String,
    val avatarUrl: String,
    val gravatarId: String,
    val url: String,
    val htmlUrl: String,
    val followersUrl: String,
    val followingUrl: String,
    val gistsUrl: String,
    val starredUrl: String,
    val subscriptionsUrl: String,
    val organizationsUrl: String,
    val reposUrl: String,
    val eventsUrl: String,
    val receivedEventsUrl: String,
    val type: Type,
    val siteAdmin: Boolean,
    val contributions: Long
)

enum class Type {
    User
}
