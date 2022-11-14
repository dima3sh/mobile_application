package com.example.app4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        Log.i(MainActivity.class.getSimpleName(), "Start");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.i(MainActivity.class.getSimpleName(), "Stop");
        super.onStop();
    }

    @Override
    protected void onResume() {
        Log.i(MainActivity.class.getSimpleName(), "Resume");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        Log.i(MainActivity.class.getSimpleName(), "Restart");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Log.i(MainActivity.class.getSimpleName(), "Destroy");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.i(MainActivity.class.getSimpleName(), "Pause");
        super.onPause();
    }
//
//    @Override
//    protected void onSaveInstanceState(@NotNull Bundle outState) {
//        Log.i(MainActivity.class.getSimpleName(), "Reverse");
//        super.onSaveInstanceState(outState);
////        outState.putString("Attempt", attempt.getText().toString());
////        outState.putString("Hint", hint.getText().toString());
        //getFragmentManager().putFragment(outState, "myfragment", myfragment);
//    }
//    @Override
//    public void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
////        attempt.setText(savedInstanceState.getString("Attempt"));
////        hint.setText(savedInstanceState.getString("Hint"));
//    }

//    public void restart(View view) {
//        attempt.setText(String.valueOf(mode.getAttempt()));
//        editText.setText("");
//        hint.setText(getResources().getString(mode.getHint()));
//        guess.setText(getResources().getString(R.string.show_msg_label));
//
//        listener.setNum(GenerateNumUtil.randomCompNum(mode.getAttempt() - 3));
//        Button button = findViewById(R.id.btn_guess);
//        button.setEnabled(true);
//        save.setEnabled(false);
//    }

//    public class GuessOnClickListener implements View.OnClickListener {
//
//        private int number;
//
//        public GuessOnClickListener(int number) {
//            this.number = number;
//        }
//
//        @Override
//        public void onClick(View view) {
//
//            int attemptLeft = Integer.parseInt(attempt.getText().toString());
//
//            if ("".equals(editText.getText().toString().trim())) return;
//            int userNum = Integer.parseInt(editText.getText().toString());
//
//            if (attemptLeft == 1 && userNum != number) {
//                Toast toast = Toast.makeText(view.getContext(), getResources().getString(R.string.btn_bad_msg_label), Toast.LENGTH_LONG);
//                toast.show();
//                Button button = findViewById(R.id.btn_guess);
//                button.setEnabled(false);
//                save.setEnabled(true);
//            }
//
//            if (userNum > number) {
//                hint.setText(getResources().getString(R.string.show_hint_less_label));
//            } else if (userNum < number) {
//                hint.setText(getResources().getString(R.string.show_hint_more_label));
//            } else {
//                share(getResources().getString(R.string.share_result) + (mode.getMode() + 1));
//                Toast.makeText(getApplicationContext(),
//                        getResources().getString(R.string.btn_good_msg_label),
//                        Toast.LENGTH_LONG).show();
//                guess.setText(getResources().getString(R.string.btn_guess_decision_label));
//                Button button = findViewById(R.id.btn_guess);
//                button.setEnabled(false);
//                save.setEnabled(true);
//            }
//            attemptLeft--;
//            attempt.setText(String.valueOf(attemptLeft));
//
//        }
//
//        public void setNum(int number) {
//            this.number = number;
//        }
//
//        public int getNum() {
//            return number;
//        }
//    }
//
//    public class RenameListener implements View.OnClickListener {
//        @Override
//        public void onClick(View view) {
//            Fragment mFragment = null;
//            mFragment = new ProfileFragment();
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.ProfileFragment, mFragment).commit();
//        }
//    }

    private AlertDialog selectModeBuilder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.settings);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        builder.setItems(R.array.diaps_array, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Bundle result = new Bundle();
                result.putString("number", String.valueOf(which));
                getSupportFragmentManager().setFragmentResult("selectMode", result);
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

//    class SaveListener implements View.OnClickListener {
//
//        @Override
//        public void onClick(View view) {
//
//            save(getReport(), view);
//        }
//
//        private void save(String request, View view) {
//            Intent shareIntent = new Intent(Intent.ACTION_SEND);
//            shareIntent.setType("text/plain");
//            shareIntent.setPackage("com.samsung.android.app.notes");
//            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Content subject");
//            shareIntent.putExtra(Intent.EXTRA_TEXT, request);
//            view.getContext().startActivity(shareIntent);
//        }
//    }

//    private String getReport() {
//        String result = getResources().getString(R.string.date_time) + new Date().toString() + "\n";
//        int attemptLeft = Integer.parseInt(attempt.getText().toString());
//        if (attemptLeft != 0) {
//            result += getResources().getString(R.string.attempt_num) + (mode.getAttempt() - attemptLeft) + "\n";
//        }
//        return result + getResources().getString(R.string.wish_num) + listener.getNum();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settingsMenuItem:
                selectModeBuilder().show();
                break;
            case R.id.aboutMenuItem:
                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment); // replace "nav_host_fragment" with the id of your navHostFragment in activity layout
                navController.navigate(R.id.aboutFragment);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}