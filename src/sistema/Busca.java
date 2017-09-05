/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;

import java.util.*;
import comuns.*;
import static java.lang.Math.*;

/**
 *
 * @author davi
 */
public class Busca implements CoordenadasGeo {
    
    private Problema prob;

    public Busca(Problema prob) {
        this.prob = prob;
    }
    
    public Stack<Integer> custoUniforme() {
        int[] obj = prob.estObj;
        TreeNode node = new TreeNode(null, prob.estIni, 0, -1);
        Comparator<TreeNode> comparator = (Comparator<TreeNode>) new TreeNodeComparator();
        TreeNodePriorityQueue frontier = new TreeNodePriorityQueue(comparator);
        frontier.add(node);
        EstadoSet explored = new EstadoSet();
        while (true) {
            if (frontier.isEmpty())
                return null;
            node = frontier.poll();
            System.out.println("Nó retirado da pilha: " + node);
            if (node.posicao[0] == obj[0] && node.posicao[1] == obj[1]) {
                System.out.println("ACHOU O BENDITO");
                Stack<Integer> solution = new Stack();
                while (node.pai != null) {
                    solution.push(node.acao);
                    node = node.pai;
                }
                return solution;
            }
            explored.add(node.posicao);
            int[] acoesPossiveis = prob.acoesPossiveisB(node.posicao);
            for (int acao=0; acao<8; acao++) {
                if (acoesPossiveis[acao] == 1) {
                    double custo;
                    if (acao == N || acao == S || acao == L || acao == O)
                        custo = 1;
                    else
                        custo = 1.5;
                    TreeNode child = new TreeNode(node, prob.sucessor(node.posicao, acao), node.custo + custo, acao);
                    if (!explored.mesmoEstado(child.posicao) && !frontier.mesmoEstado(child)) {
                        frontier.add(child);
                    } else if (frontier.mesmoEstado(child)) {
                        frontier.removeSeMaiorCusto(child);
                    }
                }
            }
        }
    }
    
    public Stack<Integer> ah1() {
        int[] obj = prob.estObj;
        double h1 = calculah1(prob.estIni,obj);
        int[] sucessor;
        HeuristicaTreeNode node = new HeuristicaTreeNode(null, prob.estIni, h1, -1, 0);
        Comparator<TreeNode> comparator = (Comparator<TreeNode>) new TreeNodeComparator();
        TreeNodePriorityQueue frontier = new TreeNodePriorityQueue(comparator);
        frontier.add(node);
        EstadoSet explored = new EstadoSet();
        while (true) {
            if (frontier.isEmpty())
                return null;
            node = (HeuristicaTreeNode) frontier.poll();
            System.out.println("Nó retirado da pilha: " + node);
            if (node.posicao[0] == obj[0] && node.posicao[1] == obj[1]) {
                System.out.println("ACHOU O BENDITO");
                Stack<Integer> solution = new Stack();
                while (node.pai != null) {
                    solution.push(node.acao);
                    node = (HeuristicaTreeNode) node.pai;
                }
                return solution;
            }
            explored.add(node.posicao);
            int[] acoesPossiveis = prob.acoesPossiveisB(node.posicao);
            for (int acao=0; acao<8; acao++) {
                if (acoesPossiveis[acao] == 1) {
                    double custo;
                    if (acao == N || acao == S || acao == L || acao == O)
                        custo = 1;
                    else
                        custo = 1.5;
                    sucessor = prob.sucessor(node.posicao, acao);
                    h1 = calculah1(sucessor, obj);
                    HeuristicaTreeNode child = new HeuristicaTreeNode(node, sucessor, node.custoJaGasto + custo + h1, acao, node.custoJaGasto + custo);
                    if (!explored.mesmoEstado(child.posicao) && !frontier.mesmoEstado(child)) {
                        frontier.add(child);
                    } else if (frontier.mesmoEstado(child)) {
                        frontier.removeSeMaiorCusto(child);
                    }
                }
            }
        }
    }
    
    public Stack<Integer> ah2() {
        int[] obj = prob.estObj;
        double h1 = calculah2(prob.estIni,obj);
        int[] sucessor;
        HeuristicaTreeNode node = new HeuristicaTreeNode(null, prob.estIni, h1, -1, 0);
        Comparator<TreeNode> comparator = (Comparator<TreeNode>) new TreeNodeComparator();
        TreeNodePriorityQueue frontier = new TreeNodePriorityQueue(comparator);
        frontier.add(node);
        EstadoSet explored = new EstadoSet();
        while (true) {
            if (frontier.isEmpty())
                return null;
            node = (HeuristicaTreeNode) frontier.poll();
            System.out.println("Nó retirado da pilha: " + node);
            if (node.posicao[0] == obj[0] && node.posicao[1] == obj[1]) {
                System.out.println("ACHOU O BENDITO");
                Stack<Integer> solution = new Stack();
                while (node.pai != null) {
                    solution.push(node.acao);
                    node = (HeuristicaTreeNode) node.pai;
                }
                return solution;
            }
            explored.add(node.posicao);
            int[] acoesPossiveis = prob.acoesPossiveisB(node.posicao);
            for (int acao=0; acao<8; acao++) {
                if (acoesPossiveis[acao] == 1) {
                    double custo;
                    if (acao == N || acao == S || acao == L || acao == O)
                        custo = 1;
                    else
                        custo = 1.5;
                    sucessor = prob.sucessor(node.posicao, acao);
                    h1 = calculah2(sucessor, obj);
                    HeuristicaTreeNode child = new HeuristicaTreeNode(node, sucessor, node.custoJaGasto + custo + h1, acao, node.custoJaGasto + custo);
                    if (!explored.mesmoEstado(child.posicao) && !frontier.mesmoEstado(child)) {
                        frontier.add(child);
                    } else if (frontier.mesmoEstado(child)) {
                        frontier.removeSeMaiorCusto(child);
                    }
                }
            }
        }
    }
    
    public double calculah1(int[] e1, int[] e2) {
        return sqrt(pow(e2[0] - e1[0],2) + pow(e2[1] - e1[1],2));
    }
    
    public double calculah2(int[] e1, int[] e2) {
        return abs(e2[1] - e1[1]);
    }
    
}
