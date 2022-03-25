package com.schibsted.security.labs.jack.types;

public class FileDescriptor {
  public final FileDescriptorType type;
  public final long inode;

  public FileDescriptor(FileDescriptorType type, long inode) {
    this.type = type;
    this.inode = inode;
  }
}
