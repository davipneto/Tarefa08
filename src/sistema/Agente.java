
package sistema;

import ambiente.*;
import comuns.*;
import java.util.*;

/**
 *
 * @author tacla
 */
public class Agente implements CoordenadasGeo {
    Model model;
    Problema prob;   // Problema estah na 'mente' do agente
    int ct;
    double custo;
    
    //@todo colocar aqui plano armazenado de acoes que será executado 
    // passo a passo em deliberar
    Integer plan[]={};
    
         
    public Agente(Model m) {
        this.model = m;  
        
        //@todo - INSTANCIAR PROBLEMA
        prob = new Problema();
        prob.criarLabirinto();
       
        //DEFINIR ESTADO INICIAL, OBJETIVO E ATUAL
        prob.defEstIni(8, 0);
        prob.defEstObj(2, 8);
        prob.defEstAtu(8, 0);
        
        custo = 0.0;
        ct = 0;
        
        Busca busca = new Busca(prob);
        Stack<Integer> solution = busca.ah2();
        plan = new Integer[solution.size()];
        int i = 0;
        while(!solution.isEmpty()) {
            plan[i] = solution.pop().intValue();
            System.out.println("acao " + plan[i]);
            i++;
        }
        
        //apenas esperando
    }
    /* 
     * Escolhe qual ação será executada em um ciclo de raciocínio
     */
    public int deliberar() {
        int ap[];
        ap = prob.acoesPossiveis(prob.estAtu);
        
        // nao atingiu objetivo e ha acoesPossiveis a serem executadas no plano
        if (ct < plan.length) {
           System.out.println("estado atual: " + prob.estAtu[0] + "," + prob.estAtu[1]);
           System.out.print("açoes possiveis: ");
           for (int i=0;i<ap.length;i++) {
               if (ap[i]!=-1)
                   System.out.print(acao[i]+" ");
           }
           // @todo executar acao ir
           System.out.println("\nct = "+ ct + " de " + (plan.length-1) + " ação=" + acao[plan[ct]] + "\n");
           executarIr(plan[ct]);
           
           // @todo calcular custo do caminho e imprimir ateh este momento
           switch (plan[ct]) {
               case N:
               case S:
               case L:
               case O: custo+=1; break;
               case NO:
               case NE:
               case SO: 
               case SE: custo+=1.5; break;
           }
           System.out.println("Custo total:" + custo);
           ct++;
        }
        else {
            return (-1);
        }
        
        return 1;
    }
    
    /*
    * Atuador: solicita ao agente 'fisico' executar a acao. Atualiza estado.
    */
    public int executarIr(int direcao) {
        model.ir(direcao);
        prob.suc(prob.estAtu, direcao); // atualiza estado do agente
        return 1; 
    }   
    
    // Sensor: lê posição atual do agente 
    // @todo
    public int[] lerSensor() {
        return model.lerPos();
    }
    
}