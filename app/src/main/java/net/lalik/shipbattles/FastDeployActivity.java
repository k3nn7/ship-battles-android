package net.lalik.shipbattles;

import android.os.Bundle;
import android.app.Activity;

import net.lalik.shipbattles.views.DeployBattlefieldView;

public class FastDeployActivity extends Activity {
    private DeployBattlefieldView deployBattlefieldView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_deploy);
        deployBattlefieldView = (DeployBattlefieldView) findViewById(R.id.deploy_battlefield_view);
    }

}
