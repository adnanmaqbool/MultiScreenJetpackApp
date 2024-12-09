package com.axelliant.wearhouse.network

enum class ErrorMessages(val errorString: String) {
    NoInternetError("No internet error"),
    SocketException("Unknown host name error"),
    SocketTimeout("Socket time out exception"),
    UnknownError("Unknown error occurs"),
    NotFound404("Api not found"),
    SessionExpired401("Session expired, please login again not found"),
    BadRequest400("Bad request"),
    InternalServerError500("Internal server error"),
    UNABLE_TO_EDIT_LEAVE("You cannot update leave type, but you can still delete this pending leave"),
    OPEN_LEAVES_ONLY("You cannot update leave with status "),
    CHECK_IN_PENDING_ONLY("You cannot update Check-In request with status "),
    DRAFT_EXPENSE_ONLY("You cannot update expense with status ")
}