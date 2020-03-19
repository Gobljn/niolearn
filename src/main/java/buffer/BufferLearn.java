package buffer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BufferLearn {

    public static void main(String[] args) {
        readFileWithChannel();
    }

    private static void readFileWithChannel() {
        String inFileStr = "/Users/nbonsi/fworks/repo/niolearn/src/main/resources/image/montagna.jpg";
        String outFileStr = "/Users/nbonsi/fworks/repo/niolearn/src/main/resources/image/montagna_write.jpg";
        long startTime;
        long elapsedTime;
        int bufferSizeKB = 4;
        int bufferSize = bufferSizeKB * 1024;

        // Check file length
        File fileIn = new File(inFileStr);
        System.out.println("File size is " + fileIn.length() + " bytes");
        System.out.println("Buffer size is " + bufferSizeKB + " KB");
        System.out.println("Using FileChannel with an indirect ByteBuffer of " + bufferSizeKB + " KB");

        try (FileChannel in = new FileInputStream(inFileStr).getChannel();
             FileChannel out = new FileOutputStream(outFileStr).getChannel() )
        {
            // Allocate an indirect ByteBuffer
            ByteBuffer bytebuf = ByteBuffer.allocate(bufferSize);

            startTime = System.nanoTime();

            int bytesCount = 0;
            // Read data from file into ByteBuffer
            while ((bytesCount = in.read(bytebuf)) > 0) {
                // flip the buffer which set the limit to current position, and position to 0.
                bytebuf.flip();
                out.write(bytebuf); // Write data from ByteBuffer to file
                bytebuf.clear(); // For the next read
            }

            elapsedTime = System.nanoTime() - startTime;
            System.out.println("Elapsed Time is " + (elapsedTime / 1000000.0) + " msec");
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
