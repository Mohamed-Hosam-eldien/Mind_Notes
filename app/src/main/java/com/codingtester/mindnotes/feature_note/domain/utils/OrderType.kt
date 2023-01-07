package com.codingtester.mindnotes.feature_note.domain.utils

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
