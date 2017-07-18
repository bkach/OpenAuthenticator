package com.github.bkach.openauthenticator

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * This is a temporary activity to show what can be done, eventually it will be removed
 */
class MainActivity : AppCompatActivity() {

    var refreshDisposable : Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userInfo : UserInfo? = load()
        if (userInfo == null) {
            scan()
        } else {
            showResults(userInfo)
        }

        refresh_button.setOnClickListener {
            scan()
        }

    }

    fun scan() {
        IntentIntegrator(this).setBeepEnabled(false).setPrompt("Scan your code").initiateScan()
    }

    /**
     * This is depreciated, but it needs to be supported until the O SDK comes out
     */
    private fun vibrate() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        val contents = result?.contents
        if (contents == null) {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
            vibrate()
            showResults(save(Uri.parse(result.contents)))
        }
    }

    @SuppressLint("SetTextI18n")
    fun showResults(userInfo: UserInfo) {
        text_view_1.text = "User: ${userInfo.user}"
        text_view_2.text = "Secret: ${userInfo.secret}"
        text_view_3.text = "Issuer: ${userInfo.issuer}"


        val numSeconds = 30
        progress_bar.max = (numSeconds - 1) * 1000
        val halfMinInMs = TimeUnit.SECONDS.toMillis(numSeconds.toLong())

        refreshDisposable?.dispose()
        refreshDisposable =
                Observable.interval(0, 100, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map {
                        Date().time / halfMinInMs
                    }
                    .subscribe({

                        val cutoffTime = it * halfMinInMs
                        val difference = Date().time - cutoffTime
                        progress_bar.progress = difference.toInt()
                        val code = String.format("%06d",
                                Authenticator.calculateCode(userInfo.secret, it))
                        text_view_4.text = code
                    }, Throwable::printStackTrace)
    }

    fun save(uri: Uri) : UserInfo {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val userInfo =
                UserInfo(uri.path, uri.getQueryParameter("secret"), uri.getQueryParameter("issuer"))
        editor.putString("USER", userInfo.user)
        editor.putString("SECRET", userInfo.secret)
        editor.putString("ISSUER", userInfo.issuer)
        editor.apply()
        return userInfo
    }

    private fun load(): UserInfo? {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val userInfo = UserInfo(
            sharedPreferences.getString("USER", "Error: Not Found"),
            sharedPreferences.getString("SECRET", "Error: Not Found"),
            sharedPreferences.getString("ISSUER", "Error: Not Found"))
        if (userInfo.secret == "Error: Not Found") {
            return null
        } else {
            return userInfo
        }
    }


    data class UserInfo(val user : String, val secret : String, val issuer : String?)

    fun bdebug(message: String) {
        Log.d("bdebug", message)
    }
}
