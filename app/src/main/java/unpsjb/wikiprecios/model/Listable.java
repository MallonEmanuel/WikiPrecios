package unpsjb.wikiprecios.model;


/**
 * La interface listable permite a quien la implemente poder ser parte de
 * una lista de elementos visibles en la app.
 */
public interface Listable {
    public String getTitle();
    public String getSubtitle();
    public int getImg();
    public boolean isFavourite();
}
