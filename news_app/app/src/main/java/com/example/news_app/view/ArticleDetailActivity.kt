package com.example.news_app.view


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.news_app.R
import com.squareup.picasso.Picasso

class ArticleDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)

        // Initialize views
        val titleTextView: TextView = findViewById(R.id.text_title)
        val descriptionTextView: TextView = findViewById(R.id.text_description)
        val imageView: ImageView = findViewById(R.id.image_view)
        val button: Button = findViewById(R.id.button)

        // Bind the article data to views
        titleTextView.text = intent.getStringExtra("article_title")
        descriptionTextView.text = intent.getStringExtra("article_content")
        Picasso.get().load(intent.getStringExtra("article_image")).into(imageView)

        // Button click listener
        button.setOnClickListener {
            val url = intent.getStringExtra("article_url")
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("url", url)
            startActivity(intent)
        }
    }
}
