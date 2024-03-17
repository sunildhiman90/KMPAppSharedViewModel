import LoginViewModel
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.icerock.moko.mvvm.flow.compose.observeAsActions


@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = viewModel()
) {


    val username by loginViewModel.username.collectAsStateWithLifecycle()
    val password by loginViewModel.password.collectAsStateWithLifecycle()
    val isProcessing by loginViewModel.isProcessing.collectAsStateWithLifecycle()
    val isButtonEnabled by loginViewModel.isButtonEnabled.collectAsStateWithLifecycle()

    val context = LocalContext.current

    loginViewModel.actions.observeAsActions {

        //Navigate to home screen
        Toast.makeText(context, "Login Successful", Toast.LENGTH_LONG).show()
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Login", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = username,
            enabled = !isProcessing,
            label = {
                Text("Username")
            },
            onValueChange = {
                loginViewModel.username.value = it
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            enabled = !isProcessing,
            visualTransformation = PasswordVisualTransformation(),
            label = {
                Text("Password")
            },
            onValueChange = {
                loginViewModel.password.value = it
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (isProcessing) {
            CircularProgressIndicator()
        } else {
            Button(
                modifier = Modifier.fillMaxWidth().height(48.dp),
                enabled = isButtonEnabled,
                onClick = {
                    loginViewModel.onButtonPressed()
                }) {
                Text("Submit")
            }
        }

    }

}