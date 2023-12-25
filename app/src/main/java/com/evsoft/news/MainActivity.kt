package com.evsoft.news

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val apiKey = "pub_340469d6333404c292102362c229ad4b54efe"
    private val newsList = findViewById<RecyclerView>(R.id.news_list)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchButton = findViewById<Button>(R.id.search_button)
        val searchField = findViewById<EditText>(R.id.search_field)

        searchButton.setOnClickListener {
            val query = searchField.text.toString()
            searchNews(query)
        }
    }

    private fun searchNews(keyword: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: Response<NewsResponse> = newsAPI.getNews(apiKey, keyword)

                if (response.isSuccessful) {
                    val jsonObject = response.body()?.results
                    withContext(Dispatchers.Main) {
                        if (jsonObject != null) {
                            newsList.adapter = NewsAdapter(jsonObject)
                        }
                    }
                } else {
                    Log.e("MainActivity", "Error: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Exception: $e")
            }
        }
    }
}
