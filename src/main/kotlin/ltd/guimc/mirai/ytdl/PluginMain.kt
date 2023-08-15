package ltd.guimc.mirai.ytdl

import ltd.guimc.mirai.ytdl.command.ytdlCommand
import ltd.guimc.mirai.ytdl.utils.YtdlRunner
import net.mamoe.mirai.console.command.CommandManager
import net.mamoe.mirai.console.permission.AbstractPermitteeId
import net.mamoe.mirai.console.permission.PermissionService
import net.mamoe.mirai.console.permission.PermissionService.Companion.hasPermission
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.contact.User
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.event.events.NewFriendRequestEvent
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.message.data.Image.Key.queryUrl
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.utils.info

object PluginMain : KotlinPlugin(
    JvmPluginDescription(
        id = "ltd.guimc.mirai.ytdl",
        name = "Youtube-dl for Mirai",
        version = "0.1.0"
    ) {
        author("BakaBotTeam@GitHub")
    }
) {
    lateinit var ytdlRunner: YtdlRunner

    override fun onEnable() {
        logger.info { "Plugin loading" }
        ytdlRunner = YtdlRunner()
        CommandManager.registerCommand(ytdlCommand)
        logger.info { "Plugin loaded" }
    }

    override fun onDisable() {
        logger.info { "Plugin unloaded" }
    }
}
