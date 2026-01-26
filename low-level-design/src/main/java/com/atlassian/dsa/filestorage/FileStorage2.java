package com.atlassian.dsa.filestorage;


import java.util.*;

public class FileStorage2 {

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

    static class FileCollection implements Comparable<FileCollection>{

        private final String name;
        private final Set<File> files;
        private int size;

        public FileCollection(String name) {
            this.name = name;
            this.files = new HashSet<>();
            this.size = 0;
        }

        public boolean addFile(File file) {
            if (file != null) {
                this.files.add(file);
                this.size = this.size + file.getSize();
                return true;
            }
            return false;
        }

        public String getName() {
            return name;
        }

        public int getSize() {
            return size;
        }

        @Override
        public int compareTo(FileCollection o) {
            return name.compareTo(o.name);
        }
    }

    static class FileService {

        private final Map<String, File> fileMap = new HashMap<>();
        private final Map<String, FileCollection> fileCollectionMap = new HashMap<>();
        private final TreeMap<Integer, SortedSet<FileCollection>> collectionBySize = new TreeMap<>(Comparator.reverseOrder());

        public File addFile(String fileName, int size) {
            if (!fileMap.containsKey(fileName)) {
                File newFile = new File(fileName, size);
                fileMap.put(fileName, newFile);
                return newFile;
            } else {
                return fileMap.get(fileName);
            }
        }

        public FileCollection addCollection(String collectionName) {
            if (!fileCollectionMap.containsKey(collectionName)) {
                FileCollection newCollection = new FileCollection(collectionName);
                fileCollectionMap.put(collectionName, newCollection);
                collectionBySize.computeIfAbsent(0, k -> new TreeSet<>()).add(newCollection);
                return newCollection;
            } else {
                return fileCollectionMap.get(collectionName);
            }
        }

        public boolean addFileToCollection(String fileName, String collectionName) {
            File file = fileMap.get(fileName);
            FileCollection fileCollection = fileCollectionMap.get(collectionName);
            if (fileCollection == null || file == null) {
                return false;
            }
            int currSize = fileCollection.getSize();
            collectionBySize.get(currSize).remove(fileCollection);
            fileCollection.addFile(file);
            int newSize = fileCollection.getSize();
            collectionBySize.computeIfAbsent(newSize, k -> new TreeSet<>()).add(fileCollection);
            return true;
        }

        public List<FileCollection> topKCollectionBySize(int k) {
            List<FileStorage2.FileCollection> topCollections = new ArrayList<>();
            boolean isDone = false;
            for (Integer size : collectionBySize.keySet()) {
                SortedSet<FileCollection> fileCollections = collectionBySize.get(size);
                for (FileCollection fileCollection : fileCollections) {
                    topCollections.add(fileCollection);
                    if (topCollections.size() == k) {
                        isDone = true;
                        break;
                    }
                }
                if (isDone) {
                    break;
                }
            }
            return topCollections;
        }
    }

}