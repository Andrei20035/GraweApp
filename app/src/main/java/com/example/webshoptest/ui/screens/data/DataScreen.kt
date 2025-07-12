package com.example.webshoptest.ui.screens.data

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.webshoptest.domain.model.CountryItem
import com.example.webshoptest.ui.components.CustomSnackbar
import com.example.webshoptest.ui.navigation.Screen
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun DataScreen(
    viewModel: DataViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    LaunchedEffect(uiState.offers) {
        if (uiState.offers.isNotEmpty()) {
            navController.navigate(Screen.Offers.route)
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                CustomSnackbar(data.visuals.message)
            }
        }
    ) { padding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .background(color = Color(0xFF108565))
        ) {
            DataScreenForm(
                uiState = uiState,
                countries = viewModel.countries.collectAsState().value,
                onAction = { action ->
                    when (action) {
                        is DataScreenAction.SetFullYear -> viewModel.setFullYear()
                        is DataScreenAction.SetInsuranceCoverage -> viewModel.setInsuranceCoverage(
                            action.coverage
                        )

                        is DataScreenAction.SetPandemicProtection -> viewModel.setPandemicProtection()
                        is DataScreenAction.SetSki -> viewModel.setSki()
                        is DataScreenAction.SetTravelReason -> viewModel.setTravelReason(action.reason)
                        is DataScreenAction.SetBirthDate -> viewModel.setBirthDate(action.birthDate)
                        is DataScreenAction.SetCoverageArea -> viewModel.setCoverageArea(action.coverage)
                        is DataScreenAction.SetCountryCode -> viewModel.setCountryCode(action.countryCode)
                        is DataScreenAction.SetStartDate -> viewModel.setStartDate(action.begin)
                        is DataScreenAction.SetEndDate -> viewModel.setEndDate(action.end)
                        is DataScreenAction.GetOffers -> viewModel.getOffers()
                        else -> {}
                    }
                }
            )
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun DataScreenForm(
    uiState: DataUiState,
    countries: List<CountryItem>,
    onAction: (DataScreenAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Tell us about your trip",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
        Spacer(Modifier.height(40.dp))
        DateField(
            label = "Birthdate",
            date = uiState.birthDate,
            onDateChanged = { onAction(DataScreenAction.SetBirthDate(it)) }
        )
        DateField(
            label = "Start date",
            date = uiState.insuranceBeginDate,
            onDateChanged = { onAction(DataScreenAction.SetStartDate(it)) }
        )
        DateField(
            label = "End date",
            date = uiState.insuranceEndDate,
            onDateChanged = { onAction(DataScreenAction.SetEndDate(it)) }
        )
        EnumDropdownField(
            label = "Insurance Coverage",
            options = InsuranceCoverage.entries,
            selectedOption = uiState.insuranceCoverage,
            onOptionSelected = { onAction(DataScreenAction.SetInsuranceCoverage(it)) },
            optionLabel = { it.displayName }
        )

        EnumDropdownField(
            label = "Travel Reason",
            options = TravelReason.entries,
            selectedOption = uiState.travelReason,
            onOptionSelected = { onAction(DataScreenAction.SetTravelReason(it)) },
            optionLabel = { it.displayName }
        )

        EnumDropdownField(
            label = "Coverage Area",
            options = CoverageArea.entries,
            selectedOption = uiState.coverageArea,
            onOptionSelected = { onAction(DataScreenAction.SetCoverageArea(it)) },
            optionLabel = { it.displayName }
        )
        DestinationCountryField(
            countryCode = uiState.countryCode,
            countries = countries,
            onCountrySelected = { onAction(DataScreenAction.SetCountryCode(it)) }
        )

        LabeledSwitch(
            label = "Full year",
            checked = uiState.fullYear,
            onCheckedChange = { onAction(DataScreenAction.SetFullYear) }
        )

        LabeledSwitch(
            label = "Pandemic protection",
            checked = uiState.pandemicProtection,
            onCheckedChange = { onAction(DataScreenAction.SetPandemicProtection) }
        )

        LabeledSwitch(
            label = "Include ski protection",
            checked = uiState.ski,
            onCheckedChange = { onAction(DataScreenAction.SetSki) }
        )

        GetOfferButton(
            onClick = { onAction(DataScreenAction.GetOffers)}
        )
    }
}

@Composable
fun DateField(
    label: String,
    date: LocalDate?,
    onDateChanged: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }


    val datePickerDialog = remember {
        val currentDate = LocalDate.now()
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                onDateChanged(LocalDate.of(year, month + 1, dayOfMonth))
            },
            date?.year ?: currentDate.year,
            date?.monthValue?.minus(1) ?: (currentDate.monthValue - 1),
            date?.dayOfMonth ?: currentDate.dayOfMonth
        )
    }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> {
                    datePickerDialog.show()
                }
            }
        }
    }

    Column(modifier = modifier) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp, start = 8.dp)
        )

        OutlinedTextField(
            value = date?.format(
                DateTimeFormatter.ofPattern(
                    "yyyy/MM/dd",
                    Locale.ENGLISH
                )
            ) ?: "",
            placeholder = {
                Text(
                    text = "",
                    color = Color.Gray.copy(alpha = 0.6f),
                    fontWeight = FontWeight.Medium
                )
            },
            onValueChange = {},
            interactionSource = interactionSource,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .height(55.dp),
            readOnly = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White
            ),
            singleLine = true,
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            ),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> EnumDropdownField(
    label: String,
    options: List<T>,
    selectedOption: T,
    onOptionSelected: (T) -> Unit,
    optionLabel: (T) -> String
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(bottom = 8.dp, start = 8.dp)
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = optionLabel(selectedOption),
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White
                ),
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(optionLabel(option)) },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DestinationCountryField(
    countryCode: String,
    countries: List<CountryItem>,
    onCountrySelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val selectedCountryName = countries.find { it.code == countryCode }?.name ?: ""

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Destination Country",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(bottom = 8.dp, start = 8.dp)
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedCountryName,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White
                ),
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                countries.forEach { country ->
                    DropdownMenuItem(
                        text = { Text(country.name) },
                        onClick = {
                            onCountrySelected(country.code)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun LabeledSwitch(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    labelColor: Color = Color.White
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = labelColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(start = 8.dp)
        )

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                uncheckedThumbColor = Color.Gray,
                checkedTrackColor = Color(0xFF073629),
                uncheckedTrackColor = Color.LightGray
            )
        )
    }
}

@Composable
fun GetOfferButton(
    onClick: (DataScreenAction) -> Unit
) {
    Button(
        onClick = {
            onClick(DataScreenAction.GetOffers)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(bottom = 8.dp),
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF073629),
            disabledContainerColor = Color(0xFF073629).copy(alpha = 0.7f),
            disabledContentColor = Color.Black.copy(alpha = 0.7f)
        ),
    ) {
        Text(
            text = "Get offer",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

