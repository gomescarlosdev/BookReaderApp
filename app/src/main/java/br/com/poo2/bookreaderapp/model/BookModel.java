package br.com.poo2.bookreaderapp.model;

import java.util.Arrays;

public class BookModel {

    private Integer id;
    private String customerId;
    private String fileName;
    private byte[] fileData;

    // Construtor
    public BookModel(Integer id, String customerId, String fileName, byte[] fileData) {
        this.id = id;
        this.customerId = customerId;
        this.fileName = fileName;
        this.fileData = fileData;
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
