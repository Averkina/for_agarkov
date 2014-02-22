package Task2;

import java.util.*;

public class Module {

    public String[] depends;

    Module(String... depends) {
        this.depends = depends;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String depend : depends) {
            if (builder.length() > 0) {
                builder.append("_");
            }
            builder.append(depend);
        }
        return builder.toString();
    }

    public static void print(ArrayList<String> id) {
        Iterator<String> it = id.iterator(); 
        while (it.hasNext()) {
            String idList = it.next();
            System.out.print(idList + " ");
        }
    }

    public static void removeUnresolvedModules(HashMap<String, Module> modules) {
        Iterator<Map.Entry<String, Module>> it = modules.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Module> entry = it.next();
            Module module = entry.getValue();
            for (String depend : module.depends) {
                if (!modules.containsKey(depend)) {
                    it.remove();
                }
            }
        }
    }

    public static ArrayList<String> findAndPrintAndRemoveIndependentModules(HashMap<String, Module> modules) {
        Iterator<Map.Entry<String, Module>> it = modules.entrySet().iterator();
        ArrayList<String> idList = new ArrayList<String>();
        while (it.hasNext()) {
            Map.Entry<String, Module> entry = it.next();
            Module module = entry.getValue();
            if (!containsExistsDependence(module, modules)) {
                String id = entry.getKey();
                it.remove();
                idList.add(id);
            }
        }
        return idList;
    }

    public static boolean containsExistsDependence(Module module, HashMap<String, Module> modules) {
        for (String depend : module.depends) {
            if (modules.containsKey(depend)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

        HashMap<String, Module> modules = new HashMap<String, Module>();

        modules.put("1", new Module());
        modules.put("2", new Module("1"));
        modules.put("3", new Module("2"));
        modules.put("4", new Module("3", "2"));
        modules.put("5", new Module("1", "4"));
        modules.put("61", new Module("1", "70"));
        modules.put("6", new Module("5"));


        //        modules.put("1", new Module());
        //        modules.put("2", new Module("1"));
        //        modules.put("A", new Module("1", "B"));
        //        modules.put("B", new Module("X"));

        System.out.println("Modules with dependences:");
        System.out.println(modules.toString());
        System.out.println("\nModules without the resolved dependences:");

        removeUnresolvedModules(modules);

        System.out.println(modules.toString());
        System.out.println("\nId resolved dependences:");

        while (!modules.isEmpty()) {
            print(findAndPrintAndRemoveIndependentModules(modules));
        }
    }
}
