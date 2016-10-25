/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cyk.v1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author darkblshadox
 */
public class CYKV1 {

    /**
     * @param args the command line arguments
     */
    static String cadenaInput;// cadena de entrada a evaluar
    static int cadenaLength;// Longitud de la cadenaInput
    static ArrayList<Character> vTerminales = new ArrayList<Character>();// Lista de elementos terminales
    static ArrayList<Character> vNoTerminales = new ArrayList<Character>();// Lista de elementos no terminales
    static ArrayList<String> produccionesAll = new ArrayList<String>();// Lista de producciones de la gramatica
    static int numProducciones;//Numero de producciones en la gramatica
    static int numNoTerm;//Numero de elementos no terminales
    static char ntInicial;//Cual es el simbolo de inicio de las producciones
    static String[][] producciones;//Array doble con las producciones
    //static String[][] m; //Matriz de la tabla CYK [las que sean necesarias][numero de elementos terminales]

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
    
    public static void printArray(String[][] array){//Imprime el array de las producciones
        for(int i=0; i< array.length; i++){
            System.out.println(""+array[i][0]+"->"+array[i][1]);
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
        
        //Muestra los elementos dentro de la arraylist
        printArrayCharlist(listaTerminales);
         //Define el numero de elementos en el arraylist
//        numElementos = listaTerminales.size();
    }
    
    public static void addNoTerminalsInArraylist(ArrayList<Character> listanoTerminales, ArrayList<Character> listaTerminales, String[][] producciones){// Agrega los elemento de la cadena dentro de la  listaArray si aun no son agregado
        char letter;
        String vals;
        for(int i=0; i<producciones.length; i++){
            for(int j=0; j<producciones[i].length; j++){
                vals=producciones[i][j];
                for(int k=0; k<vals.length();k++){
                    letter=vals.charAt(k);
                    if(isTerminalInArray(listanoTerminales,letter)==false && isTerminalInArray(listaTerminales,letter)==false){//Si el elemento no esta en la lista de vTerminales
                        listanoTerminales.add(letter);
                    }
                }
            }
        }
        
        //Muestra los elementos dentro de la arraylist
        System.out.print("Elementos no terminales: ");
        printArrayCharlist(listanoTerminales);
        numNoTerm=listanoTerminales.size();
         //Define el numero de elementos en el arraylist
//        numElementos = listaTerminales.size();
    }
    
    public static void printArrayCharlist(ArrayList<Character> arrayList){//Imprimir contenidos del arraylist de caracteres
        for(int i=0; i<arrayList.size();i++){
            System.out.print(arrayList.get(i) + ", ");
        }
//        System.out.println("\nFin de Arraylist.");
    }
    
    public static void printArrayStringlist(ArrayList<String> arrayList){//Imprimir contenidos del arraylist de Strings
        for(int i=0; i<arrayList.size();i++){
            System.out.println(arrayList.get(i) + "");
        }
//        System.out.println("\nFin de Arraylist.");
    }
    
//    public static void searchQo(char q0){//Verifica si el q0 previamente definido si esta en las producciones
//        String val = ""+q0;
//        for(int i = 0; i < producciones.length; i++){
//            if(producciones[i][0]!=val){
//                System.out.println("ERROR: El q0 previamente definido no esta en las producciones.\nTerminando programa.");
//                System.exit(0);
//            }
//        }
//    }
    
    public static void addGrammar (int numofPs){//Agrega las producciones de la gramatica al arraylist produccionesAll desde input
        Scanner pr = new Scanner(System.in);
        String input;
        
        System.out.println("Nota: Las siguientes producciones deben de tener el \nformato S:AB y/o A:a\nIntroduzca las producciones:");
        for(int i = 0; i<numofPs; i++){
            System.out.print("Produccion #"+(i+1)+": ");
            input = pr.nextLine();
            produccionesAll.add(input);
        }
    
    }
    
    public static void CYKalgorithm(){
    
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner in = new Scanner(System.in);
        System.out.print("Proyecto #2: Algoritmo CYK\n\nCual es la cadena a evaluar: ");
        String filename = "producciones.txt";// Nombre del archivo externo de donde se obtiene las producciones

        //Cadena
        cadenaInput = in.nextLine();//Recibe la cadena a evaluar
        cadenaLength = cadenaInput.length();//Define la longitud de la cadena
        
        //Elementos terminales
        System.out.print("\nElementos Terminales en cadena: ");
        addElementsInArraylist(vTerminales,cadenaInput);//Llena la lista de elementos Terminales con la cadena a evaluar y define el numero de elementos terminales
        System.out.println("\nNumero de elementos Terminales = " + vTerminales.size());
       
        //Producciones

//        System.out.print("Cual es el numero de producciones? ");//Entrada manual por el usuario
//        numProducciones = in.nextInt();
//        addGrammar (numProducciones);
        //printArrayStringlist(produccionesAll);//Imprime las producciones para revisar si se agregaron
        
        System.out.print("\nCual es el elemento no terminal de inicio? ");
        ntInicial = in.nextLine().charAt(0);// Se defina el elemento inicial
        numProducciones = countProd(filename);//Se cuenta la cantidad de producciones desde el archivo externo
        archivoDeProduc(filename);//Lee el archivo externo para guardar las producciones en el doublearray producciones
        System.out.println("\nNumero de producciones dentro del archivo '" + filename + "' = "+numProducciones);
        addNoTerminalsInArraylist(vNoTerminales,vTerminales,producciones);//Obtiene los elementos no terminales y cuenta el numero de no terminales
        System.out.println("\nNumero de elementos no terminales = "+numNoTerm);
        
        //Desplegar datos obtenidos
        System.out.println("\n\nq0 = " + ntInicial + "\n\nP = ");
        printArray(producciones);
        //searchQo(ntInicial);
        System.out.println("\nPara la cadena: " + cadenaInput);
        System.out.println("Longitud de cadena de entrada=" + cadenaLength);
        
        //ejecucion...
    }
    
}
