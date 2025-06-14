package com.example.multilingualappjc

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.multilingualappjc.ui.theme.MultiLingualAppJCTheme
import java.util.*

class MainActivity : ComponentActivity() {

    companion object {
        var selectedLanguage: String = "en"
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(applyLanguage(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MultiLingualAppJCTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    LoginScreen { langCode ->
                        selectedLanguage = langCode
                        recreate()
                    }
                }
            }
        }
    }

    private fun applyLanguage(context: Context): Context {
        val locale = Locale(selectedLanguage)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }
}

@Composable
fun LoginScreen(onLanguageChange: (String) -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showLangOptions by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.welcome_back),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(stringResource(id = R.string.username_hint)) },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(id = R.string.password_hint)) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
        )

        Button(
            onClick = {
                if (username == "admin" && password == "admin") {
                    Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Invalid credentials", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(id = R.string.login))
        }

        Button(
            onClick = { showLangOptions = !showLangOptions },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Text(stringResource(id = R.string.change_language))
        }

        if (showLangOptions) {
            Text(
                text = stringResource(id = R.string.choose_language),
                modifier = Modifier.padding(top = 24.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                LanguageButton("en", R.string.english, onLanguageChange)
                LanguageButton("hi", R.string.hindi, onLanguageChange)
                LanguageButton("fr", R.string.french, onLanguageChange)
            }
        }
    }
}
@Composable
fun LanguageButton(code: String, labelRes: Int, onClick: (String) -> Unit) {
    Button(
        onClick = { onClick(code) },
        modifier = Modifier
            .padding(4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Text(text = stringResource(id = labelRes))
    }
}
