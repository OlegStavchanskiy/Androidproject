package com.example.droidquest

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var mTrueButton: Button
    private lateinit var mFalseButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        mTrueButton = findViewById(R.id.true_button)
        mTrueButton.setOnClickListener {
            Toast.makeText(
                this, R.string.correct_toast,
                Toast.LENGTH_SHORT
            ).show()
        }
        mFalseButton = findViewById(R.id.false_button)
        mFalseButton.setOnClickListener{
            Toast.makeText(this, R.string.incorrect_toast,
                Toast.LENGTH_SHORT).show()

        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
}