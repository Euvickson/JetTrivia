package br.com.euvickson.jettrivia.repository

import br.com.euvickson.jettrivia.data.DataOrException
import br.com.euvickson.jettrivia.model.QuestionItem
import br.com.euvickson.jettrivia.network.QuestionAPI
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val api: QuestionAPI) {

    private val listOfQuestions = DataOrException<ArrayList<QuestionItem>, Boolean, Exception>()
}