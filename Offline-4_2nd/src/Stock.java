import java.awt.*;
import java.io.IOException;
import java.io.Serializable;

public interface Stock extends Serializable {
    void NotifyAllSubs(String message) throws IOException;
}
