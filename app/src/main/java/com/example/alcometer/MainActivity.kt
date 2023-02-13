package com.example.alcometer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.alcometer.ui.theme.AlcometerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlcometerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AlcoScreen()
                }
            }
        }
    }
}

@Composable
fun AlcoScreen() {
    val title = stringResource(R.string.title)
    var weightInput by rememberSaveable { mutableStateOf("") }
    var weight = weightInput.toIntOrNull() ?: 0
    var male by rememberSaveable { mutableStateOf(true) }
    var bottles by rememberSaveable { mutableStateOf(0) }
    var hours by rememberSaveable { mutableStateOf(1) }
    var result: Float by rememberSaveable { mutableStateOf(0f) }
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        Header(title = title)
        WeightField(weightInput = weightInput, onChange = { weightInput = it })
        SexChoice(male = male, setSexMale = { male = it })
        BottlesList(onClick = { bottles = it })
        HoursList(onClick = { hours = it })
        ResultButton(
            male = male,
            weight = weight,
            bottles = bottles,
            hours = hours,
            setResult = { result = it })
        Text(
            text = result.toString(),
            color = MaterialTheme.colors.secondary,
            fontWeight = FontWeight.Bold
        )
        Text(text = bottles.toString())
        Text(text = hours.toString())

    }
}

@Composable
fun Header(title: String) {
    Text(
        text = title,
        fontSize = 24.sp,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp)
    )
}

@Composable
fun WeightField(weightInput: String, onChange: (String) -> Unit) {
    OutlinedTextField(
        value = weightInput,
        onValueChange = onChange,
        label = { Text(text = stringResource(R.string.weightLabel)) },
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun SexChoice(male: Boolean, setSexMale: (Boolean) -> Unit) {
    Column(Modifier.selectableGroup()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = male, onClick = { setSexMale(true) })
            Text(text = "Male")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = !male, onClick = { setSexMale(false) })
            Text(text = "Female")
        }
    }
}

@Composable
fun BottlesList(onClick: (Int) -> Unit) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedText by rememberSaveable { mutableStateOf("0") }
    val items = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
    var textSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column {
        OutlinedTextField(
            readOnly = true,
            value = selectedText,
            onValueChange = { selectedText = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textSize = coordinates.size.toSize()
                },
            label = { Text(text = "Select bottles") },
            trailingIcon = {
                Icon(icon,
                    contentDescription = stringResource(R.string.keyboardIconDescription),
                    Modifier.clickable { expanded = !expanded })
            },
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(with(LocalDensity.current) { textSize.width.toDp() })
        ) {
            items.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedText = label

                    val bottles: Int = label.toIntOrNull() ?: 0
                    onClick(bottles)
                    expanded = false
                }) {
                    Text(text = label)
                }

            }
        }
    }
}

@Composable
fun HoursList(onClick: (Int) -> Unit) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedText by rememberSaveable { mutableStateOf("1") }
    val items = listOf(
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "10",
        "11",
        "12",
        "13",
        "14",
        "15",
        "16",
        "17",
        "18",
        "19",
        "20",
        "21",
        "22",
        "23",
        "24"
    )
    var textSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column {
        OutlinedTextField(
            readOnly = true,
            value = selectedText,
            onValueChange = { selectedText = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textSize = coordinates.size.toSize()
                },
            label = { Text(text = "Select hours") },
            trailingIcon = {
                Icon(icon,
                    contentDescription = stringResource(R.string.keyboardIconDescription),
                    Modifier.clickable { expanded = !expanded })
            },
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(with(LocalDensity.current) { textSize.width.toDp() })
        ) {
            items.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedText = label

                    val bottles: Int = label.toIntOrNull() ?: 1
                    onClick(bottles)
                    expanded = false
                }) {
                    Text(text = label)
                }

            }
        }
    }
}

@Composable
fun ResultButton(male: Boolean, weight: Int, bottles: Int, hours: Int, setResult: (Float) -> Unit) {
    Button(
        onClick = {
            val liters: Float = bottles * 0.33f
            val grams: Float = liters * 8f * 4.5f
            val burning: Float = weight / 10f
            val gramsLeft: Float = grams - (burning * hours)
            if (male) {
                setResult((gramsLeft / (weight * 0.7)).toFloat())
            } else {
                setResult((gramsLeft / (weight * 0.6)).toFloat())
            }
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Calculate")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AlcometerTheme {
        AlcoScreen()
    }
}