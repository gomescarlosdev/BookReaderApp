package br.com.poo2.bookreaderapp.model;

public class BookFile {

    private String fileName;
    private String fileContext;

    public BookFile() {

    }

    public BookFile(String fileName, String fileUrlRef) {
        this.fileName = fileName;
        this.fileContext = fileUrlRef;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileContext() {
        return fileContext;
    }


}
