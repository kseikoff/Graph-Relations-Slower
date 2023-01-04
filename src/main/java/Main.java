import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[] edge_vertex_container = new int[2];
        boolean deuce_presence = false;
        int count_zeroes_for_symmetric;
        int count_zeroes_for_reflexivity;

        String edge_vertex_values = scanner.nextLine();

        edge_vertex_container[0] = Integer.parseInt(edge_vertex_values.split(" ")[0]);
        edge_vertex_container[1] = Integer.parseInt(edge_vertex_values.split(" ")[1]);

        if(edge_vertex_container[0] == 0 && edge_vertex_container[1] == 0){
            System.out.println("Для пустого графа выполняются все отношения");
            System.exit(0);
        }
        else if(edge_vertex_container[0] == 0 && edge_vertex_container[1] != 0){
            System.out.println("Граф без ребер антирефлексивен, остальные отношения любые");
            System.exit(0);
        }

        ArrayList<ArrayList<Integer>> paths_container = new ArrayList<>(edge_vertex_container[0]);

        for(int i = 0; i < edge_vertex_container[0]; i++) {
            String path_values = scanner.nextLine();

            ArrayList<Integer> path = new ArrayList<>(2);

            path.add(Integer.parseInt(path_values.split(" ")[0]));
            path.add(Integer.parseInt(path_values.split(" ")[1]));

            paths_container.add(path);
        }

        count_zeroes_for_symmetric = edge_vertex_container[0];

        count_zeroes_for_reflexivity = edge_vertex_container[1];

        ArrayList<Integer> transitivity = new ArrayList<>(edge_vertex_container[0]){
            {
                for(int i = 0; i < edge_vertex_container[0]; i++) {
                    add(0);
                }
            }
        };

        for(int i = 0; i < paths_container.size(); i++){
            if(Objects.equals(paths_container.get(i).get(0), paths_container.get(i).get(1))){
                count_zeroes_for_reflexivity -= 1;
            }
            ArrayList<Integer> reversed_path = new ArrayList<>(2);
            reversed_path.add(paths_container.get(i).get(1));
            reversed_path.add(paths_container.get(i).get(0));
            if(paths_container.contains(reversed_path)){
                count_zeroes_for_symmetric -= 1;
            }
            if((!Objects.equals(paths_container.get(i).get(0), paths_container.get(i).get(1)))){
                for(int j = 0; j < paths_container.size(); j++) {
                    if(i != j){
                        if ((Objects.equals(paths_container.get(i).get(1), paths_container.get(j).get(0)))){
                            if((!Objects.equals(paths_container.get(j).get(0), paths_container.get(j).get(1)))
                                    && (!Objects.equals(paths_container.get(i).get(0), paths_container.get(j).get(1)))){
                                ArrayList<Integer> transitivity_path = new ArrayList<>(2);
                                transitivity_path.add(paths_container.get(i).get(0));
                                transitivity_path.add(paths_container.get(j).get(1));
                                if (paths_container.contains(transitivity_path)) {
                                    if (transitivity.get(i) == -1){
                                        transitivity.set(i, 2);
                                        deuce_presence = true;
                                    }
                                    else{
                                        if(transitivity.get(i) != 2){
                                            transitivity.set(i, 1);
                                        }
                                    }
                                    if(transitivity.get(j) == -1){
                                        transitivity.set(j, 2);
                                        deuce_presence = true;
                                    }
                                    else{
                                        if(transitivity.get(j) != 2){
                                            transitivity.set(j, 1);
                                        }
                                    }
                                    if(transitivity.get(paths_container.indexOf(transitivity_path)) == -1){
                                        transitivity.set(paths_container.indexOf(transitivity_path), 2);
                                        deuce_presence = true;
                                    }
                                    else{
                                        if(transitivity.get(paths_container.indexOf(transitivity_path)) != 2){
                                            transitivity.set(paths_container.indexOf(transitivity_path), 1);
                                        }
                                    }
                                }
                                else if(transitivity.get(i) != 1 && transitivity.get(j) != 1
                                        && transitivity.get(i) != 2 && transitivity.get(j) != 2){
                                    transitivity.set(i, -1);
                                    transitivity.set(j, -1);
                                }
                                else{
                                    if(transitivity.get(i) != 2){
                                        if(transitivity.get(i) != 0 && transitivity.get(i) != -1){
                                            transitivity.set(i, 2);
                                            deuce_presence = true;
                                        }
                                        else {
                                            transitivity.set(i, -1);
                                        }
                                    }
                                    if(transitivity.get(j) != 2){
                                        if(transitivity.get(j) != 0 && transitivity.get(j) != -1){
                                            transitivity.set(j, 2);
                                            deuce_presence = true;
                                        }
                                        else {
                                            transitivity.set(j, -1);
                                        }
                                    }
                                }
                            } else {
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
        if(count_zeroes_for_symmetric == 0){
            System.out.println("Симметричный");
        }
        else if(count_zeroes_for_symmetric == edge_vertex_container[0]){
            System.out.println("Антисимметричный");
        }
        else{
            System.out.println("Несимметричный");
        }
        if(count_zeroes_for_reflexivity == 0){
            System.out.println("Рефлексивный");
        }
        else if(count_zeroes_for_reflexivity == edge_vertex_container[1]){
            System.out.println("Антирефлексивный");
        }
        else{
            System.out.println("Нерефлексивный");
        }
        if(deuce_presence || (transitivity.contains(1) && transitivity.contains(-1))){
            System.out.println("Нетранзитивный");
        }
        else if(!transitivity.contains(-1) && transitivity.contains(1)){
            System.out.println("Транзитивный");
        }
        else if(!transitivity.contains(1) && transitivity.contains(-1)){
            System.out.println("Антитранзитивный");
        }
        else {
            System.out.println("Транзитивный/Антитранзитивный/Нетранзитивный");
        }
    }
}