package Builder;

import Ingredients.*;
import Shakes.ShakeTypes;

public interface Builder {
    void setShakeType(ShakeTypes type);
    void setJelloLevel(Jello jello);
    void setSyrup(Syrup syrup);
    void setMilk(Milk milk);
    void setIceCream(IceCream iceCream);
    void setFlavoring(Flavors flavor);
    void setPrice(int price);

    void setCandy();
    void setCookies();

    void setAlmond();
}
