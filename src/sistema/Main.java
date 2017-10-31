/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;

import ambiente.*;
import java.util.Formatter;
import java.util.Scanner;

/**
 *
 * @author tacla
 */
public class Main {
    public static void main(String args[]) {
        
        // Cria o labirinto = ambiente
        Model mdl = new Model(9, 9);

        // Cria um agente
        Agente ag = new Agente(mdl);
        // Ciclo de execucao do sistema
        Scanner sc = new Scanner(System.in);
        double custo = 0.0;
        do {
            mdl.reset();
            ag.reset();
            mdl.desenhar();
            System.out.print("Caminho feito: " + "8,0");
            while (true) {
                double x = ag.deliberar();
                if(x == -1)
                    break;
                else
                    custo+=x;
                System.out.println("Posicao atual: " + mdl.lerPos()[0] + mdl.lerPos()[1]);
                mdl.desenhar();
            }
            
            System.out.println("\nCusto total: " + custo);
            System.out.println("Situacao da tabela no momento: ");
            for (int i=0;i<5;i++) {
                for (int j=0;j<9;j++) {
                    System.out.print(String.format("%.2f\t", ag.ltra[i][j]));
                }
                System.out.println("");
            }
            
            ag.prob.defEstAtu(8, 0);
            mdl.setPosicao(8, 0);
            custo = 0.0;
            System.out.println("Digite q para sair");
        } while(!sc.nextLine().equals("q"));
    }
}
