package estructuras;

import java.util.ArrayList;
import java.util.Arrays;

public class TablaHash<K extends Comparable<K> ,V> {

	/**
	 * Una enumeracion que representa los tipos de colisiones que se pueden manejar
	 * Simple: En caso de colision busca la siguiente casilla libre.
	 * Linked: En caso de colision encadena los elementos colisionados.
	 * @author danielsoto
	 *
	 */
	public enum CollisionType{
		SIMPLE, LINKED
	}

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

	/**
	 * El tipo de colision que va a manejar.
	 */
	private CollisionType tipo;

	//Constructores

	@SuppressWarnings("unchecked")
	public TablaHash(){
		factorCarga = 0;
		factorCargaMax = 0.8f;
		count = 0;
		capacidad = 100;
		tipo = CollisionType.SIMPLE;
		
		data = new NodoHash[capacidad];
	}

	@SuppressWarnings("unchecked")
	public TablaHash(int capacidad, float factorCargaMax, CollisionType tipo) {
		super();
		this.factorCargaMax = factorCargaMax;
		this.capacidad = capacidad;
		factorCarga = 0;
		count = 0;
		this.tipo = tipo;

		data = new NodoHash[capacidad];
	}

	public void put(K llave, V valor){

		int pos = hash(llave);

		switch (tipo) {
		case SIMPLE:
			while (pos<capacidad && data[pos] != null && data[pos].getLlave().compareTo(llave) != 0){
				pos++;
			}
			
			if (factorCarga >= factorCargaMax || pos>=capacidad){
				data = Arrays.copyOf(data, (int)(capacidad*1.5));
				capacidad*=1.5;
			}
			
			data[pos] = new NodoHash<K,V>(llave, valor);
			count++;
			factorCarga = count/capacidad;
			break;

		case LINKED:
			if (factorCarga >= factorCargaMax || pos>=capacidad){
				data = Arrays.copyOf(data, (int)(capacidad*1.5));
				capacidad*=1.5;
			}
			if (data[pos] != null){
				NodoHash<K, V> nodo = data[pos];
				while (nodo.getSiguiente()!=null){
					nodo = nodo.getSiguiente();
				}
				nodo.setSiguiente(new NodoHash<K,V>(llave, valor));
			}else{
				data[pos] = new NodoHash<K,V>(llave, valor);
			}
			count++;
			factorCarga = count/capacidad;
			break;

		default:
			break;
		}


	}

	public V get(K llave){
		
		int pos = hash(llave);

		switch (tipo) {
		case SIMPLE:
			while (data[pos] != null && data[pos].getLlave().compareTo(llave) != 0){
				pos++;
			}
			if (pos < capacidad){
				return data[pos] != null && data[pos].getLlave().compareTo(llave) == 0?data[pos].getValor():null;
			}
			break;
		case LINKED:
			if (data[pos] != null){
				NodoHash<K, V> nodo = data[pos];
				while (data[pos].getLlave().compareTo(llave) != 0 && nodo.getSiguiente()!=null){
					nodo = nodo.getSiguiente();
				}
				return data[pos].getLlave().compareTo(llave) == 0?data[pos].getValor():null;
			}
			break;
		default:
			break;
		}
		
		return null;
	}

	//Hash
	private int hash(K llave){
		return (llave.hashCode() & 0x7fffffff) % capacidad;
	}

	public ArrayList<V> getMultiple(K llave) {
		switch (tipo) {
		case SIMPLE:
			return null;
		case LINKED:
			ArrayList<V> valores = new ArrayList<V>();
			int pos = hash(llave);
			if (data[pos] != null){
				NodoHash<K, V> nodo = data[pos];
				if (nodo.getLlave().compareTo(llave)==0){
					valores.add(nodo.getValor());
				}
				while (nodo.getSiguiente()!=null){
					nodo = nodo.getSiguiente();
					if (nodo.getLlave().compareTo(llave)==0){
						valores.add(nodo.getValor());
					}
				}
				return valores;
			}
			break;
		default:
			break;
		}
		return null;
	}

}
