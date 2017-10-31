
/**
 *
 * @author tacla Modela o ambiente 'simulado' composto por um labirinto com
 * paredes e por um agente
 */

package ambiente;
import comuns.CoordenadasGeo;
import comuns.Labirinto;
import sistema.Agente.Decision;

public class Model implements CoordenadasGeo {
    protected int maxCol; // tamanho do labirinto em X (colunas)
    protected int maxLin; // tamanho do labirinto em Y (linhas)
    protected int pos[]={8,0}; // pos do agente {linha, coluna)
    protected Labirinto lab;   // representacao do labirinto
    protected View view;
    protected int energy;

    public Model(int linhas, int colunas) {
        if (linhas <= 0) {
            linhas = 5;
        }
        if (colunas <= 0) {
            colunas = 5;
        }
        this.maxCol = colunas;
        this.maxLin = linhas;
        
        // instancia o labirinto
        lab = new Labirinto(linhas, colunas);
        lab.porParedeHorizontal(0, 7, 0);
        lab.porParedeHorizontal(0, 1, 1);
        lab.porParedeVertical(2, 3, 0);
        lab.porParedeHorizontal(3, 5, 2);
        lab.porParedeHorizontal(3, 6, 3);
        lab.parede[5][2] = 1;
        lab.porParedeHorizontal(5, 7, 5);
        lab.porParedeHorizontal(4, 7, 6);
        lab.porParedeHorizontal(4, 7, 7);
        lab.porParedeVertical(6, 8, 1);
        lab.porParedeHorizontal(1, 2, 8);
        lab.carregaFrutas();
        energy = 250;
        System.out.println("Energia inicial: " + 250);
        // instancia a visualizacao do ambiente associando-a ao model
        view = new View(this);
    }
    
    public void desenhar() {
        view.desenhar();
    }
    
    public int[] lerPos() {
        return pos;
    }

    /**
     * @param coord int de 0 a 7 iniciando por N no sentido horário
     * @param decision
     */
    public int ir(int coord, Decision decision) {
        int lin = this.pos[0];
        int col = this.pos[1];
        energy -= 75;
        if (decision == Decision.eat) {
            int energia = lab.frutas[lin][col].getEnergy();
            energy += energia - 15;
            System.out.println("Comeu a fruta com energia: " + energia);
        }
        System.out.println("Energia atual: " + energy);
        if (energy < 0)
            return -1;
        switch (coord) {
            case N:
                lin--;
                break;
            case NE:
                col++;
                lin--;
                break;
            case L:
                col++;
                break;
            case SE:
                col++;
                lin++;
                break;
            case S:
                lin++;
                break;
            case SO:
                col--;
                lin++;
                break;
            case O:
                col--;
                break;
            case NO:
                col--;
                lin--;
                break;
        }
        // verifica se está fora do grid
        if (col < 0 || col >= maxCol || lin < 0 || lin >= maxLin) {
            lin = pos[0];
            col = pos[1];  // fica na posicao atual
            
        }
        // verifica se bateu em algum obstaculo
        if (lab.parede[lin][col] == 1) {
            lin = pos[0];
            col = pos[1];  // fica na posicao atual
        }

        // atribui nova posicao
        this.pos[0] = lin;
        this.pos[1] = col;
        return 0;
    }
    
    public void setPosicao(int lin, int col) {
        pos[0] = lin;
        pos[1] = col;
    }
    
    public Fruit[][] getFruits() {
        return lab.frutas;
    }
    
    public void reset() {
        energy = 250;
        lab.carregaFrutas();
        System.out.println("Energia inicial: " + 250);
    }

}