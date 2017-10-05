package unpsjb.wikiprecios.model;

import unpsjb.wikiprecios.R;

/**
 * Created by emanuel on 02/10/17.
 */
public class Category implements Listable{

    private int id;
    private String initials;
    private String name;
    private int img;

    public Category(int id, String letra, String nombre) {
        this.id = id;
        this.initials = letra;
        this.name = nombre;
        this.img =  R.drawable.ic_space;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg(int img) {
        this.img = img;
    }

    @Override
    public String getTitle() {
        return initials;
    }

    @Override
    public String getSubtitle() {
        return name;
    }

    @Override
    public int getImg() {
        return img;
    }

    @Override
    public boolean isFavourite() {
        return false;
    }


}
