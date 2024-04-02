package com.example.droidquest

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DeceitActivity : AppCompatActivity() {
    private lateinit var mAnswerTextView: TextView
    private lateinit var mShowAnswer: Button
    private var deceit: Boolean = false
    companion object {
        val EXTRA_ANSWER_IS_TRUE =
            "com.example.droidquest.answer_is_true"
        val EXTRA_ANSWER_SHOWN =
            "com.example.droidquest.answer_shown"
        fun newIntent(packageContext: Context?, answerIsTrue: Boolean, isDeceiter: Boolean):
                Intent {
            val intent = Intent(packageContext, DeceitActivity::class.java)
            return intent.apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
                putExtra(MainActivity.EXTRA_IS_DECEITER, isDeceiter)
            }
        }


        fun newIntent(packageContext: Context?, answerIsTrue: Boolean):
                Intent {
            val intent = Intent(packageContext,
                DeceitActivity::class.java)
            return intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
        }
        fun wasAnswerShown(result: Intent) =
            result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_deceit)
        val mAnswerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        var mIsDeceiter = intent.getBooleanExtra(MainActivity.EXTRA_IS_DECEITER, false)


        mAnswerTextView = findViewById(R.id.answer_text_view);
        mShowAnswer = findViewById(R.id.show_answer_button);
        mShowAnswer.setOnClickListener {
            mAnswerTextView.setText(
                if (mAnswerIsTrue) R.string.true_button
                else R.string.false_button)
            setAnswerShownResult(true)
        }
        if (savedInstanceState != null) {
            deceit = savedInstanceState.getBoolean(EXTRA_ANSWER_SHOWN, false)
            if (deceit){
                setAnswerShownResult(true)
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.deceit)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent()
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        setResult(Activity.RESULT_OK, data)
        deceit = true
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(EXTRA_ANSWER_SHOWN, deceit)
    }

}