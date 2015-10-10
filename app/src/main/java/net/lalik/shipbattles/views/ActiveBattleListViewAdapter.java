package net.lalik.shipbattles.views;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.lalik.shipbattles.R;
import net.lalik.shipbattles.sdk2.entity.Account;
import net.lalik.shipbattles.sdk2.entity.Battle;

public class ActiveBattleListViewAdapter extends ArrayAdapter<Battle> {
    private final Context context;
    private final Battle[] battles;
    private Account account;

    public ActiveBattleListViewAdapter(Context context, Battle[] battles, Account account) {
        super(context, -1, battles);
        this.context = context;
        this.battles = battles;
        this.account = account;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item_active_battle, parent, false);
        TextView opponentName = (TextView)rowView.findViewById(R.id.opponent_name);
        TextView battleState = (TextView)rowView.findViewById(R.id.battle_state);

        opponentName.setText(buildOpponentNameText(position));
        battleState.setText(buildBattleStateText(position));

        return rowView;
    }

    private String buildBattleStateText(int position) {
        Battle battle = battles[position];
        switch (battle.getState()) {
            case 2:
                return "wodowanie statków";
            case 3:
//                if (battle.getCurrentAccount().getId() == account.getId()) {
                    return "twoja tura";
//                } else {
//                    return "oczekiwanie na ruch przeciwnika";
//                }
            case 4:
                return "bitwa zakończona";
        }
        return "invalid";
    }

    private String buildOpponentNameText(int position) {
        Battle battle = battles[position];
//        Account opponent = battle.getSecondAccountId(account.getId());
//        return String.format("z %s", opponent.getNick());
        return "foo";
    }

//    @Override
//    public long getItemId(int position) {
//        return battles[position].getId();
//    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
