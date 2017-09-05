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
public class TreeNodeComparator implements Comparator<TreeNode>{

    @Override
    public int compare(TreeNode o1, TreeNode o2) {
        if (o1.custo < o2.custo)
            return -1;
        if (o1.custo > o2.custo)
            return 1;
        return 0;
    }
    
}
