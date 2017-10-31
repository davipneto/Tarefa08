/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;

/**
 *
 * @author davi
 */
public class HeuristicaTreeNode extends TreeNode {
    
    double custoJaGasto;

    public HeuristicaTreeNode(TreeNode pai, int[] posicao, double custo, int acao, double custoJaGasto) {
        super(pai, posicao, custo, acao);
        this.custoJaGasto = custoJaGasto;
    }
    
    
    
}
