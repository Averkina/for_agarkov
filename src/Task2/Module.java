package Task2;

import java.util.ArrayList;
import java.util.*;

public class Module {

    public String ID;
    public ArrayList<String> deps;

    Module(String newID, String... newDependencies) {
        this.ID = newID;
        deps = new ArrayList<String>();
        for (int i = 0; i < newDependencies.length; ++i) {
            deps.add(newDependencies[i]);
        }
    }

    Module(String newID, ArrayList<String> newDeps) {
        this.ID = newID;
        this.deps = newDeps;
    }

    public static void main(String[] args) {

        ArrayList<Module> modules = new ArrayList<Module>();

        modules.add(new Module("1", ""));
        modules.add(new Module("2", "1"));
        modules.add(new Module("3", "2"));
        modules.add(new Module("4", "3", "2"));
        modules.add(new Module("5", "1", "4"));
        modules.add(new Module("61", "1", "70"));
        modules.add(new Module("6", "5"));

        Collections.shuffle(modules);

        boolean anythingMoved;
        do {
            anythingMoved = false;
            for (int i = 0; i < modules.size(); ++i) {
                String myId = modules.get(i).ID;
                int newPos = -1;
                for (int j = i + 1; j < modules.size(); ++j) {
                    if (modules.get(j).deps.contains(myId)) {
                        newPos = j; 
                    }
                }

                if (newPos != -1) {
                    Module m = new Module(modules.get(newPos).ID, modules.get(newPos).deps);
                    modules.set(newPos, modules.get(i));
                    modules.set(i, m);

                    anythingMoved = true;
                }
            }
        }

        while (anythingMoved);

        Collections.reverse(modules);

        System.out.println("Sorted before removal of the unnecessary");
        for (int i = 0; i < modules.size(); ++i) {
            System.out.println(modules.get(i).ID);
        }

        for (int i = 0 + 1; i < modules.size(); ++i) {
            for (int j = 0; j < modules.get(i).deps.size(); ++j) {
                String myDep = modules.get(i).deps.get(j);
                boolean resolved = false;

                for (int prev = 0; prev < i && !resolved; ++prev) {
                    if (modules.get(prev).ID.equals(myDep)) {
                        resolved = true;
                    }
                }

                if (!resolved) {
                    System.out.println("Module with id " + modules.get(i).ID + " was removed");
                    modules.remove(i);
                    --i;
                    break;
                }
            }
        }

        System.out.println("Result:");
        for (int i = 0; i < modules.size(); ++i) {
            System.out.println(modules.get(i).ID);
        }
    }
}