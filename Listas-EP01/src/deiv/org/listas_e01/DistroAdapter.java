package deiv.org.listas_e01;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DistroAdapter extends BaseAdapter {

	ListaDistros distros;
	LayoutInflater inflater;

	public DistroAdapter(Context context, String[] nombres, String[] anios)
	{
		inflater = LayoutInflater.from(context);
		
		this.distros = new ListaDistros(nombres, anios);
	}
	
	public int getCount() 
	{
		return distros.size();
	}

	public Object getItem(int index) 
	{
		return distros.get(index);
	}

	public long getItemId(int id) 
	{
		return id;
	}

	public View getView(int index, View view, ViewGroup parent) 
	{
		view = inflater.inflate(R.layout.fila_distro, null);
		TextView nombre = (TextView) view.findViewById(R.id.textViewNombre);
		TextView anio   = (TextView) view.findViewById(R.id.textViewAnio);
		
		nombre.setText(distros.get(index).nombre);
		anio.setText(distros.get(index).anio);
		
		return view;
	}

	class ListaDistros extends ArrayList<DistroInfo> {

		public ListaDistros(String[] nombres, String[] anios)
		{
			DistroInfo info;
			
			for (int c=0; c < nombres.length; c++) {
				info = new DistroInfo(nombres[c], anios[c]);
				this.add(info);
			}
		}
	}
}
