package common

fun Any.log(value: String) {
    println(
        "--------------------------------------\n" +
            "-- $value --\n" +
            "--------------------------------------\n"
    )
}
