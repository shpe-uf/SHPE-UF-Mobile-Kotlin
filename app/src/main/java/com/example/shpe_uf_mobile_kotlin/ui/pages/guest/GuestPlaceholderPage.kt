package com.example.shpe_uf_mobile_kotlin.ui.pages.guest

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.shpe_uf_mobile_kotlin.R
import com.example.shpe_uf_mobile_kotlin.data.SHPEUFAppViewModel
import com.example.shpe_uf_mobile_kotlin.ui.pages.sponsors.GuestTopHeader
import com.example.shpe_uf_mobile_kotlin.ui.theme.ThemeColors
import com.example.shpe_uf_mobile_kotlin.ui.theme.headerOrange
import kotlin.math.roundToInt
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.Path


@Composable
fun AboutUsTopBar(isDarkMode: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(83.dp)
            .background(Color(0xFFD25917)),
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            text = "Who We Are",
            color = Color.White,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 12.dp)
        )
    }
}

@Composable
fun MissionStatementSection(isDarkMode: Boolean) {
    val textColor = if (isDarkMode) Color.White else Color.Black
    val logoRes = if (isDarkMode) R.drawable.guest_logo_dark else R.drawable.guest_logo_light
    val horizontalPadding = 32.dp // wider margins

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = logoRes),
            contentDescription = "SHPE Logo",
            modifier = Modifier
                .size(320.dp)
                .padding(bottom = 12.dp)
        )

        Text(
            text = "SHPE",
            style = MaterialTheme.typography.headlineLarge,
            color = textColor,
            fontSize = 28.sp
        )
        Text(
            text = "Leading Hispanics in STEM",
            color = Color(0xFFD25917)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Column(modifier = Modifier.padding(horizontal = horizontalPadding)) {
            Text(
                text = "Empowering the Hispanic community to realize its fullest potential and to impact the world through STEM awareness, access, support and development.",
                color = textColor,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .width(50.dp)
                    .height(2.dp)
                    .background(textColor)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "The Society of Hispanic Professional Engineers Chapter at the University of Florida (SHPE UF) was formerly known as the Hispanic Engineering Society. It was founded in the fall of 1982 in an effort to provide Hispanic engineers, mathematicians, and scientists with opportunities to develop as professionals while offering an amiable social environment.",
                color = textColor,
                fontSize = 18.sp
            )
        }
    }
}


@Composable
fun InstagramCarousel(posts: List<InstagramPost>) {
    var currentIndex by remember { mutableStateOf(0) }

    val visibleCount = 3
    val sideImageFraction = 0.33f // center shows full, sides ~1/3

    val context = LocalContext.current
    val sidePadding = 32.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(450.dp)
            .padding(horizontal = sidePadding),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            posts.forEachIndexed { index, post ->
                val visible = (index >= currentIndex - 1 && index <= currentIndex + 1)
                if (visible) {
                    val scale = if (index == currentIndex) 1.05f else 0.85f
                    val alpha = if (index == currentIndex) 1f else 0.4f
                    val imageHeight = 380.dp
                    val imageWidth = if (index == currentIndex) 280.dp else 220.dp

                    Box(
                        modifier = Modifier
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                                this.alpha = alpha
                            }
                            .width(imageWidth)
                            .height(imageHeight)
                    ) {
                        InstagramPostCard(post)
                    }
                }
            }
        }

        // Left arrow
        Icon(
            painter = painterResource(R.drawable.backbuttonicon),
            contentDescription = "Prev",
            tint = Color.Gray,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(40.dp)
                .clickable {
                    if (currentIndex > 0) currentIndex--
                }
        )

        // Right arrow (mirrored)
        Icon(
            painter = painterResource(R.drawable.backbuttonicon),
            contentDescription = "Next",
            tint = Color.Gray,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(40.dp)
                .graphicsLayer { rotationY = 180f }
                .clickable {
                    if (currentIndex < posts.size - 1) currentIndex++
                }
        )
    }
}

@Composable
fun InstagramPostCard(post: InstagramPost) {
    val context = LocalContext.current

    val drawableResId = context.resources.getIdentifier(
        post.localImageName!!,
        "drawable",
        context.packageName
    )

    Column(
        modifier = Modifier
            .width(250.dp)
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = drawableResId),
            contentDescription = post.caption,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Spacer(Modifier.height(4.dp))
        Text(post.caption ?: "", maxLines = 3)
    }
}

@Composable
fun QuoteSection(isDarkMode: Boolean) {
    val bgColor = Color(0xFFD25917)
    val triangleColor = if (isDarkMode) ThemeColors.Night.background else ThemeColors.Day.background
    val textColor = Color.White
    val horizontalMargin = 32.dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // Inverted triangle cutout
        Canvas(modifier = Modifier.fillMaxWidth().height(20.dp)) {
            val width = size.width
            val height = size.height
            drawPath(
                path = Path().apply {
                    moveTo(width / 2 - 20f, 0f)
                    lineTo(width / 2f, height)
                    lineTo(width / 2 + 20f, 0f)
                    close()
                },
                color = triangleColor
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(bgColor)
                .padding(horizontal = horizontalMargin, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "“What I really hope for young people is that they find a career they’re passionate about, something that’s challenging and worthwhile.”",
                color = textColor,
                fontSize = 18.sp
            )

            Spacer(Modifier.height(12.dp))

            Text("Ellen Ochoa", color = textColor, fontSize = 16.sp)

            Text(
                "Electrical Engineer\n& First Latina Astronaut",
                color = textColor,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun GuestPlaceholderPage(navController: NavHostController, shpeufAppViewModel: SHPEUFAppViewModel) {
    val uiState by shpeufAppViewModel.uiState.collectAsState()
    val isDarkMode = uiState.isDarkMode
    val aboutViewModel = remember { GuestAboutViewModel() }
    val instagramPosts by aboutViewModel.posts.collectAsState()

    LaunchedEffect(Unit) {
        aboutViewModel.loadMockPosts()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = if (isDarkMode) ThemeColors.Night.background else ThemeColors.Day.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AboutUsTopBar(isDarkMode = isDarkMode)

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 64.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                item { MissionStatementSection(isDarkMode = isDarkMode) }

                item {
                    Divider()
                }

                item {
                    InstagramCarousel(instagramPosts)
                }

                item { QuoteSection(isDarkMode = isDarkMode) }
            }
        }
    }
}
