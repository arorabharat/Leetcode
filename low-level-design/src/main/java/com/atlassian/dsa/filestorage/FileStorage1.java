package com.atlassian.dsa.filestorage;


import java.util.*;

public class FileStorage1 {

    /**
     * Questions:
     * Files - name, size
     * - can we have duplicate files? if yes then what would be their name? or diff uuid
     * Collection(s) - name, List<File>
     * - can this have dup files
     * - can a file be in 2 collections?
     * <p>
     * - total Size of all files in the System
     * - Top collections by file size.
     * <p>
     * <p>
     * F1, f2, f3, f4
     * C1, C2
     * <p>
     * C1 -> f1, f2
     * C3 -> f3, f2
     * Unlabeled -> f4
     * Is a file guaranteed to be in a collection?
     * <p>
     * Add a file w/wo label (allow dupes? -> No dupes then yes)
     * Remove a file? - First no then Yes
     * <p>
     * long getTotalSize()
     * List<Pair<String,Integer>> getTopNCollectionsBySize()
     */
    static class File {

        private final String name;
        private final int size;

        public File(String name, int size) {
            this.name = name;
            this.size = size;
        }

        public String getName() {
            return name;
        }

        public int getSize() {
            return size;
        }
    }

    static class FileCollection {
        private final String name;
        private final List<File> files;
        private int size;

        public FileCollection(String name) {
            this.name = name;
            this.files = new ArrayList<>();
            this.size = 0;
        }

        public String getName() {
            return name;
        }

        public boolean addFile(File file) {
            this.size = this.size + file.getSize();
            return this.files.add(file);
        }

        public boolean removeFile(File file) {
            this.size = this.size - file.getSize();
            return this.files.remove(file);
        }

        public int getSize() {
            return this.size;
        }
    }

    static class StorageService {

        private final TreeMap<Integer, Set<FileCollection>> sortedBySize;
        private final Map<String, FileCollection> collectDb = new HashMap<>();
        private final Map<String, File> filesDb = new HashMap<>();

        public StorageService() {
            this.sortedBySize = new TreeMap<>();
        }

        public FileCollection addCollection(String collectionName) {
            if (!collectDb.containsKey(collectionName)) {
                FileCollection newCollection = new FileCollection(collectionName);
                collectDb.put(collectionName, newCollection);
                Set<FileCollection> collectionOfSize = sortedBySize.get(0);
                if (!sortedBySize.containsKey(0)) {
                    sortedBySize.put(0, new HashSet<>());
                }
                collectionOfSize.add(newCollection);
                return newCollection;
            } else {
                return collectDb.get(collectionName);
            }
        }

        public File addFile(String fileName, int size) {
            if (!filesDb.containsKey(fileName)) {
                File file = new File(fileName, size);
                filesDb.put(fileName, file);
                return file;
            } else {
                return filesDb.get(fileName);
            }
        }

        public boolean addFileToCollection(String fileName, String collectionName) {
            File file = filesDb.get(fileName);
            FileCollection fileCollection = collectDb.get(collectionName);
            if (fileCollection == null || file == null) {
                return false;
            }
            int currCollectionSize = fileCollection.getSize();
            fileCollection.addFile(file);
            int newCollectionSize = fileCollection.getSize();
            sortedBySize.computeIfAbsent(newCollectionSize, k -> new HashSet<>());
            sortedBySize.get(newCollectionSize).add(fileCollection);
            sortedBySize.get(currCollectionSize).remove(fileCollection);
            return true;
        }

        private List<FileCollection> topKCollections(int k) {
//            List<FileCollection> fileCollections = new ArrayList<>();
//            for (int i =  0 ; i < k && i < sortedBySize.size(); i++) {
////                fileCollections.add(so)
//            }
//            for (Map.Entry<Integer>)
            return new ArrayList<>();
        }
    }

}