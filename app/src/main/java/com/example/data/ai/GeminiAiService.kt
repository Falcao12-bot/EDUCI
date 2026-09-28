package com.example.data.ai

import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object GeminiAiService {
    private const val MODEL = "gemini-3.5-flash"
    private const val BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models"

    private val client by lazy {
        OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    suspend fun askTeacher(
        userMessage: String,
        studentClass: String,
        lessonContext: String? = null,
        history: List<Pair<String, String>> = emptyList()
    ): String = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Exception) {
            ""
        }

        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            // Intelligent pedagogical offline fallback matching teacher behavior
            return@withContext getPedagogicalFallback(userMessage, studentClass, lessonContext)
        }

        try {
            val systemPrompt = """
                Tu es "Professeur EduCI", l'assistant pédagogique d'élite de la plateforme éducative ivoirienne EduCI (« Apprendre. Progresser. Réussir. »).
                Tu t'adresses à un élève de Côte d'Ivoire en classe de $studentClass.
                RÈGLES PÉDAGOGIQUES STRICTES :
                1. Adapte ton vocabulaire et ta pédagogie au niveau de l'élève (Classe : $studentClass).
                2. N'exécute pas tout l'exercice à sa place d'un coup : guide-le pas à pas avec bienveillance en lui posant des questions stimulantes.
                3. Donne des exemples concrets du quotidien ivoirien (ex : cacao, café, marché d'Adjamé, transport lagunaire, fleuve Bandama, etc.) quand c'est pertinent.
                4. Utilise des formules mathématiques claires et explicites.
                5. Structure toujours ta réponse avec des puces claires et encourage chaleureusement l'élève ("Courage !", "Très bonne question !", "Tu progresses bien !").
                ${if (!lessonContext.isNullOrBlank()) "Contexte de la leçon actuelle en cours de révision : $lessonContext" else ""}
            """.trimIndent()

            val contentsArray = JSONArray()

            // System instruction
            val systemContent = JSONObject().apply {
                put("role", "user")
                put("parts", JSONArray().apply {
                    put(JSONObject().apply { put("text", "Consigne professeur : $systemPrompt") })
                })
            }
            contentsArray.put(systemContent)

            // Conversation history
            for ((role, text) in history) {
                val turnObj = JSONObject().apply {
                    put("role", if (role == "user") "user" else "model")
                    put("parts", JSONArray().apply {
                        put(JSONObject().apply { put("text", text) })
                    })
                }
                contentsArray.put(turnObj)
            }

            // Current message
            val currentTurn = JSONObject().apply {
                put("role", "user")
                put("parts", JSONArray().apply {
                    put(JSONObject().apply { put("text", userMessage) })
                })
            }
            contentsArray.put(currentTurn)

            val requestJson = JSONObject().apply {
                put("contents", contentsArray)
                put("generationConfig", JSONObject().apply {
                    put("temperature", 0.7)
                    put("topP", 0.95)
                    put("maxOutputTokens", 1024)
                })
            }

            val requestBody = requestJson.toString().toRequestBody("application/json".toMediaType())
            val url = "$BASE_URL/$MODEL:generateContent?key=$apiKey"

            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: ""

            if (!response.isSuccessful) {
                return@withContext getPedagogicalFallback(userMessage, studentClass, lessonContext)
            }

            val jsonObject = JSONObject(responseBody)
            val candidates = jsonObject.optJSONArray("candidates")
            val firstCandidate = candidates?.optJSONObject(0)
            val content = firstCandidate?.optJSONObject("content")
            val parts = content?.optJSONArray("parts")
            val firstPart = parts?.optJSONObject(0)
            val text = firstPart?.optString("text")

            if (text.isNullOrBlank()) {
                getPedagogicalFallback(userMessage, studentClass, lessonContext)
            } else {
                text
            }
        } catch (e: Exception) {
            getPedagogicalFallback(userMessage, studentClass, lessonContext)
        }
    }

    private fun getPedagogicalFallback(
        userMessage: String,
        studentClass: String,
        lessonContext: String?
    ): String {
        val lower = userMessage.lowercase()
        return when {
            lower.contains("formule") || lower.contains("pythagore") || lower.contains("identité") -> {
                "Bonjour ! C'est une excellente question sur les formules en classe de $studentClass.\n\n" +
                "📘 **Rappel de méthode :**\n" +
                "Pour bien retenir et appliquer une formule :\n" +
                "1. **Identifie les termes :** Par exemple dans (a + b)², identifie précisément ce qui joue le rôle de a et ce qui joue le rôle de b.\n" +
                "2. **Attention au double produit :** N'oublie jamais le terme 2 × a × b.\n" +
                "3. **Vérifie avec des nombres simples :** Si tu as un doute, remplace a par 1 et b par 2 pour vérifier si tes deux membres sont égaux.\n\n" +
                "Veux-tu qu'on s'entraîne ensemble sur un exemple particulier ? Dis-moi quelle expression tu as sous les yeux !"
            }
            lower.contains("exercice") || lower.contains("donne") || lower.contains("test") -> {
                "Voici un petit exercice d'entraînement spécialement calibré pour la classe de $studentClass :\n\n" +
                "✏️ **Exercice :**\n" +
                "Développe et réduis l'expression suivante :\n" +
                "${'$'}${'$'}E = (2x + 3)^2 - 5${'$'}${'$'}\n\n" +
                "💡 **Indice pour démarrer :**\n" +
                "Commence par développer (2x + 3)² avec la formule (a + b)² = a² + 2ab + b².\n\n" +
                "Écris-moi ta première étape de calcul, et je te dirai si tu es sur la bonne voie !"
            }
            lower.contains("corrige") || lower.contains("vérifie") -> {
                """
                Très bien ! Pour que je puisse corriger ton travail :
                1. Donne-moi l'énoncé exact de la question.
                2. Partage les étapes de ton raisonnement et ta réponse finale.
                
                Je regarderai où se trouve l'éventuelle erreur et je t'expliquerai comment l'éviter le jour de l'examen !
                """.trimIndent()
            }
            else -> {
                """
                Bonjour ! Je suis le **Professeur EduCI**, ton coach scolaire pour la classe de $studentClass.

                Je suis là pour t'accompagner pas à pas dans toutes tes matières :
                - 📐 **Mathématiques** : géométrie, algèbre, calcul littéral, fonctions.
                - 📚 **Français** : grammaire, argumentation, conjugaison, rédaction.
                - 🔬 **Sciences** : physique-chimie, SVT, expériences et formules.
                - 🌍 **Histoire-Géographie & EDHC** : repères, cartes et dissertations.

                Dis-moi sur quel chapitre ou exercice tu souhaites progresser aujourd'hui !
                """.trimIndent()
            }
        }
    }
}
