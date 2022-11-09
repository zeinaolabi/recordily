package com.example.recordily_client.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.recordily_client.R
import com.example.recordily_client.navigation.Screen
import com.example.recordily_client.navigation.navigateTo
import com.example.recordily_client.responses.AlbumResponse
import com.example.recordily_client.view_models.UnreleasedViewModel
import kotlinx.coroutines.launch

@Composable
fun UnreleasedAlbumsCard(
    title: String,
    navController: NavController,
    albums: List<AlbumResponse>?,
    destination: ()->(Unit),
    viewModel: UnreleasedViewModel,
    token: String,
    onUploadClick: () -> Unit,
){
    Column(
        modifier = Modifier.padding(bottom= dimensionResource(id = R.dimen.padding_medium))
    ){
        Text(
            text = title,
            fontWeight = FontWeight.ExtraBold,
            fontSize = dimensionResource(id = R.dimen.font_medium).value.sp,
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_medium)),
            color = MaterialTheme.colors.onPrimary
        )

        CardsContent(albums, navController, destination, viewModel, token, onUploadClick)
    }
}

@Composable
private fun CardsContent(
    albums: List<AlbumResponse>?,
    navController: NavController,
    destination: ()->(Unit),
    viewModel: UnreleasedViewModel,
    token: String,
    onUploadClick: () -> Unit,
){
    val coroutinesScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .verticalScroll(ScrollState(0)),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        if(albums == null || albums.isEmpty()){
            EmptyState(message = stringResource(id = R.string.no_albums_found))
        } else {
            for (album in albums) {
                UnreleasedAlbumCard(
                    album = album,
                    navController = navController,
                    onUploadClick = {
                        coroutinesScope.launch {
                            viewModel.publishAlbum(token, album.id)
                            onUploadClick()
                        }
                    }
                )
            }

            SmallTealButton(
                text = stringResource(id = R.string.more),
                onClick = {
                    destination()
                }
            )
        }
    }
}

@Composable
fun UnreleasedAlbumCard(album: AlbumResponse, navController: NavController, onUploadClick: () -> (Unit)){
    Row(
        modifier = Modifier
            .padding(vertical = dimensionResource(id = R.dimen.padding_small))
            .height(60.dp)
            .fillMaxWidth()
            .shadow(5.dp)
            .background(MaterialTheme.colors.surface)
            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium)),
        verticalAlignment = Alignment.CenterVertically
    ){
        AlbumCardContent(album, navController, onUploadClick)
    }
}

@Composable
private fun AlbumCardContent(album: AlbumResponse, navController: NavController, onUploadClick: () -> (Unit)){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ){
        AlbumDetails(album, navController)

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .clickable {
                    onUploadClick()
                },
            verticalArrangement = Arrangement.Center
        ){
            Icon(
                painter = painterResource(id = R.drawable.upload_song),
                contentDescription = "upload",
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@Composable
private fun AlbumDetails(album: AlbumResponse, navController: NavController){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .clickable(
                interactionSource = remember { NoRippleInteractionSource() },
                indication = null
            ) {
                navigateTo(
                    navController = navController,
                    destination = Screen.UnreleasedAlbumPage.route + '/' + album.id,
                    popUpTo = Screen.UnreleasedAlbumsPage.route
                )
            }
    )
    {
        Image(
            painter =
            if(album.picture != null && album.picture != ""){
                rememberAsyncImagePainter(album.picture)
            }
            else{
                painterResource(id = R.drawable.recordily_dark_logo)
            },
            contentDescription = "album picture",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
        ){
            Text(
                text = album.name,
                fontWeight = FontWeight.Bold,
                fontSize = dimensionResource(id = R.dimen.font_small).value.sp,
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}