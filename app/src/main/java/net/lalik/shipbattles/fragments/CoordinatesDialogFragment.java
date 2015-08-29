package net.lalik.shipbattles.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import net.lalik.shipbattles.R;
import net.lalik.shipbattles.sdk.values.Coordinate;
import net.lalik.shipbattles.sdk.values.Orientation;

public class CoordinatesDialogFragment extends DialogFragment {
    public interface CoordinatesDialogListener {
        void onDeployClicked(Coordinate coordinate, Orientation orientation);
    }

    private CoordinatesDialogListener listener;
    private NumberPicker yCoordinatePicker;
    private NumberPicker xCoordinatePicker;
    private NumberPicker orientationPicker;
    private View view;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_coordinates, null);
        initXCoordinatePicker();
        initYCoordinatePicker();
        initOrientationPicker();
        return buildDialog();
    }

    public void setListener(CoordinatesDialogListener listener) {
        this.listener = listener;
    }

    private void initOrientationPicker() {
        orientationPicker = (NumberPicker)view.findViewById(R.id.orientation_picker);
        orientationPicker.setMinValue(0);
        orientationPicker.setMaxValue(1);
        orientationPicker.setDisplayedValues(new String[]{"Poziomo", "Pionowo"});
    }

    private void initXCoordinatePicker() {
        xCoordinatePicker = (NumberPicker)view.findViewById(R.id.x_coordinate_picker);
        xCoordinatePicker.setMinValue(1);
        xCoordinatePicker.setMaxValue(10);
    }

    private void initYCoordinatePicker() {
        yCoordinatePicker = (NumberPicker)view.findViewById(R.id.y_coordinate_picker);
        yCoordinatePicker.setMinValue(1);
        yCoordinatePicker.setMaxValue(10);
        yCoordinatePicker.setDisplayedValues(
                new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"}
        );
    }

    private Dialog buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setPositiveButton("Woduj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onDeployClicked();
                    }
                })
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        return builder.create();
    }

    private void onDeployClicked() {
        if (listener == null)
            return;

        listener.onDeployClicked(
                getCoordinate(),
                getOrientation()
        );
    }

    private Orientation getOrientation() {
        if (orientationPicker.getValue() == 0)
            return Orientation.HORIZONTAL;
        return Orientation.VERTICAL;
    }

    private Coordinate getCoordinate() {
        return new Coordinate(
                yCoordinatePicker.getValue(),
                xCoordinatePicker.getValue()
        );
    }
}
