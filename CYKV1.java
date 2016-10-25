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
//    static int NumTerminales=0;// Numero de elementos terminales sin que se repitan
    static ArrayList<String> produccionesAll = new ArrayList<String>();// Lista de producciones de la gramatica
    static int numProducciones;//Numero de producciones en la gramatica
    static char ntInicial;//Cual es el simbolo de inicio de las producciones
    static String[][] m; //Matriz de la tabla CYK [las que sean necesarias][numero de elementos terminales]
    static String[][] producciones;//Array doble con las producciones
    
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
    
    public static void archivoDeProduc(String file){//Funcion de lectura del archivo
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
    
    public static void addGrammar (int numofPs){//Agrega las producciones de la gramatica al arraylist produccionesAll
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
        System.out.print("Proyecto #2: Algoritmo CYK\n\nCual es la cadena a evaluar:");
        
        //Cadena
        cadenaInput = in.nextLine();//Recibe la cadena a evaluar
        cadenaLength = cadenaInput.length();//Define la longitud de la cadena
        System.out.println("cadena de entrada: " + cadenaInput);
        System.out.println("Longitud de cadena de entrada=" + cadenaLength);
        
        //Elementos terminales
        System.out.print("\nElementos Terminales: ");
        addElementsInArraylist(vTerminales,cadenaInput);//Llena la lista de elementos Terminales con la cadena a evaluar y define el numero de elementos terminales
        System.out.println("\nNumero de elementos Terminales = " + vTerminales.size());
       
        //Producciones
//        System.out.print("Cual es el numero de producciones? ");
//        numProducciones = in.nextInt();
//        addGrammar (numProducciones);
        //printArrayStringlist(produccionesAll);//Imprime las producciones para revisar si se agregaron
        System.out.print("Cual es el elemento de inicio? ");
        ntInicial = in.nextLine().charAt(0);
       //System.out.println("Elemento de inicio: " + ntInicial);
        numProducciones = countProd("producciones.txt");
        System.out.println("Numero de producciones = "+numProducciones);
        
        
        //Se necesita metodo de concatenacion
    }
    
}
