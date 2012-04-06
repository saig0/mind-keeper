package de.htwk.openNoteKeeper.server
import javax.crypto.spec.SecretKeySpec
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import org.apache.commons.codec.binary.Base64

trait CipherUtil {

  class CipherAlgorithm(val algorithm: String, val spec: String, val keyLength: Int)

  implicit val CipherAlgorithm = new CipherAlgorithm("AES", "AES/CBC/PKCS5Padding", 16)
  // implicit val CipherAlgorithm = new CipherAlgorithm("AES", "AES/ECB/PKCS5Padding", 16)
  // implicit val CipherAlgorithm = new CipherAlgorithm("DES", "DES", 8)

  private val initalVector = Array[Byte](0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f)

  def encrypt(key: String, plaintext: String)(implicit cipherAlgorithm: CipherAlgorithm) = {
    val iv = new IvParameterSpec(initalVector)

    val secretKey = generateKey(key)
    val cipher = Cipher.getInstance(cipherAlgorithm.spec)
    cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv)
    val cipherBytes = cipher.doFinal(plaintext.getBytes("UTF-8"))
    new String(Base64.encodeBase64(cipherBytes))
  }

  def decrypt(key: String, ciphertext: String)(implicit cipherAlgorithm: CipherAlgorithm) = {
    val iv = new IvParameterSpec(initalVector)

    val secretKey = generateKey(key)
    val cipher = Cipher.getInstance(cipherAlgorithm.spec)
    cipher.init(Cipher.DECRYPT_MODE, secretKey, iv)

    val encData = Base64.decodeBase64(ciphertext.getBytes())
    val decryptedtxt = cipher.doFinal(encData)
    new String(decryptedtxt, "UTF-8")
  }

  private def generateKey(key: String)(implicit cipherAlgorithm: CipherAlgorithm) = {
    var generatedKey = key;
    while (generatedKey.length() < cipherAlgorithm.keyLength) {
      generatedKey += generatedKey
    }
    generatedKey = generatedKey.substring(0, cipherAlgorithm.keyLength)
    new SecretKeySpec(generatedKey.getBytes(), cipherAlgorithm.algorithm)
  }
}