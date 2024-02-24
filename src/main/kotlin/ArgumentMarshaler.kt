import java.lang.NumberFormatException

interface ArgumentMarshaler {
    fun set(currentArgument: Iterator<String>)
}

class BooleanArgumentMarshaler: ArgumentMarshaler {
    var booleanValue = false

    override fun set(currentArgument: Iterator<String>) {
        booleanValue = if(currentArgument.hasNext()) currentArgument.next().toBoolean() else true
    }

}

class StringArgumentMarshaler: ArgumentMarshaler {
    var stringValue = ""
        private set

    override fun set(currentArgument: Iterator<String>){
        try {
            stringValue = currentArgument.next()
        } catch (e: NoSuchElementException) {
            throw ArgsException(ErrorCode.MISSING_STRING)
        }
    }
}

class IntArgumentMarshaler: ArgumentMarshaler {
    var intValue = 0
        private set

    override fun set(currentArgument: Iterator<String>) {
        var parameter = ""
        try {
            parameter = currentArgument.next()
            intValue = parameter.toInt()

        } catch (e: NoSuchElementException) {
            throw ArgsException(ErrorCode.MISSING_INTEGER)
        } catch (e: NumberFormatException) {
            throw ArgsException(ErrorCode.INVALID_INTEGER, parameter)
        }
    }
}

class DoubleArgumentMarshaler: ArgumentMarshaler {

    var doubleValue = 0.0

    override fun set(currentArgument: Iterator<String>) {
        var parameter = ""
        try {
            parameter = currentArgument.next()
            doubleValue = parameter.toDouble()
        } catch (e: NoSuchElementException) {
            throw ArgsException(ErrorCode.MISSING_DOUBLE)
        } catch (e: NumberFormatException) {
            throw ArgsException(ErrorCode.INVALID_DOUBLE, parameter)
        }    }
}


class StringArrayArgumentMarshaler: ArgumentMarshaler {
    var stringArrayValue = arrayOf<String>()

    override fun set(currentArgument: Iterator<String>) {
        TODO("Not yet implemented")
    }
}