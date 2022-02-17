package org.echoosx.mirai.plugin.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.*

@Serializable
data class JoinRequest(
    @SerialName("qq号")
    val id :Long,
    @SerialName("昵称")
    val nick: String,
    @SerialName("群组")
    val group: Long,
    @SerialName("申请信息")
    val message: String,
    @SerialName("申请时间")
    val time: String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date().time)
)