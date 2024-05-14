package com.example.news_app.view

import ArticleAdapter
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView // SearchView eklenmeli
import com.example.news_app.R
import com.example.news_app.model.Article
import com.example.news_app.model.ArticleResponse
import com.example.news_app.service.NewsAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val BASE_URL = "https://newsapi.org/v2/"
    private var articles: ArrayList<Article>? = null

    private var recyclerView: RecyclerView? = null
    private var articleAdapter: ArticleAdapter? = null

    // Arama metnini tutacak değişken
    private var searchQuery: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(this)

        val searchView = findViewById<SearchView>(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Arama düğmesine basıldığında çalışır
                searchQuery = query ?: ""
                loadData() // Arama yapılırken verileri güncelle
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Arama metni değiştiğinde çalışır
                return false
            }
        })

        loadData()
    }

    private fun loadData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(NewsAPI::class.java)
        //val call = service.getData()
        val call = service.getData("bitcoin", "dfa18be16d414df0ba4141615624b195")
        call.enqueue(object : Callback<ArticleResponse> {
            override fun onResponse(call: Call<ArticleResponse>, response: Response<ArticleResponse>) {
                if (response.isSuccessful) {
                    val articleResponse = response.body()
                    articleResponse?.let {
                        articles = ArrayList(it.articles.filter { article ->
                            // Arama metni ile başlık veya içerikte eşleşen makaleleri filtrele
                            article.title.contains(searchQuery, ignoreCase = true) ||
                                    article.description?.contains(searchQuery, ignoreCase = true) ?: false
                        })
                        articleAdapter = ArticleAdapter(this@MainActivity, articles ?: listOf())
                        recyclerView?.adapter = articleAdapter
                    }
                } else {
                    // Hata durumunda kullanıcıya bir Toast mesajı göster
                    Toast.makeText(this@MainActivity, "API'den veri alınamadı", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                t.printStackTrace()
                // Hata durumunda kullanıcıya bir Toast mesajı göster
                Toast.makeText(this@MainActivity, "İnternet bağlantısı hatası", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
