package com.example.droidquest

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var mTrueButton: Button
    private lateinit var mFalseButton: Button
    private lateinit var mNextButton: ImageButton
    private lateinit var mBackButton: ImageButton
    private lateinit var mQuestionTextView: TextView
    private lateinit var mDeceitButton: Button

    private val mQuestionBank = listOf(
        Question(R.string.question_android, true),
        Question(R.string.question_linear, false),
        Question(R.string.question_service, false),
        Question(R.string.question_res, true),
        Question(R.string.question_manifest, true),
        Question(R.string.question_apple, false),
        Question(R.string.question_market, false),
        Question(R.string.question_threading, true),
        Question(R.string.question_pc, false),
        Question(R.string.question_async, true)

    )
    private var mCurrentIndex = 0
    private var mIsDeceiter = false
    companion object{
        val EXTRA_IS_DECEITER = "x"
        private val TAG = "QuestActivity"
        private val KEY_INDEX = "index"
        private val REQUEST_CODE_DECEIT = 0
        private val IsDeceit = "bool"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate(Bundle) вызван")

        mTrueButton = findViewById(R.id.true_button)
        mTrueButton.setOnClickListener {
            checkAnswer(true)
        }
        mFalseButton = findViewById(R.id.false_button)
        mFalseButton.setOnClickListener{
            checkAnswer(false)

        }
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsDeceiter = savedInstanceState.getBoolean(IsDeceit, false)
        }

        mQuestionTextView = findViewById(R.id.question_text_view)
        mQuestionTextView.setOnClickListener{            if(mCurrentIndex+1<mQuestionBank.size){
            mCurrentIndex = mCurrentIndex + 1
            updateQuestion()}}
        mNextButton = findViewById(R.id.next_button)
        mNextButton.setOnClickListener {
            if(mCurrentIndex+1<mQuestionBank.size){
            mCurrentIndex = mCurrentIndex + 1
            mIsDeceiter = false
            updateQuestion()}

        }
        updateQuestion()
        mBackButton = findViewById(R.id.back_button)
        mBackButton.setOnClickListener{
            if(mCurrentIndex-1>=0){

            mCurrentIndex = mCurrentIndex - 1
            updateQuestion()}
        }
        mDeceitButton = findViewById(R.id.deceit_button)
        mDeceitButton.setOnClickListener{
            val answerIsTrue = mQuestionBank[mCurrentIndex].answerTrue
            val intent = DeceitActivity.newIntent(this, answerIsTrue, mIsDeceiter)
            startForResult.launch(intent)



        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


    }
    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { intent ->
                mIsDeceiter = DeceitActivity.wasAnswerShown(intent)
            }
        }
    }



//    @Deprecated("Deprecated in Java")
//    override fun onActivityResult(
//        requestCode: Int, resultCode:
//        Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode != RESULT_OK) {
//            return;
//        }
//        if (requestCode == REQUEST_CODE_DECEIT) {
//            if (data == null) {
//                return;
//            }
//            mIsDeceiter = DeceitActivity.wasAnswerShown(result = data);
//        }
//    }

    private fun updateQuestion() {
        val question = mQuestionBank[mCurrentIndex].textResId
        mQuestionTextView.setText(question)
    }
    private fun checkAnswer(userPressedTrue: Boolean) {
        val answerIsTrue = mQuestionBank[mCurrentIndex].answerTrue
        val messageResId = if (mIsDeceiter) R.string.judgment_toast
        else if (userPressedTrue == answerIsTrue) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() вызван")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() вызван")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() вызван")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() вызван")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() вызван")
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState")
        outState.putInt(KEY_INDEX, mCurrentIndex)
        outState.putBoolean(IsDeceit, mIsDeceiter)
    }

}