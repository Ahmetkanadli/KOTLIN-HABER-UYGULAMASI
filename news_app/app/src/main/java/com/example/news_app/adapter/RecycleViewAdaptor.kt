import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.news_app.R

import com.example.news_app.model.Article
import com.squareup.picasso.Picasso

class ArticleAdapter(private val context: Context, private val articles: List<Article>) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_layout , parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.text_title)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.text_description)
        private  val imageView : ImageView = itemView.findViewById(R.id.image_view)

        fun bind(article: Article) {
            titleTextView.text = article.title
            descriptionTextView.text = article.description
            Picasso.get().load(article.urlToImage).into(imageView)        }
    }
}
