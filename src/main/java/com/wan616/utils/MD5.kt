package com.wan616.utils

import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.math.BigInteger
import java.nio.channels.FileChannel
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


    @Throws(IOException::class)
    fun getFileMD5String(file: File): String {
        var inputStream: FileInputStream? = null
        try {
            val messageDigest = MessageDigest.getInstance("MD5")
            inputStream = FileInputStream(file)
            val ch = inputStream.channel
            val byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length())
            messageDigest.update(byteBuffer)
            return bufferToHex(messageDigest.digest())
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inputStream?.close()
        }
        return ""
    }

    @Throws(IOException::class)
    fun getFileMD5String(fileName: String): String {
        val f = File(fileName)
        return getFileMD5String(f)
    }

    private fun bufferToHex(bytes: ByteArray): String {
        return bufferToHex(bytes, 0, bytes.size)
    }

    private fun bufferToHex(bytes: ByteArray, m: Int, n: Int): String {
        val stringBuffer = StringBuffer(2 * n)
        val k = m + n
        for (l in m until k) {
            appendHexPair(bytes[l], stringBuffer)
        }
        return stringBuffer.toString()
    }

    private fun appendHexPair(bt: Byte, stringBuffer: StringBuffer) {
        val c0 = hexDigits[bt.toInt() and 0xf0 shr 4]
        val c1 = hexDigits[bt.toInt() and 0xf]
        stringBuffer.append(c0)
        stringBuffer.append(c1)
    }
}