package com.eva.bluetoothterminalapp.domain.bluetooth_le.util

import com.eva.bluetoothterminalapp.domain.bluetooth_le.enums.BLEValueDisplayFormat
import java.nio.charset.Charset

abstract class BLEValueModel(private val value: ByteArray) {

	private val charset: Charset
		get() = Charsets.US_ASCII

	val valueAsString: String?
		get() = value.decodeToString().let { decoded ->
			if (!charset.newEncoder().canEncode(decoded)) null
			else if (value.all { it.toInt() == 0x0 }) null
			else decoded
		}


	val valueHexString: String
		get() = if (value.isEmpty()) ""
		else value.joinToString(separator = "-", prefix = "0x-") { byte ->
			String.format("%02x", byte)
		}

	val valueOctalString: String
		get() = if (value.isEmpty()) ""
		else value.joinToString(separator = " ") { byte ->
			String.format("%03o", byte.toInt() and 0xFF)
		}

	val valueBinaryString: String
		get() = if (value.isEmpty()) ""
		else value.joinToString(separator = " ") { byte ->
			String.format("%8s", Integer.toBinaryString(byte.toInt() and 0xFF)).replace(' ', '0')
		}

	val valueSignedIntString: String
		get() = if (value.isEmpty()) ""
		else value.joinToString(separator = " ") { byte ->
			byte.toInt().toString()
		}

	val valueUnsignedIntString: String
		get() = if (value.isEmpty()) ""
		else value.joinToString(separator = " ") { byte ->
			(byte.toInt() and 0xFF).toString()
		}

	fun getValueForFormat(format: BLEValueDisplayFormat): String {
		return when (format) {
			BLEValueDisplayFormat.HEX -> valueHexString
			BLEValueDisplayFormat.UTF8 -> valueAsString ?: ""
			BLEValueDisplayFormat.OCTAL -> valueOctalString
			BLEValueDisplayFormat.BINARY -> valueBinaryString
			BLEValueDisplayFormat.SIGNED_INT -> valueSignedIntString
			BLEValueDisplayFormat.UNSIGNED_INT -> valueUnsignedIntString
		}
	}
}