package com.example.imirly.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import com.example.imirly.data.local.SessionStore
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    onFinish: () -> Unit
) {
    val context = LocalContext.current
    val sessionStore = remember { SessionStore(context) }
    val scope = rememberCoroutineScope()

    val pages = listOf(
        OnboardingPage(
            title = "Encuentra profesionales cerca de ti",
            description = "Conecta con expertos en múltiples categorías: hogar, clases, deportes, belleza y más."
        ),
        OnboardingPage(
            title = "Conecta fácilmente",
            description = "Habla directamente con quien ofrece el servicio y acuerda los detalles sin intermediarios."
        ),
        OnboardingPage(
            title = "Reserva con confianza",
            description = "Sistema de valoraciones, pagos seguros y chat directo con profesionales."
        )
    )

    val pagerState = rememberPagerState { pages.size }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primaryContainer
                    )
                )
            )
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Spacer(Modifier.height(40.dp))

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                OnboardingCard(pages[page])
            }

            Spacer(Modifier.height(24.dp))

            // Indicadores
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pages.size) { index ->
                    val selected = pagerState.currentPage == index
                    Box(
                        modifier = Modifier
                            .padding(6.dp)
                            .size(if (selected) 10.dp else 8.dp)
                            .background(
                                color = if (selected)
                                    MaterialTheme.colorScheme.onPrimary
                                else
                                    MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.4f),
                                shape = CircleShape
                            )
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // Botones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                TextButton(
                    onClick = {
                        scope.launch {
                            sessionStore.setOnboardingDone()
                            onFinish()
                        }
                    }
                ) {
                    Text(
                        "Omitir",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }

                Button(
                    onClick = {
                        scope.launch {
                            if (pagerState.currentPage == pages.lastIndex) {
                                sessionStore.setOnboardingDone()
                                onFinish()
                            } else {
                                pagerState.animateScrollToPage(
                                    pagerState.currentPage + 1
                                )
                            }
                        }
                    },
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        if (pagerState.currentPage == pages.lastIndex)
                            "Comenzar"
                        else
                            "Siguiente"
                    )
                }
            }
        }
    }
}

@Composable
fun OnboardingCard(
    page: OnboardingPage
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(180.dp))
            //  aquí luego metemos la imagen

            Text(
                text = page.title,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = page.description,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}
