
package sistema;

import ambiente.*;
import ambiente.Fruit.*;
import comuns.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Math.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tacla
 */
public class Agente implements CoordenadasGeo {
    Model model;
    Problema prob;   // Problema estah na 'mente' do agente
    int energy;
    int keeps;
    //@todo colocar aqui plano armazenado de acoes que será executado 
    // passo a passo em deliberar
    Integer plan[]={};
    double ltra[][];
         
    public Agente(Model m) {
        this.model = m;  
        
        //@todo - INSTANCIAR PROBLEMA
        prob = new Problema();
        prob.criarLabirinto();
        prob.creLab.frutas = model.getFruits();
        //DEFINIR ESTADO INICIAL, OBJETIVO E ATUAL
        prob.defEstIni(8, 0);
        prob.defEstObj(2, 6);
        prob.defEstAtu(8, 0);
        
        keeps = 0;
        energy = 250;
        ltra = new double[9][9];
        inicializaHeuristica();
    }
    /* 
     * Escolhe qual ação será executada em um ciclo de raciocínio
     */
    public double deliberar() {
        int ap[], prox[], menorAcao = -1, menorIndice[] = null;
        double menorCusto=-1, custo, menorCustoUnitario = 0.0;
        ap = prob.acoesPossiveis(prob.estAtu);
        // nao atingiu objetivo e ha acoesPossiveis a serem executadas no plano
        if (!prob.testeObjetivo()) {
//           System.out.println("estado atual: " + prob.estAtu[0] + "," + prob.estAtu[1]);
//           System.out.println("estado objetivo: " + prob.estObj[0] + "," + prob.estObj[1]);
//           System.out.print("açoes possiveis: ");
           for (int i=0;i<ap.length;i++) {
               if (ap[i]!=-1) {
                   //System.out.print(acao[i]+" ");
                   prox = prob.sucessor(prob.estAtu, i);
                   custo = ltra[prox[0]][prox[1]] + calculaCusto(i);
//                   System.out.println("vai para o estado: " + prox[0] + "," + prox[1]);
//                   System.out.println("o novo custo desse estado: " + custo);
                   if (menorCusto == -1 || custo < menorCusto) {
                       menorCusto = custo;
                       menorAcao = i;
                       menorIndice = prox;
                   }
               }
           }
           ltra[prob.estAtu[0]][prob.estAtu[1]] = menorCusto;
           //antes de ir, decide se vai comer a fruta ou não, e ve o custo real do movimento
           Fruit fruit = prob.creLab.frutas[prob.estAtu[0]][prob.estAtu[1]];
           int fruitEnergy = fruitEnergy(fruit);
           Decision decision = decide(fruitEnergy);
           if (decision == Decision.store)
               keeps++;
           int realCost = custoReal(decision, fruitEnergy);
           energy -= realCost;
            System.out.println("acho que a energia ta " + energy);
           //System.out.println("Energia atual agente: " + energy);
           //passar tb a decisao no metodo ir, e retornar -1 caso a energia tenha acabado
           if (model.ir(menorAcao, decision) == -1) {
               return -1;
           }
           prob.defEstAtu(menorIndice[0], menorIndice[1]);
           System.out.print(" -> " + prob.estAtu[0] + "," + prob.estAtu[1]);
           return realCost;
        }
        
        return -1;
        
    }
    
    /*
    * Atuador: solicita ao agente 'fisico' executar a acao. Atualiza estado.
    */
//    public int executarIr(int direcao) {
//        model.ir(direcao);
//        prob.suc(prob.estAtu, direcao); // atualiza estado do agente
//        return 1; 
//    }   
    
    // Sensor: lê posição atual do agente 
    // @todo
    public int[] lerSensor() {
        return model.lerPos();
    }
    
    public void inicializaHeuristica() {
        int i,j;
        for (i=0; i<prob.maxLin; i++)
            for (j=0; j<prob.maxCol; j++)
                //ltra[i][j] = 0;
                ltra[i][j] = sqrt(pow(i - prob.estObj[0],2) + pow(j - prob.estObj[1],2));
    }
    
    public double calculaCusto(int acao) {
        return 1;
    }
    
    public int custoReal(Decision decision, int fruitEnergy) {
        int custo = 75;
        custo += keeps * 5;
        if (decision == Decision.eat) {
            custo += (15 - fruitEnergy);
        }
        return custo;
    }
    
    private Decision decide(int fruitEnergy) {
        if (fruitEnergy > 5)
            return Decision.eat;
        else
            return Decision.drop;
    }
    
    private int fruitEnergy(Fruit fruit) {
        switch (fruit.madurez) {
            case verde:
                switch (fruit.carboidratos) {
                    case pouca:
                        switch (fruit.lipideos) {
                            case pouca:
                                switch (fruit.proteinas) {
                                    case pouca:
                                    case moderada: return 5;
                                    case alta: return 50;
                                }
                            case moderada:
                            case alta: return 25;
                        }
                    case moderada:
                    case alta:
                        switch (fruit.lipideos) {
                            case pouca: return 25;
                            case moderada:
                            case alta: return 50;
                        }
                }
            case madura:
                switch (fruit.carboidratos) {
                    case pouca:
                        switch (fruit.lipideos) {
                            case pouca: return 5;
                            case moderada:
                            case alta: return 50;
                        }
                    case moderada:
                    case alta: return 100;
                }
            case podre:
                switch (fruit.carboidratos) {
                    case pouca: return 5;
                    case moderada:
                    case alta:
                        switch (fruit.lipideos) {
                            case pouca: return 5;
                            case moderada:
                            case alta: return 25;
                        }
                }
        }
        return 0;
    }
    
    public void reset() {
        energy = 250;
        prob.creLab.frutas = model.getFruits();
        keeps = 0;
    }
    
    public enum Decision {
        eat, store, drop
    }
    
}