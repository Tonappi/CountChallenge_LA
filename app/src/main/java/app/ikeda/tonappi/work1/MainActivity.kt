package app.ikeda.tonappi.work1

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.media.AudioAttributes
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.preference.PreferenceManager
import android.util.Log
import app.ikeda.tonappi.work1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Doggy:
    //  mSoundPool, Sound という変数は onCreate の中でしか使用していないので、 onCreate 内で定義すると良いね！
    //  変数は、値を追いやすくしたり、値の変更によるバグを減らすように扱うこが大事なので、
    //  変数の生存期間（スコープ）を短くするように意識しよう！
    private lateinit var mSoundPool: SoundPool

    private var Sound = 0

    private lateinit var binding: ActivityMainBinding

    private  lateinit var sharedPreferences: SharedPreferences


    //アプリ開始時
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(this.root) }



        // Doggy: 変更されない値を val で定義できているのナイス！
        //音声の読み込み
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()

        mSoundPool = SoundPool.Builder()
            .setAudioAttributes(audioAttributes)
            .setMaxStreams(1)
            .build()

        Sound = mSoundPool.load(this, R.raw.system39new, 1)


        //前回のデータ(最後の数字)を入れる変数を初期化
        sharedPreferences = getSharedPreferences("com.example.preferencesample.my_preferences",
            Context.MODE_PRIVATE)
        // Doggy: "COUNT" というキーが複数回出てくるので、
        //  変数に代入しておいて、定数として扱うとバグを防げそう！

        // Doggy:
        //  str のように、変数名を省略したり、「何の文字列か」という情報を省くとコードの可読性が下がってしまうので、良くないとされているよ！
        //  例えば今回は前回の保存されたデータを呼び出しているので、 "savedString", "savedText" のような命名はどうかな？
        //  命名に関しては、メンバーにもその重要性をぜひ伝えてあげてほしいです！
        //前回のデータ(最後の数字)の呼び出し
        val str = sharedPreferences.getString("COUNT", "NoData")

        //前回の最後の数字を表示
        binding.numberTextView.text = str.toString()
        Log.d("MainActivityResult", str.toString())


        // Doggy: 使用する直前で変数を宣言してるのナイス！
        //  まさに「スコープを狭くする」を実践できてます！
        //数字を0に戻す
        var number: Int = 0
        binding.numberTextView.text = number.toString()


        //ボタンをクリックしたときの処理
        binding.tapButton.setOnClickListener {
            number = number + 1
            binding.numberTextView.text = number.toString()
            //奇数と偶数での表示の違い
            when (number % 2) {
                0 -> binding.numberTextView.setTextColor(Color.BLUE)
                else -> {
                    binding.numberTextView.setTextColor(Color.RED)
                    mSoundPool.play(Sound, 1.0f, 1.0f, 1, 0, 1.0f)
                }
            }


        }

    }


    //アプリ終了時
    override fun onStop() {
        super.onStop()

        //最後の数字をresultという変数に設定する
        val result = binding.numberTextView.text.toString()

        //最後の数字を保存
        val editor = sharedPreferences.edit()
        // Doggy: めっちゃ細かいけど、インデントが1つ多いね！
        //  コードを読みやすくするために、スコープが同じ行のインデントは揃えよう！
            editor.putString("COUNT", result).apply()


    }




}