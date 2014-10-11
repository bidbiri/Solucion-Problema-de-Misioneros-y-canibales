/*
 INSTITUTO TECNOLÓGICO DE COSTA RICA
 CURSO ANÁLISIS DE ALGORITMOS
 PROFESOR MAURICIO ROJAS FERNÁNDEZ
 DANIEL MONTOYA AGUILAR
 CARNET 201106385
 SOLUCIÓN DEL PROBLEMA HIPOTÉTICO DE MISIONEROS Y CANÍBALES EN UNA ISLA USANDO TÉCNICA DE BACKTRACKING
 
 
 
 
 Misioneros y caníbales. 
Hay 3 misioneros y 3 caníbales en la orilla izquierda de un río. Un bote puede transportar a 1 o 2 personas de una orilla a otra. Objetivo: pasar a todos a la otra orilla. 
Condición: “No puede ocurrir nunca que si en una orilla hay algún misionero, haya a la vez un número mayor de caníbales (se los comerían).”
Estados: 
Parámetros: número misioneros lado izquierdo, número caníbales lado izquierdo, posición bote (izquierda o derecha). 
Se debe verificar la Condición.
Operadores: 
Transportar 1 misionero. 
Transportar 1 canibal. 
Transportar 2 misioneros.
Transportar 2 caníbales.
Transportar 1 misionero y 1 caníbal.

 
 */



public class MisionerosYCanibales {
	public static String M = "M";   //se mueve un misionero
    public static String MM = "MM"; //se mueven dos misioneroos
    public static String C = "C";   //se mueve un canibal
    public static String CC = "CC"; //se mueven dos canibales
    public static String MC = "MC"; //se mueve un misionero y un canibal
    
    private String ultMov; //guarda el ultimo movimiento, para no generar ciclos
    
    //Numero maximo de personas que puede trasladar
    private final int capacidadBarca = 2;
    public int getCapacidadBarca() {
        return capacidadBarca;
    }

    // [misioneros,canibales,barca] representa la orilla izquierda
    private int misioneros; 
    private int canibales;
    private int barca; //1 orilla izquierda, 0 orilla derecha
	
	public MisionerosYCanibales(){
		//3 misioneros, 3 canibales y la barca en la orilla izquierda
        misioneros = 3;
        canibales = 2;
        barca = 1;
        ultMov = " ";
	}
	public MisionerosYCanibales(MisionerosYCanibales estado){
        ultMov = estado.ultMov;
        misioneros = estado.misioneros;
        canibales = estado.canibales;
        barca = estado.barca;
    }
	
	public int getMisioneros() {
        return misioneros;
    }

    public int getCanibales() {
        return canibales;
    }

    public int getBarca() {
        return barca;
    }
    
    public int[] dameEstado(){
        int[] est = new int[3];
        est[0] = getMisioneros();
        est[1] = getCanibales();
        est[2] = getBarca();
        return est;    
    }
    
    public String imprimirEstado() {
    	int[] estado= dameEstado();
    	return "Misioneros:"+Math.abs(estado[0])+",Canibales:"+Math.abs(estado[1])+",Bote:"+Math.abs(estado[2])+"____"+"Misioneros:"+(3-estado[0])+",Canibales:"+(2-estado[1])+",Bote:"+(1-estado[2]);
	}
  //OPERADORES
    public void mueveM(){
        if (getBarca() == 1){
            barca = 0;
            misioneros--;
        }
        else{
            barca = 1;
            misioneros++;
        }    
        ultMov = M;
    }
    
    public void mueveMM(){
        if (getBarca() == 1){
            barca = 0;
            misioneros = getMisioneros() - 2;
        }
        else{
            barca = 1;
            misioneros = getMisioneros() + 2;
        } 
        ultMov = MM;
    }
    
    public void mueveC(){
        if (getBarca() == 1){
            barca = 0;
            canibales--;
        }
        else{
            barca = 1;
            canibales++;
        } 
        ultMov = C;
    }
    
    public void mueveCC(){
        if (getBarca() == 1){
            barca = 0;
            canibales = getCanibales() - 2;
        }
        else{
            barca = 1;
            canibales = getCanibales() + 2;
        } 
        ultMov = CC;
    }
    
    public void mueveMC(){
        if (getBarca() == 1){
            barca = 0;
            misioneros--;
            canibales--;
        }else{
            barca = 1;
            misioneros++;
            canibales++;
        }
        ultMov = MC;
    }
    
    public boolean puedeMover(String mov){
        if (ultMov.equals(mov)) return false; //evito repetir ultimo movimiento
        if (mov.equals(M)){
            if (getBarca() == 1)
                return getMisioneros() >= 1 && 
                       !estadoPeligroso(misioneros-1,canibales);
            else
                return 3-getMisioneros() >= 1 && 
                       !estadoPeligroso(misioneros+1,canibales);
        }
        if (mov.equals(MM)){
            if (getBarca() == 1)
                return getMisioneros() >= 2 &&
                        !estadoPeligroso(misioneros-2,canibales);
            else
                return 3-getMisioneros() >= 2 &&
                        !estadoPeligroso(misioneros+2,canibales);
        }
        if (mov.equals(C)){
            if (getBarca() == 1)
                return getCanibales() >= 1 &&
                        !estadoPeligroso(misioneros,canibales-1);
            else
                return 3-getCanibales() >= 1 &&
                        !estadoPeligroso(misioneros,canibales+1);
        }
        if (mov.equals(CC)){
            if (getBarca() == 1)
                return getCanibales() >= 2 &&
                        !estadoPeligroso(misioneros,canibales-2);
            else
                return 3-getCanibales() >= 2 &&
                        !estadoPeligroso(misioneros,canibales+2);
        }
        if (mov.equals(MC)){
            if (getBarca() == 1)
                return getMisioneros() >= 1 && getCanibales()  >= 1 &&
                        !estadoPeligroso(misioneros-1,canibales-1);
            else
                return 3-getMisioneros() >= 1 && 3-getCanibales() >= 1 &&
                        !estadoPeligroso(misioneros+1,canibales+1);
                  
        }
        return false;
    }
    
    private boolean estadoPeligroso(int m,int c){
        return (m < c && m != 0) || (m > c && m != 3);
    }
    
    public int heuristica() {
 	   int hVal = getMisioneros() +getCanibales();
 	   return hVal;
     }
	
	public static void main(String[] args){
		MisionerosYCanibales algoritmo = new MisionerosYCanibales();
		System.out.println(algoritmo.imprimirEstado());
		System.out.println("Numero de personas sin trasladar:"+algoritmo.heuristica());
		algoritmo.mueveCC();
		System.out.println(algoritmo.imprimirEstado());
		System.out.println("Numero de personas sin trasladar:"+algoritmo.heuristica());
		algoritmo.mueveC();
		System.out.println(algoritmo.imprimirEstado());
		System.out.println("Numero de personas sin trasladar:"+algoritmo.heuristica());
		algoritmo.mueveCC();
		System.out.println(algoritmo.imprimirEstado());
		System.out.println("Numero de personas sin trasladar:"+algoritmo.heuristica());
		algoritmo.mueveC();
		System.out.println(algoritmo.imprimirEstado());
		System.out.println("Numero de personas sin trasladar:"+algoritmo.heuristica());
		algoritmo.mueveMM();
		System.out.println(algoritmo.imprimirEstado());
		System.out.println("Numero de personas sin trasladar:"+algoritmo.heuristica());
		algoritmo.mueveMC();
		System.out.println(algoritmo.imprimirEstado());
		System.out.println("Numero de personas sin trasladar:"+algoritmo.heuristica());
		algoritmo.mueveMM();
		System.out.println(algoritmo.imprimirEstado());
		System.out.println("Numero de personas sin trasladar:"+algoritmo.heuristica());
		algoritmo.mueveC();
		System.out.println(algoritmo.imprimirEstado());
		System.out.println("Numero de personas sin trasladar:"+algoritmo.heuristica());
		algoritmo.mueveCC();
		System.out.println(algoritmo.imprimirEstado());
		System.out.println("Numero de personas sin trasladar:"+algoritmo.heuristica());
		algoritmo.mueveC();
		System.out.println(algoritmo.imprimirEstado());
		System.out.println("Numero de personas sin trasladar:"+algoritmo.heuristica());
		algoritmo.mueveCC();
		System.out.println(algoritmo.imprimirEstado());
		System.out.println("Numero de personas sin trasladar:"+algoritmo.heuristica());
	}


}
