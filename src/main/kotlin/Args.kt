class Args(val schema: String, args: Array<String>) {

    // Member variables
    private val marshalers: HashMap<Char, ArgumentMarshaler>
    private val argsFound: MutableSet<Char>
    private var currentArgument: ListIterator<String>? = null

    init{
        marshalers = HashMap()
        argsFound = HashSet()
        parseSchema()
        parseArgumentStrings(args.toList())
    }

    fun parseSchema() {
        for(element in schema.split(",")) {
            if(element.trim().isNotEmpty())
                parseSchemaElement(element.trim())
        }
    }

    fun parseSchemaElement(element: String) {
        val elementId = element[0]
        val elementTail = element.substring(1)

        validateSchemaElementId(elementId)

        when {
            elementTail.isEmpty() -> marshalers.put(elementId, BooleanArgumentMarshaler())
            elementTail.equals("*") -> marshalers.put(elementId, StringArgumentMarshaler())
            elementTail.equals("#") -> marshalers.put(elementId, IntArgumentMarshaler())
            elementTail.equals("##") -> marshalers.put(elementId, DoubleArgumentMarshaler())
            elementTail.equals("[*]") -> marshalers.put(elementId, StringArrayArgumentMarshaler())
            else -> throw ArgsException(ErrorCode.INVALID_ARGUMENT_FORMAT, elementId, elementTail)
        }
    }

    fun validateSchemaElementId(elementId: Char) {

        if(!elementId.isLetter())
            throw ArgsException(ErrorCode.INVALID_ARGUMENT_NAME, elementId, "")
    }

    fun parseArgumentStrings(argsList:List<String>) {
        currentArgument = argsList.listIterator()
        while (currentArgument!!.hasNext()) {
            val argStr: String = currentArgument!!.next()
            if(argStr.startsWith("-")) {
                parseArgumentCharacters(argStr.substring(1))
            } else {
                currentArgument!!.previous()
                break
            }
        }
    }

    fun parseArgumentCharacters(argChars: String) {
        argChars.map { parseArgumentCharacter(it) }

    }

    fun parseArgumentCharacter(argChar: Char) {
        val marshaler = marshalers.get(argChar) ?: throw ArgsException(ErrorCode.UNEXPECTED_ARGUMENT, argChar, "")

        argsFound.add(argChar)
        try {
            marshaler.set(currentArgument as Iterator<String>)
        }catch (e: ArgsException) {
             e.errorArgumentId = argChar
            throw e
        }
    }

    fun getBoolean(arg: Char) =
        (marshalers[arg] as BooleanArgumentMarshaler).booleanValue

    fun getString(arg: Char) =
        (marshalers[arg] as StringArgumentMarshaler).stringValue

    fun getInt(arg: Char) =
        (marshalers[arg] as IntArgumentMarshaler).intValue

    fun getDouble(arg: Char) =
        (marshalers[arg] as DoubleArgumentMarshaler).doubleValue

    fun cardinality() = argsFound.size

    fun has(arg: Char) = argsFound.contains(arg)
}