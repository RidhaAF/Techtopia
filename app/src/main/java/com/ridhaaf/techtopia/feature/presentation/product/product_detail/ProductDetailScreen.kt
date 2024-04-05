package com.ridhaaf.techtopia.feature.presentation.product.product_detail

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ridhaaf.techtopia.core.presentation.components.DefaultButton
import com.ridhaaf.techtopia.core.presentation.components.DefaultErrorText
import com.ridhaaf.techtopia.core.presentation.components.DefaultProgressIndicator
import com.ridhaaf.techtopia.core.presentation.components.DefaultTopAppBar
import com.ridhaaf.techtopia.core.presentation.components.ProductDescription
import com.ridhaaf.techtopia.core.presentation.components.ProductName
import com.ridhaaf.techtopia.core.presentation.components.ProductPrice
import com.ridhaaf.techtopia.core.presentation.components.VerticalSpacer
import com.ridhaaf.techtopia.core.presentation.components.defaultToast
import com.ridhaaf.techtopia.feature.data.models.product.Product

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductDetailViewModel = hiltViewModel(),
    navController: NavController? = null,
    id: String,
) {
    val state = viewModel.state.value
    val error = state.productError
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(ProductDetailEvent.Refresh(id))
    }

    LaunchedEffect(key1 = error) {
        if (error.isNotBlank()) {
            defaultToast(context, error)
        }
    }

    Scaffold(
        topBar = { ProductDetailTopBar(navController) },
    ) {
        val refreshing = viewModel.isRefreshing.value
        val pullRefreshState = rememberPullRefreshState(
            refreshing = refreshing,
            onRefresh = { viewModel.onEvent(ProductDetailEvent.Refresh(id)) },
        )
        val verticalScrollState = rememberScrollState()

        Box(
            modifier = modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
                .verticalScroll(verticalScrollState)
                .padding(it),
        ) {
            ProductDetailContent(viewModel, state)
            PullRefreshIndicator(
                refreshing = refreshing,
                state = pullRefreshState,
                modifier = modifier.align(Alignment.TopCenter),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductDetailTopBar(navController: NavController? = null) {
    DefaultTopAppBar(
        title = "",
        showBackButton = true,
        navController = navController,
    )
}

@Composable
private fun ProductDetailContent(
    viewModel: ProductDetailViewModel,
    state: ProductDetailState,
) {
    val loading = state.isProductLoading
    val product = state.productSuccess
    val error = state.productError
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        if (loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                DefaultProgressIndicator()
            }
        } else if (product != null) {
            ProductDetailSection(product)
            AddToCartSection(viewModel, state, context, product)
        } else {
            DefaultErrorText(
                modifier = Modifier.padding(horizontal = 16.dp),
                message = error,
            )
        }
    }
}

@Composable
private fun ProductDetailSection(product: Product) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        ProductImageContent(product)
        ProductDetailContent(product)
    }
}

@Composable
private fun ProductImageContent(product: Product) {
    val image = product.imagesUrl.firstOrNull()
    val name = product.name

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .background(color = MaterialTheme.colorScheme.secondary)
    ) {
        AsyncImage(
            model = image,
            contentDescription = name,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
private fun ProductDetailContent(product: Product) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        ProductPrice(
            modifier = Modifier.padding(horizontal = 16.dp),
            price = product.price,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
            ),
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.onSecondary)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ProductName(
                name = product.name,
                isLimited = false,
                style = MaterialTheme.typography.bodyLarge,
            )
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Rounded.FavoriteBorder,
                    contentDescription = "Wishlist",
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Sold ${product.sold} ",
                style = MaterialTheme.typography.bodyMedium,
            )
            Row {
                Icon(
                    imageVector = Icons.Rounded.Star,
                    contentDescription = "Rating",
                    modifier = Modifier.size(16.dp),
                    tint = Color.Yellow,
                )
                Text(
                    text = product.rating.toString(),
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
        ProductDescription(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            description = product.description,
        )
        VerticalSpacer()
    }
}

@Composable
private fun AddToCartSection(
    viewModel: ProductDetailViewModel,
    state: ProductDetailState,
    context: Context,
    product: Product,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(64.dp)
            .background(MaterialTheme.colorScheme.inverseOnSurface),
        contentAlignment = Alignment.Center,
    ) {
        val loading = state.isCartLoading
        val success = state.cartSuccess
        val error = state.cartError

        DefaultButton(
            modifier = Modifier.padding(horizontal = 16.dp),
            onClick = {
                val id = product.id
                viewModel.onEvent(ProductDetailEvent.AddToCart(id))
            },
        ) {
            val text = if (loading) "Adding to cart..." else "+ Cart"

            Text(
                text,
                fontWeight = FontWeight.Bold,
            )
        }

        LaunchedEffect(key1 = state) {
            if (success != null) {
                defaultToast(context, "Successfully added to cart")
            } else if (error.isNotBlank()) {
                defaultToast(context, error)
            }
        }
    }
}