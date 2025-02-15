package utils.helpers.elementHelper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.helpers.FileHelper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public enum ElementMap {

    INSTANCE;
    Logger logger = LoggerFactory.getLogger(getClass());
    private static final String DEFAULT_DIRECTORY_PATH = "elementValues";
    ConcurrentMap<String, Object> elementMapList;
    FileHelper fileHelper = new FileHelper();

    ElementMap() {
        initMap(getFileList());
    }

    private void initMap(List<File> fileList) {
        elementMapList = new ConcurrentHashMap<>();
        Type elementType = new TypeToken<List<ElementResponse>>() {
        }.getType();
        Gson gson = new Gson();
        List<ElementResponse> elementResponseList = null;
        for (File file : fileList) {
            try {
                elementResponseList = gson
                        .fromJson(new FileReader(file), elementType);
                elementResponseList.parallelStream()
                        .forEach(elementResponse -> elementMapList.put(elementResponse.getKey(), elementResponse));
            } catch (FileNotFoundException e) {
                logger.warn("{} not found", e);
            }
        }
    }

    private List<File> getFileList() {
        List<File> fileList = fileHelper.getFileListWithDirectoryNameAndEndWith(System.getProperty("user.dir") + "/src/test/resources/elementValues", ".json");
        if (fileList == null) {
            logger.warn(
                    "File Directory Is Not Found! Please Check Directory Location. Default Directory Path = {}",
                    DEFAULT_DIRECTORY_PATH);
            throw new NullPointerException();
        }
        return fileList;
    }

    public ElementResponse findElementInfoByKey(String key) {
        return (ElementResponse) elementMapList.get(key);
    }
}