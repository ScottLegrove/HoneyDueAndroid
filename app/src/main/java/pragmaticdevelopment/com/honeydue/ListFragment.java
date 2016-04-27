package pragmaticdevelopment.com.honeydue;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import pragmaticdevelopment.com.honeydue.HelperClasses.ListHelper;

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

        // Put bundle or other args here

        return new ListFragment();
    }

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.lvLists) {
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.actions_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        SharedPreferences sp = getContext().getSharedPreferences(getString(R.string.shared_pref_id), Context.MODE_PRIVATE);
        final String spToken = sp.getString("token", null);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final long itemId = info.id;

        switch (item.getItemId()) {
            case R.id.action_share_list:
                final EditText shEditText = new EditText(this.getActivity());
                AlertDialog dialog = new AlertDialog.Builder(this.getActivity())
                        .setTitle("Share this list")
                        .setMessage("Username")
                        .setView(shEditText)
                        .setPositiveButton("Share", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String shUser = String.valueOf(shEditText.getText());
                                ListHelper.addCollabUserShareList(listIDs[(int) itemId], shUser, spToken);
                                Toast.makeText(getActivity().getApplicationContext(), "Successfully shared with " + shUser,
                                        Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
                return true;
            case R.id.action_edit_list:
                final EditText edEditText = new EditText(this.getActivity());
                AlertDialog builder = new AlertDialog.Builder(this.getActivity())
                        .setTitle("Edit List Name")
                        .setMessage("New List Name")
                        .setView(edEditText)
                        .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String newName = String.valueOf(edEditText.getText());
                                ListHelper.updateList(listIDs[(int) itemId], newName, spToken);
                                Toast.makeText(getActivity().getApplicationContext(), "Successfully changed list name to " + newName,
                                        Toast.LENGTH_SHORT).show();
                                FragmentManager fm = getActivity().getSupportFragmentManager();

                                FragmentTransaction ft = fm.beginTransaction();
                                ListFragment lf = ListFragment.newInstance();
                                ft.replace(R.id.fragFrame, lf, "lists");
                                ft.commit();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                builder.show();
                return true;
            case R.id.action_delete_list:
                Log.d("delete ", "delete");
                ListHelper.deleteList(listIDs[(int) itemId], spToken);
                Toast.makeText(getActivity().getApplicationContext(), "List deleted!",
                        Toast.LENGTH_SHORT).show();

                FragmentManager fm = getActivity().getSupportFragmentManager();

                FragmentTransaction ft = fm.beginTransaction();
                ListFragment lf = ListFragment.newInstance();
                ft.replace(R.id.fragFrame, lf, "lists");
                ft.commit();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
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
        registerForContextMenu(lvLists);

        // Get user token
        SharedPreferences sp = getContext().getSharedPreferences(getString(R.string.shared_pref_id), Context.MODE_PRIVATE);
        String spToken = sp.getString("token", null);

        if(spToken != null) {
            // Acquire lists
            lTask = new ListTask(spToken);
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
                ListsModel selected = (ListsModel)lvLists.getItemAtPosition(position);
                i.putExtra("LIST_TITLE_EXTRA", selected.getTitle());
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
