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

    public int getItemId() {
        return item != null ? item.getIdItem() : 0;
    }

    public void setItemId(int idItem) {
        if (item == null) {
            item = new Item();
        }
        item.setIdItem(idItem);
    }

    public int getEtiquetaId() {
        return etiqueta != null ? etiqueta.getIdEtiqueta() : 0;
    }

    public void setEtiquetaId(int idEtiqueta) {
        if (etiqueta == null) {
            etiqueta = new Etiqueta();
        }
        etiqueta.setIdEtiqueta(idEtiqueta);
    }
}
