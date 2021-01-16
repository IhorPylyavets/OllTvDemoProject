package com.example.olltvapplication.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

data class OllTvDemoResponse(
    @SerializedName("block_id")
    var blockId: String,
    @SerializedName("block_title")
    var blockTitle: String,
    @SerializedName("block_type")
    var blockType: String,
    @SerializedName("category_name")
    var categoryName: String,
    @SerializedName("first_now_index")
    var firstNowIndex: Int,
    @SerializedName("hasMore")
    var hasMore: Int,
    @SerializedName("items_number")
    var itemsNumber: Int,
    @SerializedName("offset")
    var offset: Int,
    @SerializedName("total")
    var total: Int,
    @SerializedName("items")
    var items: List<ChannelItem>?
)

@Parcelize
data class ChannelItem(
    @SerializedName("id")
    var id: Long,
    @SerializedName("channel_id")
    var channelId: Long,
    @SerializedName("channel_name")
    var channelName: String,
    @SerializedName("alias")
    var alias: String,
    @SerializedName("icon")
    var icon: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("start")
    var start: Date,
    @SerializedName("start_ts")
    var startTs: Long,
    @SerializedName("stop")
    var stop: Date,
    @SerializedName("stop_ts")
    var stopTs: Long,
    @SerializedName("is_free")
    var isFree: Int,
    @SerializedName("is_favorite")
    var isFavorite: Int,
    @SerializedName("under_parental_protect")
    var underParentalProtect: Int,
    @SerializedName("dvr")
    var dvr: Int,
    @SerializedName("now")
    var now: Int,
    @SerializedName("FK_catalog")
    var fkCatalog: Int,
    @SerializedName("subs")
    var subs: ChannelItemSubs
): Parcelable

@Parcelize
data class ChannelItemSubs(
    @SerializedName("id")
    var id: Long,
    @SerializedName("price")
    var price: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("subsId")
    var subsId: Long,
    @SerializedName("type")
    var type: String
): Parcelable
