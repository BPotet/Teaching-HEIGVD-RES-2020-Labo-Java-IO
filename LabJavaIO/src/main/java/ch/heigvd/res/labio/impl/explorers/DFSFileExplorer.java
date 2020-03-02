package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;
import java.io.File;
import java.io.FileFilter;

/**
 * This implementation of the IFileExplorer interface performs a depth-first
 * exploration of the file system and invokes the visitor for every encountered
 * node (file and directory). When the explorer reaches a directory, it visits all
 * files in the directory and then moves into the subdirectories.
 * 
 * @author Olivier Liechti
 */
public class DFSFileExplorer implements IFileExplorer {

  private static final FileFilter onlyConcreteFiles = File::isFile;

  private static final FileFilter onlyConcreteDirectories = File::isDirectory;

  @Override
  public void explore(File rootDirectory, IFileVisitor visitor) {

    // First I visit myself

    visitor.visit(rootDirectory);

    // Secondly I visit my child files

    File[] concreteFiles = rootDirectory.listFiles(onlyConcreteFiles);

    // if there isn't any child files, come back in our exploration

    if(concreteFiles == null)
      return;

    for(File childFile : concreteFiles) {
      if (childFile != null) {
        visitor.visit(childFile);
      }
    }

    // Thirldy I explore my subdirectories

    File[] subDirectories = rootDirectory.listFiles(onlyConcreteDirectories);

    // if there isn't any subdirectories, come back in our exploration

    if(subDirectories == null)
      return;

    for(File subDirectory : subDirectories){
      if(subDirectory != null){
        explore(subDirectory, visitor);
      }
    }

  }

}
