package contracts.models;

public interface Destroyable {
    int getHealth();

    void decreaseHealth(int damage);
}