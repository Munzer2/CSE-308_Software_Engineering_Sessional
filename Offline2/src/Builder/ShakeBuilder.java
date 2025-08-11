package Builder;

import Ingredients.*;
import Shakes.Shake;
import Shakes.ShakeTypes;

import java.util.ArrayList;
import java.util.List;

public class ShakeBuilder implements Builder{
    private ShakeTypes shakeTypes;
    private Milk milk;
    private Jello jello;
    private Syrup syrup;
    private IceCream iceCream;
    private Flavors flavor;
    private boolean sweetener;
    private boolean coffee;
    private int price;
    private List<String> addedIngredients = new ArrayList<>();

    @Override
    public void setShakeType(ShakeTypes type) {
        this.shakeTypes = type;
    }
    @Override
    public void setJelloLevel(Jello jello) {
        this.jello = jello;
    }

    @Override
    public void setSyrup(Syrup syrup) {
        this.syrup = syrup;
    }

    @Override
    public void setMilk(Milk milk) {
        this.milk = milk;
    }
    @Override
    public void setIceCream(IceCream iceCream) {
        this.iceCream = iceCream;
    }

    @Override
    public void setFlavoring(Flavors flavor) {
        this.flavor = flavor;
    }
    public Shake getResult(){
        if(this.shakeTypes.equals("Coffee")){
            this.sweetener = true;
            this.coffee  = true;
        }
        else{
            this.coffee = false;
            this.sweetener = false;
        }
        return new Shake(this.shakeTypes,this.iceCream,this.milk,this.jello,this.sweetener,this.flavor,this.syrup,this.coffee,this.price,this.addedIngredients);
    }
    @Override
    public void setPrice(int value){
        this.price = value;
    }
    @Override
    public void setCandy()
    {
        this.price += 50;
        this.addedIngredients.add("Candy");
    }
    @Override
    public void setCookies() {
        this.price += 40;
        this.addedIngredients.add("Cookies");
    }

    @Override
    public void setAlmond() {
        this.milk = Milk.Almond;
        this.price += 60;
        this.addedIngredients.add("Almond Milk");
    }

    public void clear(){
        this.addedIngredients.clear();
    }
}
