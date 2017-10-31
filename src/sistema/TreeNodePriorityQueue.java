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
public class TreeNodePriorityQueue extends PriorityQueue<TreeNode> {

    TreeNodePriorityQueue(Comparator<TreeNode> comparator) {
        super(comparator);
    }
    
    public boolean mesmoEstado(TreeNode node) {
        return this.stream().anyMatch((target) -> (target.posicao[0] == node.posicao[0] && 
                target.posicao[1] == node.posicao[1]));
    }
    
    public void removeSeMaiorCusto(TreeNode node) {
        for (TreeNode target: this) {
            if (target.posicao[0] == node.posicao[0] && target.posicao[1] == node.posicao[1]
                    && target.custo > node.custo){
                this.remove(target);
                System.out.println("No retirado: " + target + target.pai);
                this.add(node);
                System.out.println("No colocado no lugar: " + node);
                break;
            }    
        }
    }
}
