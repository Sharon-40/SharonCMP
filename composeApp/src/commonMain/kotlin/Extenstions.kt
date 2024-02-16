fun <T> kotlin.Array<T>.toArrayList(): ArrayList<T> {
    val items=ArrayList<T>()
    this.forEach {
        items.add(it)
    }
    return items
}



fun String.trimStartByZero():String
{
    return trimStart('0')
}

fun <T> ArrayList<T>.join(separator: String): String {
    val sb = StringBuilder()
    for (i in indices) {
        if (i > 0) {
            sb.append(separator)
        }
        sb.append(get(i))
    }
    return sb.toString()
}
