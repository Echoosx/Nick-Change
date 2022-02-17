package org.echoosx.mirai.plugin

import kotlinx.coroutines.delay
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.*
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.utils.info
import org.echoosx.mirai.plugin.NickChangeConfig.administrator
import org.echoosx.mirai.plugin.NickChangeConfig.availableGroup
import org.echoosx.mirai.plugin.data.JoinRequest
import org.echoosx.mirai.plugin.data.MemberJoinRequest
import org.echoosx.mirai.plugin.data.MemberJoinRequest.memberJoinRequestRecord
import org.echoosx.mirai.plugin.utl.getFollow

object NickChange : KotlinPlugin(
    JvmPluginDescription(
        id = "org.echoosx.mirai.plugin.NickChange",
        name = "NickChange",
        version = "1.0.0"
    ) {
        author("Echoosx")
        dependsOn("org.echoosx.mirai.plugin.VFollowCheck",true)
    }
) {
    override fun onEnable() {
        MemberJoinRequest.reload()
        NickChangeConfig.reload()
        logger.info { "NickChange loaded" }
        //配置文件目录 "${dataFolder.absolutePath}/"
        val eventChannel = GlobalEventChannel.parentScope(this)


        eventChannel.filter { availableGroup.isNotEmpty() }.subscribeAlways<MemberJoinRequestEvent> {
            try {
                if(groupId in availableGroup){
                    val record = JoinRequest(id = fromId, nick = fromNick, group = groupId, message = message)
                    memberJoinRequestRecord.add(0,record)
                }
            }catch (e:Throwable){
                bot.getFriendOrFail(administrator).sendMessage("加群申请记录失败，${e.message}")
                logger.error(e)
            }
        }

        eventChannel.filter { availableGroup.isNotEmpty() }.subscribeAlways<MemberJoinEvent.Active> {
            try {
                if(member.group.id in availableGroup){
                    //
                    for(record in memberJoinRequestRecord){
                        if(record.id == member.id){
                            val nick = Regex(pattern = ".*答案：(.+)").find(record.message)?.value?.substring(3)

                            if(nick != null) {
                                // 修改名片
                                member.nameCard = nick

                                // 查成分
                                val result = getFollow(nick)
                                delay(1000)
                                result?.let { it1 -> member.group.sendMessage(At(member) + "\n" + it1) }
                            }
                            break
                        }
                    }
                }
            }catch (e:Throwable){
                bot.getFriendOrFail(administrator).sendMessage("修改名片或查成分失败，${e.message}")
                logger.error(e)
            }
        }
    }
}