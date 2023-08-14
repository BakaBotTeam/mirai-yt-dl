package ltd.guimc.mirai.ytdl.command

import kotlinx.coroutines.launch
import ltd.guimc.mirai.ytdl.PluginMain
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.SimpleCommand

object ytdlCommand: SimpleCommand(
    owner = PluginMain,
    primaryName = "ytdl"
) {
    @Handler
    fun CommandSender.onHandlar(url: String) = launch {

    }
}