import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int edges_amount; int vertex_amount;
        boolean overwrite = false; boolean transitive = false; boolean non_transitive = false;
        int count_symmetric; int count_reflexivity;

        String edge_vertex_values = scanner.nextLine();

        edges_amount = Integer.parseInt(edge_vertex_values.split(" ")[0]);
        vertex_amount = Integer.parseInt(edge_vertex_values.split(" ")[1]);

        if(edges_amount == 0 && vertex_amount == 0){
            System.out.println("Для пустого графа выполняются все отношения"); System.exit(0);
        }
        else if(edges_amount == 0 && vertex_amount != 0){
            System.out.println("Граф без ребер антирефлексивен, остальные отношения любые"); System.exit(0);
        }

        ArrayList<ArrayList<Integer>> paths_container = new ArrayList<>(edges_amount);
        for(int i = 0; i < edges_amount; i++) {
            String path_values = scanner.nextLine();

            ArrayList<Integer> path = new ArrayList<>(2);

            path.add(Integer.parseInt(path_values.split(" ")[0]));
            path.add(Integer.parseInt(path_values.split(" ")[1]));

            paths_container.add(path);
        }

        count_symmetric = edges_amount;
        count_reflexivity = vertex_amount;
        ArrayList<Integer> transitivity = new ArrayList<>(edges_amount){
            {
                for(int i = 0; i < edges_amount; i++) {
                    add(0);
                }
            }
        };

        for(int i = 0; i < paths_container.size(); i++){
            if(!Objects.equals(paths_container.get(i).get(0), paths_container.get(i).get(1))){
                for(int j = 0; j < paths_container.size(); j++) {
                    if(i != j){
                        if (Objects.equals(paths_container.get(i).get(1), paths_container.get(j).get(0))){
                            if(!Objects.equals(paths_container.get(j).get(0), paths_container.get(j).get(1))){
                                if(!Objects.equals(paths_container.get(i).get(0), paths_container.get(j).get(1))){
                                    ArrayList<Integer> transitivity_path = new ArrayList<>(2);
                                    transitivity_path.add(paths_container.get(i).get(0));
                                    transitivity_path.add(paths_container.get(j).get(1));
                                    if (paths_container.contains(transitivity_path)) {
                                        if (transitivity.get(i) == -1){
                                            transitivity.set(i, 2); overwrite = true;
                                        }
                                        else if(transitivity.get(i) != 2){
                                            transitivity.set(i, 1); transitive = true;
                                        }
                                    }
                                    else{
                                        if(transitivity.get(i) == 1){
                                            transitivity.set(i, 2); overwrite = true;
                                        }
                                        else if(transitivity.get(i) != 2){
                                            transitivity.set(i, -1); non_transitive = true;
                                        }
                                    }
                                }
                                else{
                                    count_symmetric -= 1;
                                }
                            }
                        }
                    }
                }
            }
            else{
                count_symmetric -= 1;
                count_reflexivity -= 1;
                if(transitivity.get(i) == 0){
                    transitivity.set(i, 3);
                }
            }
        }
        if(count_symmetric == 0){
            System.out.println("Симметричный");
        }
        else if(count_symmetric == edges_amount){
            System.out.println("Антисимметричный");
        }
        else{
            System.out.println("Несимметричный");
        }
        if(count_reflexivity == 0){
            System.out.println("Рефлексивный");
        }
        else if(count_reflexivity == vertex_amount){
            System.out.println("Антирефлексивный");
        }
        else{
            System.out.println("Нерефлексивный");
        }
        if(overwrite || (transitive && non_transitive)){
            System.out.println("Нетранзитивный");
        }
        else if(!non_transitive && transitive){
            System.out.println("Транзитивный");
        }
        else if(non_transitive){
            System.out.println("Антитранзитивный");
        }
        else {
            System.out.println("Транзитивный/Антитранзитивный/Нетранзитивный");
        }
    }
}