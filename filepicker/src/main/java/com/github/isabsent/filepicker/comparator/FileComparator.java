package com.github.isabsent.filepicker.comparator;

import java.io.File;
import java.util.Comparator;

abstract class FileComparator implements Comparator<File> {
    private boolean ascending = true;

    FileComparator(boolean asc){
        ascending = asc;
    }

    FileComparator(){
        this(true);
    }

    public int compare(File f1, File f2){
        if (f1.isDirectory() && f2.isFile())
            return -1;
        else if (f1.isFile() && f2.isDirectory())
            return 1;
        else
            return comp((ascending ? f1 : f2), (ascending ? f2 : f1));
    }

    protected abstract int comp(File f1, File f2);
}
