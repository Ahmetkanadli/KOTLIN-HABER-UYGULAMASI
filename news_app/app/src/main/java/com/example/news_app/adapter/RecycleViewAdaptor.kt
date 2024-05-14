import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.news_app.R
import com.example.news_app.model.Article
import com.example.news_app.view.ArticleDetailActivity
import com.squareup.picasso.Picasso

class ArticleAdapter(private val context: Context, private val articles: List<Article>) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_layout, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)

        // Click listener for each item
        holder.itemView.setOnClickListener {
            // Start the new activity here and pass the clicked article's data
            val intent = Intent(context, ArticleDetailActivity::class.java)
            intent.putExtra("article_title",article.title)
            intent.putExtra("article_description",article.description)
            intent.putExtra("article_image",article.urlToImage)
            intent.putExtra("article_url",article.url)
            intent.putExtra("article_content",article.content)

            println("article : ${intent.getStringExtra("article_title")}")
            context.startActivity(intent)
        }

    }


    override fun getItemCount(): Int {
        return articles.size
    }

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.text_title)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.text_description)
        private val imageView: ImageView = itemView.findViewById(R.id.image_view)

        fun bind(article: Article) {
            titleTextView.text = article.title
            descriptionTextView.text = article.description
            Picasso.get().load(article.urlToImage).into(imageView)
        }
    }
}
