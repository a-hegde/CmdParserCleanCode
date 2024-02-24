
class ArgsException: Exception {

    var errorArgumentId: Char = ' '
    var errorCode = ErrorCode.OK
    var errorParameter = ""

    constructor(): super()

    constructor(message: String): super(message)

    constructor(errorCode: ErrorCode): this() {
        this.errorCode = errorCode
    }

    constructor(errorCode: ErrorCode, errorParameter: String ): this(errorCode) {
        this.errorParameter = errorParameter
    }

    constructor(errorCode: ErrorCode, errorArgumentId: Char, errorParameter: String ): this(errorCode, errorParameter) {
        this.errorArgumentId = errorArgumentId
    }

    fun errorMessage(): String {
        return when(errorCode) {
            ErrorCode.OK -> "Should not get here"
            ErrorCode.INVALID_ARGUMENT_FORMAT -> "$errorParameter is not a valid argument format"
            ErrorCode.UNEXPECTED_ARGUMENT -> "Argument -$errorArgumentId unexpected"
            ErrorCode.INVALID_ARGUMENT_NAME -> "'$errorArgumentId' is not a valid argument name"
            ErrorCode.MISSING_STRING -> "Could not find string parameter for -$errorArgumentId"
            ErrorCode.MISSING_INTEGER -> "Could not find integer parameter for -$errorArgumentId"
            ErrorCode.INVALID_INTEGER -> "Argument -$errorArgumentId expects an integer but was $errorParameter"
            ErrorCode.MISSING_DOUBLE -> "Could not find double parameter for -$errorArgumentId"
            ErrorCode.INVALID_DOUBLE -> "Argument -$errorArgumentId expects a double but was $errorParameter"
        }
    }

}


enum class ErrorCode {
    OK, INVALID_ARGUMENT_FORMAT, UNEXPECTED_ARGUMENT, INVALID_ARGUMENT_NAME, MISSING_STRING, MISSING_INTEGER,
    INVALID_INTEGER, MISSING_DOUBLE, INVALID_DOUBLE
}