package Avataryug.Class

import Avataryug.Client.AvatarProjectSettings
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class ModelDecryptionHandler {

    companion object {
        val instance: ModelDecryptionHandler by lazy { ModelDecryptionHandler() }
    }

    fun getGlbByte(data: ByteArray): ByteArray {
        return getGlbDecryptByte(data)
    }

    private fun stringToByteKey(key: String): ByteArray {
        val byteArray = ByteArray(key.length / 2)
        for (i in key.indices step 2) {
            byteArray[i / 2] = key.substring(i, i + 2).toInt(16).toByte()
        }
        return byteArray
    }

    private fun getGlbDecryptByte(data: ByteArray): ByteArray {
        val inputKey = stringToByteKey(AvatarProjectSettings.Sceret_Key)
        val inputIV = stringToByteKey(AvatarProjectSettings.IV_Secret_Key)
        return decrypt(data, inputKey, inputIV)
    }

    private fun decrypt(data: ByteArray, key: ByteArray, iv: ByteArray): ByteArray {
        val secretKey = SecretKeySpec(key, "AES")
        val ivParameterSpec = IvParameterSpec(iv)

        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec)

        return cipher.doFinal(data)
    }
}