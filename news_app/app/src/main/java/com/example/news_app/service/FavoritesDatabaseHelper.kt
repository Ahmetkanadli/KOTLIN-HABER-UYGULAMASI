import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.news_app.model.Article

class FavoritesDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "FavoritesDatabase.db"
        private const val TABLE_NAME = "favorites"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_IMAGE_URL = "imageUrl"
        private const val COLUMN_URL = "url"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_FAVORITES_TABLE = ("CREATE TABLE IF NOT EXISTS $TABLE_NAME ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_TITLE TEXT NOT NULL,"
                + "$COLUMN_DESCRIPTION TEXT NOT NULL,"
                + "$COLUMN_IMAGE_URL TEXT NOT NULL,"
                + "$COLUMN_URL TEXT NOT NULL)")
        db.execSQL(CREATE_FAVORITES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addFavorite(title: String, description: String, imageUrl: String, url: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_TITLE, title)
        values.put(COLUMN_DESCRIPTION, description)
        values.put(COLUMN_IMAGE_URL, imageUrl)
        values.put(COLUMN_URL, url)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun removeFavorite(title: String) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_TITLE=?", arrayOf(title))
        db.close()
    }

    fun isFavorite(title: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_TITLE = ?"
        val cursor: Cursor = db.rawQuery(query, arrayOf(title))
        val isFavorite = cursor.count > 0
        cursor.close()
        db.close()
        return isFavorite
    }

    fun getAllFavorites(): List<Article> {
        val favoriteArticles = mutableListOf<Article>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        cursor.use {
            while (it.moveToNext()) {
                val titleIndex = it.getColumnIndex(COLUMN_TITLE)
                val descriptionIndex = it.getColumnIndex(COLUMN_DESCRIPTION)
                val imageUrlIndex = it.getColumnIndex(COLUMN_IMAGE_URL)
                val urlIndex = it.getColumnIndex(COLUMN_URL)

                val title = it.getString(titleIndex)
                val description = it.getString(descriptionIndex)
                val imageUrl = it.getString(imageUrlIndex)
                val url = it.getString(urlIndex)

                favoriteArticles.add(Article(title = title, description = description, urlToImage = imageUrl,url = url, content = "", publishedAt = "", author = "", source = null))
            }
        }
        return favoriteArticles
    }



}
