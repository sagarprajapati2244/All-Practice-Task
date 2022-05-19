package com.example.firebasenotification.androidTask

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep

import com.google.gson.annotations.SerializedName



@Keep
data class Songs(
    @SerializedName("resultCount")
    val resultCount: Int, // 50
    @SerializedName("results")
    val results: List<Result>
)

@Keep
data class Result(
    @SerializedName("artistId")
    val artistId: Int, // 909253
    @SerializedName("artistName")
    val artistName: String?, // Jack Johnson
    @SerializedName("artistViewUrl")
    val artistViewUrl: String?, // https://music.apple.com/us/artist/jack-johnson/909253?uo=4
    @SerializedName("artworkUrl100")
    val artworkUrl100: String?, // https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/ae/4c/d4/ae4cd42a-80a9-d950-16f5-36f01a9e1881/source/100x100bb.jpg
    @SerializedName("artworkUrl30")
    val artworkUrl30: String?, // https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/ae/4c/d4/ae4cd42a-80a9-d950-16f5-36f01a9e1881/source/30x30bb.jpg
    @SerializedName("artworkUrl60")
    val artworkUrl60: String?, // https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/ae/4c/d4/ae4cd42a-80a9-d950-16f5-36f01a9e1881/source/60x60bb.jpg
    @SerializedName("collectionArtistId")
    val collectionArtistId: Int, // 410641764
    @SerializedName("collectionArtistName")
    val collectionArtistName: String?, // Jack Johnson
    @SerializedName("collectionArtistViewUrl")
    val collectionArtistViewUrl: String?, // https://itunes.apple.com/us/artist/buena-vista-home-entertainment-inc/410641764?uo=4
    @SerializedName("collectionCensoredName")
    val collectionCensoredName: String?, // Jack Johnson and Friends: Sing-A-Longs and Lullabies for the Film Curious George
    @SerializedName("collectionExplicitness")
    val collectionExplicitness: String?, // notExplicit
    @SerializedName("collectionHdPrice")
    val collectionHdPrice: Double, // 99.99
    @SerializedName("collectionId")
    val collectionId: Int, // 1469577723
    @SerializedName("collectionName")
    val collectionName: String?, // Jack Johnson and Friends: Sing-A-Longs and Lullabies for the Film Curious George
    @SerializedName("collectionPrice")
    val collectionPrice: Double, // 9.99
    @SerializedName("collectionViewUrl")
    val collectionViewUrl: String?, // https://music.apple.com/us/album/upside-down/1469577723?i=1469577741&uo=4
    @SerializedName("contentAdvisoryRating")
    val contentAdvisoryRating: String?, // PG-13
    @SerializedName("country")
    val country: String?, // USA
    @SerializedName("currency")
    val currency: String?, // USD
    @SerializedName("discCount")
    val discCount: Int, // 1
    @SerializedName("discNumber")
    val discNumber: Int, // 1
    @SerializedName("hasITunesExtras")
    val hasITunesExtras: Boolean, // true
    @SerializedName("isStreamable")
    val isStreamable: Boolean, // true
    @SerializedName("kind")
    val kind: String?, // song
    @SerializedName("longDescription")
    val longDescription: String?, // Visionary director J.J. Abrams brings to life the motion picture event of a generation. As Kylo Ren and the sinister First Order rise from the ashes of the Empire, Luke Skywalker is missing when the galaxy needs him most. It's up to Rey, a desert scavenger, and Finn, a defecting stormtrooper, to join forces with Han Solo and Chewbacca in a desperate search for the one hope of restoring peace to the galaxy.
    @SerializedName("previewUrl")
    val previewUrl: String?, // https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview125/v4/5e/5b/3d/5e5b3df4-deb5-da78-5d64-fe51d8404d5c/mzaf_13341178261601361485.plus.aac.p.m4a
    @SerializedName("primaryGenreName")
    val primaryGenreName: String?, // Rock
    @SerializedName("releaseDate")
    val releaseDate: String?, // 2005-01-01T12:00:00Z
    @SerializedName("shortDescription")
    val shortDescription: String?, // Lucasfilm and visionary director J.J. Abrams join forces to take you back again to a galaxy far, far
    @SerializedName("trackCensoredName")
    val trackCensoredName: String?, // Upside Down
    @SerializedName("trackCount")
    val trackCount: Int, // 14
    @SerializedName("trackExplicitness")
    val trackExplicitness: String?, // notExplicit
    @SerializedName("trackHdPrice")
    val trackHdPrice: Double, // 9.99
    @SerializedName("trackHdRentalPrice")
    val trackHdRentalPrice: Double, // 3.99
    @SerializedName("trackId")
    val trackId: Int, // 1469577741
    @SerializedName("trackName")
    val trackName: String?, // Upside Down
    @SerializedName("trackNumber")
    val trackNumber: Int, // 1
    @SerializedName("trackPrice")
    val trackPrice: Double, // 1.29
    @SerializedName("trackRentalPrice")
    val trackRentalPrice: Double, // 3.99
    @SerializedName("trackTimeMillis")
    val trackTimeMillis: Int, // 208643
    @SerializedName("trackViewUrl")
    val trackViewUrl: String?, // https://music.apple.com/us/album/upside-down/1469577723?i=1469577741&uo=4
    @SerializedName("wrapperType")
    val wrapperType: String?, // track
    @SerializedName("isFavourite")
    var isFavourite:Boolean = false, // true
    @SerializedName("isPlaying")
    var isPlaying:Boolean = false,
    @SerializedName("isWorking")
    var isWorking:Boolean = false
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(artistId)
        parcel.writeString(artistName)
        parcel.writeString(artistViewUrl)
        parcel.writeString(artworkUrl100)
        parcel.writeString(artworkUrl30)
        parcel.writeString(artworkUrl60)
        parcel.writeInt(collectionArtistId)
        parcel.writeString(collectionArtistName)
        parcel.writeString(collectionArtistViewUrl)
        parcel.writeString(collectionCensoredName)
        parcel.writeString(collectionExplicitness)
        parcel.writeDouble(collectionHdPrice)
        parcel.writeInt(collectionId)
        parcel.writeString(collectionName)
        parcel.writeDouble(collectionPrice)
        parcel.writeString(collectionViewUrl)
        parcel.writeString(contentAdvisoryRating)
        parcel.writeString(country)
        parcel.writeString(currency)
        parcel.writeInt(discCount)
        parcel.writeInt(discNumber)
        parcel.writeByte(if (hasITunesExtras) 1 else 0)
        parcel.writeByte(if (isStreamable) 1 else 0)
        parcel.writeString(kind)
        parcel.writeString(longDescription)
        parcel.writeString(previewUrl)
        parcel.writeString(primaryGenreName)
        parcel.writeString(releaseDate)
        parcel.writeString(shortDescription)
        parcel.writeString(trackCensoredName)
        parcel.writeInt(trackCount)
        parcel.writeString(trackExplicitness)
        parcel.writeDouble(trackHdPrice)
        parcel.writeDouble(trackHdRentalPrice)
        parcel.writeInt(trackId)
        parcel.writeString(trackName)
        parcel.writeInt(trackNumber)
        parcel.writeDouble(trackPrice)
        parcel.writeDouble(trackRentalPrice)
        parcel.writeInt(trackTimeMillis)
        parcel.writeString(trackViewUrl)
        parcel.writeString(wrapperType)
        parcel.writeByte(if (isFavourite) 1 else 0)
        parcel.writeByte(if (isPlaying) 1 else 0)
        parcel.writeByte(if (isWorking) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Result> {
        override fun createFromParcel(parcel: Parcel): Result {
            return Result(parcel)
        }

        override fun newArray(size: Int): Array<Result?> {
            return arrayOfNulls(size)
        }
    }
}