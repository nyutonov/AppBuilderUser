package uz.gita.appbuilderuser.presenter.componentsimport androidx.compose.foundation.Imageimport androidx.compose.foundation.clickableimport androidx.compose.foundation.layout.Rowimport androidx.compose.foundation.layout.Spacerimport androidx.compose.foundation.layout.fillMaxSizeimport androidx.compose.foundation.layout.fillMaxWidthimport androidx.compose.foundation.layout.heightimport androidx.compose.foundation.layout.paddingimport androidx.compose.foundation.shape.RoundedCornerShapeimport androidx.compose.material3.Cardimport androidx.compose.material3.CardDefaultsimport androidx.compose.material3.MaterialThemeimport androidx.compose.material3.Textimport androidx.compose.runtime.Composableimport androidx.compose.ui.Alignmentimport androidx.compose.ui.Modifierimport androidx.compose.ui.draw.clipimport androidx.compose.ui.graphics.Colorimport androidx.compose.ui.graphics.ColorFilterimport androidx.compose.ui.res.painterResourceimport androidx.compose.ui.tooling.preview.Previewimport androidx.compose.ui.unit.dpimport uz.gita.appbuilderuser.Rimport uz.gita.appbuilderuser.data.model.DrawsData@Composablefun DrawsComponent(    drawsData: DrawsData,    onClick: () -> Unit,) {    Card(        modifier = Modifier            .fillMaxWidth()            .height(76.dp)            .padding(12.dp)            .clip(RoundedCornerShape(12.dp))            .clickable { onClick() },        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),        colors = CardDefaults.cardColors(Color(0xff4d648d))    ) {        Row(            modifier = Modifier                .fillMaxSize()                .padding(                    horizontal = 16.dp, vertical = 12.dp                )        ) {            Text(                text = drawsData.key,                color = Color.White,                modifier = Modifier                    .align(Alignment.CenterVertically)            )            Spacer(modifier = Modifier.weight(1f))            Image(                painter = painterResource(id = if (drawsData.state) R.drawable.baseline_done_all_24 else R.drawable.round_access_time_24),                contentDescription = "",                colorFilter = ColorFilter.tint(Color.White),                modifier = Modifier.align(Alignment.CenterVertically)            )        }    }}//@Preview(showBackground = true)//@Composable//fun DrawsComponentPreview() {//    MaterialTheme {//        DrawsComponent(DrawsData("", "Sample Task", false), {})//    }//}