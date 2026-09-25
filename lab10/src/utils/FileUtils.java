package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * 简单文件操作工具库。你可以自由修改此文件。
 */
public class FileUtils {
    /**
     * 将指定内容写入具有给定文件名的文件中。
     *
     * @param filename 要写入的文件名。
     * @param contents 要写入文件的内容。
     * @throws RuntimeException 如果在写入操作期间发生 IOException。
     */
    public static void writeFile(String filename, String contents) {
        try {
            Files.writeString(new File(filename).toPath(), contents);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 读取具有给定文件名的文件内容。
     *
     * @param filename 要读取的文件名。
     * @return 文件内容的字符串表示形式。
     * @throws RuntimeException 如果在读取操作期间发生 IOException。
     */
    public static String readFile(String filename) {
        try {
            return Files.readString(new File(filename).toPath());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 检查具有给定文件名的文件是否存在。
     *
     * @param filename 要检查是否存在的文件的文件名。
     * @return 如果文件存在则返回 true，否则返回 false。
     */
    public static boolean fileExists(String filename) {
        return new File(filename).exists();
    }
}
