package com.wan616.utils.encrypt

import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * MD5加密工具类
 * Create by Qing at 2025/2/13 16:23
 */
object MD5 {

    private var hexDigits =
        charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')

    fun String.md5(): String {
        val md5 = MessageDigest.getInstance("MD5")
        return BigInteger(1, md5.digest(toByteArray())).toString(16).padStart(32, '0')
    }

    fun getMD5(s: String): String {
        return try {
            val md5 = MessageDigest.getInstance("MD5")
            val byteArray = s.toByteArray(StandardCharsets.UTF_8)
            val md5Bytes = md5.digest(byteArray)
            md5Bytes.joinToString("") { byte -> "%02x".format(byte) }
        } catch (e: NoSuchAlgorithmException) {
            throw IllegalStateException("Failed to get MD5 instance", e)
        } catch (e: Exception) {
            throw IllegalStateException("Failed to compute MD5 hash", e)
        }
    }
}