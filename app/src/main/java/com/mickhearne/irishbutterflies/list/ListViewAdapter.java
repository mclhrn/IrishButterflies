package com.mickhearne.irishbutterflies.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.mickhearne.irishbutterflies.R;
import com.mickhearne.irishbutterflies.model.Butterfly;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends ArrayAdapter<Butterfly> implements Filterable {

	Context context;
    List<Butterfly> butterflies;
    private List<Butterfly> origList;
    private Filter filter = null;


    public ListViewAdapter(Context context, List<Butterfly> butterflies) {
    	super(context, android.R.id.content, butterflies);
        this.context = context;
        this.butterflies = butterflies;
        this.origList = butterflies;
    }


    public int getCount() {
		return butterflies.size();
	}


	public Butterfly getItem(int position) {
		return butterflies.get(position);
	}


//	public long getItemId(int position) {
//		return butterflies.get(position).hashCode();
//	}


    @Override
	public View getView(int position, View convertView, ViewGroup parent) {

    	LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.list_item, null);

        Butterfly butterfly = butterflies.get(position);

        TextView tv = (TextView) view.findViewById(R.id.list_item_name);
        tv.setText(butterfly.getName());

        tv = (TextView) view.findViewById(R.id.latin);
        tv.setText(butterfly.getLatinName());

        ImageView iv = (ImageView) view.findViewById(R.id.thumb);
        int imageResource = context.getResources().getIdentifier(
                butterfly.getImageThumb(), "drawable", context.getPackageName());
        if (imageResource != 0) {
        	iv.setImageResource(imageResource);
        }

        return view;
	}

    public void resetData() {
        butterflies = origList;
	}

    @Override
	public Filter getFilter() {
		if (filter == null)
			filter = new ButterflyFilter();

		return filter;
	}

    private class ButterflyFilter extends Filter {

		@Override
    	protected FilterResults performFiltering(CharSequence cs) {

    		FilterResults results = new FilterResults();

			if (cs == null || cs.length() == 0) {
				// return full list
				results.values = origList;
				results.count = origList.size();
			}
			else {
				// We perform filtering operation
				List<Butterfly> nList = new ArrayList<Butterfly>();

				for (Butterfly b : butterflies) {
					if (b.getName().toUpperCase().startsWith(cs.toString().toUpperCase()))
						nList.add(b);
				}
				results.values = nList;
				results.count = nList.size();
			}
			return results;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {

			// inform the adapter about the new list filtered
			if (results.count == 0)
				notifyDataSetInvalidated();
			else {
                butterflies = (List<Butterfly>) results.values;
				notifyDataSetChanged();
			}
		}
	}
}
