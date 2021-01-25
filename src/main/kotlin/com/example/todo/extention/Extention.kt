package templates.extention

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

val ALGORITHM = "HmacSHA256"
val SALT = "my_salt"

fun String.encript(): String {

        // ハッシュ生成器を初期化
        val seacretKey = SecretKeySpec(SALT.toByteArray(), ALGORITHM)
        val mac = Mac.getInstance(ALGORITHM)
        mac.init(seacretKey)

        // ハッシュ化処理
        val byteChars = mac.doFinal("$this".toByteArray())
        val stringBuilder = StringBuilder(2 * byteChars.size)
        for (b in byteChars) {
                stringBuilder.append((b.toInt() and 0x0f).toByte())
        }

        // ハッシュ化した文字列を応答
        return stringBuilder.toString()

}

fun String.containsWithRegex(list: MutableList<String>): Boolean{
        var isMatch: Boolean = false
        for(item in list) {
                val regex = Regex("${item}")
                isMatch = regex.matches("$this")
                println("${item}*")
                println("$this")
                println(isMatch)
                if(isMatch){
                        break
                }
        }
        return isMatch
}