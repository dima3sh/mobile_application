package com.example.app4;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app4.enums.ConceivedNumberType;

import java.util.Date;

public class MainFragment extends Fragment {

    static final String ACCESS_KEY = "name";
    private static ConceivedNumberType mode = ConceivedNumberType.TWO_NUMBER;
    private TextView hint;
    private TextView attempt;
    private TextView guess;
    private EditText editText;
    private Button save;

    GuessOnClickListener listener;

    public MainFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,
                container, false);
        listener = new GuessOnClickListener(GenerateNumUtil.randomCompNum(mode.getAttempt() - 3));

        this.hint = view.findViewById(R.id.show_hint);
        this.attempt = view.findViewById(R.id.show_attempts_left);
        this.guess = view.findViewById(R.id.show_msg);
        this.editText = view.findViewById(R.id.editText);
        this.save = view.findViewById(R.id.btn_save_result);
        if (savedInstanceState != null) {
            attempt.setText(savedInstanceState.getString("Attempt"));
            hint.setText(savedInstanceState.getString("Hint"));
        }

        ((TextView) view.findViewById(R.id.name)).setText(getResources().getString(R.string.player_name));
        view.findViewById(R.id.btn_guess).setOnClickListener(listener);
        view.findViewById(R.id.btn_rename).setOnClickListener(new RenameListener());
        view.findViewById(R.id.btn_restart).setOnClickListener(new RestartListener());
        save.setOnClickListener(new SaveListener());
        save.setEnabled(false);
        Log.i(MainActivity.class.getSimpleName(), "Угадай число");
        registerForContextMenu(view.findViewById(R.id.star));

        getParentFragmentManager().setFragmentResultListener("rename", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                // We use a String here, but any type that can be put in a Bundle is supported
                String result = bundle.getString("rename");
                // Do something with the result
                TextView titleText = requireActivity().findViewById(R.id.name);
                titleText.setText(bundle.getString("data"));
            }
        });

        requireActivity().getSupportFragmentManager().setFragmentResultListener("selectMode", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                // We use a String here, but any type that can be put in a Bundle is supported
                String result = bundle.getString("number");
                // Do something with the result
                mode = ConceivedNumberType.values()[Integer.parseInt(result)];
                listener.setNum(GenerateNumUtil.randomCompNum(mode.getAttempt() - 3));

                hint.setText(getResources().getString(mode.getHint()));
                attempt.setText(String.valueOf(mode.getAttempt()));
            }
        });
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Attempt", attempt.getText().toString());
        outState.putString("Hint", hint.getText().toString());
    }

    public class RestartListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            attempt.setText(String.valueOf(mode.getAttempt()));
            editText.setText("");
            hint.setText(getResources().getString(mode.getHint()));
            guess.setText(getResources().getString(R.string.show_msg_label));

            listener.setNum(GenerateNumUtil.randomCompNum(mode.getAttempt() - 3));
            Button button = requireActivity().findViewById(R.id.btn_guess);
            button.setEnabled(true);
            save.setEnabled(false);
        }
    }

    public class GuessOnClickListener implements View.OnClickListener {

        private int number;

        public GuessOnClickListener(int number) {
            this.number = number;
        }

        @Override
        public void onClick(View view) {

            int attemptLeft = Integer.parseInt(attempt.getText().toString());

            if ("".equals(editText.getText().toString().trim())) return;
            int userNum = Integer.parseInt(editText.getText().toString());

            if (attemptLeft == 1 && userNum != number) {
                Toast toast = Toast.makeText(view.getContext(), getResources().getString(R.string.btn_bad_msg_label), Toast.LENGTH_LONG);
                toast.show();
                Button button = view.findViewById(R.id.btn_guess);
                button.setEnabled(false);
                save.setEnabled(true);
            }

            if (userNum > number) {
                hint.setText(getResources().getString(R.string.show_hint_less_label));
            } else if (userNum < number) {
                hint.setText(getResources().getString(R.string.show_hint_more_label));
            } else {
                share(getResources().getString(R.string.share_result) + (mode.getMode() + 1));
                Toast.makeText(requireActivity().getApplicationContext(),
                        getResources().getString(R.string.btn_good_msg_label),
                        Toast.LENGTH_LONG).show();
                guess.setText(getResources().getString(R.string.btn_guess_decision_label));
                Button button = view.findViewById(R.id.btn_guess);
                button.setEnabled(false);
                save.setEnabled(true);
            }
            attemptLeft--;
            attempt.setText(String.valueOf(attemptLeft));

        }

        public void setNum(int number) {
            this.number = number;
        }

        public int getNum() {
            return number;
        }
    }

    public class RenameListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            TextView titleText = requireActivity().findViewById(R.id.name);
            Bundle result = new Bundle();
            result.putString("data", titleText.getText().toString());
            getParentFragmentManager().setFragmentResult("name", result);
            Navigation.findNavController(view).navigate(R.id.action_mainFragment3_to_profileFragment2);
        }
    }

    private AlertDialog selectModeBuilder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle(R.string.settings);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        builder.setItems(R.array.diaps_array, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                mode = ConceivedNumberType.values()[which];
                listener.setNum(GenerateNumUtil.randomCompNum(mode.getAttempt() - 3));

                hint.setText(getResources().getString(mode.getHint()));
                attempt.setText(String.valueOf(mode.getAttempt()));
            }
        });

        return builder.create();
    }

    private void share(String request) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Content subject");
        shareIntent.putExtra(Intent.EXTRA_TEXT, request);
        startActivity(Intent.createChooser(shareIntent, "Sharing something."));
        startActivity(shareIntent);
    }

    class SaveListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            save(getReport(), view);
        }

        private void save(String request, View view) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.setPackage("com.samsung.android.app.notes");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Content subject");
            shareIntent.putExtra(Intent.EXTRA_TEXT, request);
            view.getContext().startActivity(shareIntent);
        }
    }

    private String getReport() {
        String result = getResources().getString(R.string.date_time) + new Date().toString() + "\n";
        int attemptLeft = Integer.parseInt(attempt.getText().toString());
        if (attemptLeft != 0) {
            result += getResources().getString(R.string.attempt_num) + (mode.getAttempt() - attemptLeft) + "\n";
        }
        return result + getResources().getString(R.string.wish_num) + listener.getNum();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        requireActivity().getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settingsMenuItem:
                Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.show_hello_message), Toast.LENGTH_LONG).show();
                break;
            case R.id.aboutMenuItem:
                Toast.makeText(requireActivity(), String.valueOf(listener.getNum()), Toast.LENGTH_LONG).show();
                notifyUser();

        }
        return super.onContextItemSelected(item);
    }

    private void notifyUser() {
        NotificationManager mNotificationManager;

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(requireActivity().getApplicationContext(), "notify_001");

        mBuilder.setSmallIcon(R.drawable._23123);
        mBuilder.setContentTitle("Loser");
        mBuilder.setContentText(requireActivity().getResources().getString(R.string.show_loser_hint_message));
        mBuilder.setPriority(Notification.PRIORITY_MAX);

        mNotificationManager =
                (NotificationManager) requireActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }
        mNotificationManager.notify(0, mBuilder.build());
    }
}