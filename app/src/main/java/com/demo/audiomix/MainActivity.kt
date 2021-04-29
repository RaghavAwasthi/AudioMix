package com.demo.audiomix

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.demo.audiomix.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var mbind: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mbind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mbind.root)

        val player = SoundMergePlay()
        player.initialise(arrayListOf(R.raw.sound1, R.raw.sound2), applicationContext, 5)

        mbind.play.setOnClickListener {
            player.play()
        }
        mbind.pause.setOnClickListener {
            player.pause()
        }
        mbind.release.setOnClickListener {
            player.release()
        }


    }
}

class SoundMergePlay {

    lateinit var mcontext: Context
    val mediaplayerlist = ArrayList<MediaPlayer>()

    fun initialise(array: ArrayList<Int>, context: Context, maxTrack: Int) {
        this.mcontext = context
        load(array)
    }


    fun play() {
        mediaplayerlist.forEachIndexed { index, it ->
            it.start()
        }

    }

    fun load(array: ArrayList<Int>) {
        // Accepts an array of resources and loads them into memory


        array.forEachIndexed() { index, it ->
            mediaplayerlist.add(MediaPlayer.create(mcontext, it))
        }


    }

    fun pause() {
        mediaplayerlist.forEach {
            it.pause()
        }
    }

    fun release() {

        mediaplayerlist.forEach {
            it.release()
        }
    }


}