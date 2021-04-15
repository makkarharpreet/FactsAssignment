package com.assignment.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.assignment.R
import com.assignment.databinding.ActivityMainBinding

/**
 * @author Harpreet Singh
 */
class MainActivity : AppCompatActivity() {
    /**
     * @param savedInstanceState
     */
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
}