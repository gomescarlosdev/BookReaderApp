package br.com.poo2.bookreaderapp.model;

public class BookFile {
    private String fileName;
    private String fileUrl;

    public BookFile() {

    }

    public BookFile(String fileName, String fileUrl) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }
}
