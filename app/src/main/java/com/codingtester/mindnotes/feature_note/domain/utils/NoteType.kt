package com.codingtester.mindnotes.feature_note.domain.utils

sealed class NoteType(val orderType: OrderType) {
    class Title(orderType: OrderType) : NoteType(orderType)
    class Date(orderType: OrderType) : NoteType(orderType)
    class Color(orderType: OrderType) : NoteType(orderType)
}
