package Maker;
import Builder.Builder;
import Ingredients.*;
import Shakes.ShakeTypes;

public class ShakerProducer {
    public void chocolateShakeBuilder(Builder builder){
        builder.setMilk(Milk.regular);
        builder.setIceCream(IceCream.Chocolate);
        builder.setShakeType(ShakeTypes.Chocolate);
        builder.setJelloLevel(Jello.Sugar);
        builder.setSyrup(Syrup.Chocolate);
        builder.setFlavoring(Flavors.Chocolate);
        builder.setPrice(230);
    }

    public void strawberryShakeBuilder(Builder builder){
        builder.setMilk(Milk.regular);
        builder.setIceCream(IceCream.Strawberry);
        builder.setShakeType(ShakeTypes.Strawberry);
        builder.setJelloLevel(Jello.Sugar);
        builder.setSyrup(Syrup.Strawberry);
        builder.setFlavoring(Flavors.Strawberry);
        builder.setPrice(200);
    }
    public void CoffeeShakeBuilder(Builder builder){
        builder.setMilk(Milk.regular);
        builder.setIceCream(IceCream.Null);
        builder.setShakeType(ShakeTypes.Coffee);
        builder.setJelloLevel(Jello.Sugar);
        builder.setSyrup(Syrup.Null);
        builder.setFlavoring(Flavors.Coffee);
        builder.setPrice(250);
    }
    public void zeroShakeBuilder(Builder builder){
        builder.setMilk(Milk.regular);
        builder.setIceCream(IceCream.Null);
        builder.setShakeType(ShakeTypes.Zero);
        builder.setJelloLevel(Jello.SugarFree);
        builder.setSyrup(Syrup.Null);
        builder.setFlavoring(Flavors.Vanilla);
        builder.setPrice(240);
    }
    public void vanillaShakeBuilder(Builder builder){
        builder.setMilk(Milk.regular);
        builder.setIceCream(IceCream.Null);
        builder.setShakeType(ShakeTypes.Vanilla);
        builder.setJelloLevel(Jello.Sugar);
        builder.setSyrup(Syrup.Null);
        builder.setFlavoring(Flavors.Vanilla);
        builder.setPrice(190);
    }
    public void AddCandy(Builder builder){
        builder.setCandy();
    }
    public void AddCookies(Builder builder){
        builder.setCookies();
    }

    public void AddBoth(Builder builder) {
        builder.setCookies();
        builder.setCandy();
    }
    public void AlmondMilk(Builder builder){
        builder.setAlmond();
    }
}
