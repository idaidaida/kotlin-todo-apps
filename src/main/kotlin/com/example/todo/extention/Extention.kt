package templates.extention

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

val ALGORITHM = "HmacSHA256"
val SALT = "my_salt"

fun String.generateHashValue(): String {
        // initialize hash generator
        val seacretKey = SecretKeySpec(SALT.toByteArray(), ALGORITHM)
        val mac = Mac.getInstance(ALGORITHM)
        mac.init(seacretKey)
        // generate hash
        val byteChars = mac.doFinal("$this".toByteArray())
        val stringBuilder = StringBuilder(2 * byteChars.size)
        for (b in byteChars) {
                stringBuilder.append((b.toInt() and 0x0f).toByte())
        }
        return stringBuilder.toString()
}

fun String.containsWithRegex(list: MutableList<String>): Boolean{
        // return true if "String" is matched list item
        // compared by Regex
        var isMatch: Boolean = false
        for(item in list) {
                val regex = Regex("${item}")
                isMatch = regex.matches("$this")
                if(isMatch){
                        break
                }
        }
        return isMatch
}