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
public class EstadoSet extends HashSet<int[]> {
    
    public boolean mesmoEstado(int[] estado) {
        return this.stream().anyMatch((target) -> (target[0] == estado[0] && target[1] == estado[1]));
    }
    
}
