/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cykalgorithm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Hugo_Gutierrez
 */
public class CYKalgorithm {

    /**
     * @param args the command line arguments
     */
    static String cadenaInput;// cadena de entrada a evaluar
    static int cadenaLength;// Longitud de la cadenaInput
    static ArrayList<Character> vTerminales = new ArrayList<Character>();// Lista de elementos terminales
    static ArrayList<Character> vNoTerminales = new ArrayList<Character>();// Lista de elementos no terminales
    static ArrayList<String> ABList = new ArrayList<String>();//Lista de elementos no terminales concatenados
    static int numProducciones;//Numero de producciones en la gramatica
    static int numNoTerm;//Numero de elementos no terminales
    static char ntInicial;//Cual es el simbolo de inicio de las producciones
    static String[][] producciones;//Array doble con las producciones
    static String[][] AST; //Matriz de la tabla CYK [las que sean necesarias][numero de elementos terminales
    
    static ArrayList<String> Abtesting = new ArrayList<String>();

    public static int countProd(String file){//Cuenta el numero de producciones en el documento
        String filename = file;
        String line = null;
        
        int numProd = 0;
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                numProd++;
            }
            bufferedReader.close();
    
        }catch(FileNotFoundException ex) {
            System.out.println("ERROR: El archivo '"+filename+"' no se encuentra en el folder.");
            System.exit(0);                
        }catch(IOException ex) {
            System.out.println("ERROR: Falla de lectura del archivo '"+filename+"'.");
            System.exit(0);
        }
        
        return numProd;
    }
    
    public static void archivoDeProduc(String file){//Funcion de lectura del archivo para obtener las producciones
        String filename = file;
        String line = null;
        String[] prod;
        producciones = new String[numProducciones][2];
        int numProd = 0;
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                //System.out.println(line);
                prod = line.split(":");
                producciones[numProd][0] = prod[0];
                producciones[numProd][1] = prod[1];
                numProd++;
            }
            bufferedReader.close();
    
        }catch(FileNotFoundException ex) {
            System.out.println("ERROR: El archivo '"+filename+"' no se encuentra en el folder.");
            System.exit(0);                
        }catch(IOException ex) {
            System.out.println("ERROR: Falla de lectura del archivo '"+filename+"'.");
            System.exit(0);
        }
    }
    
    public static void printProdArray(String[][] array){//Imprime el array de las producciones
        for(int i=0; i< array.length; i++){
            System.out.println(""+array[i][0]+"->"+array[i][1]);
        }
    }
    
    public static void printdoubleArray(String[][] array){//Imprime el doublearray de Strings
        for(int i=0; i< array.length; i++){
            for(int j=0; j<array[i].length; j++){
                System.out.print(""+array[i][j]+"|");
            }
            System.out.print("\n");
        }
    }
    
    public static void printArrayCharlist(ArrayList<Character> arrayList){//Imprimir contenidos del arraylist de caracteres
        for(int i=0; i<arrayList.size();i++){
            System.out.print(arrayList.get(i) + ", ");
        }
    }
    
    public static void printArrayStringlist(ArrayList<String> arrayList){//Imprimir contenidos del arraylist de Strings
        for(int i=0; i<arrayList.size();i++){
            System.out.println(arrayList.get(i) + "");
        }
    }
    
    public static boolean isTerminalInArray(ArrayList<Character> arrayList, char letter){// Verifica si el elemento terminal ya esta o no en el ArrayList
        if(arrayList.contains(letter)){
            return true;
        }else{
            return false;
        }
    }
    
    public static void addElementsInArraylist(ArrayList<Character> listaTerminales, String cadena){// Agrega los elemento de la cadena dentro de la  listaArray si aun no son agregado
        char letter;
        for(int i=0; i<cadena.length(); i++){
            letter=cadena.charAt(i);
            if(isTerminalInArray(listaTerminales,letter)==false){//Si el elemento no esta en la lista de vTerminales
                listaTerminales.add(letter);
            }
//            else{//sino
//                System.out.println("Elemento " + letter + " ya esta dentro de la lista de terminales.");
//            }
        }
        //printArrayCharlist(listaTerminales);
    }
    
    public static void addNoTerminalsInArraylist(ArrayList<Character> listanoTerminales, ArrayList<Character> listaTerminales, String[][] producciones){// Agrega los elemento de la cadena dentro de la  listaArray si aun no son agregado
        char letter;
        String vals;
        for(int i=0; i<producciones.length; i++){
            for(int j=0; j<producciones[i].length; j++){
                vals=producciones[i][j];
                for(int k=0; k<vals.length();k++){
                    letter=vals.charAt(k);
                    if(isTerminalInArray(listanoTerminales,letter)==false && isTerminalInArray(listaTerminales,letter)==false){//Si el elemento no esta en la lista de vNoterminales y vTerminales
                        listanoTerminales.add(letter);
                    }
                }
            }
        }
//        System.out.print("Elementos no terminales: ");
//        printArrayCharlist(listanoTerminales);
        numNoTerm=listanoTerminales.size();
    }
    
    public static void initAST(){//Inicializa la matriz del AST
        AST = new String[cadenaLength][cadenaLength];
        for(int i=0;i<AST.length;i++){
            for(int j=0;j<AST[i].length;j++){
                AST[i][j]="-";
            }
        }
    }
    
    public static void AtoaTypeProd(){//Funcion que busca las producciones del tipo A->a aka primera parte del algoritmo
        String test="";
        String validNT="";//No terminales
        StringBuilder validNTform;//
        for(int i=0; i<cadenaInput.length();i++){
            test = ""+cadenaInput.charAt(i);
            //System.out.println("test: "+test);
            for(int j=0; j<producciones.length;j++){
                //System.out.println("validNT: "+producciones[j][1]);
                if(producciones[j][1].contains(test)){
                 validNT += producciones[j][0]+",";
                }    
            }
            if(validNT != null && !validNT.isEmpty()) { 
                validNTform = new StringBuilder(validNT);//Se utiliza para poder modificar el String con el conjunto de elementos no terminales
                //System.out.println("test builder= "+validNTform);
                validNTform.deleteCharAt((validNT.length()-1));//Elimina la coma al final del string
                validNT = validNTform.toString();
                AST[(cadenaInput.length()-1)][i]=validNT;//Guarda el conjunto en el AST
            }else{
                validNT="-";
                AST[(cadenaInput.length()-1)][i]=validNT;//Guarda el conjunto en el AST    
            }
            validNT=""; 
        }
    }
    
    public static void ABStestCreat(String cel1,String cel2){//Crea la lista de AB para verificar si pertenecen a alguna produccion
        String[] conj1=cel1.split(",");
        String[] conj2=cel2.split(",");
        String val1="";
        for(int i=0; i<conj1.length;i++){
            for(int j=0; j<conj2.length;j++){
                val1=conj1[i]+conj2[j];
                Abtesting.add(val1);
            }
        }
        //printArrayStringlist(Abtesting);
    }
    
    
    public static String isABinProd(String[][] prods){//Revisa si los elementos no terminales concatenados y creados desde ABStestCreat estan presente en las producciones tipo A->AB
        String ABs="";
        StringBuilder validNTform;
        for(int j=0; j<Abtesting.size();j++){
            for(int i =0; i<prods.length;i++){
                //System.out.println("VtA = "+Abtesting.get(j)+" & VtB = "+prods[i][1]);
                if(Abtesting.get(j).equals(prods[i][1])){
                    
                    if(ABs.contains(prods[i][0])){
//                        System.out.println("No agregado, ya esta presente");
                        System.out.print("");
                    }else{
                        ABs += prods[i][0]+",";
                    }
                }
            }
        }
        if(ABs != null && !ABs.isEmpty()) { 
                validNTform = new StringBuilder(ABs);//Se utiliza para poder modificar el String con el conjunto de elementos no terminales
                //System.out.println("test builder= "+validNTform);
                validNTform.deleteCharAt((ABs.length()-1));//Elimina la coma al final del string
                ABs = validNTform.toString();
//                AST[(cadenaInput.length()-1)][i]=ABs;//Guarda el conjunto en el AST
            }else{
                ABs="-";
//                AST[(cadenaInput.length()-1)][i]=ABs;//Guarda el conjunto en el AST    
            }
        //System.out.println("ABs que se va a agregar: "+ABs);
        return ABs;
    }
    
    public static void concatenacionAB(String[] val1,String[] val2){//Concatena elementos no terminales
        String newval="";
        for(int i=0;i<val1.length;i++){
            for(int j=0;j<val2.length;j++){
                newval = val1[i]+val2[j];
                ABList.add(newval);
            }
        }
    }
    
    public static void AtoABtype(String w, String[][] T){//Funcion que busca las producciones del tipo A->AB aka segunda parte del algoritmo
        int m=1,n=1,x=1,pos=0;
        for(int i=w.length()-2;i>=0;i--){
            //System.out.println("Siguiente linea... ");
            for(int j=0; j<=(w.length()-(2+pos));j++){
                //System.out.println("T a llenar: ["+i+"]["+j+"]");
                m=1;
                do{
                    //System.out.println("T1["+(i+m)+"]["+j+"] x T2["+(i+n)+"]["+(j+n)+"]");
                    ABStestCreat(T[i+m][j],T[i+n][j+n]);
                    T[i][j]=isABinProd(producciones);
                    m++;
                    n--;
                    //System.out.println("m = "+m+" y n = "+n);
                }while(n!=0);
                n=x;
                Abtesting.clear();
            }
            pos++;
            x++;
            n=x;
        } 
    }
    
    public static void cadenValid(){//Revisa si la cadena se rechaza o se acepta
        String test = "" + ntInicial;
        if(AST[0][0].contains(test)){
            System.out.println("\nLa cadena si se acepta.");
        }else{
            System.out.println("\nLa cadena no se acepta.");
        }
    }
    
    public static void CYKalgorithm(){//Ejecucion del algoritmo CYK
        initAST();
        AtoaTypeProd();//Busca las producciones del tipo A-> a y las agrega al AST
        AtoABtype(cadenaInput,AST);//Busca las producciones del tipo A->B,A->CB y las agrega al AST
        System.out.println("-----------AST-----------");
        printdoubleArray(AST);//Imprime el contenido del AST
        System.out.println("-------------------------");
        cadenValid();// Valida si la cadena es aceptada o rechazada
        System.out.println("\n-----FIN DEL PROGRAMA-----");
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner in = new Scanner(System.in);
        System.out.print("Proyecto #2: Algoritmo CYK\n\n"
                       + "RECORDATORIO: Nada de espacios en blanco o lineas vacias,\n"
                       + "y seguir el mismo formato que el archivo de muestra:\n"
                       + "'producciones.txt'. Gracias :)\n\nCual es el nombre del archivo con las producciones>");
        String filename = in.nextLine();// Nombre del archivo externo de donde se obtiene las producciones
        
        //Cadena
        System.out.print("\nCual es la cadena a evaluar>");
        cadenaInput = in.nextLine();//Recibe la cadena a evaluar
        cadenaLength = cadenaInput.length();//Define la longitud de la cadena
        
        //Elementos terminales
        //System.out.print("\nElementos Terminales en cadena: ");
        addElementsInArraylist(vTerminales,cadenaInput);//Llena la lista de elementos Terminales con la cadena a evaluar y define el numero de elementos terminales
        //System.out.println("\nNumero de elementos Terminales = " + vTerminales.size());
       
        //Producciones
        System.out.print("\nCual es el elemento no terminal de inicio>");
        ntInicial = in.nextLine().charAt(0);// Se define el elemento inicial
        numProducciones = countProd(filename);//Se cuenta la cantidad de producciones desde el archivo externo
        archivoDeProduc(filename);//Lee el archivo externo para guardar las producciones en el doublearray producciones
        //System.out.println("\nNumero de producciones dentro del archivo '" + filename + "' = "+numProducciones);
        addNoTerminalsInArraylist(vNoTerminales,vTerminales,producciones);//Obtiene los elementos no terminales y cuenta el numero de no terminales
        //System.out.println("\nNumero de elementos no terminales = "+numNoTerm);
        
        //Desplegar datos obtenidos
        System.out.println("\n\nq0 = " + ntInicial + "\n\nP = ");
        printProdArray(producciones);
        //System.out.println("\nPara la cadena: " + cadenaInput);
        System.out.println("\nw = " + cadenaInput + "\n");
        //System.out.println("Longitud de cadena de entrada=" + cadenaLength);
        
        //ejecucion de algoritmo CYK
        CYKalgorithm();
    }
    
}
