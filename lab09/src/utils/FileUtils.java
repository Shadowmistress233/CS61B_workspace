package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * 提供简单文件操作的工具库。如有需要，你可以自由修改此文件。
 */
public class FileUtils {
    /**
     * 将指定内容写入给定文件名的文件中。
     *
     * @param filename 要写入的文件名/路径。
     * @param contents 要写入文件的内容。
     * @throws RuntimeException 如果在写入操作过程中发生 IOException。
     */
    public static void writeFile(String filename, String contents) {
        try {
            Files.writeString(new File(filename).toPath(), newlineReplacer(contents));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 读取给定文件名文件的全部内容。
     *
     * @param filename 要读取的文件名/路径。
     * @return 文件的字符串内容。
     * @throws RuntimeException 如果在读取操作过程中发生 IOException。
     */
    public static String readFile(String filename) {
        try {
            return newlineReplacer(Files.readString(new File(filename).toPath()));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 检查给定文件名的文件是否存在。
     *
     * @param filename 要检查是否存在的文件名/路径。
     * @return 如果文件存在则返回 true，否则返回 false。
     */
    public static boolean fileExists(String filename) {
        return new File(filename).exists();
    }

    /**
     * 从字符串中移除 '\r' 回车符，以提升本实验在 Windows 与其他操作系统之间的兼容性。
     *
     * @param contents 待处理的字符串内容。
     * @return 去除 '\r' 后的字符串内容。
     */
    private static String newlineReplacer(String contents) {
        return contents.replace("\r", "");
    }
}
