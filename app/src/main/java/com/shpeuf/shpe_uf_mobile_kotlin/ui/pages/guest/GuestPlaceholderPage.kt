package com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.guest

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.shpeuf.shpe_uf_mobile_kotlin.R
import com.shpeuf.shpe_uf_mobile_kotlin.data.SHPEUFAppViewModel
import com.shpeuf.shpe_uf_mobile_kotlin.ui.theme.ThemeColors
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
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
    val textColor = if (isDarkMode) Color.White else Color(0xFF011F35)
    val logoRes = if (isDarkMode) R.drawable.guest_logo_dark else R.drawable.guest_logo_light
    val horizontalPadding = 32.dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp, bottom = 16.dp),
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
            fontSize = 72.sp
        )
        Text(
            text = "Leading Hispanics in STEM",
            color = Color(0xFFD25917),
            fontSize = 14.sp
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
fun InstagramImageItem(post: InstagramPost, isDarkMode: Boolean) {
    val context = LocalContext.current
    val imageName = post.localImageName!!
    val drawableResId = context.resources.getIdentifier(imageName, "drawable", context.packageName)

    val imageAspectRatio = 4f / 3f
    val textColor = if (isDarkMode) Color.White else Color.Black
    val avatarBorder = if (isDarkMode) Color(0x22FFFFFF) else Color(0x22000000)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.wrapContentHeight()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.shpe_1),
                contentDescription = "profile picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .border(1.dp, avatarBorder, CircleShape)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "shpeuf",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            )
        }
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier
                .aspectRatio(imageAspectRatio)
                .clip(RoundedCornerShape(16.dp))
        ) {
            Image(
                painter = painterResource(id = drawableResId),
                contentDescription = post.caption,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text =buildAnnotatedString {
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("shpeuf ")
                }
                append(post.caption ?: "")
            },
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = textColor,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )
    }
}

@Composable
fun InstagramCarousel(posts: List<InstagramPost>, isDarkMode: Boolean) {
    val virtualPageCount = Int.MAX_VALUE
    val startIndex = virtualPageCount / 2
    val pagerState = rememberPagerState(initialPage = startIndex) { virtualPageCount }
    val scope = rememberCoroutineScope()

    // Width & spacing
    val imageWidth = 250.dp
    val pageGap = 24.dp

    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        val boxWidth = maxWidth

        val density = LocalDensity.current
        var pageItemHeight by remember { mutableStateOf(0.dp) }
        val fallbackHeight = (imageWidth / (4f / 3f)) + 120.dp // safe first layout
        val resolvedHeight = if (pageItemHeight > 0.dp) pageItemHeight else fallbackHeight

        LaunchedEffect(pagerState.currentPage) {
            delay(5000)
            scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
        }

        HorizontalPager(
            state = pagerState,
            pageSpacing = pageGap,
            contentPadding = PaddingValues(horizontal = (boxWidth - imageWidth) / 2),
            modifier = Modifier
                .width(boxWidth)
                .height(resolvedHeight)
        ) { index ->
            val actual = index % posts.size
            val post = posts[actual]

            val pageOffset = (
                    (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction
                    ).absoluteValue

            val scale = lerp(start = 0.85f, stop  = 1.08f, fraction = (1f - pageOffset).coerceIn(0f, 1f))
            val alpha = lerp(0.5f, 1f, 1f - pageOffset.coerceIn(0f, 1f))

            Box(
                modifier = Modifier
                    .width(imageWidth)
                    .wrapContentHeight()
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        this.alpha = alpha
                    }
                    .zIndex(if (pageOffset < 0.5f) 1f else 0f) // keep the middle on top
                    .onGloballyPositioned { coords ->
                        val h = with(density) { coords.size.height.toDp() }
                        if (h > 0.dp && pageItemHeight == 0.dp) pageItemHeight = h
                    },
                contentAlignment = Alignment.Center
            ) {
                InstagramImageItem(post = post, isDarkMode = isDarkMode)
            }
        }
    }
}

//Im not adding a triangle drawable blud
@Composable
fun QuoteTriangle(isDarkMode: Boolean) {
    val backgroundColor =  if (isDarkMode) ThemeColors.Night.background else ThemeColors.Day.background

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

                item {Spacer(modifier = Modifier.height(6.dp))}

                item { InstagramCarousel(instagramPosts, isDarkMode = isDarkMode) }

                item {Spacer(modifier = Modifier.height(24.dp))}

                item { QuoteSection(isDarkMode = isDarkMode) }
            }
        }
    }
}
