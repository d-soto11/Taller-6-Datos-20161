package interfaz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import mundo.DirectorioTelefonico;
import mundo.RegistroTelefonico;

public class Main {

	private static String rutaEntrada = "./data/ciudadcentral.csv";
	private static DirectorioTelefonico directorio;

	public static void main(String[] args) {
		//Cargar registros
		System.out.println("Bienvenido al directorio telefonico de Ciudad Central");
		System.out.println("Espere un momento mientras cargamos la informacion...");
		System.out.println("Esto puede tardar unos minutos...");
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(rutaEntrada)));
			String entrada = br.readLine();

			directorio = new DirectorioTelefonico();
			int i = 0;
			entrada = br.readLine();
			while (entrada != null){
				String[] datos = entrada.split(",");
				RegistroTelefonico reg = new RegistroTelefonico(datos[0], datos[1], datos[2]);
				directorio.agregarRegistro(reg);
				++i;
				if (++i%500000 == 0)
					System.out.println(i+" entradas...");
				
				entrada = br.readLine();
			}
			System.out.println(i+" entradas cargadas en total");
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 

		System.out.println("Ciudad Central Directorio v1.0");

		boolean seguir = true;

		while (seguir)
			try {
				System.out.println("Bienvenido, seleccione alguna opcion del menu a continuacion:");
				System.out.println("1: Agregar registro telefonico");
				System.out.println("2: Buscar registro telefonico");
				System.out.println("3: Buscar registros por apellido");
				System.out.println("Exit: Salir de la aplicacion");
				
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				String in = br.readLine();
				switch (in) {
				case "1":
					System.out.println("Ingrese el nombre (solo primer nombre) de la persona:");
					String nombre = br.readLine();
					System.out.println("Ingrese el primer apellido de la persona:");
					String apellido = br.readLine();
					System.out.println("Ingrese el numero de telefono de la persona:");
					String tel = br.readLine();

					RegistroTelefonico reg = new RegistroTelefonico(nombre, apellido, tel);

					directorio.agregarRegistro(reg);
					
					System.out.println("Agregado correctamente!");
					break;
				case "2":
					System.out.println("Ingrese el numero que desea buscar: ");
					String tele = br.readLine();
					RegistroTelefonico registro = directorio.buscarRegistroTelefono(tele);
					System.out.println(registro);
					break;
				case "3":
					System.out.println("Ingrese el apellido a buscar: ");
					String apellidos = br.readLine();
					ArrayList<RegistroTelefonico> registros = directorio.buscarRegistrosApellido(apellidos);
					for (RegistroTelefonico registroTelefonico : registros) {
						System.out.println(registroTelefonico);
					}
					break;
				case "Exit":
					System.out.println("Cerrando directorio...");
					seguir = false;
					break;

				default:
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}


	}

}
