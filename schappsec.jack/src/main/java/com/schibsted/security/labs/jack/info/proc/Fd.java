package com.schibsted.security.labs.jack.info.proc;

import com.schibsted.security.labs.jack.readers.file.FileReader;
import com.schibsted.security.labs.jack.types.FileDescriptor;
import com.schibsted.security.labs.jack.types.FileDescriptorType;

import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Fd {
  public List<FileDescriptor> fds;
  public int sockets;
  public int pipes;

  public Fd(List<FileDescriptor> fds) {
    this.fds = fds;

    for (var fd : fds) {
      if (fd.type == FileDescriptorType.SOCKET) {
        this.sockets += 1;
      } else if (fd.type == FileDescriptorType.PIPE) {
        this.pipes += 1;
      }
    }

  }

  public static Fd read(int processId) {
    String fdDirectory = "/proc/" + processId + "/fd/";
    try (var files = Files.list(Paths.get(fdDirectory))) {
      List<FileDescriptor> fds = files.map(fd -> {
        if (Files.isSymbolicLink(fd)) {
          try {
            String path = fdDirectory + fd.getFileName().toString();
            String result = FileReader.readSymbolicLink(path);
            int start = result.indexOf('[');
            // FIXME
            if (result.startsWith("/") || start < 0) {
              return new FileDescriptor(FileDescriptorType.FILE, 0);
            }
            if (result.startsWith("anon_inode:")) {
              return new FileDescriptor(FileDescriptorType.ANON_INODE, 0);
            }

            int end = result.indexOf(']');
            long inode = Long.parseLong(result.substring(start + 1, end));
            if (result.startsWith("pipe:")) {
              return new FileDescriptor(FileDescriptorType.PIPE, inode);
            } else if (result.startsWith("socket:")) {
              return new FileDescriptor(FileDescriptorType.SOCKET, inode);
            } else {
              throw new RuntimeException("unknown type");
            }
          } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
          }
        } else {
          // FIXME get inode or use path instead
          throw new RuntimeException("unsupported");
        }
      }).collect(Collectors.toList());

      return new Fd(fds);
    } catch (IOException e) {
      return new Fd(new ArrayList<>());
    }
  }
}
