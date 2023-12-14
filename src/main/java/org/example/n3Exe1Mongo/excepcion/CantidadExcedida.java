package org.example.n3Exe1Mongo.excepcion;

public class CantidadExcedida extends Exception {

	private static final long serialVersionUID = 1L;

	public CantidadExcedida() {
		
	}

	public CantidadExcedida(String errorMessage) {
		super(errorMessage);

	}
}
