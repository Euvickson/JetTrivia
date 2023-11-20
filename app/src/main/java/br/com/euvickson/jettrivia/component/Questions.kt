package br.com.euvickson.jettrivia.component

import android.util.Log
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import br.com.euvickson.jettrivia.screens.QuestionsViewModel

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