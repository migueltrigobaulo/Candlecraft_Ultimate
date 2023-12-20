package hc.candlecraft.candlecraftultimate.common

import android.net.Uri
import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun uriToString(uri: Uri?): String {
        return uri.toString()
    }
    @TypeConverter
    fun stringToUri(string: String?): Uri? {
        return Uri.parse(string)
    }

}