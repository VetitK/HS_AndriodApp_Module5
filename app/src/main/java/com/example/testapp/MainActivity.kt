package com.example.testapp

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testapp.ui.theme.TestAppTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import androidx.lifecycle.ViewModel
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.testapp.data.UnsplashApiProvider
import com.example.testapp.data.UnsplashPhoto
import com.example.testapp.data.UnsplashPhotoDetails
import com.example.testapp.data.cb.UnsplashDetailResult
import com.example.testapp.data.cb.UnsplashResult


class MainActivity : ComponentActivity(), UnsplashResult {
    private val provider = UnsplashApiProvider()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            TestAppTheme {
                ImageDetail(provider)
            }
        }
    }
    override fun onDataFetchedSuccess(images: List<UnsplashPhoto>) {
        // Update the state with the fetched images
        // This will automatically trigger a recomposition
        setContent {
            TestAppTheme {
//                ImageDetail(provider, images)
                ErrorPage()
            }
        }
    }

    override fun onDataFetchedFailed() {
        setContent {
            TestAppTheme {
                ErrorPage()
            }
        }
    }
}

@Composable
fun ErrorPage() {
    Text(text = "ERROR")
}

@Composable
fun GridItem(topic: String, details: String) {
    return (Column {
        Text(text = topic, color=Color.White,
            fontSize = 14.sp)
        Text(text =  details, color=Color.LightGray, fontWeight = FontWeight.Light,
            fontSize = 12.sp, modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 6.dp))
    })
}
@Composable
fun ImageDetail(provider: UnsplashApiProvider, images: List<UnsplashPhoto> = emptyList()) {
    var imageList by remember { mutableStateOf(images) }
    var imageDetail by remember { mutableStateOf<UnsplashPhotoDetails?>(null) }
    LaunchedEffect(key1 = Unit) {
        if (images.isEmpty()) {
            provider.fetchImages(object : UnsplashResult {
                override fun onDataFetchedSuccess(images: List<UnsplashPhoto>) {
                    imageList = images
                    if (imageList.isNotEmpty()) {
                        val firstImageId = imageList.first().id
                        provider.fetchDetailsOnPhoto(firstImageId, object : UnsplashDetailResult {
                            override fun onDataFetchedSuccess(detail: UnsplashPhotoDetails) {
                                imageDetail = detail
                                Log.d("Test", "${imageDetail}")
                            }
                            override fun onDataFetchedFailed() {
                                Log.d("ERR", "ERRRRRR")
                            }
                        })
                    }

                }

                override fun onDataFetchedFailed() {
                }
            })
        }

    }
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier
        .fillMaxWidth()
        .background(color = Color.Black)
        .fillMaxSize()
    ) {
        // Cover Image
        item {


            // Overlay Text and Location Pin Icon
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                if(imageList.isNotEmpty()) {
                    AsyncImage(model = imageList[0].urls.regular, contentDescription = null)
                }

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .align(Alignment.BottomStart)

                ) {
                    // Placeholder for overlay text and location pin icon
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .size(16.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Text(
                        text = imageDetail?.description.toString(),
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light,

                        )
                }
            }
        }

        // Avatar, Name, Icons, Divider, and Text Grid
        item {
            Row (verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp)
            ){
                Image(
                    painter = painterResource(id = R.drawable.baseline_account_circle_24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .clip(MaterialTheme.shapes.small)
                )

                // Name TextView
                Text(
                    text = imageDetail?.user?.name.toString(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(start = 8.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = { /* Handle button click */ },
                    ) {
                        Image(painter = painterResource(id = R.drawable.baseline_download_24), contentDescription = null)
                    }

                    IconButton(
                        onClick = { /* Handle button click */ },
                    ) {
                        Image(painter = painterResource(id = R.drawable.baseline_favorite_border_24), contentDescription = null)
                    }
                    IconButton(
                        onClick = { /* Handle button click */ },
                    ) {
                        Image(painter = painterResource(id = R.drawable.baseline_bookmark_border_24), contentDescription = null)
                    }

//
                }
            }
        }

        // Divider
        item {
            Divider(
                thickness = 1.dp,
                color=Color.DarkGray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp)
            )
        }

        // Text Grid
        item {
            Row (horizontalArrangement = Arrangement.Center, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp)){
                Column (modifier=Modifier.weight(1f)) {
                    GridItem(topic = "Camera", details = imageDetail?.exif?.model ?: "N/A")
                    GridItem(topic = "Focal Length", details = imageDetail?.exif?.focal_length ?: "N/A")
                    GridItem(topic = "ISO", details = imageDetail?.exif?.iso.toString())
                }
                Column (modifier=Modifier.weight(1f)){
                    GridItem(topic = "Aperture", details = imageDetail?.exif?.aperture ?: "N/A")
                    GridItem(topic = "Shutter Speed", details = imageDetail?.exif?.exposure_time ?: "N/A")
                    GridItem(topic = "Dimensions", details = imageDetail?.width.toString() + "x" + imageDetail?.height.toString())
                }
            }
        }
        // Divider
        item {
            Divider(
                thickness = 1.dp,
                color=Color.DarkGray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp)
            )
        }
        item {
            Row ( modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 16.dp)){
//                Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)){
//                    Text(text = "Views", color=Color.White, fontSize = 14.sp)
//                    Text(text = imageDetail?., color=Color.LightGray, fontSize = 12.sp)
//                }
                Column (horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier.weight(1f)){
                    Text(text = "Downloads", color=Color.White, fontSize = 14.sp)
                    Text(text = imageDetail?.downloads.toString(), color=Color.LightGray, fontSize = 12.sp)
                }
                Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)){
                    Text(text = "Likes", color=Color.White, fontSize = 14.sp)
                    Text(text = imageDetail?.likes.toString(), color=Color.LightGray, fontSize = 12.sp)
                }

            }
        }
        // Divider
        item {
            Divider(
                thickness = 1.dp,
                color=Color.DarkGray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp)
            )
        }



        // Button Row
        item {
            Row(
                modifier = Modifier
                    .padding(16.dp),

                ) {
                FilledTonalButton (
                    onClick = { },
                    modifier = Modifier
                        .padding(end = 16.dp),
                    colors = ButtonDefaults.buttonColors(Color.DarkGray),
                ) {
                    Text("Scenery")
                }

                FilledTonalButton (
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(Color.DarkGray),
                    modifier = Modifier
                        .padding(end = 16.dp),

                    ) {
                    Text("Spain")
                }
            }
        }
    }


}

//@Preview(showBackground = true)
//@Composable
//fun ImageDetailPreview() {
//    TestAppTheme {
//        ImageDetail()
//    }
//}
