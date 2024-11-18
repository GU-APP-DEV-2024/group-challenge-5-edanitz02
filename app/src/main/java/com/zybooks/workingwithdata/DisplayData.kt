package com.zybooks.workingwithdata

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

class DisplayData : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: PatentCustomAdapter
    lateinit var dummyData: ArrayList<PatentData>
    lateinit var searchEditText: EditText
    lateinit var searchButton: Button

    data class PatentData(
        val title: String,
        val patentNumber: String,
        val abstract: String,
        val url: String
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tech_transfer)

        recyclerView = findViewById(R.id.recyclerView)
        searchEditText = findViewById(R.id.searchEditText)
        searchButton = findViewById(R.id.searchButton)

        // Dummy data
        dummyData = arrayListOf(
            PatentData(
                title = "Advanced Rocket Engine",
                patentNumber = "US12345678",
                abstract = "A novel rocket engine design with improved fuel efficiency.",
                url = "https://patents.nasa.gov/12345678"
            ),
            PatentData(
                title = "AI for Space Navigation",
                patentNumber = "US87654321",
                abstract = "AI algorithms for efficient interplanetary navigation.",
                url = "https://patents.nasa.gov/87654321"
            )
        )

        adapter = PatentCustomAdapter(dummyData) { patent ->
            val intent = Intent(this, PatentDetailActivity::class.java).apply {
                putExtra("title", patent.title)
                putExtra("abstract", patent.abstract)
                putExtra("url", patent.url)
            }
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        searchButton.setOnClickListener {
            searchPatents(searchEditText.text.toString())
        }
    }

    private fun searchPatents(query: String) {
        val url = "https://api.nasa.gov/techtransfer/patent/?query=$query&patent_issued=2023&" +
                "software=true&api_key=${BuildConfig.NASA_API_KEY}"


        val queue: RequestQueue = Volley.newRequestQueue(applicationContext)

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                dummyData.clear()
                for (i in 0 until response.length()) {
                    val patent = response.getJSONArray(i)
                    dummyData.add(
                        PatentData(
                            title = patent.getString(0),
                            patentNumber = patent.getString(1),
                            abstract = patent.getString(2),
                            url = patent.getString(3)
                        )
                    )
                }
                adapter.notifyDataSetChanged()
            },
            { error ->
                Toast.makeText(this, "Error fetching data: $error", Toast.LENGTH_LONG).show()
            }
        )

        queue.add(jsonArrayRequest)
    }
}