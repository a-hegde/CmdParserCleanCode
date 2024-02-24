import org.junit.Assert.*
import org.junit.Test

class ArgsExceptionTest() {

    @Test
    fun testUnexpectedMessage() {
        val e = ArgsException(ErrorCode.UNEXPECTED_ARGUMENT, 'x', "")
        assertEquals("Argument -x unexpected", e.errorMessage())
    }

    @Test
    fun testMissingStringMessage() {
        val e = ArgsException(ErrorCode.MISSING_STRING, 'x', "")
        assertEquals("Could not find string parameter for -x", e.errorMessage())
    }

    @Test
    fun testInvalidIntegerMessage() {
        val e = ArgsException(ErrorCode.INVALID_INTEGER, 'x', "Twenty")
        assertEquals("Argument -x expects an integer but was Twenty", e.errorMessage())
    }

    @Test
    fun testMissingIntegerMessage() {
        val e = ArgsException(ErrorCode.MISSING_INTEGER, 'x', "")
        assertEquals("Could not find integer parameter for -x", e.errorMessage())
    }

    @Test
    fun testInvalidDoubleMessage() {
        val e = ArgsException(ErrorCode.INVALID_DOUBLE, 'x', "Twenty")
        assertEquals("Argument -x expects a double but was Twenty", e.errorMessage())
    }
    @Test
    fun testMissingDoubleMessage() {
        val e = ArgsException(ErrorCode.MISSING_DOUBLE, 'x', "")
        assertEquals("Could not find double parameter for -x", e.errorMessage())
    }


}