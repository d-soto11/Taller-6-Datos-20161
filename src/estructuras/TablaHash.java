package estructuras;

import java.util.ArrayList;
import java.util.Arrays;

public class TablaHash<K extends Comparable<K> ,V> {

	/**
	 * Factor de carga actual de la tabla (porcentaje utilizado).
	 */
	private float factorCarga;
	
	/**
	 * Factor de carga maximo que soporta la tabla.
	 */
	private float factorCargaMax;
	
	/**
	 * Estructura que soporta la tabla.
	 */
	private NodoHash<K, V>[] data;
	
	/**
	 * La cuenta de elementos actuales.
	 */
	private int count;
	
	/**
	 * La capacidad actual de la tabla. Tamaño del arreglo fijo.
	 */
	private int capacidad;
	
	//Constructores
	
	public TablaHash(){
		factorCarga = 0;
		factorCargaMax = 0.8f;
		count = 0;
		capacidad = 100;
		
		data = new NodoHash[capacidad];
	}

	public TablaHash(int capacidad, float factorCargaMax) {
		super();
		this.factorCargaMax = factorCargaMax;
		this.capacidad = capacidad;
		factorCarga = 0;
		count = 0;

		data = new NodoHash[capacidad];
	}
	
	public void put(K llave, V valor){
		if (factorCarga >= factorCargaMax){
			data = Arrays.copyOf(data, capacidad+100);
			capacidad+=100;
		}
		int pos = hash(llave);
		while (pos<capacidad && data[pos] != null && data[pos].getLlave().compareTo(llave) != 0){
			pos++;
		}
		data[pos] = new NodoHash<K,V>(llave, valor);
		count++;
		factorCarga = count/capacidad;
	}
	
	public V get(K llave){
		int pos = hash(llave);
		while (data[pos] != null && data[pos].getLlave().compareTo(llave) != 0){
			pos++;
		}
		if (pos < capacidad){
			return data[pos]!=null?data[pos].getValor():null;
		}
		return null;
	}
	
	//Hash
	private int hash(K llave){
		return (llave.hashCode() & 0x7fffffff) % capacidad;
	}
	
}
