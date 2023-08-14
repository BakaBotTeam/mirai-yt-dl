package ltd.guimc.mirai.ytdl.utils

object SystemUtils {
    fun isWindows(): Boolean = System.getProperty("os.name").lowercase().contains("windows")
}