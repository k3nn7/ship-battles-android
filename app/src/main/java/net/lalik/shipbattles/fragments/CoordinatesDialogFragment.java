package net.lalik.shipbattles.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import net.lalik.shipbattles.R;
import net.lalik.shipbattles.views.Coordinate;
import net.lalik.shipbattles.views.Ship;

public class CoordinatesDialogFragment extends DialogFragment {
    public interface CoordinatesDialogListener {
        public void onDeployClicked(Coordinate coordinate, Ship.Orientation orientation);
    }

    private CoordinatesDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();


        View view = inflater.inflate(R.layout.dialog_coordinates, null);
        final NumberPicker picker = (NumberPicker)view.findViewById(R.id.x_coordinate_picker);
        picker.setMinValue(1);
        picker.setMaxValue(10);
        picker.setDisplayedValues(
                new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"}
        );

        final NumberPicker picker2 = (NumberPicker)view.findViewById(R.id.y_coordinate_picker);
        picker2.setMinValue(1);
        picker2.setMaxValue(10);

        final NumberPicker picker3 = (NumberPicker)view.findViewById(R.id.orientation_picker);
        picker3.setMinValue(0);
        picker3.setMaxValue(1);
        picker3.setDisplayedValues(
                new String[]{"Poziomo", "Pionowo"}
        );

        builder.setView(view)
                .setPositiveButton("Woduj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            Ship.Orientation orientation;
                            if (picker3.getValue() == 0) {
                                orientation = Ship.Orientation.HORIZONTAL;
                            } else {
                                orientation = Ship.Orientation.VERTICAL;
                            }
                            listener.onDeployClicked(
                                    new Coordinate(picker2.getValue(), picker.getValue()),
                                    orientation
                                    );
                        }
                        Log.d("abc", String.format("X: %d Y: %d", picker2.getValue(), picker.getValue()));
                    }
                })
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


        return builder.create();
    }

    public void setListener(CoordinatesDialogListener listener) {
        this.listener = listener;
    }
}
