package com.eva.bluetoothterminalapp.presentation.feature_le_connect.state

import com.eva.bluetoothterminalapp.domain.bluetooth_le.enums.BLEValueDisplayFormat

sealed interface WriteCharacteristicEvent {

	data object OpenDialog : WriteCharacteristicEvent
	data object CloseDialog : WriteCharacteristicEvent
	data class OnTextFieldValueChange(val value: String) : WriteCharacteristicEvent
	data class OnWriteFormatChange(val format: BLEValueDisplayFormat) : WriteCharacteristicEvent
	data object WriteCharacteristicValue : WriteCharacteristicEvent

}