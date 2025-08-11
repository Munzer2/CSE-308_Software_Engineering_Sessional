package Shakes;

import Ingredients.*;

import java.util.ArrayList;
import java.util.List;

public class Shake {
    private final ShakeTypes shakeTypes;

    private final IceCream iceCream;
    private final Milk milk;
    private final Jello jello;
    private boolean sweetener;
    private final Flavors flavors;
    private final Syrup syrup;
    private boolean coffee;
    private int basePrice;
    private boolean Sugar;
    private List<String> additional = new ArrayList<>();


    public Shake(ShakeTypes shakeTypes, IceCream iceCream, Milk milk, Jello jello, boolean sweetener, Flavors flavors, Syrup syrup, boolean coffee, int price, List<String> addedIngredients) {
        this.shakeTypes = shakeTypes;
        this.iceCream = iceCream;
        this.milk = milk;
        this.jello = jello;
        this.sweetener = sweetener;
        this.flavors = flavors;
        this.syrup = syrup;
        this.coffee = coffee;
        this.basePrice = price;
        if(addedIngredients.size() > 0 ){
            this.additional.addAll(addedIngredients);
        }
        if( shakeTypes.equals("Zero")) this.Sugar = false;
        else this.Sugar = true;
    }

    public IceCream getIceCream() {
        return iceCream;
    }

    public ShakeTypes getShakeTypes() {
        return shakeTypes;
    }

    public Milk getMilk() {
        return milk;
    }

    public Jello getJello() {
        return jello;
    }

    public boolean getSweetener() {
        return sweetener;
    }

    public boolean getSugar() {
        return Sugar;
    }

    public Flavors getFlavors() {
        return flavors;
    }

    public Syrup getSyrup() {
        return syrup;
    }

    public boolean getCoffee() {
        return coffee;
    }
    public int getBasePrice() { return basePrice;  }
    public void setBasePrice(int value){
        this.basePrice = value;
    }

    public void Printdetails(){
        System.out.println("Type: "  + shakeTypes);
        System.out.println("Base Ingrdients: ");
        if(this.flavors != Flavors.Null) System.out.print("Flavor: " + this.flavors + " ");
        if(this.syrup != Syrup.Null) System.out.print("Syrup: " + this.syrup + " ");
        if(this.jello == Jello.Sugar) System.out.println("Jello: sweet");
        else System.out.println("Jello: sugarfree");
        if(this.iceCream != IceCream.Null) System.out.println(this.iceCream + " Ice-cream Milk: " + this.milk + " ");
        if(this.Sugar) System.out.println("Not sugar-free.");
        else System.out.println("Sugar-free");
        if(additional.size() > 0){
            System.out.println("Additional ingredients: ");
            for(String x: additional) System.out.print(x + " ");
            System.out.println("");
        }
        System.out.println("Total price: " + this.basePrice);
    }
}
