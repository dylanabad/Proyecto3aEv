package model;

public class ItemEtiqueta {
    private Item item;
    private Etiqueta etiqueta;

    // Constructor vacío
    public ItemEtiqueta() {}


    public ItemEtiqueta(Item item, Etiqueta etiqueta) {
        this.item = item;
        this.etiqueta = etiqueta;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Etiqueta getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(Etiqueta etiqueta) {
        this.etiqueta = etiqueta;
    }
}
