import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[] edge_vertex_container = new int[2];

        String edge_vertex_values = scanner.nextLine();
        while(edge_vertex_values.split(" ").length != 2){
            System.out.println("Введите два числа через пробел");
            edge_vertex_values = scanner.nextLine();
        }
        for(int i = 0; i < 2; i++) {edge_vertex_container[i] = Integer.parseInt(edge_vertex_values.split(" ")[i]);
        }
        if(edge_vertex_container[0] == 0 && edge_vertex_container[1] == 0){
            System.out.println("Для пустого графа выполняются все отношения");
            System.exit(0);
        }
        else if(edge_vertex_container[0] == 0 && edge_vertex_container[1] != 0){
            System.out.println("Граф без ребер антирефлексивен, остальные отношения любые");
            System.exit(0);
        }

        ArrayList<ArrayList<Integer>> list = new ArrayList<>(edge_vertex_container[0]);

        for(int i = 0; i < edge_vertex_container[0]; i++) {
            String paths = scanner.nextLine();
            while(paths.split(" ").length != 2){
                System.out.println("Введите два числа через пробел");
                paths = scanner.nextLine();
            }

            ArrayList<Integer> list1 = new ArrayList<>(2);

            for(int j = 0; j < 2; j++) {
                list1.add(Integer.parseInt(paths.split(" ")[j]));
            }
            list.add(list1);
        }

        ArrayList<Boolean> symmetric = new ArrayList<>(edge_vertex_container[0]){
            {
                for(int i = 0; i < edge_vertex_container[0]; i++) {
                    add(false);
                }
            }
        };

        ArrayList<Boolean> reflexivity = new ArrayList<>(edge_vertex_container[1]){
            {
                for(int i = 0; i < edge_vertex_container[1]; i++) {
                    add(false);
                }
            }
        };

        ArrayList<Integer> transitivity = new ArrayList<>(edge_vertex_container[0]){
            {
                for(int i = 0; i < edge_vertex_container[0]; i++) {
                    add(0);
                }
            }
        };

        for(int i = 0; i < list.size(); i++){
            if(list.get(i).get(0) == list.get(i).get(1)){
                reflexivity.set(list.get(i).get(0) - 1, true);
            }
            if(list.contains(Main.reverseIntegerArrayList(list.get(i)))){
                symmetric.set(i, true);
            }
            if((list.get(i).get(0) != list.get(i).get(1))){
                for(int j = 0; j < list.size(); j++) {
                    if(i != j){
                        if ((list.get(i).get(1) == list.get(j).get(0))){
                            if((list.get(j).get(0) != list.get(j).get(1)) && (list.get(i).get(0) != list.get(j).get(1))){
                                ArrayList<Integer> temp = new ArrayList<>(2);
                                temp.add(list.get(i).get(0));
                                temp.add(list.get(j).get(1));
                                if (list.contains(temp)) {
                                    if (transitivity.get(i) == -1){
                                        transitivity.set(i, 2);
                                    }
                                    else{
                                        if(transitivity.get(i) != 2){
                                            transitivity.set(i, 1);
                                        }
                                    }
                                    if(transitivity.get(j) == -1){
                                        transitivity.set(j, 2);
                                    }
                                    else{
                                        if(transitivity.get(j) != 2){
                                            transitivity.set(j, 1);
                                        }
                                    }
                                    if(transitivity.get(list.indexOf(temp)) == -1){
                                        transitivity.set(list.indexOf(temp), 2);
                                    }
                                    else{
                                        if(transitivity.get(list.indexOf(temp)) != 2){
                                            transitivity.set(list.indexOf(temp), 1);
                                        }
                                    }
                                }
                                else if(transitivity.get(i) != 1 && transitivity.get(j) != 1
                                        && transitivity.get(i) != 2 && transitivity.get(j) != 2){
                                        transitivity.set(i, -1);
                                        transitivity.set(j, -1);
                                }
                                else{
                                    transitivity.set(i, 2);
                                    transitivity.set(j, 2);
                                }
                            }
                            else{
                                if(transitivity.get(j) == 0){
                                    transitivity.set(j, 3);
                                }
                            }
                        }
                    }
                }
            }
            else{
                if(transitivity.get(i) == 0){
                    transitivity.set(i, 3);
                }
            }
        }
        if(transitivity.contains(2)){
            System.out.println("Нетранзитивный");
        }
        else if(!transitivity.contains(-1) && transitivity.contains(1)){
            System.out.println("Транзитивный");
        }
        else if((Main.count(transitivity, 1) == 0) && transitivity.contains(-1)){
            System.out.println("Антитранзитивный");
        }
        else if((Main.count(transitivity, 0) == transitivity.size()
                || Main.count(transitivity, 3) == transitivity.size())){
            System.out.println("Транзитивный/Антитранзитивный/Нетранзитивный");
        }
        if(!symmetric.contains(false)){
            System.out.println("Симметричный");
        }
        else if(symmetric.contains(true)){
            System.out.println("Несимметричный");
        }
        else{
            System.out.println("Антисимметричный");
        }
        if(!reflexivity.contains(false)){
            System.out.println("Рефлексивный");
        }
        else if(reflexivity.contains(true)){
            System.out.println("Нерефлексивный");
        }
        else{
            System.out.println("Антирефлексивный");
        }
    }
    public static ArrayList<Integer> reverseIntegerArrayList(ArrayList<Integer> list){
        ArrayList<Integer> list1 = new ArrayList<>(list.size());
        for(int i = list.size() - 1; i >= 0; i--){
            list1.add(list.get(i));
        }
        return list1;
    }
    public static int count(ArrayList<Integer> list, int value){
        int count = 0;
        for (Integer integer : list) {
            if (integer == value) {
                count++;
            }
        }
        return count;
    }
}