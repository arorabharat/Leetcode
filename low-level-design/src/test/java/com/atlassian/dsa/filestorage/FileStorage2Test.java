package com.atlassian.dsa.filestorage;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileStorage2Test {

    @Test
    void baseCase() {
        FileStorage2.FileService  fileService = new FileStorage2.FileService();
        List<FileStorage2.FileCollection> fileCollections = fileService.topKCollectionBySize(2);
        assertEquals(0, fileCollections.size());
    }

    @Test
    void with2Files() {
        FileStorage2.FileService  fileService = new FileStorage2.FileService();
        fileService.addFile("F1", 100);
        fileService.addFile("F2", 200);
        fileService.addFile("F3", 300);
        fileService.addCollection("C1");
        fileService.addCollection("C2");
        fileService.addCollection("C3");
        fileService.addFileToCollection("F1", "C1");
        fileService.addFileToCollection("F2", "C2");
        fileService.addFileToCollection("F3", "C2");
        List<FileStorage2.FileCollection> fileCollections = fileService.topKCollectionBySize(2);
        assertEquals(2, fileCollections.size());
        assertEquals("C2", fileCollections.get(0).getName());
        assertEquals("C1", fileCollections.get(1).getName());
    }

    @Test
    void name() {

    }
}