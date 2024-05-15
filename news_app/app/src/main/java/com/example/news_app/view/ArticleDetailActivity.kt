package com.example.news_app.view

import FavoritesDatabaseHelper
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.news_app.R
import com.squareup.picasso.Picasso

class ArticleDetailActivity : AppCompatActivity() {

    private lateinit var dbHelper: FavoritesDatabaseHelper
    private lateinit var title: String
    private lateinit var description: String
    private lateinit var imageUrl: String
    private lateinit var url: String
    private lateinit var favButton: ImageView
    private lateinit var shareButton: ImageView
    private var isFavorite: Boolean = false // Favori durumunu tutmak için bir işaretleyici

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)

        // Initialize views
        val titleTextView: TextView = findViewById(R.id.text_title)
        val descriptionTextView: TextView = findViewById(R.id.text_description)
        val imageView: ImageView = findViewById(R.id.image_view)
        val button: Button = findViewById(R.id.button)
        favButton = findViewById(R.id.image_favorite)
        shareButton = findViewById(R.id.image_share)


        // Bind the article data to views
        title = intent.getStringExtra("article_title") ?: ""
        description = intent.getStringExtra("article_content") ?: ""
        imageUrl = intent.getStringExtra("article_image") ?: ""
        url = intent.getStringExtra("article_url") ?: ""

        titleTextView.text = title
        descriptionTextView.text = description
        Picasso.get().load(imageUrl).into(imageView)

        // Database helper initialization
        dbHelper = FavoritesDatabaseHelper(this)

        // Check if the article is already in favorites and update favButton accordingly
        isFavorite = dbHelper.isFavorite(title)
        updateFavoriteButton()

        // Button click listener
        button.setOnClickListener {
            // WebViewActivity'ye git
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("url", url)
            startActivity(intent)
        }

        // Favori durumunu kontrol et ve güncelle
        favButton.setOnClickListener {
            isFavorite = !isFavorite // Favori durumunu tersine çevir
            if (isFavorite) {
                // Favorilere ekle
                dbHelper.addFavorite(title, description, imageUrl, url)
            } else {
                // Favorilerden kaldır
                dbHelper.removeFavorite(title)
            }
            updateFavoriteButton()
        }

        shareButton.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, url)
            startActivity(Intent.createChooser(shareIntent, "Share URL"))
        }

    }

    private fun updateFavoriteButton() {
        if (isFavorite) {
            // Favorilere eklenmiş
            // Favori ikonunu değiştir
            favButton.setImageResource(R.drawable.baseline_favorite_red)
        } else {
            // Favorilere eklenmemiş
            // Favori ikonunu değiştir
            favButton.setImageResource(R.drawable.baseline_favorite_grey)
        }
    }
}


