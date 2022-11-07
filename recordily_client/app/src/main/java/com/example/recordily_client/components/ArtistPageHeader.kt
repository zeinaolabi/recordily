package com.example.recordily_client.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.recordily_client.R
import com.example.recordily_client.responses.UserResponse
import com.example.recordily_client.view_models.ArtistProfileViewModel
import kotlinx.coroutines.launch

@Composable
fun ArtistPageHeader(artistInfo: UserResponse?, artistFollowers: Int?, isFollowed: Boolean?, token: String){
    Row(
        modifier = Modifier.padding(
            vertical = dimensionResource(id = R.dimen.padding_large),
            horizontal = dimensionResource(id = R.dimen.padding_medium)
        )
    ){
        Image(
            painter =
            if(artistInfo?.profile_picture != ""){
                rememberAsyncImagePainter(artistInfo?.profile_picture)
            }
            else{
                painterResource(id = R.drawable.profile_picture)
            },
            contentDescription = "playlist picture",
            modifier = Modifier
                .size(125.dp)
                .clip(CircleShape)
                .border(3.dp, MaterialTheme.colors.secondary, CircleShape),
            contentScale = ContentScale.Crop
        )

        ArtistHeaderContent(artistInfo, artistFollowers, isFollowed, token)
    }
}

@Composable
private fun ArtistHeaderContent(artistInfo: UserResponse?, artistFollowers: Int?, isFollowed: Boolean?, token: String){

    val coroutinesScope = rememberCoroutineScope()
    val artistProfileViewModel: ArtistProfileViewModel = viewModel()

    Column(
        modifier = Modifier
            .height(125.dp)
            .padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.SpaceBetween
    ){

        Column{
            artistInfo?.name?.let {
                Text(
                    text = it,
                    fontSize = dimensionResource(id = R.dimen.font_medium).value.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_small)),
                    color = MaterialTheme.colors.onPrimary
                )
            }

            Text(
                text = "$artistFollowers Followers",
                fontSize = dimensionResource(id = R.dimen.font_small).value.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colors.onPrimary
            )
        }

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ){
            MediumRoundButton(
                text = if(isFollowed == true) "Unfollow" else "Follow",
                onClick =
                {
                    coroutinesScope.launch {
                        if(isFollowed == true){
                            artistProfileViewModel.unfollow(token, artistInfo?.id.toString())
                        }
                        else{
                            artistProfileViewModel.follow(token, artistInfo?.id.toString())
                        }
                        artistProfileViewModel.isFollowed(token, artistInfo?.id.toString())
                    }
                }
            )
        }
    }
}