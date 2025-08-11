import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class File implements Component{
    private String FileName;
    private int Size;

    private LocalDateTime creationTime;
    private String directory;
    private Component parent;

    public File(String name, int sz, LocalDateTime time, String directory){
        this.FileName = name;
        this.Size = sz;
        this.creationTime = time;
        this.directory = directory;
        this.parent = null;
    }

    public String getName() {
        return FileName;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    @Override
    public Component getParent() {
        return this.parent;
    }

    public void setparent(Component comp) {
        this.parent = comp;
    }


    public int getSize(){return this.Size;}

    public void setSize(int sz) { this.Size = sz; }
    @Override
    public void PrintDetails() {
        System.out.println("Name: " + this.FileName);
        System.out.println("Type: File");
        System.out.println("Size: " + this.Size);
        System.out.println("Directory: " + this.directory + ":\\" + this.FileName);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy h:mm a");
        String formattedDateTime = this.creationTime.format(formatter);
        System.out.println("Creation Time: " + formattedDateTime);
    }
}
