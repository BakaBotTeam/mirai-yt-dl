package ltd.guimc.mirai.ytdl.utils

import ltd.guimc.mirai.ytdl.PluginMain
import java.io.*
import java.net.URL
import java.nio.channels.Channels
import java.util.concurrent.TimeUnit
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream


class YtdlRunner {
    val ytdlPath = File(PluginMain.dataFolderPath.toFile(), if (SystemUtils.isWindows()) "yt-dlp.exe" else "yt-dlp")

    init {
        val url = URL(if (SystemUtils.isWindows()) "https://ghproxy.com/https://github.com/yt-dlp/yt-dlp/releases/latest/download/yt-dlp.exe"
            else "https://ghproxy.com/https://github.com/yt-dlp/yt-dlp/releases/latest/download/yt-dlp")

        if (!ytdlPath.exists()) {
            PluginMain.logger.info("正在下载yt-dlp")
            url.openStream().use {
                Channels.newChannel(it).use { rbc ->
                    FileOutputStream(ytdlPath).use { fos ->
                        fos.channel.transferFrom(rbc, 0, Long.MAX_VALUE)
                    }
                }
            }
            var ytdlPathStr = ytdlPath.toString()
            if (!ytdlPathStr.startsWith("/")) {
                ytdlPathStr = "./$ytdlPathStr"
            }

            "chmod +x $ytdlPathStr".runCommand(File("./"))
            PluginMain.logger.info("下载yt-dlp成功")
        } else {
            PluginMain.logger.info("正在检查更新")
            PluginMain.logger.info(run("-U"))
        }
    }

    fun run(args: String): String? {
        var ytdlPathStr = ytdlPath.toString()
        if (!ytdlPathStr.startsWith("/")) {
            ytdlPathStr = "./$ytdlPathStr"
        }

        return "$ytdlPathStr $args".runCommand(File("./"))
    }

    fun fetch(url: String): File {
        val targetDir = File(PluginMain.dataFolder, "tmp-${System.currentTimeMillis()}")
        val zipFile = File(PluginMain.dataFolder, "tmp-${System.currentTimeMillis()}.zip")
        targetDir.mkdirs()
        PluginMain.logger.info(run("-P ${targetDir.absolutePath} ${url}"))
        if (targetDir.listFiles() == null || targetDir.listFiles()?.isEmpty() == true) {
            throw Exception("解析失败")
        } else {
            zip(targetDir.listFiles()!!.toList(), zipFile)
        }

        for (file in targetDir.listFiles()!!) {
            file.delete()
        }
        targetDir.delete()

        return zipFile
    }

    fun zip(files: List<File>, zipFile: File) {
        ZipOutputStream(BufferedOutputStream(FileOutputStream(zipFile))).use { output ->
            files.forEach { file ->
                if (file.length() > 1) {
                    FileInputStream(file).use { input ->
                        BufferedInputStream(input).use { origin ->
                            val entry = ZipEntry(file.name)
                            output.putNextEntry(entry)
                            origin.copyTo(output, 1024)
                        }
                    }
                }
            }
        }
    }

    fun String.runCommand(workingDir: File): String? {
        try {
            val parts = this.split("\\s".toRegex())
            val proc = ProcessBuilder(*parts.toTypedArray())
                .directory(workingDir)
                .redirectOutput(ProcessBuilder.Redirect.PIPE)
                .redirectError(ProcessBuilder.Redirect.PIPE)
                .start()

            proc.waitFor(60, TimeUnit.MINUTES)
            return proc.inputStream.bufferedReader().readText()
        } catch(e: IOException) {
            e.printStackTrace()
            return null
        }
    }
}