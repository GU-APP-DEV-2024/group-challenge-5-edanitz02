package com.zybooks.workingwithdata

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PatentDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patent_detail)

        val title = intent.getStringExtra("title")
        val abstract = intent.getStringExtra("abstract")
        val url = intent.getStringExtra("url")

        findViewById<TextView>(R.id.titleTextView).text = title
        findViewById<TextView>(R.id.abstractTextView).text = abstract
        findViewById<TextView>(R.id.urlTextView).apply {
            text = url
            setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(browserIntent)
            }
        }
    }
}