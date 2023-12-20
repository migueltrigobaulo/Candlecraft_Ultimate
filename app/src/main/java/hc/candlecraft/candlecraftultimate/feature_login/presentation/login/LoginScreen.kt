package hc.candlecraft.candlecraftultimate.feature_login.presentation.login

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import hc.candlecraft.candlecraftultimate.R
import hc.candlecraft.candlecraftultimate.navigation.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        Login(Modifier.align(Alignment.Center), navController, viewModel)
    }
}

@Composable
fun Login(modifier: Modifier, navController: NavController, viewModel: LoginViewModel) {

    val email: String by viewModel.email.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")
    val loginEnabled: Boolean by viewModel.loginEnabled.observeAsState(initial = false)
    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)
    val corroutineScope = rememberCoroutineScope()

    val animationTimer = 1000L
    val animationTimer1Finished: Boolean by viewModel.animationTimer1Finished.observeAsState(initial = false)
    val animationTimer2Finished: Boolean by viewModel.animationTimer2Finished.observeAsState(initial = false)

    val lifecycle = LocalLifecycleOwner.current.lifecycle


    LaunchedEffect(key1 = true) {
        launch {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.finishedLoading.collectLatest { finishedLoading: Boolean ->
                    if (finishedLoading) {
                        navController.popBackStack()
                        navController.navigate(Screen.RecipesScreen.route)
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = true) {
        launch {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.animationTimer1Finished()
                delay(animationTimer)
                if (false) {
                    viewModel.animationTimer2Finished()
                } else {
                    navController.popBackStack()
                    navController.navigate(Screen.RecipesScreen.route)
                }
            }
        }
    }

    AnimatedVisibility(
        modifier = modifier,
        visible = isLoading,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
    AnimatedVisibility(
        modifier = modifier,
        visible = !isLoading,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column(modifier = modifier) {
            AnimatedVisibility(
                visible = animationTimer1Finished,
                enter = fadeIn(animationSpec = tween(1000)),
                exit = fadeOut()
            ) {
                HeaderImage(Modifier.align(CenterHorizontally))
            }
            AnimatedVisibility(
                visible = animationTimer2Finished,
                enter = expandVertically(animationSpec = tween(1000)).plus(
                    fadeIn(
                        animationSpec = tween(
                            1500
                        )
                    )
                ),
                exit = shrinkVertically(animationSpec = tween(1000)).plus(
                    fadeOut(
                        animationSpec = tween(
                            1500
                        )
                    )
                )
            ) {
                Column {
                    Spacer(modifier = Modifier.padding(16.dp))
                    EmailField(email) { viewModel.onLoginChanged(it, password) }
                    Spacer(modifier = Modifier.padding(4.dp))
                    PasswordField(password) { viewModel.onLoginChanged(email, it) }
                    Spacer(modifier = Modifier.padding(8.dp))
                    ForgotPassword(Modifier.align(Alignment.End))
                    Spacer(modifier = Modifier.padding(16.dp))
                    LoginButton(loginEnabled) { corroutineScope.launch { viewModel.onLoginClicked() } }
                }
            }
        }
    }
}

@Composable
fun HeaderImage(modifier: Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Image(
            modifier = modifier
                .height(150.dp)
                .width(210.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
            painter = painterResource(id = R.drawable.image_candle_1),
            contentDescription = "Login Logo"
        )
        Image(
            modifier = modifier
                .height(100.dp)
                .width(250.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
            painter = painterResource(id = R.drawable.candlecraft_logo_name),
            contentDescription = "Login Logo"
        )
    }
}

@Composable
fun EmailField(email: String, onTextFieldChanged: (String) -> Unit) {
    OutlinedTextField(
        value = email,
        shape = RoundedCornerShape(15.dp),
        onValueChange = { onTextFieldChanged(it) },
        placeholder = { Text(text = stringResource(R.string.email)) },
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun PasswordField(password: String, onTextFieldChanged: (String) -> Unit) {
    OutlinedTextField(
        value = password,
        onValueChange = { onTextFieldChanged(it) },
        shape = RoundedCornerShape(15.dp),
        placeholder = { Text(text = stringResource(R.string.password)) },
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun ForgotPassword(modifier: Modifier) {
    Text(
        text = stringResource(R.string.forgot_password),
        modifier = modifier
            .clickable { },
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun LoginButton(loginEnabled: Boolean, onLoginClicked: () -> Unit) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .imePadding(),
            onClick = { onLoginClicked() },
            enabled = loginEnabled
        ){ Text(text = stringResource(R.string.login))}
}