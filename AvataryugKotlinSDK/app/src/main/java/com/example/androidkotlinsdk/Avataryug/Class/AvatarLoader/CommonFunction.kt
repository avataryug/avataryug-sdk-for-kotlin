package Avataryug.Class.AvatarLoader

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.nio.Buffer
import java.nio.ByteBuffer

class CommonFunction {
    companion object {
        val instance: CommonFunction by lazy { CommonFunction() }
    }



    open fun downloadGlb(url:String,onComplete : (Buffer) -> Unit)
    {
        val request = Request.Builder().url(url).build()
        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException)
            {
                error(e)
            }
            override  fun onResponse(call: Call, response: Response)
            {
                try
                {
                    val byteArray : InputStream? = response.body?.byteStream()
                    val inputStream = BufferedInputStream(byteArray)
                    GlobalScope.launch {
                        ByteArrayOutputStream().use { output ->
                            inputStream.copyTo(output)
                            val byteArr = output.toByteArray()
                            val byteBuffer = ByteBuffer.wrap(byteArr)
                            val rewound = byteBuffer.rewind()
                            withContext(Dispatchers.Main) {
                                onComplete(rewound)
                            }
                        }
                    }
                } catch (e: Exception) {
                    error(e)
                }
            }
        })
    }
}