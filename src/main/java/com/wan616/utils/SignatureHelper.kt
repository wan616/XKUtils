package com.wan616.utils

import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.Signature
import java.security.MessageDigest

/**
 * 获取证书指纹帮助类
 * Create by Qing at 2025/2/19 14:43
 */
object SignatureHelper {

    fun getSignatureStr(context: Context): String? {
        val signature: Signature = getSignature(context) ?: return ""
        val cert = signature.toByteArray()
        return try {
            val md5 = MessageDigest.getInstance("MD5")
            val sha1 = MessageDigest.getInstance("SHA1")
            val sha256 = MessageDigest.getInstance("SHA256")
            val md5Key = md5.digest(cert)
            val sha1Key = sha1.digest(cert)
            val sha256Key = sha256.digest(cert)
            String.format(
                "MD5: %s\n\nSHA1: %s\n\nSHA-256: %s",
                byteArrayToString(md5Key),
                byteArrayToString(sha1Key),
                byteArrayToString(sha256Key)
            )
        } catch (e: Exception) {
            ""
        }
    }

    @Suppress("DEPRECATION")
    private fun getSignature(argContext: Context): Signature? {
        var signature: Signature? = null
        try {
            val packageName = argContext.packageName
            val packageManager = argContext.packageManager
            val packageInfo =
                packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            val signatures = packageInfo.signatures
            signature = signatures[0]
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return signature
    }

    // 解析签名信息
    private fun byteArrayToString(array: ByteArray): String {
        val hexString = StringBuilder()
        for(i in array.indices) {
            val byte = array[i]
            val hex = Integer.toHexString(0xFF and byte.toInt())
            if (hex.length == 1) {
                hexString.append('0')
            }
            hexString.append(hex)
            if (i < array.size - 1) {
                hexString.append(':')
            }
        }
        return hexString.toString()
    }
}