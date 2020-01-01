package com.compassitesinc.chat.operator.constructs;

/**
 * Created by prakashjetty on 11/27/18.
 */
public class ChannelFileMessageFragment {

    private String fileName;
    private Long fileSize;
    private String fileBytesBase64;
    private String filePath;


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileBytesBase64() {
        return fileBytesBase64;
    }

    public void setFileBytesBase64(String fileBytesBase64) {
        this.fileBytesBase64 = fileBytesBase64;
    }
}
