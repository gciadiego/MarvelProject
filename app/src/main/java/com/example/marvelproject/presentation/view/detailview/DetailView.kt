package com.example.marvelproject.presentation.view.detailview

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.marvelproject.R
import com.example.marvelproject.domain.model.SeriesAndComicsDomain
import com.example.marvelproject.ui.theme.Background
import com.example.marvelproject.ui.theme.CardLeftColor
import com.example.marvelproject.ui.theme.CardRightColor
import com.example.marvelproject.ui.theme.CardSeparator
import com.example.marvelproject.presentation.view.MainViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailView(vm: MainViewModel,
               vmKoin: MainViewModel = koinViewModel(),
               navigateToList: () -> Unit) {

    val lazyList = remember {
        if(vmKoin.buttonSelected.value == "Series"){
            vmKoin.currentCharacter.value?.series
        }
        else{
           vmKoin.currentCharacter.value?.comics
        }
    }

    Box(modifier = Modifier
        .background(Background)
        .fillMaxSize()
        .padding(start = 15.dp, top = 15.dp, end = 15.dp)
    ){
        Column{
            Text(
                modifier = Modifier
                    .padding(bottom = 10.dp),
                style = TextStyle(
                    color = Color.White,
                    fontSize = 22.sp
                ),
                text = vmKoin.buttonSelected.value
            )

            LazyColumn {
                lazyList?.let {
                    items(it){
                        item -> SeriesAndComicsCard(item, vmKoin)
                    }
                }
            }
        }
    }
}

@Composable
fun SeriesAndComicsCard(item: SeriesAndComicsDomain,
                        //vm: MainViewModel,
                        vmKoin: MainViewModel = koinViewModel()) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.horizontalGradient(
                    colors = listOf(CardLeftColor, CardRightColor)
                )
            )
            .drawBehind {
                val borderSize = 2.dp.toPx()
                drawLine(
                    color = CardSeparator,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = borderSize
                )
            }
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ){
            Text(
                style = TextStyle(
                    color = Color.White,
                    fontSize = 18.sp
                ),
                text = item.name
            )
        }
    }
}
