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
 * @author darkblshadox, carlations
 */
public class CYKV1 {

    /**
     * @param args the command line arguments
     */
    static String cadenaInput;// cadena de entrada a evaluar
    static int cadenaLength;// Longitud de la cadenaInput
    static ArrayList<Character> vTerminales = new ArrayList<Character>();// Lista de elementos terminales
    static ArrayList<String> produccionesAll = new ArrayList<String>();// Lista de producciones de la gramatica
    static int numProducciones;//Numero de producciones en la gramatica
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
    
    public static String[][] CYKalgorithm(int num, Arraylist<Character> vT, String[][] prod, char ntIni, String cinput){
        String[][] tablares = new String[num][num];
        for(int i = 0; i < n; i++){
            tablares[i][i+1] = "";
            for(int j = 0; j < num; j++){
                if(prod[j][1].length() == 1 && cinput.substring(i)(i+1).equals(prod[j][1])){
                    tablares[i][i+1] += prod[j][0];
                }
            }
        }
        for(int m = 0; m <= num; m++){
            for(int i = 0; i <= n-m; i++){
                tablares[i][i+m] = "";
                for(int j = i+1; j < (i+m-1); j++){
                    for(int f = 0; f <= num; f++){
                        if(prod[f][1].length() == 2 && tablares[i][j].contains(prod[f][1].charAt(0)) && tablares[j][i+m].contains(prod[f][1].charAt(1))){
                            tablares[i][i+m] += prod[f][0];
                        }
                    }
                }
            }
        }
        return tablares;
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner in = new Scanner(System.in);
        System.out.print("Proyecto #2: Algoritmo CYK\n\nCual es la cadena a evaluar:");
        String filename = "producciones.txt";

        //Cadena
        cadenaInput = in.nextLine();//Recibe la cadena a evaluar
        cadenaLength = cadenaInput.length();//Define la longitud de la cadena
        
        //Elementos terminales
        System.out.print("\nElementos Terminales: ");
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
       //System.out.println("Numero de producciones = "+numProducciones);

        //Desplegar datos obtenidos
        System.out.println("\nq0 = " + ntInicial + "\n\nP = ");
        printArray(producciones);
        System.out.println("\nPara la cadena: " + cadenaInput);
        System.out.println("Longitud de cadena de entrada=" + cadenaLength);
        
        //Se necesita metodo de concatenacion
    }
    
}
