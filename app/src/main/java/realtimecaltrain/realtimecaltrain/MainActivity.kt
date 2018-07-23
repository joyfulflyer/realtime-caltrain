package realtimecaltrain.realtimecaltrain

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import realtimecaltrain.realtimecaltrain.viewModels.MainViewModel

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    lateinit var button : Button
    lateinit var textView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        button = findViewById(R.id.button)
        textView = findViewById(R.id.textView)

        button.setOnClickListener{viewModel.onTapped()}

        viewModel.data.observe(this, Observer {textView.text = it})


    }
}
