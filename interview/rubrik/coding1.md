//We’re going to think about how file copying works. You have four magic functions, open, pread, pwrite, close. You can think of these as syscalls if that’s familiar to you. These don’t need to be implemented, but rather you can use these to implement a 5th function: copy. Here are the signatures of each function:



// public interface FileSystem {

//     /**
//      * Opens a file descriptor for a named file.
//      * @return An integer representing the file descriptor (fd).
//      * @throws Exception if the file cannot be opened.
//      */
//     int open(String name) throws Exception;

//     /**
//      * Reads up to buf.length bytes from the file starting at the specific offset.
//      *
//      * @param fd The file descriptor.
//      * @param buf The buffer to fill.
//      * @param offset The absolute offset in the file to start reading from.
//      * @return The number of bytes actually read, or -1 if the offset is at or past the End of File (EOF).
//      * @throws Exception if a read error occurs.
//      */
//     int pread(int fd, byte[] buf, long offset) throws Exception;

//     /**
//      * Writes the entire content of buf to the file starting at the specific offset.
//      *
//      * @param fd The file descriptor.
//      * @param buf The buffer containing data to write.
//      * @param offset The absolute offset in the file to start writing to.
//      * @throws Exception if not all bytes could be successfully written.
//      */
//     void pwrite(int fd, byte[] buf, long offset) throws Exception;

//     /**
//      * Closes the file descriptor and flushes outstanding writes.
//      * @param fd The file descriptor to close.
//      * @throws Exception if the file cannot be closed properly.
//      */
//     void close(int fd) throws IOException;

//     /**
//      * Copies the contents of file src to file dst.
//      */
//     public void copy(String dst, String src) throws Exception {
//         // TODO: Implement this
//     }
// }


//# Provide an abstract base class (ABC) so they know exactly
// # what methods they have.
// from abc import ABC, abstractmethod

// class FileHandle(ABC):
//     """
//     An interface for a file that supports random access.
//     """

//     @abstractmethod
//     def pread(self, n_bytes: int, offset: int) -> bytes:
//         """
//         Reads n_bytes from the file at a specific offset.
//         - Returns: `bytes` object with the data read.
//         - If (n_bytes + offset) > file size, it reads to the end.
//         - If offset >= file size, it returns b"".
//         """
//         pass

//     @abstractmethod
//     def pwrite(self, data: bytes, offset: int) -> None:
//         """
//         Writes the given data to the file at a specific offset.
//         - Raises: IOError if the write fails.
//         """
//         pass

//     @abstractmethod
//     def close(self) -> None:
//         """Closes the file handle."""
//         pass

// class FileSystemClient(ABC):

//     @abstractmethod
//     def open(self, name: str) -> FileHandle:
//         """
//         Opens a file and returns a handle.
//         - Raises: FileNotFoundError if the file doesn't exist.
//         """
//         pass

// #
// # Given the FileSystemClient and FileHandle interfaces above,
// # please implement the following function:

// def copy(client: FileSystemClient, src_name: str, dst_name: str) -> None:
//     # Candidate implements this...
//     pass

//     //




import java.io.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.text.*;
import java.math.*;
import java.nio.Buffer;
import java.util.regex.*;

import org.apache.commons.logging.Log;



public interface FileSystem {

    /**
     * Opens a file descriptor for a named file.
     * @return An integer representing the file descriptor (fd).
     * @throws Exception if the file cannot be opened.
     */
    int open(String name) throws Exception;

    /**
     * Reads up to buf.length bytes from the file starting at the specific offset.
     *
     * @param fd The file descriptor.
     * @param buf The buffer to fill.
     * @param offset The absolute offset in the file to start reading from.
     * @return The number of bytes actually read, or -1 if the offset is at or past the End of File (EOF).
     * @throws Exception if a read error occurs.
     */
    int pread(int fd, byte[] buf, long offset) throws Exception;

    /**
     * Writes the entire content of buf to the file starting at the specific offset.
     *
     * @param fd The file descriptor.
     * @param buf The buffer containing data to write.
     * @param offset The absolute offset in the file to start writing to.
     * @throws Exception if not all bytes could be successfully written.
     */
    void pwrite(int fd, byte[] buf, long offset) throws Exception;

    /**
     * Closes the file descriptor and flushes outstanding writes.
     * @param fd The file descriptor to close.
     * @throws Exception if the file cannot be closed properly.
     */
    void close(int fd) throws IOException;

    /**
     * Copies the contents of file src to file dst.
     */
    public void copy(String dst, String src) throws Exception;
}


class WriteWorker implements Runnable {

    int writeOffSet;
    byte[] buffer;
    int decFileDesc;
    FileSystem fileSystem;
    CountDownLatch countDownLatch;
    
    WriteWorker(int writeOffSet,
        byte[] buffer,
        int decFileDesc,
        FileSystem fileSystem,
        CountDownLatch countDownLatch) {
        
    }
    
    
    @Override
    public void run() {
        try {
            fileSystem.pwrite(decFileDesc, buffer, writeOffSet);     
            countDownLatch.await();
        } catch (Exception e) {
            // log
            throw new Exception(e);
        }
        countDownLatch.notify();
    }
}

class CopyFileHandler implements FileSystem {

    @Override
    public void copy(String dst, String src) throws Exception {
        int srcFileDesc = -1;
        
        int decFileDesc = -1;
        
        try {
            srcFileDesc = open(src);
        } catch (FileNotFoundException fileNotFoundException) {
            // log exception.
            throw new Exception(fileNotFoundException);
        }
        
        try {
            decFileDesc = open(dst);
        } catch (FileNotFoundException fileNotFoundException) {
            // log exception.
            throw new Exception(fileNotFoundException);
        }
        
        
        int bufferSize = 1024 * 8; //8KB
        byte buffer[] = new byte[bufferSize];
        
        
        int srcFileOffset = srcFileDesc;
        int desFileOffset = decFileDesc;
        
        int nobytes = pread(srcFileDesc, buffer, srcFileOffset);
        
        int cores = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor threadPoolExecutor = Executors.newCachedThreadPool(cores);
        CountDownLatch countDownLatch = new CountDownLatch(0);
        
        // chunk by chunk
        while(nobytes > 0) {
            if(nobytes == bufferSize) {
                
                WriteWorker writeWorker = new WriteWorker(decFileDesc, buffer, desFileOffset, this, countDownLatch);
                threadPoolExecutor.submit(writeWorker);      

                srcFileOffset = srcFileOffset + bufferSize;
                desFileOffset = desFileOffset + bufferSize; 
                nobytes = pread(srcFileDesc, buffer, srcFileOffset);
            }
            else {
                byte partialBuffer[] = new byte[nobytes];
                for(int i=0;i<nobytes;i++) {
                    partialBuffer[i] = buffer[i];
                }
                
                WriteWorker writeWorker = new WriteWorker(decFileDesc, partialBuffer, desFileOffset, this, countDownLatch), countDown;
                threadPoolExecutor.submit(writeWorker);
                break;   
            }
        }
        
        countDownLatch.await();
        
        // close the file
        try {
            close(desFileOffset);    
        } catch (Exception exception) {
            // log exception
            throw new Exception(exception);    
        }
        
        try {
            close(srcFileDesc);    
        } catch (Exception exception) {
            // log exception
            throw new Exception(exception);    
        }
        
    }
}
public class Solution {


    void copy (String srcFile, String destFile) {
        
    }

    static int addNumbers(int a, int b) {
        return a+b; 
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        
        int a;
        a = in.nextInt();
        int b;
        b = in.nextInt();
        int sum;

        sum = addNumbers(a, b);
        System.out.println(sum);
    }
}
