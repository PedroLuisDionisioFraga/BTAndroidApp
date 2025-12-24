package com.eva.bluetoothterminalapp.presentation.feature_le_connect.state

import com.eva.bluetoothterminalapp.domain.bluetooth_le.enums.BLEValueDisplayFormat

data class CharacteristicWriteDialogState(
	val showDialog: Boolean = false,
	val textFieldValue: String = "",
	val errorText: String? = null,
	val writeFormat: BLEValueDisplayFormat = BLEValueDisplayFormat.UTF8,
)