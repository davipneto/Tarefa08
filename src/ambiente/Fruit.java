/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ambiente;

import java.util.Random;

/**
 *
 * @author davi
 */
public class Fruit {
    
    public Madurez madurez;
    public Nutrientes carboidratos;
    public Nutrientes fibras;
    public Nutrientes proteinas;
    public Nutrientes lipideos;
    
    public Fruit() {}
    
    public static Fruit randomFruit() {
        Random random = new Random();
        int pick = random.nextInt(3);
        Fruit f = new Fruit();
        f.madurez = Madurez.values()[pick];
        pick = random.nextInt(3);
        f.carboidratos = Nutrientes.values()[pick];
        pick = random.nextInt(3);
        f.fibras = Nutrientes.values()[pick];
        pick = random.nextInt(3);
        f.proteinas = Nutrientes.values()[pick];
        pick = random.nextInt(3);
        f.lipideos = Nutrientes.values()[pick];
        return f;
    }
    
    protected int getEnergy() {
        if (lipideos == Nutrientes.pouca) {
            if (carboidratos == Nutrientes.pouca) {
                if (proteinas == Nutrientes.alta && fibras == Nutrientes.alta && madurez != Madurez.podre)
                    return 50;
            } else {
                if (madurez == Madurez.podre)
                    return 5;
                if (madurez == Madurez.verde)
                    return 25;
                if (madurez == Madurez.madura)
                    return 100;
            }
        } else {
            if (carboidratos == Nutrientes.pouca) {
                if (madurez == Madurez.podre)
                    return 5;
                if (madurez == Madurez.verde)
                    return 25;
                if (madurez == Madurez.madura)
                    return 50;
            } else {
                if (madurez == Madurez.podre)
                    return 25;
                if (madurez == Madurez.verde)
                    return 50;
                if (madurez == Madurez.madura)
                    return 100;
            }
        }
        return 5;
    }
    
    @Override
    public String toString() {
        return madurez + "," + carboidratos + "," + fibras + "," + proteinas + "," + lipideos + "," + getEnergy();
    }
    
    public enum Madurez {
        verde, madura, podre
    }
    public enum Nutrientes {
        pouca, moderada, alta
    }

}


