package ltd.guimc.mirai.ytdl.command

import kotlinx.coroutines.launch
import ltd.guimc.mirai.ytdl.PluginMain
import ltd.guimc.mirai.ytdl.utils.OverflowUtils
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.console.util.cast
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.utils.ExternalResource.Companion.toExternalResource
import top.mrxiaom.overflow.OverflowAPI
import top.mrxiaom.overflow.contact.RemoteBot
import java.io.File

object ytdlCommand: SimpleCommand(
    owner = PluginMain,
    primaryName = "ytdl"
) {
    @Handler
    fun CommandSender.onHandlar(url: String) = launch {
        require(subject is Group)
        sendMessage("解析中...")
        try {
            val file = PluginMain.ytdlRunner.fetch(url)
            sendMessage("解析成功, 正在上传")
            val er = file.toExternalResource()
            (subject!! as Group).files.uploadNewFile("video-${System.currentTimeMillis()}.zip", er)
            er.close()
            file.delete()
        } catch (e: Exception) {
            sendMessage("发生错误")
            e.printStackTrace()
        }
    }
}