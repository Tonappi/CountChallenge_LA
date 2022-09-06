package app.ikeda.tonappi.work1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.ikeda.tonappi.work1.databinding.ActivityTitleBinding

class TitleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTitleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTitleBinding.inflate(layoutInflater).apply{setContentView(this.root)}

        //intent_buttonクリック時にMainActivityへ画面遷移
        binding.intentButton.setOnClickListener {
            //1.Intentを作る
            val toMainActivityIntent = Intent(this,MainActivity::class.java)
            //2.Intentの設定(今回はなし)
            //3.Intentを使って画面遷移
            startActivity(toMainActivityIntent)
        }
    }
}