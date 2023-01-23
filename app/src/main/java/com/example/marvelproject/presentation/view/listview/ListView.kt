package com.example.marvelproject.presentation.view.listview

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.marvelproject.R
import com.example.marvelproject.domain.model.CharacterDomain
import com.example.marvelproject.presentation.view.MainViewModel
import com.example.marvelproject.ui.theme.Background
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListView(vmKoin: MainViewModel = koinViewModel(),
             navigateToDetails: () -> Unit) {
    val context = LocalContext.current

    val lazyList = remember {
        //vm.list
        vmKoin.list
    }

    val loading = vmKoin.loading

    Box(
        modifier = Modifier
            .background(Background)
            .fillMaxSize()
            .padding(start = 15.dp, top = 15.dp, end = 15.dp),
        contentAlignment = Alignment.Center
    ){
        if (loading.value){
            CircularProgressIndicator()
        }
        else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(lazyList) { character ->
                    CharacterCard(character, navigateToDetails, vmKoin, context)
                }
            }
        }
    }
}

@Composable
fun CharacterCard(character: CharacterDomain,
                  navigateToDetails: () -> Unit,
                  //vm: MainViewModel,
                  vmKoin: MainViewModel = koinViewModel(),
                  context: Context) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(bottom = 15.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = 5.dp
    ) {
        //Background image
        Image(
            painter = rememberAsyncImagePainter(character.img),
            contentDescription = null ,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        //Column to put name in one line and row (with buttons) in second line
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black)
                    )
                )
                .padding(20.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            //Hero name
            Text(
                style = TextStyle(
                    color = Color.White,
                    fontSize = 22.sp
                ),
                text = character.name
            )

            //Hero description
            if(character.description.isNotEmpty()){
                Text(
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp
                    ),
                    text = character.description
                )
            }

            //Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if(character.series.isNotEmpty()) {
                    ClickableText(
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp
                        ),
                        text = AnnotatedString(stringResource(id = R.string.series_label)),
                        onClick = {
                            vmKoin.setButtonSelected("Series")
                            vmKoin.setCurrentCharacter(character)
                            navigateToDetails.invoke()
                        }
                    )
                }

                if(character.comics.isNotEmpty()){
                    ClickableText(
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp
                        ),
                        text = AnnotatedString(stringResource(id = R.string.comics_label)),
                        onClick = {
                            vmKoin.setButtonSelected("Comics")
                            vmKoin.setCurrentCharacter(character)
                            navigateToDetails.invoke()
                        }
                    )
                }

                if(character.detail.isNotBlank()){
                    ClickableText(
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp
                        ),
                        text = AnnotatedString(stringResource(id = R.string.details_label)),
                        onClick = {
                            vmKoin.setCurrentCharacter(character)

                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(character.detail))
                            context.startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}
