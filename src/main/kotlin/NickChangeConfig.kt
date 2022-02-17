package org.echoosx.mirai.plugin

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object NickChangeConfig:AutoSavePluginConfig("Setting") {
    @ValueDescription("管理员账号")
    val administrator:Long by value()

    @ValueDescription("使用本插件的群组")
    val availableGroup:MutableSet<Long> by value()

}