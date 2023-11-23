package com.example.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

class CounterActivity : AppCompatActivity() {
    lateinit var viewModel : CounterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counter)
        viewModel = ViewModelProvider(this).get(CounterViewModel::class.java)
        val tv_counter = findViewById<TextView>(R.id.tv_counter)
        findViewById<Button>(R.id.button_counter).setOnClickListener {

            viewModel.increment()
        }

        viewModel.number.observe(this){
            number -> tv_counter.text = number.toString()
        }




    }
}