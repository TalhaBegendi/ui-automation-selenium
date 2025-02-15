package utils.helpers;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileHelper {

    public static List<File> getFileListWithDirectoryNameAndEndWith(String directoryName, String endWith) {
        File directory = new File(directoryName);
        List<File> resultList = new ArrayList<File>();
        File[] fList = directory.listFiles();
        resultList.addAll(Arrays.asList(fList));
        for (File file : fList) {
            if (file.isFile()) {
                System.out.println(file.getAbsolutePath());
            } else if (file.isDirectory()) {
                resultList.addAll(getFileListWithDirectoryNameAndEndWith(file.getAbsolutePath(), endWith));
            }
        }
        return resultList.stream().filter(Object -> Object.getName().endsWith(endWith)).collect(Collectors.toList());
    }
}
