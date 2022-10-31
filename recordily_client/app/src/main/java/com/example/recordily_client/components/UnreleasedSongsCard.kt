package com.example.recordily_client.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.example.recordily_client.R

@Composable
fun UnreleasedSongsCard(title: String, destination: ()->(Unit), onSongClick: ()->(Unit),onUploadClick: () -> (Unit)){
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

        CardsContent(destination, onSongClick, onUploadClick)
    }
}

@Composable
private fun CardsContent(destination: ()->(Unit), onSongClick: ()->(Unit), onUploadClick: () -> (Unit)){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .verticalScroll(ScrollState(0)),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        for(i in 1..3){
            UnreleasedSongCard(
                onSongClick = onSongClick,
                onUploadClick = {onUploadClick()}
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

@Composable
fun UnreleasedSongCard(onSongClick: ()->(Unit), onUploadClick: () -> (Unit)){
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
        SongCardContent(onSongClick, onUploadClick)
    }
}

@Composable
fun UnreleasedAlbumSongCard(onSongClick: ()->(Unit), onDeleteClick: ()->(Unit)){
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
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            SongDetails(onSongClick)

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .clickable{
                        onDeleteClick()
                    },
                verticalArrangement = Arrangement.Center
            ){
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "upload",
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}

@Composable
private fun SongCardContent(onSongClick: ()->(Unit), onUploadClick: () -> (Unit)){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ){
        SongDetails(onSongClick)

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .clickable{
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
private fun SongDetails(onSongClick: ()->(Unit)){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .clickable(
                interactionSource = remember { NoRippleInteractionSource() },
                indication = null
            ) {
                onSongClick()
            }
    )
    {
        Image(
            painter = painterResource(R.drawable.recordily_dark_logo),
            contentDescription = "song picture",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
        ){
            Text(
                text = "Song title",
                fontWeight = FontWeight.Bold,
                fontSize = dimensionResource(id = R.dimen.font_small).value.sp,
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}