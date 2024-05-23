public class ItemOrder {
    Item item;
    int quanity;
    int orderid;

    public ItemOrder(Item item, int quanity, int orderid) {
        this.item = item;
        this.quanity = quanity;
        this.orderid = orderid;
    }

    public String toString(){//overriding the toString() method
        return this.item.name +" x " +this.quanity;
    }

    public Item getItem() {
        return item;
    }

    public int getQuanity() {
        return quanity;
    }

    public int getOrderid() {
        return orderid;
    }
}
