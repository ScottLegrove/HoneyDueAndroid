package pragmaticdevelopment.com.honeydue;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import pragmaticdevelopment.com.honeydue.DBSource.ListsModel;

import static pragmaticdevelopment.com.honeydue.HelperClasses.ListHelper.*;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private ListTask lTask = null;
    private ListView lvLists;
    private ListsAdapter la;
    private ListsModel[] lists;
    private int[] listIDs;

    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();

        // Put bundle or other args here

        return fragment;
    }

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        // Initialize lists
        lvLists = (ListView) view.findViewById(R.id.lvLists);

        // Get user token
        SharedPreferences sp = getContext().getSharedPreferences(getString(R.string.shared_pref_id), Context.MODE_PRIVATE);
        String spToken = sp.getString("token", null);

        if(spToken != null) {
            // Acquire lists
            ListTask lTask = new ListTask(spToken);
            lTask.execute((Void) null);

            try { lTask.get(); // this will cause application to wait until it returns
            }catch(Exception ex){}

            // Set adapter to ListView
            lvLists.setAdapter(la);

            // Put IDs of lists in an array
            listIDs = new int[lists.length];
            for (int i = 0; i < lists.length; i++) {
                listIDs[i] = lists[i].getId();
            }
        }

        lvLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getContext(), TaskListActivity.class);
                i.putExtra("LIST_ID_EXTRA", listIDs[position]);
                startActivity(i);
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public class ListsAdapter extends ArrayAdapter<ListsModel>{
        public ListsAdapter(Context context, ArrayList<ListsModel> lists){
            super(context, 0, lists);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListsModel list = getItem(position);

            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list, parent, false);
            }

            TextView tvList = (TextView) convertView.findViewById(R.id.txtListTitle);

            tvList.setText(list.getTitle());

            return convertView;
        }
    }

    public class ListTask extends AsyncTask<Void, Void, Boolean>{
        String uToken;

        ListTask(String token){
            uToken = token;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try{
                lists = getAllLists(uToken);
                la = new ListsAdapter(getContext(), new ArrayList<ListsModel>(Arrays.asList(lists)));

                return la.getCount() > 0;
            }catch(Exception e){
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success){
            lTask = null;

            // If there are no lists
            if(!success) Toast.makeText(getContext(), "No Lists Available", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onCancelled(){
            lTask = null;
        }
    }
}
