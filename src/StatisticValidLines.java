import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class StatisticValidLines {

    private static int validLines = 0;
    private static int emptyLines = 0;
    private static int commentLines = 0;

    public static void main(String[] args) throws IOException {

        File file = new File("/Users/kingwufeng/workspace/StatisticValidLines/src/testCase/");
        if (file.exists()) {
            searchFiles(file);
        }

        System.out.println("\n总有效代码行数: " + validLines);
        System.out.println("总空白行数：" + emptyLines);
        System.out.println("总注释行数：" + commentLines);
        System.out.println("总行数：" + (validLines + emptyLines + commentLines));
    }

    private static void searchFiles(File filePath) throws IOException {

        if (filePath.isDirectory()) {
            File[] files = filePath.listFiles();
            if (files != null) {
                for (File file : files) {
                    searchFiles(file);
                }
            }

        }
        if (filePath.isFile()) {
            //判断文件后缀是否为.java
            if (filePath.getName().matches(".*.java")) {
                statisticLines(filePath);
            }
        }
    }

    private static void statisticLines(File file) {
        BufferedReader br = null;
        // 判断此行是否为注释行
        boolean comment = false;

        int cur_emptyLines = 0;
        int cur_commentLines = 0;
        int cur_validLines = 0;

        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.matches("^[//s&&[^//n]]*$")) {
                    emptyLines++;
                    cur_emptyLines++;
                } else if (line.startsWith("/*") && !line.endsWith("*/")) {
                    commentLines++;
                    comment = true;
                    cur_commentLines++;
                } else if (comment && !line.endsWith("*/")) {
                    commentLines++;
                    cur_commentLines++;
                } else if (comment && line.endsWith("*/")) {
                    commentLines++;
                    cur_commentLines++;
                    comment = false;
                } else if (line.startsWith("//")) {
                    commentLines++;
                    cur_commentLines++;
                } else {
                    validLines++;
                    cur_validLines++;
                }
            }

            System.out.println("文件名:" + file.getName() + "\t有效行数" + cur_validLines +
                    " ,空白行数" + cur_emptyLines +
                    " ,注释行数" + cur_commentLines +
                    " ,总行数" + (cur_validLines + cur_emptyLines + cur_commentLines)
            );

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
