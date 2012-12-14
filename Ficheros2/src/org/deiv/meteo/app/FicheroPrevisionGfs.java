package org.deiv.meteo.app;

import org.deiv.meteo.FicheroDatos;
import org.deiv.meteo.colecciones.MapaOrdenacionFecha;

class FicheroPrevisionGfs extends FicheroDatos<DatoPrevisionGfs5Km, MapaOrdenacionFecha<DatoPrevisionGfs5Km> > {

	private static final long serialVersionUID = -1958458497148397046L;

	public FicheroPrevisionGfs()
	{
		super(DatoPrevisionGfs5Km.class, MapaOrdenacionFecha.class);
	}
}
