import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Folder implements Component{
    private List<Component> components;
    private String FolderName;

    private LocalDateTime creationTime;

    private int Size;
    private String directory;
    private Component parent;

    public Folder(String name, LocalDateTime Time,String directory){
        this.FolderName = name;
        this.components = new ArrayList<>();
        this.creationTime = Time;
        this.Size = 0;
        this.directory = directory;
        this.parent = null;
    }

    public void add(Component comp){
        this.components.add(comp);
        this.Size += comp.getSize();

        Component temp = parent;
        while(temp!= null){
            if(temp instanceof Folder){
                Folder fold = (Folder)temp;
                fold.setSize(fold.getSize() + comp.getSize());
            }
            else if( temp instanceof Drive){
                Drive drive = (Drive) temp;
                drive.setSize(drive.getSize()+ comp.getSize());
            }
            temp = temp.getParent();
        }
        if(comp instanceof File){
            File file = (File)comp;
            file.setparent(this);
        }
        else if(comp instanceof Folder){
            Folder fold = (Folder)comp;
            fold.setparent(this);
        }
    }

    public void setSize(int Size) {
        this.Size = Size;
    }

    public Component getParent(){return this.parent;}

    public void setparent(Component comp) {
        this.parent = comp;
    }

    public int getSize() {return this.Size; }

    public String getName() {
        return FolderName;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
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
            if(flag){
                int sz = foundComp.getSize()*-1;
                this.recursiveUpdateParentSize(sz);
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
            int sz = curr.getSize()*-1;
            if(curr instanceof File){
                System.out.println("Name belongs to a file, not a folder.");
                components.remove(curr);
                this.Size -= curr.getSize();
                recursiveUpdateParentSize(sz);
            }
            else if(curr instanceof Folder){
                if(curr.getSize() == 0 ){
                    components.remove(curr);
                }
                else{
                    Folder folder = (Folder)(curr);
                    folder.recursiveDeleteAll();
                    components.remove(curr);
                }
            }
        }
        return flag;
    }

    public void recursiveUpdateParentSize(int value){
        Component temp = parent;
        while(temp != null){
            temp.setSize(temp.getSize()+ value);
            temp = temp.getParent();
        }
    }

    public void recursiveDeleteAll(){
        for(Component component: components){
            if(component instanceof File){
            }
            else if(component instanceof Folder){
                if(component.getSize() != 0){
                    Folder folder = (Folder)(component);
                    folder.recursiveDeleteAll();
                }
            }
        }
        int sz  = Size * (-1);
        this.recursiveUpdateParentSize(sz);
        components.clear();
        this.Size = 0;
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
        System.out.println("Name: " + this.FolderName);
        System.out.println("Type: Folder");
        System.out.println("Size: " + this.Size + " KB");
        System.out.println("Directory: " + this.directory + ":\\" + this.FolderName);
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
