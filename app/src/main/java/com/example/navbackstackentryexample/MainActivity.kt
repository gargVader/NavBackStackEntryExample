package com.example.navbackstackentryexample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navbackstackentryexample.ui.theme.NavBackStackEntryExampleTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ACTIVITY_LIFECYCLE", "Activity onCreate")
        enableEdgeToEdge()
        setContent {
            NavBackStackEntryExampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("ACTIVITY_LIFECYCLE", "Activity onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("ACTIVITY_LIFECYCLE", "Activity onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("ACTIVITY_LIFECYCLE", "Activity onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("ACTIVITY_LIFECYCLE", "Activity onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ACTIVITY_LIFECYCLE", "Activity onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("ACTIVITY_LIFECYCLE", "Activity onRestart")
    }
}

@Composable
private fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "screenA",
        modifier = modifier
    ) {
        composable("screenA") { backStackEntry ->
            ObserveBackStackLifecycle(backStackEntry, "ScreenA")

            ScreenContent(
                screenName = "Screen A",
                onNavigateForward = { navController.navigate("screenB") }
            )
        }

        composable("screenB") { backStackEntry ->
            ObserveBackStackLifecycle(backStackEntry, "ScreenB")

            ScreenContent(
                screenName = "Screen B",
                onNavigateForward = { navController.navigate("screenC") }
            )
        }

        composable("screenC") { backStackEntry ->
            ObserveBackStackLifecycle(backStackEntry, "ScreenC")

            ScreenContent(
                screenName = "Screen C",
                onNavigateForward = null // Last screen, no forward navigation
            )
        }
    }
}

@Composable
private fun ObserveBackStackLifecycle(backStackEntry: NavBackStackEntry, screenName: String) {
    DisposableEffect(backStackEntry) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE ->
                    Log.d("NAV_LIFECYCLE", "NavBackStackEntry($screenName) onCreate")
                Lifecycle.Event.ON_START ->
                    Log.d("NAV_LIFECYCLE", "NavBackStackEntry($screenName) onStart")
                Lifecycle.Event.ON_RESUME ->
                    Log.d("NAV_LIFECYCLE", "NavBackStackEntry($screenName) onResume")
                Lifecycle.Event.ON_PAUSE ->
                    Log.d("NAV_LIFECYCLE", "NavBackStackEntry($screenName) onPause")
                Lifecycle.Event.ON_STOP ->
                    Log.d("NAV_LIFECYCLE", "NavBackStackEntry($screenName) onStop")
                Lifecycle.Event.ON_DESTROY ->
                    Log.d("NAV_LIFECYCLE", "NavBackStackEntry($screenName) onDestroy")
                Lifecycle.Event.ON_ANY -> {}
            }
        }
        backStackEntry.lifecycle.addObserver(observer)
        onDispose {
            backStackEntry.lifecycle.removeObserver(observer)
        }
    }
}

@Composable
private fun ScreenContent(
    screenName: String,
    onNavigateForward: (() -> Unit)?
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = screenName, style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Check Logcat with tags:\nAPP_LIFECYCLE, ACTIVITY_LIFECYCLE, NAV_LIFECYCLE",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(24.dp))

        if (onNavigateForward != null) {
            Button(onClick = onNavigateForward) {
                Text("Navigate Forward →")
            }
        } else {
            Text(
                text = "Last screen. Press back to pop.",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
