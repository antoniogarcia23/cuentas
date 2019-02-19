package PracticaCuentas;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Banco {
	//Atributos necesarios.
	private int numCuentas;
	private CCuenta clientes[];
	
	public Banco() {
		//inicializamos en el constructor
		numCuentas = 0;
		clientes = pedirMemoria(numCuentas);
	}
	////////////////////////////////
	private void unElementoMas(CCuenta aux[])
	{
		//Pedimos memoria para un elementos mas.
		clientes = pedirMemoria(numCuentas+1);
		//Copiamos los clientes anteriores en el arrar
		for(int i = 0; i < numCuentas; i++)
		{
			clientes[i] = aux[i];
		}
		//actualizamos el numero de clientes.
		numCuentas++;
	}
	/////////////////////////////////
	public void insertarEn(CCuenta cuenta, int posicion)
	{
		//validamos la posicion.
		if(posicion >= 0 && posicion < numCuentas)
		{
			clientes[posicion] = cuenta;
		}else
		{
			System.out.println("La posicion no es valida");
		}
	}
	/////////////////////////////////
	public CCuenta retornarValor(int posicion)
	{
		//validamos posicion.
			if(posicion >= 0 && posicion < numCuentas)
			{
				return clientes[posicion];
			}else
			{
				return null;
			}
	}
	/////////////////////////////////
	public boolean arrayVacio()
	{
		return (numCuentas == 0);
	}
	////////////////////////////////
	public void anadir(CCuenta cuenta)
	{
		unElementoMas(clientes);
		insertarEn(cuenta,numCuentas-1);
	}
	/////////////////////////////////
	public void unElementoMenos(CCuenta aux[])
	{
		//pedimos memoria para un elemento menos.
		clientes = new CCuenta[numCuentas -1];
		int j = 0;
		for(int i = 0; i < numCuentas; i++)
		{
			if(aux[i] != null)
			{
				clientes[j] = aux[i];
				j++;
			}
		}
		numCuentas--;
	}
	///////////////////////////////
	public int buscar(String cuenta)
	{
		for(int i = 0; i < numCuentas;i++)
		{
			if(cuenta.equals(clientes[i].getCuenta()))
			{
				return i;
			}
		}
		return -1;
	}
	///////////////////////////////
	public void eliminar(String cuenta)
	{
		if(arrayVacio())
		{
			System.out.println("No existe ninguna cuenta");
		}else
		{
			//vemos si hay alguna cuenta.
			int posicion = buscar(cuenta);
			boolean existe = (posicion != -1);
			if(existe)
			{
				clientes[posicion] = null;
				unElementoMenos(clientes);
				System.out.println("La cuenta ha sido eliminada");
			}else
			{
				System.out.println("El contacto no existe");
			}
		}
	}
	////////////////////////////////
	public int verMenu()
	{
		@SuppressWarnings("resource")
		Scanner entrada = new Scanner(System.in);
		boolean opcionValida = true;
		int opcion;
		
		System.out.println("1.Consultar saldo");
		System.out.println("2.Ingreso");
		System.out.println("3.Reintegro");
		System.out.println("4.Altas");
		System.out.println("5.Bajas");
		System.out.println("6.Mantenimiento");	
		System.out.println("7.Salir");
		System.out.println("Escoge una opcion");
		do
		{	
			opcion = entrada.nextInt();
			opcionValida = (opcion >=1 && opcion <=7);
			if(!opcionValida)
			{
				System.out.println("Error, introduce una opcion valida");
			}
			
		}while(!opcionValida);
		return opcion;
	}
	//////////////////////////////////	
	public void operacionesMenu()
	{
		boolean salir = false;
		do
		{
			int opcion = verMenu();
			salir =  (opcion == 7);
			if(!salir)
			{
				switch(opcion)
				{
				case 1:
					consulta();
					break;
				case 2:
					ingreso();
					break;
				case 3:
					reintegro();
					break;
				case 4:
					altas();
					break;
				case 5 :
					bajas();
					break;
				case 6:
					mantenimiento();
					break;
				}
			}
		}while(!salir);
		System.out.println("Programa finalizado. Hasta pronto");
	}
	/////////////////////////////
	public void altas()
	{
		@SuppressWarnings("resource")
		Scanner entrada = new Scanner(System.in);
		boolean opcionValida = true;
		int opcion;
		
		System.out.println("1. Cuenta ahorro");
		System.out.println("2. Cuenta corriente");
		System.out.println("3. Cuenta corriente con intereses");
		
		System.out.println("Elige el tipo de cuenta");
		do
		{
			opcion = entrada.nextInt();
			opcionValida = (opcion >=1 && opcion <=3);
			if(!opcionValida)
			{
				System.out.println("Introduce una opcion valida");
			}
			
		}while(!opcionValida);
		leerDatos(opcion);
	}
	///////////////////////////
	public void leerDatos(int opcion)
	{
		@SuppressWarnings("resource")
		Scanner entrada = new Scanner(System.in);
		System.out.println("Introduce el nombre del titular");
		String nombre = entrada.nextLine();
		System.out.println("Introduce el numero de cuenta");
		String numCuenta = entrada.nextLine();
		System.out.println("Introduce el tipo de interes");
		double interes = entrada.nextDouble();
		System.out.println("introduce el saldo");
		double saldo = entrada.nextDouble();
		
		CCuenta cuenta = null;
		
		if(opcion == 1)
		{
			System.out.println("Introduce la cuota de mantenimiento");
			double cuotaMantenimiento = entrada.nextDouble();
			cuenta = new CCuentaAhorro(nombre, numCuenta, interes, saldo, cuotaMantenimiento);
			}else
			{
				System.out.println("Introduce el importe por transaccion");
				double importePorTrans = entrada.nextDouble();
				System.out.println("Introduce el numero de transacciones exentas");
				int transExentas = entrada.nextInt();
				if(opcion == 2)
				{
					cuenta = new CCuentaCorriente(nombre, numCuenta, interes, saldo, importePorTrans, transExentas);
					
				}else
				{
					cuenta = new CCuentaCorrienteConIn(nombre, numCuenta, interes, saldo, importePorTrans, transExentas);
				}
			}
		anadir(cuenta);
	}
	/////////////////////////////////
	public void consulta()
	{
		@SuppressWarnings("resource")
		Scanner entrada = new Scanner(System.in);
		if(arrayVacio())
		{
			System.out.println("No existen cuentas en el banco");
		}else
		{
			System.out.println("Introduce el numero de cuenta");
			String cuenta = entrada.nextLine();
			int posicion = buscar(cuenta);
			boolean existe = (posicion != -1);
			if(existe)
			{
				System.out.println("Ttitular: " + clientes[posicion].getNombre());
				System.out.println("Numero de cuenta: " + clientes[posicion].getCuenta());
				System.out.println("Saldo: " + clientes[posicion].getSaldo());
				
			}else
			{
				System.out.println("La cuenta no existe");
			}
		}
	}
	////////////////////////////////////////
	public void ingreso()
	{
		@SuppressWarnings("resource")
		Scanner entrada = new Scanner(System.in);
		if(arrayVacio())
		{
			System.out.println("No hay cuentas en el banco");
		}else
		{
			System.out.println("Introduce el numero de cuenta");
			String cuenta = entrada.nextLine();
			int posicion = buscar(cuenta);
			boolean existe = (posicion != -1);
			if(existe)
			{
				System.out.println("Introduce la cantidad a ingresar");
				clientes[posicion].ingreso(entrada.nextDouble());
			}else
			{
				System.out.println("La cuenta no existe");
			}
		}
	}
	//////////////////////////////////////////
	public void reintegro()
	{
		@SuppressWarnings("resource")
		Scanner entrada = new Scanner(System.in);
		if(arrayVacio())
		{
			System.out.println("No hay cuentas en el banco");
		}else
		{
			System.out.println("Introduce el numero de cuenta");
			String cuenta = entrada.nextLine();
			int posicion = buscar(cuenta);
			boolean existe = (posicion != -1);
			if(existe)
			{
				System.out.println("Introduce la cantidad que quieras retirar");
				clientes[posicion].reintegro(entrada.nextDouble());
			}else
			{
				System.out.println("La cuenta no existe");
			}
			
		}
	}
	////////////////////////////////////////
	public void bajas()
	{
		@SuppressWarnings("resource")
		Scanner entrada = new Scanner(System.in);
		if(arrayVacio())
		{
			System.out.println("No hay cuentas en el banco");
		}else
		{
			System.out.println("Introduce el numero de cuenta");
			String cuenta = entrada.nextLine();
			int posicion = buscar(cuenta);
			boolean existe = (posicion != -1);
			if(existe)
			{
				eliminar(clientes[posicion].getCuenta());
				System.out.println("La cuenta ha sido eliminada");
			}else
			{
				System.out.println("El contacto no existe");
			}
		}
	}
	//////////////////////////////////////
	public void mantenimiento()
	{
		for(int i = 0; i < numCuentas; i++)
		{
			clientes[i].comisiones();
			clientes[i].intereses();
			
		}
	}
	//////////////////////////////////////
	public CCuenta[]pedirMemoria(int numCuentas) {
		try {
			return new CCuenta[numCuentas]; 
		}
		catch (OutOfMemoryError error) {
			System.err.println("Error, memoria insuficiente");
			return null;
		}
		
	}
}
