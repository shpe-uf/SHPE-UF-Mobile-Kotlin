package com.example.shpe_uf_mobile_kotlin.ui.pages.guest

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.shpe_uf_mobile_kotlin.R
import com.example.shpe_uf_mobile_kotlin.data.SHPEUFAppViewModel
import com.example.shpe_uf_mobile_kotlin.ui.theme.ThemeColors
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.util.lerp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

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

//https://proandroiddev.com/swipeable-image-carousel-with-smooth-animations-in-jetpack-compose-76eacdc89bfb

@Composable
fun InstagramCarousel(posts: List<InstagramPost>) {
    val virtualPageCount = Int.MAX_VALUE
    val startIndex = virtualPageCount / 2
    val pagerState = rememberPagerState(initialPage = startIndex) { virtualPageCount }
    val coroutineScope = rememberCoroutineScope()

    val imageWidth = 190.dp
    val imageAspectRatio = 4f / 5f
    val imageHeight = imageWidth / imageAspectRatio

    val arrowButtonSize = 40.dp
    val edgePadding = 16.dp
    val totalHorizontalPadding = arrowButtonSize * 2 + edgePadding * 2

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(imageHeight + 40.dp),
        contentAlignment = Alignment.Center
    ) {
        val boxWidth = maxWidth

        // ⏱️ Auto-scroll every 4s
        LaunchedEffect(pagerState.currentPage) {
            delay(4000)
            coroutineScope.launch {
                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            }
        }

        // 🎠 Carousel
        HorizontalPager(
            state = pagerState,
            pageSpacing = -(imageWidth / 2f), // less occlusion
            contentPadding = PaddingValues(horizontal = edgePadding),
            modifier = Modifier
                .width(boxWidth - totalHorizontalPadding)
                .height(imageHeight)
        ) { index ->
            val actualIndex = index % posts.size
            val post = posts[actualIndex]

            val pageOffset = (
                    (pagerState.currentPage - index) +
                            pagerState.currentPageOffsetFraction
                    ).absoluteValue

            val scale = lerp(0.85f, 1.1f, 1f - pageOffset.coerceIn(0f, 1f))

            Box(
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
                    .zIndex(if (pageOffset < 0.5f) 1f else 0f)
                    .width(imageWidth)
                    .aspectRatio(imageAspectRatio)
                    .clip(RoundedCornerShape(0.dp))
                    .background(Color.LightGray)
            ) {
                InstagramImageItem(post = post)
            }
        }

        // ⬅️ Left Button
        IconButton(
            onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                }
            },
            modifier = Modifier
                .size(arrowButtonSize)
                .align(Alignment.CenterStart)
                .padding(start = edgePadding)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.backbuttonicon),
                contentDescription = "Previous",
                tint = Color.Unspecified,
                modifier = Modifier.size(arrowButtonSize * 0.75f)
            )
        }

        // ➡️ Right Button
        IconButton(
            onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            },
            modifier = Modifier
                .size(arrowButtonSize)
                .align(Alignment.CenterEnd)
                .padding(end = edgePadding)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.backbuttonicon),
                contentDescription = "Next",
                tint = Color.Unspecified,
                modifier = Modifier
                    .graphicsLayer { rotationY = 180f }
                    .size(arrowButtonSize * 0.75f)
            )
        }
    }
}

@Composable
fun InstagramImageItem(post: InstagramPost, useLowRes: Boolean = false) {
    val context = LocalContext.current
    val imageName = if (useLowRes) "${post.localImageName}_lowres" else post.localImageName!!
    val drawableResId = context.resources.getIdentifier(imageName, "drawable", context.packageName)

    Image(
        painter = painterResource(id = drawableResId),
        contentDescription = post.caption,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(4f/5f)
    )
}

//Im not adding a triangle drawable blud
@Composable
fun QuoteTriangle(isDarkMode: Boolean) {
    val backgroundColor = if (isDarkMode) Color(0xFF011F35) else Color(0xFFFFFFFF) // white for dark mode, SHPE blue for light

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(16.dp)
    ) {
        val triangleWidth = 40.dp.toPx()
        val triangleHeight = size.height
        val centerX = size.width / 2f

        val path = Path().apply {
            moveTo(centerX - triangleWidth / 2f, 0f)
            lineTo(centerX, triangleHeight)
            lineTo(centerX + triangleWidth / 2f, 0f)
            close()
        }

        drawPath(path = path, color = backgroundColor)
    }
}

@Composable
fun QuoteSection(isDarkMode: Boolean) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color(0xFFD25917))
    ) {
        QuoteTriangle(isDarkMode)
        Column(
            modifier = Modifier
                .padding(horizontal = 32.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "“What I really hope for young people is that they find a career they’re passionate about, something that’s challenging and worthwhile.”",
                color = Color.White,
                fontSize = 18.sp
            )
            Spacer(Modifier.height(12.dp))
            Text("Ellen Ochoa", color = Color.White, fontSize = 16.sp)
            Text("Electrical Engineer", color = Color.White, fontSize = 14.sp)
            Text("& First Latina Astronaut", color = Color.White, fontSize = 14.sp)
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
                    .padding(bottom = 0.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                item { MissionStatementSection(isDarkMode = isDarkMode) }

                item { InstagramCarousel(instagramPosts) }

                item { QuoteSection(isDarkMode = isDarkMode) }
            }
        }
    }
}
