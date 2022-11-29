package org.telegram.ui.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.util.Log;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.tgnet.TLRPC;
import org.telegram.ui.Cells.DialogCell;
import org.telegram.ui.Cells.HintDialogCell;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.DialogsActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RecentChatsAdapter extends RecyclerListView.SelectionAdapter {
    List<String> dialog;
    private final Context mContext;
    private final int currentAccount;
    private boolean drawChecked;

    public RecentChatsAdapter(Context context, int account, boolean drawChecked) {
        this.drawChecked = drawChecked;
        mContext = context;
        currentAccount = account;
        dialog = getDialogId();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HintDialogCell cell = new HintDialogCell(mContext);
        cell.setLayoutParams(new RecyclerView.LayoutParams(AndroidUtilities.dp(80), AndroidUtilities.dp(86)));
        return new RecyclerListView.Holder(cell);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HintDialogCell cell = (HintDialogCell) holder.itemView;

        Long dialogId = Long.parseLong(dialog.get(position));
        String name = "";
        long did = 0;
        TLRPC.User user = MessagesStorage.getInstance(currentAccount).getUser(dialogId);
        if (user!=null){
            did = dialogId;
            name = user.first_name;
        }else{
            TLRPC.Chat chat = MessagesStorage.getInstance(currentAccount).getChat(dialogId);
            if (chat != null){
                name = chat.title;
                if (ChatObject.isChannel(chat)){
                    did = -dialogId;
                }else{
                    did = dialogId;
                }
            }else{
                name = "Hello";
            }
        }

        cell.setTag(dialog.get(position));

        long finalDid = did;
        String finalName = name;
        AndroidUtilities.runOnUIThread(() -> {
            cell.setDialog(finalDid, false, finalName);
        });

    }

    @Override
    public int getItemCount() {
        return dialog.size();
    }

    @Override
    public boolean isEnabled(RecyclerView.ViewHolder holder) {
        return true;
    }

    public List<String> getDialogId(){
        SharedPreferences prefs = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        String dialogId = prefs.getString("DIALOG_ID", "Пусто");
        String[] dialogIds = dialogId.split(";");
        List<String> dialogList = new ArrayList<>();

        if (dialogId.equals("Пусто")){
           return dialogList;
        }else if (dialogIds.length>1){
            dialogList = Arrays.asList(dialogIds);
            Collections.reverse(dialogList);
            return dialogList;
        }else{
            dialogList.add(dialogId);
            return dialogList;
        }
    }
}
