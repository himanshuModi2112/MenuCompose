import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.CategoryViewModel
import com.example.myapplication.models.Category
import com.example.myapplication.models.Item
import com.example.myapplication.models.SubCategory

@Composable
fun CategoryScreen(viewModel: CategoryViewModel) {
    val breadcrumb by viewModel.breadcrumb.collectAsState()
    val items by viewModel.currentSubcategories.collectAsState()

    // Handle back press
    BackHandler(enabled = breadcrumb.size > 1) {
        viewModel.navigateBack()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Breadcrumb
        Text(
            text = breadcrumb.joinToString(" > "),
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Use grid instead of list
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items) { item ->
                when (item) {
                    is Category -> CategoryCard(item.name) {
                        viewModel.navigateToCategory(item.name, item.subcategories ?: item.items ?: emptyList())
                    }
                    is SubCategory -> SubCategoryCard(item.name) {
                        viewModel.navigateToCategory(item.name, item.subcategories ?: item.items ?: emptyList())
                    }
                    is Item -> ItemCard(item.name, item.price)
                }
            }
        }
    }
}

@Composable
fun CategoryCard(name: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f) // square cards
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50)) // Green 500
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
    }
}

@Composable
fun SubCategoryCard(name: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF87CEFA)) // Light Sky Blue
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }
    }
}


@Composable
fun ItemCard(name: String, price: Int?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f), // square cards
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEB3B)) // Yellow 500
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp),
                color = Color.Black
            )

            if (price != null) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = Color.Black,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "₹$price",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.White)
                    )
                }
            }
        }
    }
}


