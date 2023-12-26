package com.evsoft.news

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val apiKey = "pub_340469d6333404c292102362c229ad4b54efe"
    private lateinit var newsList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newsList = findViewById(R.id.news_list)
        newsList.layoutManager = LinearLayoutManager(this)

        val searchButton = findViewById<Button>(R.id.search_button)
        val searchField = findViewById<EditText>(R.id.search_field)

        searchButton.setOnClickListener {
            val query = searchField.text.toString()
            if (query.isNotEmpty()) {
                showNews(query)
            } else {
                Toast.makeText(this, "Пожалуйста, введите ключевое слово", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showNews(keyword: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: Response<NewsResponse> = newsAPI.getNews(apiKey, keyword)

                if (response.isSuccessful) {
                    val jsonObject = response.body()?.results

                    if (jsonObject?.size == 0) {
                        withContext(Dispatchers.Main) {
                            val builder = AlertDialog.Builder(this@MainActivity)
                            builder.setTitle("Ничего не найдено")
                            builder.setMessage("Попробуйте изменить ключевое слово")
                            builder.setPositiveButton("Ок") { _: DialogInterface, _: Int -> }
                            builder.show()
                        }
                    }

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
