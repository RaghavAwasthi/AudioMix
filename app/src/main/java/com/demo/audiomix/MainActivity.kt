package com.demo.audiomix

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
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

        mbind.button.setOnClickListener {
            player.play()
        }


    }
}

class SoundMergePlay {

    lateinit var mcontext: Context
    fun initialise(array: ArrayList<Int>, context: Context, maxTrack: Int) {
        this.mcontext = context

        mAudioManager = mcontext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
//        mSoundPool = SoundPool(maxTrack, AudioManager.STREAM_MUSIC, 0)
        mSoundPool = SoundPool.Builder()
                .setMaxStreams(5)
                .setAudioAttributes(AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build())
                .build()

        load(array)
    }

    lateinit var mAudioManager: AudioManager
    lateinit var mSoundPool: SoundPool

    val soundpoolHashmap = ArrayList<Int>()
    fun play() {
        val streamVolume = mAudioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC).toFloat()

        soundpoolHashmap.forEachIndexed { index, it ->
            mSoundPool.play(it, streamVolume, streamVolume, 1, 0, 1f)
        }

    }

    fun load(array: ArrayList<Int>) {
        // Accepts an array of resources and loads them into memory
        mSoundPool.setOnLoadCompleteListener { soundpool, sampleId, status ->
            soundpoolHashmap.add(sampleId)
        }

        array.forEachIndexed() { index, it ->
            mSoundPool.load(mcontext, it, index)
        }


    }

    fun pause() {
//        mSoundPool.pause()
    }

    fun release() {

        mSoundPool.release()
    }


}