import java.time.LocalDateTime;

public interface Component {
    void PrintDetails();
    int getSize();
    String getName();
    LocalDateTime getCreationTime();
    Component getParent();
    void setSize(int sz);
}
