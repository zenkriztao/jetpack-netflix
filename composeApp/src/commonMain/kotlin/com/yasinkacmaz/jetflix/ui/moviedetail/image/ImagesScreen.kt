package com.zenkriztao.netflix.ui.moviedetail.image

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.zenkriztao.netflix.LocalNavController
import com.zenkriztao.netflix.ui.theme.spacing
import com.zenkriztao.netflix.ui.widget.CircleIconButton
import com.zenkriztao.netflix.util.JetflixImage
import jetflix.composeapp.generated.resources.Res
import jetflix.composeapp.generated.resources.back
import jetflix.composeapp.generated.resources.likes_content_description
import jetflix.composeapp.generated.resources.poster_content_description
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@Composable
fun ImagesScreen(images: List<Image>, initialPage: Int) {
    if (images.isEmpty() || initialPage !in images.indices) return

    val pagerState = rememberPagerState(initialPage = initialPage, initialPageOffsetFraction = 0f) { images.size }
    val navController = LocalNavController.current
    val coroutineScope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }
    Box(
        Modifier
            .fillMaxSize()
            .focusRequester(focusRequester)
            .focusable()
            .onKeyEvent {
                if (it.type != KeyEventType.KeyDown) return@onKeyEvent false

                when (it.key) {
                    Key.DirectionLeft -> {
                        coroutineScope.launch {
                            val prevPage = (pagerState.currentPage - 1).coerceAtLeast(0)
                            pagerState.animateScrollToPage(prevPage)
                        }
                        true
                    }

                    Key.DirectionRight -> {
                        coroutineScope.launch {
                            val nextPage = (pagerState.currentPage + 1).coerceAtMost(pagerState.pageCount - 1)
                            pagerState.animateScrollToPage(nextPage)
                        }
                        true
                    }

                    else -> false
                }
            },
    ) {
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
        HorizontalPager(state = pagerState, key = { images[it].url + it }, beyondViewportPageCount = 3) {
            Poster(images[it])
        }
        Index(position = pagerState.currentPage + 1, imageCount = pagerState.pageCount)
        CircleIconButton(
            modifier = Modifier.statusBarsPadding().padding(MaterialTheme.spacing.l),
            onClick = { navController.navigateUp() },
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = stringResource(Res.string.back),
            )
        }
    }
}

@Composable
private fun Poster(image: Image) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        BlurImage(image.url)
        Card(
            modifier = Modifier
                .systemBarsPadding()
                .padding(MaterialTheme.spacing.xxl)
                .shadow(16.dp, RoundedCornerShape(12.dp))
                .wrapContentSize()
                .animateContentSize(),
        ) {
            Box {
                JetflixImage(
                    data = image.url,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .align(Alignment.Center),
                )
                VoteCount(image.voteCount)
            }
        }
    }
}

@Composable
private fun BlurImage(url: String) {
    JetflixImage(
        data = url,
        contentDescription = stringResource(Res.string.poster_content_description),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .blur(16.dp),
    )
}

@Composable
private fun BoxScope.VoteCount(voteCount: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .wrapContentSize()
            .align(Alignment.BottomStart)
            .background(
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f),
                shape = RoundedCornerShape(bottomStart = 12.dp, topEnd = 12.dp),
            )
            .padding(MaterialTheme.spacing.xs),
    ) {
        Icon(
            imageVector = Icons.Filled.Favorite,
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = stringResource(Res.string.likes_content_description),
            modifier = Modifier.padding(end = MaterialTheme.spacing.xs),
        )
        Text(text = voteCount.toString(), style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun BoxScope.Index(position: Int, imageCount: Int) {
    Text(
        text = "$position / $imageCount",
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .navigationBarsPadding()
            .padding(MaterialTheme.spacing.xs)
            .shadow(16.dp, RoundedCornerShape(16.dp))
            .background(color = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f))
            .padding(horizontal = MaterialTheme.spacing.s, vertical = MaterialTheme.spacing.xxs),
    )
}
