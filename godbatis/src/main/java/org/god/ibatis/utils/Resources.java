package org.god.ibatis.utils;

import java.io.InputStream;

/**
 * godbatis 框架提供的一個工具類
 * 這個工具類專門完成"類路徑"中資源的加載
 * @author Roger
 * @version 1.0
 * @since 1.0
 */
public class Resources {

    /**
     * 工具類的建構方法都是建設私有化的。
     * 因為工具類中的方法都是靜態的，不需要創建物件就能調用。
     * 為了避免 new 物件，所有的建構方法私有化。
     * 這只是一種編程習慣。
     */
    private Resources() {}

    /**
     * 從類路徑當中加載資源。
     * @param resource 放在類路徑當中的資源文件。
     * @return 指向資源文件的一個輸入流。
     */
    public static InputStream getResourceAsStream(String resource) {
        return ClassLoader.getSystemClassLoader().getResourceAsStream(resource);
    }
}
