package com.wan616.utils.encrypt

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * AES加解密工具类
 * Create by Qing at 2025/2/19 14:41
 */
object AESUtils {

    private const val OFFSET = 0
    private const val KEY_LEN = 16
    private const val AES_KEY = "wan$%@CVBGgdt12q"

    /**
     * 加密
     * @param encryptStr 待加密字符串
     * @param encryptKey 加密密钥
     */
    fun encrypt(encryptStr: String, encryptKey: String = AES_KEY): String {
        return try {
            val secretKey = SecretKeySpec(encryptKey.toByteArray(charset("UTF-8")), "AES")
            val iv = IvParameterSpec(encryptKey.toByteArray(), OFFSET, KEY_LEN)
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv)
            val encryptData = cipher.doFinal(encryptStr.toByteArray(charset("UTF-8")))
            Base64.encodeToString(encryptData, Base64.NO_WRAP)
        } catch (e: Exception) {
            encryptStr
        }
    }

    /**
     * 解密
     * @param decryptStr 待解密字符串
     * @param decryptKey 解密密钥
     */
    fun decrypt(decryptStr: String, decryptKey: String = AES_KEY): String {
        return try {
            val secretKey = SecretKeySpec(decryptKey.toByteArray(charset("UTF-8")), "AES")
            val iv = IvParameterSpec(decryptKey.toByteArray(), OFFSET, KEY_LEN)
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv)
            val decryptData = cipher.doFinal(Base64.decode(decryptStr, Base64.NO_WRAP))
            String(decryptData, charset("UTF-8"))
        } catch (e: Exception) {
            decryptStr
        }
    }
}