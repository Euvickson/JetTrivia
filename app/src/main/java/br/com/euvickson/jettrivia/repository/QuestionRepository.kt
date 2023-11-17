package br.com.euvickson.jettrivia.repository

import android.util.Log
import br.com.euvickson.jettrivia.data.DataOrException
import br.com.euvickson.jettrivia.model.QuestionItem
import br.com.euvickson.jettrivia.network.QuestionAPI
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val api: QuestionAPI) {

    private val dataOrException =
        DataOrException<ArrayList<QuestionItem>,
                Boolean,
                Exception>()

    suspend fun getAllQuestions() : DataOrException<ArrayList<QuestionItem>, Boolean, Exception> {
        try {

            dataOrException.loading = true
            dataOrException.data = api.getAllQuestions()

            if (dataOrException.data.toString().isNotEmpty()) dataOrException.loading = false

        } catch (exception: Exception) {

            dataOrException.e = exception
            Log.d("Exc", "getAllQuestions: ${dataOrException.e!!.localizedMessage}")

        }
        return dataOrException
    }
}