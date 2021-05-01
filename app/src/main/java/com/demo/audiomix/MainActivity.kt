package com.demo.audiomix

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.demo.audiomix.databinding.ActivityMainBinding
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.mp3.Mp3Extractor
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.MergingMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.RawResourceDataSource


class MainActivity : AppCompatActivity() {

    lateinit var mbind: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mbind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mbind.root)

        val player = SoundMergePlay()
        player.initialise(arrayListOf(R.raw.sound1, R.raw.sound2), applicationContext, 5)
//        player.initialise(arrayListOf(R.raw.sound1), applicationContext, 5)

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
    lateinit var player: SimpleExoPlayer
    fun initialise(array: ArrayList<Int>, context: Context, maxTrack: Int) {
        this.mcontext = context
        player = SimpleExoPlayer.Builder(context).build()
        load(array)

    }


    fun play() {
        player.setPlayWhenReady(true)

    }

    fun load(array: ArrayList<Int>) {
        // Accepts an array of resources and loads them into memory

        var sourceList = ArrayList<MediaSource>()
        array.forEachIndexed() { index, it ->
            val uri = RawResourceDataSource.buildRawResourceUri(it)
            val dataSource = RawResourceDataSource(mcontext)
            dataSource.open(DataSpec(uri))

            val source = ExtractorMediaSource(uri, DataSource.Factory { dataSource }, Mp3Extractor.FACTORY, null, null)

            sourceList.add(source)
        }
        var mergesource = MergingMediaSource(sourceList.get(1), sourceList.get(0))
//        var mergesource=MergingMediaSource(sourceList.map{it}.toTypedArray())
//
        player.prepare(mergesource)


    }

    fun pause() {
        player.setPlayWhenReady(false)
    }

    fun release() {

       player.release()
    }


}