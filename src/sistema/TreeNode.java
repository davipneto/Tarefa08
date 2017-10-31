/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;
import java.util.*;

/**
 *
 * @author davi
 */
public class TreeNode {
    
    TreeNode pai;
    int[] posicao;
    double custo;
    int acao;
    
    public TreeNode(TreeNode pai, int[] posicao, double custo, int acao) {
        this.pai = pai;
        this.posicao = posicao;
        this.custo = custo;
        this.acao = acao;
    }
    
    @Override
    public String toString() {
        return " Posição: " + posicao[0] + "," + posicao[1] +
                " Custo do nó: " + custo + " Ação: " + acao;
    }

}
