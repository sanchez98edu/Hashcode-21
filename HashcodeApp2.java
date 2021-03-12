import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.*;
import java.util.Arrays;  
import java.util.List;  
import java.util.ArrayList;

public class HashcodeApp2{
	public static void main(String[] args){
		
		//Nombre de los ficheros a leer
		String[] ficheros = new String[]{"a.txt","b.txt","c.txt","d.txt","e.txt","f.txt"};
		
		//Abrimos el fichero
		File file = new File(ficheros[Integer.parseInt(args[0])-1]);
		Scanner fich = null;
		try {
			fich = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//Lectura de fichero
		
		int tSim = fich.nextInt();
		int nInt = fich.nextInt();
		int nCal = fich.nextInt();
		int nCoc = fich.nextInt();
		int bonus = fich.nextInt();
		fich.nextLine();
		
		ArrayList<Calle> ciudad= new ArrayList<Calle>();
		
		for(int i = 0; i < nCal; i++){
			int inicio = fich.nextInt();
			int fin = fich.nextInt();
			String aux = fich.nextLine();
			String nombre = aux.split(" ")[1];
			int tiempo = Integer.valueOf(aux.split(" ")[2]);
			Calle calle = new Calle(i, inicio, fin, nombre, tiempo);
			ciudad.add(calle);
			//System.out.println(inicio + " " + fin + " " + nombre + " " + tiempo);
		}
		
		ArrayList<Coche> cocheria= new ArrayList<Coche>();
		
		for(int i = 0; i < nCoc ; i++){
			int nRec = fich.nextInt();
			int inter = -1;
			ArrayList<String> calles = new ArrayList<String>(Arrays.asList(fich.nextLine().split(" ")));
			Coche coche = new Coche(i, nRec, calles, inter);
			cocheria.add(coche);
		}
		
		for (int i=0; i< nCoc; i++) {
            ArrayList <String>calle_car=cocheria.get(i)._calles;
            //System.out.println(calle_car);
            int tiempo_path=0;
            for(int j=0; j< calle_car.size();j++) {
                for(int p=0; p<nCal;p++) {
                    if(calle_car.get(j).equals(ciudad.get(p)._nombre) ) {
                        tiempo_path+=ciudad.get(p)._tiempo;
                    }
                }
            }
            if(tiempo_path>tSim) {
                cocheria.remove(i);
                i--;
                nCoc--;
            }
        }
		
		ArrayList<Interseccion> cruces = new ArrayList<Interseccion>();
		
		for(int i = 0; i < nInt; i++){
			Interseccion inter = new Interseccion(i);
			cruces.add(inter);
		}
		
		//Creamos el fichero de escritura
		FileWriter salida = null;
		String name_salida = ficheros[Integer.parseInt(args[0])-1] + ".out";
		try {
			salida = new FileWriter(name_salida);
		
		//Escritura de fichero y calculos
		
		salida.write(nInt + "\n");
		
		
		
		for(int i = 0; i < nCoc ; i++){
			int nR = cocheria.get(i)._nRec;
			for(int j = 0; j < nR; j++){
				String calle = cocheria.get(i)._calles.get(j);
				for(int k = 0; k < nCal; k++){
					if(ciudad.get(k)._nombre.equals(calle)){
						ciudad.get(k)._n++;
					}
				}
			}
		
		}	
		
		for(int i = 0; i < nCal; i++){
			//int id1 = ciudad.get(i)._inicio;
			int id2 = ciudad.get(i)._fin;
			//System.out.println(ciudad.get(i)._nombre);
			//cruces.get(id1)._calles.add(ciudad.get(i));
			cruces.get(id2)._calles.add(ciudad.get(i));
			System.out.println(i);
		}
		
		for(int i = 0; i < nInt; i++){
			salida.write(cruces.get(i)._id + "\n");
			salida.write(cruces.get(i)._calles.size() + "\n");
			Interseccion in = cruces.get(i);
			int tiempo = 0;
			for(int j = 0; j < in._calles.size(); j++){
				tiempo = tiempo + in._calles.get(j)._n;
			}
			for(int j = 0; j < in._calles.size(); j++){
				salida.write(in._calles.get(j)._nombre);
				int porcentaje = Math.max(in._calles.get(j)._n * tiempo / tSim,1);
				salida.write(" " + porcentaje + "\n");
			}
			System.out.println(i);
		}
		
		
		salida.close();
		} catch (IOException e) {e.printStackTrace();}
	}
}

//Otras funciones

class Calle{
	public int _id;
	public int _inicio;
	public int _fin;
	public String _nombre;
	public int _tiempo;
	public int _n;
	
	public Calle(int id, int inicio, int fin, String nombre, int tiempo){
		_id = id;
		_inicio = inicio;
		_fin = fin;
		_nombre = nombre;
		_tiempo = tiempo;
		_n = 0;
	}
}

class Coche{
	public int _id;
	public int _nRec;
	public ArrayList<String> _calles;
	public int _tiempo;
	public int _inter;
	public boolean _recien;
	
	public Coche(int id, int nRec, ArrayList<String> calles, int inter){
		_nRec = nRec;
		_calles = calles;
		_tiempo = 0;
		_inter = inter;
		_recien = true;
	}
}

class Interseccion{
	public int _id;
	public ArrayList<Calle> _calles;
	
	public Interseccion(int id){
		_id = id;
		_calles = new ArrayList<Calle>();
	}
}



