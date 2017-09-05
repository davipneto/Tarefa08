/* 
 * @author Tacla
 * Classe para representar um problema no labirinto; sao representacoes que
 * ficam na 'mente' do agente
 */
package sistema;

import comuns.*;

public class Problema implements CoordenadasGeo {

    protected int estIni[] = new int[2];  // estado inicial [linha, coluna] = pos do agente
    protected int estObj[] = new int[2];  // estado objetivo [linha, coluna]
    protected int estAtu[] = new int[2];  // estado atual [linha, coluna]
    protected Labirinto creLab;           // crença do agente sobre como eh o labirinto
    protected int maxLin, maxCol;

    /*
     * O que o agente crê sobre o labirinto
     */
    public void criarLabirinto() {
        maxLin = 9;
        maxCol = 9;
        //colocar paredes
        creLab = new Labirinto(maxLin, maxCol);
        creLab.porParedeVertical(0,1,0);
        creLab.porParedeHorizontal(0,1,0);
        creLab.porParedeHorizontal(4, 7, 0);
        creLab.porParedeVertical(0, 1, 7);
        creLab.porParedeHorizontal(3, 5, 2);
        creLab.porParedeHorizontal(3, 5, 3);
        creLab.parede[3][7] = 1;
        creLab.porParedeVertical(5, 8, 1);
        creLab.porParedeHorizontal(1, 2, 5);
        creLab.porParedeHorizontal(1, 2, 8);
        creLab.porParedeVertical(6, 7, 4);
        creLab.porParedeVertical(5, 6, 5);
        creLab.porParedeVertical(5, 7, 7);
    }

    /*
     * Define estado inicial
     */
    protected void defEstIni(int linha, int coluna) {
        estIni[0] = linha;
        estIni[1] = coluna;
    }

    /*
     * Define estado objetivo
     */
    protected void defEstObj(int linha, int coluna) {
        estObj[0] = linha;
        estObj[1] = coluna;
    }
    /*
     * Define estado atual
     */

    protected void defEstAtu(int linha, int coluna) {
        estAtu[0] = linha;
        estAtu[1] = coluna;
    }
    /*
     * Funcao sucessora: recebe um estado '(lin, col)' e calcula o estado atual
     * que resulta da execucao da acao = {N, NE, L, SE, S, SO, O, NO}
     */

    protected int[] suc(int[] estado, int acao) {
        // @todo
        // calcular estado sucessor a partir do estado e acao
        int lin,col;
        if (acoesPossiveis(estado)[acao] == 1) {
            switch (acao) {
                case N: estAtu[0] -= 1; break;
                case NE: estAtu[0] -= 1; estAtu[1] += 1; break;
                case NO: estAtu[0] -= 1; estAtu[1] -= 1; break;
                case S: estAtu[0] += 1; break;
                case SE: estAtu[0] += 1; estAtu[1] += 1; break;
                case SO: estAtu[0] += 1; estAtu[1] -= 1; break;
                case L: estAtu[1] += 1; break;
                case O: estAtu[1] += 1; break;
            }
        }
        // atribui nova posicao ao estado atual
        // defEstAtu(lin, col);
        return estAtu;
    }
    
    protected int[] sucessor(int[] oestado, int acao) {
        // @todo
        // calcular estado sucessor a partir do estado e acao
        int[] estado = new int[2];
        estado[0] = oestado[0];
        estado[1] = oestado[1];
        int lin,col;
        switch (acao) {
            case N: estado[0] -= 1; break;
            case NE: estado[0] -= 1; estado[1] += 1; break;
            case NO: estado[0] -= 1; estado[1] -= 1; break;
            case S: estado[0] += 1; break;
            case SE: estado[0] += 1; estado[1] += 1; break;
            case SO: estado[0] += 1; estado[1] -= 1; break;
            case L: estado[1] += 1; break;
            case O: estado[1] += 1; break;
        }
        // atribui nova posicao ao estado atual
        // defEstAtu(lin, col);
        return estado;
    }

    /*
     * Retorna as acoesPossiveis possiveis de serem executadas em um estado 
     * O valor retornado é um vetor de inteiros. Se o valor da posicao é -1
     * então a ação correspondente não pode ser executada, caso contrario valera 1.
     * Por exemplo, 
     * [-1, -1, -1, 1, 1, -1, -1, -1] indica apenas que S e SO podem ser executadas.
     */
    protected int[] acoesPossiveis(int[] estado) {
        int acoes[] = new int[8];
        for(int i=0;i<8;i++)
            acoes[i]=-1;
        //@todo
        if(podeIrN())
            acoes[N] = 1;
        if(podeIrNE())
            acoes[NE] = 1;
        if(podeIrNO())
            acoes[NO] = 1;
        if(podeIrS())
            acoes[S] = 1;
        if(podeIrSE())
            acoes[SE] = 1;
        if(podeIrSO())
            acoes[SO] = 1;
        if(podeIrL())
            acoes[L] = 1;
        if(podeIrO())
            acoes[O] = 1;
        return acoes;
    }
    
    protected boolean podeIrN() {
        return podeIr(estAtu[0]-1,estAtu[1]);
    }
    
    protected boolean podeIrS() {
        return podeIr(estAtu[0]+1,estAtu[1]);
    }
    
    protected boolean podeIrL() {
        return podeIr(estAtu[0],estAtu[1]+1);
    }
    
    protected boolean podeIrO() {
        return podeIr(estAtu[0],estAtu[1]-1);
    }
    
    protected boolean podeIrNE() {
        return podeIr(estAtu[0]-1,estAtu[1]+1);
    }
    
    protected boolean podeIrNO() {
        return podeIr(estAtu[0]-1,estAtu[1]-1);
    }
    
    protected boolean podeIrSE() {
        return podeIr(estAtu[0]+1,estAtu[1]+1);
    }
    
    protected boolean podeIrSO() {
        return podeIr(estAtu[0]+1,estAtu[1]-1);
    }
    
    protected int[] acoesPossiveisB(int[] oestado) {
        int[] estado = new int[2];
        estado[0] = oestado[0];
        estado[1] = oestado[1];
        int acoes[] = new int[8];
        for(int i=0;i<8;i++)
            acoes[i]=-1;
        //@todo
        if(podeIrN(estado))
            acoes[N] = 1;
        if(podeIrNE(estado))
            acoes[NE] = 1;
        if(podeIrNO(estado))
            acoes[NO] = 1;
        if(podeIrS(estado))
            acoes[S] = 1;
        if(podeIrSE(estado))
            acoes[SE] = 1;
        if(podeIrSO(estado))
            acoes[SO] = 1;
        if(podeIrL(estado))
            acoes[L] = 1;
        if(podeIrO(estado))
            acoes[O] = 1;
        return acoes;
    }
    
    protected boolean podeIrN(int[] estado) {
        return podeIr(estado[0]-1,estado[1]);
    }
    
    protected boolean podeIrS(int[] estado) {
        return podeIr(estado[0]+1,estado[1]);
    }
    
    protected boolean podeIrL(int[] estado) {
        return podeIr(estado[0],estado[1]+1);
    }
    
    protected boolean podeIrO(int[] estado) {
        return podeIr(estado[0],estado[1]-1);
    }
    
    protected boolean podeIrNE(int[] estado) {
        return podeIr(estado[0]-1,estado[1]+1);
    }
    
    protected boolean podeIrNO(int[] estado) {
        return podeIr(estado[0]-1,estado[1]-1);
    }
    
    protected boolean podeIrSE(int[] estado) {
        return podeIr(estado[0]+1,estado[1]+1);
    }
    
    protected boolean podeIrSO(int[] estado) {
        return podeIr(estado[0]+1,estado[1]-1);
    }
    
    protected boolean podeIr(int x, int y) {
        return !(x < 0 || y < 0 || x > (maxLin - 1) || y > (maxCol - 1) || creLab.parede[x][y] == 1);
    }

    /*
     * Retorna true quando estado atual = estado objetivo, caso contrario retorna falso
     */
    protected boolean testeObjetivo() {
        //fazer o teste 
        for (int i=0; i<2; i++) {
            if(estAtu[i] != estObj[i])
                break;
            return true;
        }
        return false;
    }
}