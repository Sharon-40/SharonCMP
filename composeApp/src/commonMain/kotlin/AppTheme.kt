import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import data.PlatformUtils
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveTheme
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import io.github.alexzhirkevich.cupertino.adaptive.Theme
import io.github.alexzhirkevich.cupertino.theme.CupertinoTheme
import io.github.alexzhirkevich.cupertino.theme.darkColorScheme
import io.github.alexzhirkevich.cupertino.theme.lightColorScheme
import org.koin.compose.koinInject

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val platformUtils: PlatformUtils = koinInject()

    AdaptiveTheme(
        material = {
            MaterialTheme(
                colorScheme = if (useDarkTheme) {
                    androidx.compose.material3.darkColorScheme()
                } else {
                    androidx.compose.material3.lightColorScheme()
                },
                content = it
            )
        },
        cupertino = {
            CupertinoTheme(
                colorScheme = if (useDarkTheme) {
                    darkColorScheme()
                } else {
                    lightColorScheme()
                },
                content = it
            )

        },
        target = platformUtils.determineTheme(),
        content = content
    )
}