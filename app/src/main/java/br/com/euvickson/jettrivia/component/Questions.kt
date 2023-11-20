package br.com.euvickson.jettrivia.component

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.euvickson.jettrivia.model.QuestionItem
import br.com.euvickson.jettrivia.screens.QuestionsViewModel
import br.com.euvickson.jettrivia.util.AppColors

@Composable
fun Questions(viewModel: QuestionsViewModel) {
    val questions = viewModel.data.value.data?.toMutableList()
    if (viewModel.data.value.loading == true) {
        CircularProgressIndicator()
        Log.d("Loading", "Questions: Loading...")
    } else {
        Log.d("Loading", "Questions: Loading STOPPED...")
        questions?.forEach {questionItem ->
            Log.d("Result", "Questions: ${questionItem.question}")
        }
    }
}


@Composable
fun QuestionDisplay(
    question: QuestionItem,
    questionIndex: MutableState<Int>,
    viewModel: QuestionsViewModel,
    onNextClicked: (Int) -> Unit
) {

    val choicesState = remember (question) {question.choices.toMutableList()}

    val answerState = remember (question) {mutableStateOf<Int?>(null)}

    val updateAnswer: (Int) -> Unit = remember (question) {
        {
            answerState.value = it
        }
    }

    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)

    Surface(modifier = Modifier
        .fillMaxSize()
        .padding(4.dp), color = AppColors.mDarkPurple) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start) {
            QuestionTracker()
            DrawDottedLine(pathEffect = pathEffect)

            Column {
                Text(
                    text = "What's the meaning of all this",
                    modifier = Modifier
                        .padding(6.dp)
                        .align(alignment = Alignment.Start)
                        .fillMaxHeight(0.3f),
                    fontSize = 17.sp,
                    color = AppColors.mOffWhite,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp
                )

                //Choices
                choicesState.forEachIndexed { index, answerText ->

                    Row (modifier = Modifier
                        .padding(3.dp)
                        .fillMaxWidth()
                        .height(45.dp)
                        .border(
                            width = 4.dp,
                            brush = Brush
                                .linearGradient(
                                    colors = listOf(
                                        AppColors.mOffDarkPurple,
                                        AppColors.mOffDarkPurple
                                    )
                                ), shape = RoundedCornerShape(15.dp)
                        )
                        .clip(
                            RoundedCornerShape(
                                topStartPercent = 50,
                                topEndPercent = 50,
                                bottomEndPercent = 50,
                                bottomStartPercent = 50
                            )
                        )
                        .background(Color.Transparent)
                        ,verticalAlignment = Alignment.CenterVertically) {

                        RadioButton(
                            selected = (answerState.value == index)
                            ,onClick = {
                                updateAnswer(index)
                            })
                    }
                }
            }
        }
    }
}

@Composable
fun QuestionTracker(counter: Int = 10, outOf: Int = 100) {
    Text(text = buildAnnotatedString {

        withStyle(style = ParagraphStyle(textIndent = TextIndent.None)) {

            withStyle(style = SpanStyle(
                color = AppColors.mLightGray,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )) {
                append("Question $counter/")
                withStyle(style = SpanStyle(
                    color = AppColors.mLightGray,
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp
                )) {
                    append("$outOf")
                }
            }

        }
    }, modifier = Modifier.padding(20.dp))
}

@Composable
fun DrawDottedLine(pathEffect: PathEffect) {

    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(1.dp)) {
        drawLine(
            color = AppColors.mLightGray,
            start = Offset(x = 0f, y = 0f),
            end = Offset(size.width, y = 0f),
            pathEffect = pathEffect
        )
    }

}