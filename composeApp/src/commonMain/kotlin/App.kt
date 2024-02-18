import androidx.compose.runtime.Composable
import moe.tlaster.precompose.PreComposeApp
import org.koin.compose.KoinContext
import presentation.navigation.AppNavigation

@Composable
fun App() {
    KoinContext {
        PreComposeApp {
            AppTheme {
                AppNavigation()
            }
        }
    }
}