package com.eva.bluetoothterminalapp.presentation.feature_le_connect.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.eva.bluetoothterminalapp.R
import com.eva.bluetoothterminalapp.domain.bluetooth_le.enums.BLEValueDisplayFormat
import com.eva.bluetoothterminalapp.ui.theme.BlueToothTerminalAppTheme

@Composable
fun BLEValueFormatSelector(
	selectedFormat: BLEValueDisplayFormat,
	onFormatSelected: (BLEValueDisplayFormat) -> Unit,
	modifier: Modifier = Modifier,
) {
	var isExpanded by remember { mutableStateOf(false) }

	Row(
		modifier = modifier.fillMaxWidth(),
		horizontalArrangement = Arrangement.spacedBy(12.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		Text(
			text = stringResource(id = R.string.ble_data_view_title),
			style = MaterialTheme.typography.labelLarge,
			color = MaterialTheme.colorScheme.onSurface,
			modifier = Modifier.weight(1f)
		)
		Box {
			OutlinedButton(
				onClick = { isExpanded = true },
				shape = MaterialTheme.shapes.medium
			) {
				Text(
					text = selectedFormat.toDisplayString(),
					style = MaterialTheme.typography.bodyMedium
				)
				Icon(
					imageVector = Icons.Default.ArrowDropDown,
					contentDescription = null,
					modifier = Modifier.padding(start = 4.dp)
				)
			}
			DropdownMenu(
				expanded = isExpanded,
				onDismissRequest = { isExpanded = false },
				shape = MaterialTheme.shapes.medium,
				tonalElevation = 2.dp
			) {
				BLEValueDisplayFormat.entries.forEach { format ->
					DropdownMenuItem(
						text = {
							Text(
								text = format.toDisplayString(),
								style = MaterialTheme.typography.bodyMedium
							)
						},
						onClick = {
							onFormatSelected(format)
							isExpanded = false
						},
						colors = if (format == selectedFormat) {
							MenuDefaults.itemColors(
								textColor = MaterialTheme.colorScheme.primary
							)
						} else {
							MenuDefaults.itemColors()
						}
					)
				}
			}
		}
	}
}

@Composable
private fun BLEValueDisplayFormat.toDisplayString(): String {
	return when (this) {
		BLEValueDisplayFormat.HEX -> stringResource(id = R.string.ble_data_type_hex)
		BLEValueDisplayFormat.UTF8 -> stringResource(id = R.string.ble_data_type_utf8)
		BLEValueDisplayFormat.OCTAL -> stringResource(id = R.string.ble_data_type_octal)
		BLEValueDisplayFormat.BINARY -> stringResource(id = R.string.ble_data_type_binary)
		BLEValueDisplayFormat.SIGNED_INT -> stringResource(id = R.string.ble_data_type_int_signed)
		BLEValueDisplayFormat.UNSIGNED_INT -> stringResource(id = R.string.ble_data_type_int_unsigned)
	}
}

@PreviewLightDark
@Composable
private fun BLEValueFormatSelectorPreview() = BlueToothTerminalAppTheme {
	Surface(color = MaterialTheme.colorScheme.surfaceContainer) {
		BLEValueFormatSelector(
			selectedFormat = BLEValueDisplayFormat.HEX,
			onFormatSelected = {},
			modifier = Modifier.padding(16.dp)
		)
	}
}
