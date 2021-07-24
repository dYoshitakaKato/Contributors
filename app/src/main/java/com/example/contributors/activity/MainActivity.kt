package com.example.contributors.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.contributors.R
import com.example.contributors.fragments.MainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}