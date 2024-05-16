package com.mlb.news.playground

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.models.Article

@Composable
fun NewsFeedPage(viewModel: NewsFeedViewModel = hiltViewModel()){
    val articleList by viewModel.articleList.collectAsState()
    val internetConnection by viewModel.internetConnection.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(key1 = coroutineScope) {
        viewModel.setList()
    }
    viewModel.checkForInternet(context)

    Column {
        Text(text = stringResource(id = R.string.mlb_news_playground),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp)
                .align(Alignment.CenterHorizontally)
                .testTag("MLBTitle"))
        ArticleList(list = articleList, hasInternet = internetConnection)
    }

}

@Composable
fun ArticleList(modifier: Modifier = Modifier.fillMaxHeight(), list: List<Article>, hasInternet: Boolean){
    val context = LocalContext.current
    LazyColumn(modifier = Modifier.padding(start = 10.dp, end = 10.dp).testTag("lazylist")) {
        items(list){
            item ->  ArticleItem(item, onItemClicked = {
            if (hasInternet) {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(item.links?.mobile?.href ?: "http://www.mlb.com")
                )
                context.startActivity(intent)
            } else {
                Toast.makeText(context, context.getText(R.string.no_internet), Toast.LENGTH_LONG).show()
            }
        })
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun NewsImage(url: String, contentDescription: String?, modifier: Modifier = Modifier){
    AsyncImage(
        model = url,
        contentDescription = contentDescription,
        placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
        modifier = Modifier.size(height = 200.dp, width = 120.dp).testTag("asyncImage")

    )
}

@Composable
fun ArticleItem(article: Article, onItemClicked: () -> Unit){
    Surface(modifier = Modifier
        .height(120.dp)
        .fillMaxWidth()
        .border(2.dp, Color.Green, RoundedCornerShape(10.dp))
        .clickable { onItemClicked() }
        .testTag("itemSurface")) {
        Row {
            Column(modifier = Modifier.padding(end = 5.dp)){
                NewsImage(
                    url = article.images.first().url,
                    contentDescription = "News Image",
                    modifier = Modifier
                        .padding(start = 2.dp, end = 2.dp, top = 2.dp, bottom = 2.dp)
                        .testTag("itemImage"))
            }
            Column {
                Text(text = article.headline,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 10.dp, bottom = 2.dp, start = 2.dp, end = 2.dp)
                        .testTag("itemTitle"))
                Text(text = article.description,
                    fontSize = 10.sp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 2.dp, bottom = 10.dp, start = 2.dp, end = 2.dp)
                        .testTag("itemDescription"))
            }
        }
    }

}