package pragmaticdevelopment.com.honeydue;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;


public class AlertDialogNewListFragment extends DialogFragment {

    private newListDialogListener nListener;

    public interface newListDialogListener{
        void onDialogPositiveClick(AlertDialogNewListFragment dialog);
        void onDialogNegativeClick(AlertDialogNewListFragment dialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        AlertDialog.Builder aDialog = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.alert_dialog_new_list, null);
        aDialog.setView(view)
                .setPositiveButton("add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialogNewListFragment.this.getDialog().cancel();
                    }
                });
        return aDialog.create();
    }

    public AlertDialogNewListFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AlertDialogNewListFragment newInstance() {
        AlertDialogNewListFragment fragment = new AlertDialogNewListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_alert_dialog_new_list, container, false);
//    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            nListener = (newListDialogListener) activity;
        }catch (Exception e){
            throw new ClassCastException(activity.toString()+ "Must implement listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        nListener = null;
    }
}
