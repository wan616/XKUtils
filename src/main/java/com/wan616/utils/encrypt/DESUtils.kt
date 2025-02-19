package com.wan616.utils.encrypt

import com.wan616.utils.EncodeUtils
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * DES加解密工具类
 * Create by Qing at 2025/2/19 14:39
 */
object DESUtils {

    private val iv1 = byteArrayOf(18, 52, 86, 120, -112, -85, -51, -17)
    private const val AES_KEY = "PdNcUYRf"

    /**
     * 加密
     * @param encryptString 待加密字符串
     * @param encryptKey 加密密钥
     */
    fun encrypt(encryptString: String, encryptKey: String = AES_KEY): String {
        return try {
            val iv = IvParameterSpec(iv1)
            val dks = DESKeySpec(encryptKey.toByteArray())
            val keyFactory = SecretKeyFactory.getInstance("DES")
            val key = keyFactory.generateSecret(dks)
            val cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE, key, iv)
            String(EncodeUtils.base64Encode(cipher.doFinal(encryptString.toByteArray(charset("UTF-8")))))
        } catch (var7: Exception) {
            encryptString
        }
    }

    /**
     * 解密
     * @param decryptString 待解密字符串
     * @param decryptKey 解密密钥
     */
    fun decrypt(decryptString: String, decryptKey: String = AES_KEY): String {
        return try {
            val iv = IvParameterSpec(iv1)
            val key = SecretKeySpec(decryptKey.toByteArray(charset("UTF-8")), "DES")
            val cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")
            cipher.init(Cipher.DECRYPT_MODE, key, iv)
            String(cipher.doFinal(EncodeUtils.base64Decode(decryptString)))
        } catch (var7: Exception) {
            decryptString
        }
    }
}