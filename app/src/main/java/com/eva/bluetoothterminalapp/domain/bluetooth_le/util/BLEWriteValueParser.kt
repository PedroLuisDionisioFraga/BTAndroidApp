package com.eva.bluetoothterminalapp.domain.bluetooth_le.util

import com.eva.bluetoothterminalapp.domain.bluetooth_le.enums.BLEValueDisplayFormat

/**
 * Converts a string input to ByteArray based on the specified format
 * @param input The input string to convert
 * @param format The format to interpret the input
 * @return Result containing the ByteArray or an exception if parsing fails
 */
fun parseStringToByteArray(input: String, format: BLEValueDisplayFormat): Result<ByteArray> {
    return try {
        val bytes = when (format) {
            BLEValueDisplayFormat.UTF8 -> input.encodeToByteArray()

            BLEValueDisplayFormat.HEX -> {
                // Accept formats: "48 65 6C" or "48-65-6C" or "0x48 0x65" or "486C"
                val cleanInput = input
                    .replace("0x", "")
                    .replace("-", " ")
                    .replace(",", " ")
                    .trim()

                if (cleanInput.contains(" ")) {
                    cleanInput.split("\\s+".toRegex())
                        .filter { it.isNotBlank() }
                        .map { it.toInt(16).toByte() }
                        .toByteArray()
                } else {
                    // Handle continuous hex string like "48656C6C6F"
                    cleanInput.chunked(2)
                        .map { it.toInt(16).toByte() }
                        .toByteArray()
                }
            }

            BLEValueDisplayFormat.OCTAL -> {
                input.trim()
                    .split("\\s+".toRegex())
                    .filter { it.isNotBlank() }
                    .map { it.toInt(8).toByte() }
                    .toByteArray()
            }

            BLEValueDisplayFormat.BINARY -> {
                input.trim()
                    .split("\\s+".toRegex())
                    .filter { it.isNotBlank() }
                    .map { it.toInt(2).toByte() }
                    .toByteArray()
            }

            BLEValueDisplayFormat.SIGNED_INT -> {
                input.trim()
                    .split("\\s+".toRegex())
                    .filter { it.isNotBlank() }
                    .map {
                        val value = it.toInt()
                        if (value < -128 || value > 127) {
                            throw NumberFormatException("Value $value out of range for signed byte (-128 to 127)")
                        }
                        value.toByte()
                    }
                    .toByteArray()
            }

            BLEValueDisplayFormat.UNSIGNED_INT -> {
                input.trim()
                    .split("\\s+".toRegex())
                    .filter { it.isNotBlank() }
                    .map {
                        val value = it.toInt()
                        if (value < 0 || value > 255) {
                            throw NumberFormatException("Value $value out of range for unsigned byte (0 to 255)")
                        }
                        value.toByte()
                    }
                    .toByteArray()
            }
        }
        Result.success(bytes)
    } catch (e: NumberFormatException) {
        Result.failure(IllegalArgumentException("Invalid format: ${e.message}"))
    } catch (e: Exception) {
        Result.failure(e)
    }
}
