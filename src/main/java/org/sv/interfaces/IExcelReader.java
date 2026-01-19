package org.sv.interfaces;

import java.io.File;
import java.util.List;

public interface IExcelReader<T> {
    boolean soporta(File file);

    List<T> leer(File file);
}
