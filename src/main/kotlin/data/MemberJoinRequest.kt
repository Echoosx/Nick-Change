package org.echoosx.mirai.plugin.data


import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value


object MemberJoinRequest:AutoSavePluginData("JoinRecord") {
    @ValueDescription("入群申请记录")
    val memberJoinRequestRecord:MutableList<JoinRequest> by value()
}