import org.junit.Test
import org.junit.Assert.*

class ArgsTest {

    @Test
    fun testCreateWithNoSchemaOrArguments() {
        val args = Args("", arrayOf())
        assertEquals(0, args.cardinality())
    }


    @Test
    fun testCreateWithNoSchemaButOneArgument() {
        try {
            Args("", arrayOf("-x"))
            fail()
        } catch (e: ArgsException){
            assertEquals(ErrorCode.UNEXPECTED_ARGUMENT, e.errorCode)
            assertEquals('x', e.errorArgumentId)
        }

    }

    @Test
    fun testCreateWithNoSchemaButMultipleArguments() {
        try {
            Args("", arrayOf("-x", "-y"))
            fail()
        } catch (e: ArgsException){
            assertEquals(ErrorCode.UNEXPECTED_ARGUMENT, e.errorCode)
            assertEquals('x', e.errorArgumentId)
        }
    }

    @Test
    fun testNonLetterSchema() {
        try {
            Args("*", arrayOf())
            fail("Args constructor should have thrown an exception")
        } catch (e: ArgsException){
            assertEquals(ErrorCode.INVALID_ARGUMENT_NAME, e.errorCode)
            assertEquals('*', e.errorArgumentId)
        }
    }

    @Test
    fun testInvalidArgumentFormat() {
        try {
            Args("f~", arrayOf())
            fail("Args constructor should have thrown an exception")
        } catch (e: ArgsException){
            assertEquals(ErrorCode.INVALID_ARGUMENT_FORMAT, e.errorCode)
            assertEquals('f', e.errorArgumentId)
        }
    }


    @Test
    fun testSimpleBoolean() {
        val args = Args("x", arrayOf("-x", "true"))
        assertEquals(1, args.cardinality())
        assertEquals(true, args.getBoolean('x'))
    }

    @Test
    fun testSimpleString() {
        val args = Args("x*", arrayOf("-x", "param"))
        assertEquals(1, args.cardinality())
        assertTrue(args.has('x'))
        assertEquals("param", args.getString('x'))
    }

    @Test
    fun testMissingStringArguments() {
        try {
            val args = Args("x*", arrayOf("-x"))
            fail()
        } catch (e: ArgsException){
            assertEquals(ErrorCode.MISSING_STRING, e.errorCode)
            assertEquals('x', e.errorArgumentId)
        }
    }


    @Test
    fun testSimpleIntPresent() {
        val args = Args("x#", arrayOf("-x", "42"))
        assertEquals(1, args.cardinality())
        assertTrue(args.has('x'))
        assertEquals(42, args.getInt('x'))
    }

    @Test
    fun testSpacesInFormat() {
        val args = Args("x, y", arrayOf("-xy"))
        assertEquals(2, args.cardinality())
        assertTrue(args.has('x'))
        assertTrue(args.has('y'))
    }


    @Test
    fun testInvalidInteger() {
        try {
            val args = Args("x#", arrayOf("-x", "Forty Two"))
            fail()
        } catch (e: ArgsException){
            assertEquals(ErrorCode.INVALID_INTEGER, e.errorCode)
            assertEquals('x', e.errorArgumentId)
            assertEquals("Forty Two", e.errorParameter)
        }
    }

    @Test
    fun testMissingInteger() {
        try {
            val args = Args("x#", arrayOf("-x"))
            fail()
        } catch (e: ArgsException){
            assertEquals(ErrorCode.MISSING_INTEGER, e.errorCode)
            assertEquals('x', e.errorArgumentId)
        }
    }

    @Test
    fun testSimpleDoublePresent() {
        val args = Args("x##", arrayOf("-x", "42.3"))
        assertEquals(1, args.cardinality())
        assertTrue(args.has('x'))
        assertEquals(args.getDouble('x'), 42.3, 0.01)
    }

    @Test
    fun testInvalidDouble() {
        try {
            Args("x##", arrayOf("-x", "Forty Two"))
            fail()
        } catch (e: ArgsException){
            assertEquals(ErrorCode.MISSING_INTEGER, e.errorCode)
            assertEquals('x', e.errorArgumentId)
            assertEquals("Forty Two", e.errorParameter)
        }
    }

    @Test
    fun testMissingDouble() {
        try {
            Args("x##", arrayOf("-x"))
            fail()
        } catch (e: ArgsException){
            assertEquals(ErrorCode.MISSING_DOUBLE, e.errorCode)
            assertEquals('x', e.errorArgumentId)
        }
    }

}

