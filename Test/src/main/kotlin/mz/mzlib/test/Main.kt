tailrec fun foo(x: Int) {
    foo(x)
}



fun main() {
    throw ClassFormatError()
}