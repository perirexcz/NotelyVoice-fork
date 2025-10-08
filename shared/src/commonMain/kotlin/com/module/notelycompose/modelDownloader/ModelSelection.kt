package com.module.notelycompose.modelDownloader

import com.module.notelycompose.onboarding.data.PreferencesRepository
import kotlinx.coroutines.flow.first

const val NO_MODEL_SELECTION = -1
const val STANDARD_MODEL_SELECTION = 0
const val OPTIMIZED_MODEL_SELECTION = 1
const val HINDI_MODEL_SELECTION = 2

data class TranscriptionModel(val name:String, val size:String, val description:String, val url:String){
    fun getModelDownloadMessage():String = "File size: approximately $size\n$description"
}

class ModelSelection(private val preferencesRepository: PreferencesRepository) {

    /**
     * Available Whisper models
     */
    private val models = listOf(
        TranscriptionModel(
            "ggml-base-en.bin",
            "139 MB",
            "English-optimized model (faster, smaller)",
            "https://huggingface.co/ggerganov/whisper.cpp/resolve/main/ggml-base-en.bin"
        ),
        TranscriptionModel(
            "ggml-small.bin",
            "468 MB",
            "Multilingual model (slower, more-accurate)",
            "https://huggingface.co/ggerganov/whisper.cpp/resolve/main/ggml-small.bin"
        ),
        TranscriptionModel(
            "ggml-base-hi.bin",
            "140 MB",
            "Hindi & Gujarati model (fast, hindi focused)",
            "https://huggingface.co/khidrew/whisper-base-hindi-ggml/resolve/main/ggml-base-hi.bin"
        )
    )

    /**
     * Get the appropriate model based on transcription language
     * @return The model to use
     */
    suspend fun getSelectedModel(): TranscriptionModel {
        val defaultLanguage = preferencesRepository.getDefaultTranscriptionLanguage().first()
        return when (defaultLanguage) {
            "en" -> models[0]
            "hi" -> models[2]
            else -> models[1]
        }
    }

    fun getDefaultTranscriptionModel() = models[0]


}