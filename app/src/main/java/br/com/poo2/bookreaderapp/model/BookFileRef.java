package br.com.poo2.bookreaderapp.model;

public class BookFileRef {
    private String fileName;
    private String fileUrlRef;

    public BookFileRef() {

    }

    public BookFileRef(String fileName, String fileUrlRef) {
        this.fileName = fileName;
        this.fileUrlRef = fileUrlRef;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileUrlRef() {
        return fileUrlRef;
    }


}
