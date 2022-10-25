/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.execute.around;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Date: 12/12/15 - 3:21 PM
 *
 * @author Jeroen Meulemeester
 */
class SimpleFileWriterTest {

  @TempDir
  public File testFolder;

  @Test
  void testWriterNotNull() throws Exception {
    final var temporaryFile = File.createTempFile("junit", null, testFolder);
    new SimpleFileWriter(temporaryFile.getPath(), Assertions::assertNotNull);
  }

  @Test
  void testCreatesNonExistentFile() throws Exception {
    final var nonExistingFile = new File(this.testFolder, "non-existing-file");
    assertFalse(nonExistingFile.exists());

    new SimpleFileWriter(nonExistingFile.getPath(), Assertions::assertNotNull);
    assertTrue(nonExistingFile.exists());
  }

  @Test
  void testContentsAreWrittenToFile() throws Exception {
    final var testMessage = "Test message";

    final var temporaryFile = File.createTempFile("junit", null, testFolder);
    assertTrue(temporaryFile.exists());

    new SimpleFileWriter(temporaryFile.getPath(), writer -> writer.write(testMessage));
    assertTrue(Files.lines(temporaryFile.toPath()).allMatch(testMessage::equals));
  }

  @Test
  void testRipplesIoExceptionOccurredWhileWriting() {
    var message = "Some error";
    File temporaryFile = getTemporaryFile();
    assertThrows(IOException.class, () -> {
      new SimpleFileWriter(temporaryFile.getPath(), writer -> {
        throw new IOException(message);
      });
    }, message);
  }

  private File getTemporaryFile() {
    File temporaryFile = null;
    try {
        temporaryFile = File.createTempFile("junit", null, testFolder);
    } catch (IOException e) {
        e.printStackTrace();
    }
    return temporaryFile;
  }
}
