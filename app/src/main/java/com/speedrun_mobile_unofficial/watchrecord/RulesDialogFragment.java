package com.speedrun_mobile_unofficial.watchrecord;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.speedrun_mobile_unofficial.R;

public class RulesDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.fragment_rules_dialog, null);
        builder.setView(rootView);

        String rules = getArguments().getString("rules");

        TextView ruleText = rootView.findViewById(R.id.rule_dialog_text);
        ruleText.setText(rules);
        Button okButton = rootView.findViewById(R.id.rule_dialog_btn);
        okButton.setOnClickListener(v -> dismiss());

        return builder.create();
    }
}
