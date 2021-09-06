# WHAT IS THIS
This repository implements a set of Approaches to play audio files on Android Application. It further provides a POC to validate concepts.
Detailed Pro's and Cons are listed below

## Part 1 - Playing Multiple Sounds together without creating a merged file
Approaches for solving above statement can be found on respective branches in detail from code stand point.


## Approach 1 use Sound Pool
A SoundPool is a collection of samples that can be loaded into memory from a resource inside the APK or from a file in the file system. The SoundPool library uses the MediaPlayer service to decode the audio into a raw 16-bit PCM mono or stereo stream. This allows applications to ship with compressed streams without having to suffer the CPU load and latency of decompressing during playback.
In addition to low-latency playback, SoundPool can also manage the number of audio streams being rendered at once.
https://developer.android.com/reference/android/media/SoundPool
 
SoundPool is recommended for <1MB sound clips, so I'd use MediaPlayer in your case. You can call the following for each sound and they will play simultaneously:
MediaPlayer.create(YourActivity.this, R.raw.your_sound).start();
Or you can create multiple MediaPlayer objects and play a sound on each, then release() them.
 
For a workaround on this we could use a third party library as mentioned here
https://stackoverflow.com/questions/6296361/android-soundpool-heap-limits
https://gitlab.com/olekdia/common/libraries/sound-pool
 
## Approach 2 Use  Media Player
We could use multiple Media Player Instances to play the music simultaneously and that way it could work.
But it would create multiple objects in memory and that would be expensive
 
## Approach 3 Use ExoPlayer(Doesn’t work)
As per this answer we could do it with exoplayer but again with  creating multiple instances . It states it can’t be done with 1 instance 
https://stackoverflow.com/questions/56990443/play-multiple-audio-files-through-exoplayer-or-some-other-option
 
This link states that merging audio sources is not supported by ExoPlayer
https://github.com/google/ExoPlayer/issues/2200
 
I tried testing the MergingMediaSource, It only plays one Audio File , not other ones
 

