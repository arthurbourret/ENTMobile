package com.example.entmobile.mails;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.entmobile.R;
import com.example.entmobile.mails.adapter.MessagesAdapter;
import com.example.entmobile.mails.helper.DividerItemDecoration;
import com.example.entmobile.mails.model.Message;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MailViewerActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener, MessagesAdapter.MessageAdapterListener {

    private List<Message> messages = new ArrayList<>();
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private MessagesAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ActionModeCallback actionModeCallback;
    private ActionMode actionMode;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_viewer);

        fab = findViewById(R.id.fab);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycler_view);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);

        setActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMailWriter();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(this);

        mAdapter = new MessagesAdapter(this, messages, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        actionModeCallback = new ActionModeCallback();

        // show loader and fetch messages
        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        getInbox();
                    }
                }
        );
    }

    private void openMailWriter() {
        startActivity(new Intent(this, MailWriterActivity.class));
    }

    private void getInbox() {
        swipeRefreshLayout.setRefreshing(true);

        messages.clear();

        List<String> namesList = new ArrayList<String>();
        namesList.add("Robert");
        namesList.add("Michou");
        namesList.add("Patrick");
        namesList.add("Bernard");
        namesList.add("Jacques");

        List<String> subjectList = new ArrayList<String>();
        subjectList.add("Tu l'as vu?");
        subjectList.add("J'ai un plumeau dans mon placard");
        subjectList.add("Incroyable mais vrai !");
        subjectList.add("Quatorze");
        subjectList.add("J'ai plus d'inspi");

        List<String> messageList = new ArrayList<String>();
        messageList.add("Je t'envois un beau message");
        messageList.add("Le message qui tue");
        messageList.add("Pour un message acheté, un massage offert");
        messageList.add("Il s'est passé un truc incroyable");
        messageList.add("Bah salut quoi");

        List<String> timeList = new ArrayList<String>();
        timeList.add(" hours ago");
        timeList.add(" days ago");
        timeList.add(" days ago");
        timeList.add(" days ago");
        timeList.add(" days ago");
        timeList.add(" days ago");
        timeList.add(" weeks ago");
        timeList.add(" weeks ago");
        timeList.add(" months ago");
        timeList.add(" years ago");

        List<Boolean> importanceList = new ArrayList<Boolean>();
        importanceList.add(true);
        importanceList.add(false);

        List<Boolean> readList = new ArrayList<Boolean>();
        readList.add(true);
        readList.add(false);

        Random rand = new Random();

        for (int i=0; i<10; i++) {
            Message newMessage = new Message();

            newMessage.setId(i);
            newMessage.setColor(getRandomMaterialColor("400"));
            newMessage.setFrom(namesList.get(rand.nextInt(5)));
            newMessage.setSubject(subjectList.get(rand.nextInt(5)));
            newMessage.setMessage(messageList.get(rand.nextInt(5)));
            newMessage.setTimestamp(1+rand.nextInt(7)+timeList.get(i));
            newMessage.setImportant(importanceList.get(rand.nextInt(2)));
            newMessage.setRead(importanceList.get(rand.nextInt(2)));

            messages.add(newMessage);
        }

        mAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    /**
     * chooses a random color from array.xml
     */
    private int getRandomMaterialColor(String typeColor) {
        int returnColor = Color.GRAY;
        int arrayId = getResources().getIdentifier("mdcolor_" + typeColor, "array", getPackageName());

        if (arrayId != 0) {
            TypedArray colors = getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.GRAY);
            colors.recycle();
        }
        return returnColor;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        // swipe refresh is performed, fetch the messages again
        getInbox();
    }

    @Override
    public void onIconClicked(int position) {
        if (actionMode == null) {
            actionMode = startActionMode(actionModeCallback);
        }
        toggleSelection(position);
    }

    @Override
    public void onIconImportantClicked(int position) {
        // Star icon is clicked,
        // mark the message as important
        Message message = messages.get(position);
        message.setImportant(!message.isImportant());
        messages.set(position, message);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMessageRowClicked(int position) {
        // verify whether action mode is enabled or not
        // if enabled, change the row state to activated
        if (mAdapter.getSelectedItemCount() > 0) {
            enableActionMode(position);
        }
        else {
            // read the message which removes bold from the row
            Message message = messages.get(position);
            message.setRead(true);
            messages.set(position, message);
            mAdapter.notifyDataSetChanged();

            Toast.makeText(getApplicationContext(), "Read: " + message.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRowLongClicked(int position) {
        // long press is performed, enable action mode
        enableActionMode(position);
    }

    private void enableActionMode(int position) {
        if (actionMode == null) {
            actionMode = startActionMode(actionModeCallback);
        }
        toggleSelection(position);
    }

    private void toggleSelection(int position) {
        mAdapter.toggleSelection(position);
        int count = mAdapter.getSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
            getActionBar().show();
        }
        else {
            getActionBar().hide();
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }


    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_action_mode, menu);

            // disable swipe refresh if action mode is enabled
            swipeRefreshLayout.setEnabled(false);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete:
                    // delete all the selected messages
                    deleteMessages();
                    mode.finish();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mAdapter.clearSelections();
            swipeRefreshLayout.setEnabled(true);
            actionMode = null;
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    mAdapter.resetAnimationIndex();
                    // mAdapter.notifyDataSetChanged();
                }
            });
            getActionBar().show();
        }
    }

    // deleting the messages from recycler view
    private void deleteMessages() {
        mAdapter.resetAnimationIndex();
        getActionBar().show();
        List<Integer> selectedItemPositions = mAdapter.getSelectedItems();
        for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
            mAdapter.removeData(selectedItemPositions.get(i));
        }
        mAdapter.notifyDataSetChanged();
    }
}
