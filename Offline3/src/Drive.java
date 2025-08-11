import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Drive implements Component{
    private List<Component> components;
    private String DriveName;
    private LocalDateTime creationTime;
    private int Size;
    private String directory;


    public Drive(String name,LocalDateTime Time,String directory){
        this.DriveName = name;
        this.components = new ArrayList<>();
        this.creationTime = Time;
        this.Size =0;
        this.directory = directory;
    }
    public String getName() {
        return DriveName;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    @Override
    public Component getParent() {
        return null;
    }

    public void add(Component comp){
        this.components.add(comp);
        this.Size += comp.getSize();
        if(comp instanceof Folder){
            Folder folder = (Folder)comp;
            folder.setparent(this);
        }
    }
    @Override
    public int getSize(){
        return this.Size;
    }

    public void setSize(int val){
        this.Size = val;
    }

    public boolean remove(String compName){
        Component foundComp = null;
        boolean flag = false;
        for(Component component:components){
            if(component.getName().equalsIgnoreCase(compName)){
                foundComp = component;
            }
        }
        if(flag){
            if(foundComp instanceof File){
                components.remove(foundComp);
                this.Size -= foundComp.getSize();
            }
            else if(foundComp instanceof Folder){
                if(foundComp.getSize() == 0) components.remove(foundComp);
                else{
                    System.out.println("Cannot delete non-empty folder.");
                    flag = false;
                }
            }
        }
        return flag;
    }

    public boolean recursiveRemove(String file_name){
        Component curr = null;
        boolean flag = false;
        for(Component component: components){
            if(component.getName().equalsIgnoreCase(file_name)){
                curr = component;
                flag = true;
                break;
            }
        }
        if(flag){
            if(curr instanceof File){
                System.out.println("Name belongs to a file, not a folder.");
                components.remove(curr);
                this.Size -= curr.getSize();
            }
            else if(curr instanceof Folder){
                if(curr.getSize() == 0 ){
                    components.remove(curr);
                }
                else{
                    Folder folder = (Folder)(curr);
                    this.Size -= folder.getSize();
                    components.remove(curr);
                }
            }
        }
        return flag;
    }

    public void listAllComponents(){
        if(components.size() == 0){
            System.out.println("No file/folder available.");
        }
        else{
            for(Component component: components){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy h:mm a");
                String formattedDateTime = this.creationTime.format(formatter);
                System.out.println(component.getName() + " " + component.getSize() + " KB " + formattedDateTime);
            }
        }
    }

    @Override
    public void PrintDetails() {
        System.out.println("Name: " + this.DriveName);
        System.out.println("Type: Drive");
        System.out.println("Size " + this.Size + " KB");
        System.out.println("Directory: " + this.directory + ":\\" + this.DriveName);
        System.out.println("Component Count: " + this.components.size());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy h:mm a");
        String formattedDateTime = this.creationTime.format(formatter);
        System.out.println("Creation Time: " + formattedDateTime);
    }

    public Folder changeDir(String name){
        Folder found = null;
        for(Component component: components){
            if(component.getName().equalsIgnoreCase(name)){
                if(component instanceof File){
                    return found;
                }
                else{
                    found = (Folder)component;
                    return found;
                }
            }
        }
        return found;
    }

    public boolean printCompDetails(String name){
        for(Component component: components){
            if(component.getName().equalsIgnoreCase(name)){
                component.PrintDetails();
                return true;
            }
        }
        return  false;
    }

}
