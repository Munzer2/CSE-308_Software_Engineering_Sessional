import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String inp;
        List<Drive>drives = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        boolean Drive_flag = false;
        boolean Folder_flag = false;
        String Dir = "";
        Drive curr_drive = null;
        Folder curr_folder = null;
        while(true){
            inp = scanner.nextLine();
            if(inp.equalsIgnoreCase("exit")) break;
            if(!Drive_flag){
                String arr[] = inp.split(" ");
                if(arr[0].equalsIgnoreCase("mkdrive")){
                    curr_drive = new Drive(arr[1],LocalDateTime.now(),Dir);
                    drives.add(curr_drive);
                }
                else if (arr[0].equalsIgnoreCase("cd")){
                    if(arr[1].equalsIgnoreCase("~")){

                    }
                    else{
                        if(drives.size() == 0 ) System.out.println("make a drive first.");
                        else{
                            Dir = "";
                            for(Drive drive: drives){
                                if(drive.getName().equalsIgnoreCase(arr[1])){
                                    curr_drive = drive;
                                    Drive_flag = true;
                                    Dir += curr_drive.getName() ;
                                    break;
                                }
                            }
                            if(!Drive_flag){
                                System.out.println("Not found.");
                            }
                        }
                    }
                }
                else{
                    System.out.println("Make a drive first");
                }
            }
            else{
                String arr[] = inp.split(" ");
                if(arr[0].equalsIgnoreCase("Touch")){
                    if(!Folder_flag) curr_drive.add(new File(arr[1],Integer.parseInt(arr[2]),LocalDateTime.now(),Dir));
                    else{
                        curr_folder.add(new File(arr[1],Integer.parseInt(arr[2]),LocalDateTime.now(),Dir));
                    }
                }
                else if(arr[0].equalsIgnoreCase("Mkdir")){
                    if(!Folder_flag){
                        curr_drive.add(new Folder(arr[1],LocalDateTime.now(),Dir));
                    }
                    else{
                        curr_folder.add(new Folder(arr[1],LocalDateTime.now(),Dir));
                    }
                }
                else if(arr[0].equalsIgnoreCase("Delete")){
                    if(arr.length == 2){
                        if(!Folder_flag){
                            boolean found = curr_drive.remove(arr[1]);
                            if(!found ) System.out.println("Could not delete.");
                        }
                        else{
                            boolean found = curr_folder.remove(arr[1]);
                            if(!found) System.out.println("Could not delete.");
                        }
                    }
                    else{
                        if(!Folder_flag){
                            boolean found = curr_drive.recursiveRemove(arr[2]);
                            if(!found ) System.out.println("Could not delete.");
                        }
                        else{
                            boolean found = curr_folder.recursiveRemove(arr[2]);
                            if(!found) System.out.println("Could not delete.");
                        }
                    }
                }
                else if(arr[0].equalsIgnoreCase("cd")){
                    if(arr[1].equalsIgnoreCase("~")){
                        Drive_flag = false;
                        Folder_flag = false;
                        Dir = "";
                        curr_folder = null;
                        curr_drive = null;
                    }
                    else{
                        if(!Folder_flag){
                            Folder found = curr_drive.changeDir(arr[1]);
                            if(found == null){
                                System.out.println("Invalid.");
                            }
                            else{
                                curr_folder = found;
                                Folder_flag = true;
                                Dir += ":\\" + arr[1];
                            }
                        }
                        else{
                            Folder found = curr_folder.changeDir(arr[1]);
                            if(found == null){
                                System.out.println("Invalid.");
                            }
                            else{
                                curr_folder = found;
                                Dir += ":\\" + arr[1];
                            }
                        }
                    }
                }
                else if(arr[0].equalsIgnoreCase("list")){
                    if(!Folder_flag) curr_drive.listAllComponents();
                    else curr_folder.listAllComponents();
                }
                else if(arr[0].equalsIgnoreCase("ls")){
                    if(!Folder_flag){
                        if(!curr_drive.printCompDetails(arr[1])) {
                            System.out.println("No such file/folder in current directory.");
                        }
                    }
                    else {
                        if(!curr_folder.printCompDetails(arr[1])){
                            System.out.println("No such file/folder in current directory.");
                        }
                    }
                }
                else{
                    System.out.println("Invalid Command.");
                }
            }
        }
    }
}