
fun main(args: Array<String>) {
    println("Hello World!")
    println("Program arguments: ${args.joinToString()}")

    try {
        val argsObj = Args("l, p#, d*", args)
        val logging = argsObj.getBoolean('l')
        val port = argsObj.getInt('p')
        val directory = argsObj.getString('d')
        println("Execute application with logging turned ${if(logging) "on" else "off"}, port = $port, using directory = $directory")
    } catch (e: ArgsException) {
        println("Argument error: ${e.errorCode}\n")
    }

}
