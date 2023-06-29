package br.com.poo2.bookreaderapp.model;

import java.util.Arrays;

public class BookModelItem {

    private Integer id;
    private String customerId;
    private String fileName;
    private byte[] fileData;
    private int cover;

    // Construtor
    public BookModelItem(Integer id, String customerId, String fileName, byte[] fileData, int cover) {
        this.id = id;
        this.customerId = customerId;
        this.fileName = fileName;
        this.fileData = fileData;
        this.cover = cover;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public int getCover() {
        return cover;
    }

    public void setCover(int cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        return "BookModel{" +
                "id=" + id +
                ", customerId='" + customerId + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileData=" + Arrays.toString(fileData) +
                '}';
    }


}
