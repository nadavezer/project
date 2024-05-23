public class Vehicle {
    int vehicleid;
    int capacity;
    boolean avaliable;

    public Vehicle(int vehicleid, int capacity) {
        this.vehicleid = vehicleid;
        this.capacity = capacity;
        this.avaliable = true;
    }

    public int getVehicleid() {
        return vehicleid;
    }

    public void setVehicleid(int vehicleid) {
        this.vehicleid = vehicleid;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isAvaliable() {
        return avaliable;
    }

    public void setAvaliable(boolean avaliable) {
        this.avaliable = avaliable;
    }
    public String toString(){//overriding the toString() method
        return ("vehicle:"+this.vehicleid+ " cap:" + this.capacity);
    }
}
