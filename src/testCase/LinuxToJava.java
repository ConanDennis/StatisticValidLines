package testCase;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class LinuxToJava {

    public static void main(String[] args) {

        String[] cmds = new String[3];
        cmds[0] = "/bin/bash";
        cmds[1] = "-c";

        Scanner sc = new Scanner(System.in);
        System.out.println("请输入sort命令的具体执行方式(格式如\"-i\")");
        String sortDetail = sc.nextLine();
        System.out.println("请输入grep命令的具体执行方式及匹配内容(格式如\"-h abc\")");
        String grepDetail = sc.nextLine();
        System.out.println("请输入cut命令的具体执行方式及位数(格式如\"-b 3\")");
        String cutDetail = sc.nextLine();
        System.out.println("请输入wc命令的具体执行方式(格式如\"-c\")");
        String wcDetail = sc.nextLine();

        String path = "/Users/kingwufeng/workspace/LinuxChannel/data/";
        File[] files = new File(path).listFiles();
        if(files == null || files.length == 0) {
            System.out.println("The directory is empty!");
            return;
        }
        for (File file : files) {
            String fileName = file.getName();
            if(file.isFile() && fileName.contains(".txt")) {

                cmds[2] = catSortCommand(fileName, sortDetail);
                printResult(fileName, cmds[2], commandExec(cmds));

                cmds[2] = catGrepCommand(fileName, grepDetail);
                printResult(fileName, cmds[2], commandExec(cmds));

                cmds[2] = catCutCommand(fileName, cutDetail);
                printResult(fileName, cmds[2], commandExec(cmds));

                cmds[2] = catWcCommand(fileName, wcDetail);
                printResult(fileName, cmds[2], commandExec(cmds));
            }
        }

    }

    private static String commandExec(String[] cmds) {
        Process process = null;
        BufferedReader br = null;
        try {
            process = Runtime.getRuntime().exec(cmds);
            br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        } catch(IOException e1){
            //此处加log
            System.out.println("The excution of command is failed!");
        } finally {
            if (process != null) {
                process.destroy();
            }
        }

        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e2) {
            /*
            此处加log
             */
            System.out.println("Data inputing is failed!");
        }

        return sb.toString();
    }

    private static void printResult(String fileName , String command , String execRes) {
        System.out.println("file:" + fileName + "\t" + "command:" + command + "\n" +
                "result:" + "\n" + execRes);
        System.out.println();
    }

    private static String catSortCommand(String fileName , String sortDetail) {
        return "cat ./data/" + fileName + " | " + "sort " + sortDetail;
    }

    private static String catGrepCommand(String fileName , String grepDetail) {
        return "cat ./data/" + fileName + " | " + "grep " + grepDetail;
    }

    private static String catCutCommand(String fileName , String cutDetail) {
        return "cat ./data/" + fileName + " | " + "cut " + cutDetail;
    }

    private static String catWcCommand(String fileName , String wcDetail) {
        return "cat ./data/" + fileName + " | " + "wc " + wcDetail;
    }

}
