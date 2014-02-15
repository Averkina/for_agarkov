package Task2;

import java.util.*;



public class Module {

    public final String id;
    public final String[] depends;

    Module(String id, String... depends) {
        this.id = id;
        this.depends = depends;
    }


    public static boolean existsId(String targetId, ArrayList<Module> modules) {
        for (int idIndex = 0; idIndex < modules.size(); idIndex++) {
            String nextId = modules.get(idIndex).id;
//            System.out.println("depends: " + targetId + " and id: " + nextId);
            if (targetId.equals(nextId)) {
                return true;
            }
        }
        return false;
    }

    public static boolean canResolveDependence(ArrayList<Module> modules, int moduleIndex) {
        Module module = modules.get(moduleIndex);
        for (int dependsIndex = 0; dependsIndex < module.depends.length; dependsIndex++) {
            String dependenceId = module.depends[dependsIndex];
            if (!existsId(dependenceId, modules)) {
                return false;
            }
        }
        return true;
    }

    public static void print(ArrayList<Module> modules) {

        Iterator<Module> itr = modules.iterator(); 
        while (itr.hasNext()) {
            Module element = itr.next();
            System.out.print(element.id + " ");
            for (final String depend : element.depends) {
                System.out.print("[" + depend + "]");
            }
            System.out.println();
        }
    }


    public static int removeUnresolvedModules(ArrayList<Module> modules) {
        int removed = 0;
        for (int moduleIndex = 0; moduleIndex < modules.size(); moduleIndex++) {
            if (canResolveDependence(modules, moduleIndex) == false) {
//                System.out.println("remove: " + modules.get(moduleIndex).id);
                modules.remove(moduleIndex);
                removed++;
            }
        }
        return removed;
    }
    
    public static boolean hasLiveDependepcies(Module module, ArrayList<Module> modules) {
        for (String dep : module.depends) {
            if (existsId(dep, modules)) {
                return true;
            }
        }
        return false;
    }
    
    public static void findAndPrintAndRemoveIndependentModules(ArrayList<Module> modules) {
        Iterator<Module> it = modules.iterator(); 
        while (it.hasNext()) {
            Module module = it.next();
            if (!hasLiveDependepcies(module, modules)) {
                System.out.print(module.id + ", ");
                it.remove();
            }
        }
    }
    


    public static void main(String[] args) {

        ArrayList<Module> moduleList = new ArrayList<Module>();

        moduleList.add(new Module("1"));
        moduleList.add(new Module("2", "1"));
        moduleList.add(new Module("3", "2"));
        moduleList.add(new Module("4", "3", "2"));
        moduleList.add(new Module("5", "1", "4"));
        moduleList.add(new Module("61", "1", "70"));
        moduleList.add(new Module("6", "5"));

//        moduleList.add(new Module("1"));
//        moduleList.add(new Module("2", "1"));
//        moduleList.add(new Module("A", "1", "B"));
//        moduleList.add(new Module("B", "X"));

        while (removeUnresolvedModules(moduleList) > 0) {
        }
        
        //print(moduleList);
        
        while (!moduleList.isEmpty()) {
            findAndPrintAndRemoveIndependentModules(moduleList);
        }
        
        //print(moduleList);
    }
}



