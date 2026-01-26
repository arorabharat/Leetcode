package com.atlassian.dsa.filestorage;


import java.util.*;

public class FileStorage {

    /**
     * Questions:
     * Files - name, size
     * - can we have duplicate files? if yes then what would be their name? or diff uuid
     * Collection(s) - name, List<File>
     *     - can this have dup files
     *     - can a file be in 2 collections?
     *
     *     - total Size of all files in the System
     *     - Top collections by file size.
     *
     *
     * F1, f2, f3, f4
     * C1, C2
     *
     * C1 -> f1, f2
     * C3 -> f3, f2
     * Unlabeled -> f4
     * Is a file guaranteed to be in a collection?
     *
     * Add a file w/wo label (allow dupes? -> No dupes then yes)
     * Remove a file? - First no then Yes
     *
     * long getTotalSize()
     * List<Pair<String,Integer>> getTopNCollectionsBySize()
     */

    static class File {
        String name;
        int size;
        File(String name, int size) {
            if (size <= 0) {
                throw new RuntimeException("0 or less size not allowed");
            }
            this.name = name;
            this.size = size;
        }
    }

    static class Collection {
        String name;
        Set<File> files;
        int size;
        Collection(String name) {
            this.name = name;
            files = new HashSet<>();
        }
        void addFile(File file) {
            if (file != null) {
                files.add(file);
                size += file.size;
            }
        }
    }
    static class Pair<T,V> {
        T first;
        V second;

        Pair (T first, V second) {
            this.first = first;
            this.second = second;
        }
        T getFirst() {
            return first;
        }
        V getSecond() {
            return second;
        }
        @Override
        public String toString() {
            return first.toString() + " : " + second.toString();
        }
    }

    long totalSize = 0;
    Map<String, File> fileMap;
    TreeMap<Integer, List<String>> topSizeCollection;
    Map<String, Collection> collectionMap;

    FileStorage() {
        fileMap = new HashMap<>();
        topSizeCollection = new TreeMap<>(Comparator.reverseOrder());
        collectionMap = new HashMap<>();
        Collection unnamedCollection = new Collection("unnamed");
        collectionMap.put("unnamed", unnamedCollection);
    }

    /**
     * Adds file to the given collection. if no collection is listed then adds this to the unnamed collection
     * @param name
     * @param size
     * @param collectionNames
     */
    void addFile(String name, int size, List<String> collectionNames) {
        File file = new File(name, size);
        File oldFile = fileMap.put(name, file);
        totalSize += file.size;

        if (oldFile != null) {
            // we overwrote old file
            // ignore for now
        }
        if (collectionNames != null && !collectionNames.isEmpty()) {
            for (var collectionName : collectionNames) {
                Collection collection = collectionMap.get(collectionName);
                if (collection == null) {
                    createCollection(collectionName);
                }
                addFileToCollection(collectionName, file);
            }
        }
    }

    void addFileToCollection(String collectionName, File file) {
        Collection collection = collectionMap.get(collectionName);
        if (collection == null) {
            return;
        }

        topSizeCollection.get(collection.size).remove(collectionName);
        if (topSizeCollection.get(collection.size).isEmpty()){
            topSizeCollection.remove(collection.size);
        }

        collection.addFile(file);
        topSizeCollection.computeIfAbsent(collection.size, k -> new ArrayList<>()).add(collectionName);
    }

    Collection createCollection(String name) {
        Collection collection = new Collection(name);
        collectionMap.put(name, collection);
        topSizeCollection.computeIfAbsent(collection.size, k -> new ArrayList<>()).add(name);
        return collection;
    }

    // What if we have > N collection for same size?
    List<Pair<String, Integer>> getTopNCollection(int N) {
        List<Pair<String, Integer>> topCollection = new ArrayList<>();
        for(var size : topSizeCollection.keySet()) {
            if (topCollection.size() >= N) {
                break;
            }
            List<String> collectionNames = topSizeCollection.get(size);
            for (var collectionName : collectionNames) {
                if (topCollection.size() >= N) {
                    break;
                }
                Pair<String, Integer> collectionDetail = new Pair<>(collectionName, size);
                topCollection.add(collectionDetail);
            }
            if (topCollection.size() >= N) {
                break;
            }
        }
        return topCollection;
    }

    public long getTotalFileSize() {
        return totalSize;
    }

    public static void main(String[] args) {
        FileStorage fileStorage = new FileStorage();
        fileStorage.addFile("first", 1 , null);
        fileStorage.addFile("pic1", 2 , List.of("photos", "movie"));
        fileStorage.addFile("pic2", 1 , List.of("photos"));
        fileStorage.addFile("movie", 1 , List.of("video", "movie"));
        System.out.println(fileStorage.getTopNCollection(4));
        System.out.println(fileStorage.getTotalFileSize());
    }

}