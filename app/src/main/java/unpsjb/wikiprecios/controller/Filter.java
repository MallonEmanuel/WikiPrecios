package unpsjb.wikiprecios.controller;

import java.util.List;

/**
 * Permite filtrar una lista con respecto a algun discriminante.
 */
public interface Filter {
    public List filter(List list);
}
