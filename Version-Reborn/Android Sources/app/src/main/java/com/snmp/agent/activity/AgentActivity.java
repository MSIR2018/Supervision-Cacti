package com.snmp.agent.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.wifi.WifiManager;
import android.os.*;

import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.snmp.agent.model.MIBtree;
import com.snmp.agent.service.AgentService;
import com.snmp.agent.R;

import java.util.ArrayList;

public class AgentActivity extends AppCompatActivity implements View.OnClickListener {
    /** Messenger for communicating with service. */
    Messenger mService = null;
    /** Flag indicating whether we have called bind on the service. */
    boolean mIsBound;


    private LinearLayout messagesReceivedScrollView;
    private ListView registeredManagersList;
    private ArrayAdapter<String> messagesReceivedAdapter;
    //private Button dangerButton;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TextView monIP = (TextView) findViewById(R.id.monIP);

        messagesReceivedScrollView = (LinearLayout) findViewById(R.id.snmp_messages_history);

        messagesReceivedAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        registeredManagersList = (ListView) findViewById(R.id.list_of_registered_managers);
        registeredManagersList.setAdapter(messagesReceivedAdapter);
        //dangerButton = (Button) findViewById(R.id.danger_alert_button);
        //dangerButton.setOnClickListener(this);

        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        monIP.setText("Adresse IP : "+ip);






        Intent intent = new Intent(this, AgentService.class);
        startService(intent);
        doBindAgentService();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            /*case R.id.danger_alert_button:
                handleSendDangerAlert();
                break;*/
        }
    }

    private void handleSendDangerAlert() {
        Message msg = Message.obtain(null,
                AgentService.MSN_SEND_DANGER_TRAP);
        msg.replyTo = mMessenger;
        sendMessageToAgentService(msg);
    }

    /**
     * Handler of incoming messages from service.
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case AgentService.MSG_SET_VALUE:
                    break;

                case AgentService.MSG_SNMP_REQUEST_RECEIVED:
                    TextView aux = new TextView(AgentActivity.this);

                    /*String myLastRequestReceived = AgentService.lastRequestReceived;
                    if (myLastRequestReceived.contains("1.3.6.1.2.1.1.1")) {
                        aux.setText("Récupération Model Number Info");
                    }else if(myLastRequestReceived.contains("1.3.6.1.2.1.1.2")){
                        aux.setText("Récupération Android Version Info");
                    }else if(myLastRequestReceived.contains("1.3.6.1.2.1.1.3")){
                        aux.setText("Récupération Cacti Info");
                    }else if(myLastRequestReceived.contains("1.3.6.1.2.1.1.4")){
                        aux.setText("Récupération Uptime Info");
                    }else if(myLastRequestReceived.contains("1.3.6.1.2.1.1.4")){
                        aux.setText("Récupération Contact Info");
                    }else if(myLastRequestReceived.contains("1.3.6.1.2.1.1.5")){
                        aux.setText("Récupération Name Info");
                    }else if(myLastRequestReceived.contains("1.3.6.1.2.1.1.6")){
                        aux.setText("Récupération Location Info");
                    }else if(myLastRequestReceived.contains("1.3.6.1.4.1.12619.1.2.1")){
                        aux.setText("Récupération Service Number Info");
                    }else if(myLastRequestReceived.contains("1.3.6.1.4.1.12619.1.2.2")){
                        //aux.setText("Récupération Service Table Info");
                    }else if(myLastRequestReceived.contains("1.3.6.1.4.1.12619.1.2.2.1")){
                        aux.setText("Récupération Service Entry Info");
                    }else if(myLastRequestReceived.contains("1.3.6.1.4.1.12619.1.2.2.1.1")){
                        aux.setText("Récupération Service Index Info");
                    }else if(myLastRequestReceived.contains("1.3.6.1.4.1.12619.1.2.2.1.2")){
                        aux.setText("Récupération Description Service Info");
                    }else if(myLastRequestReceived.contains("1.3.6.1.4.1.12619.1.2.2.1.3")){
                        aux.setText("Récupération Service Running Time Info");
                    }else if(myLastRequestReceived.contains("1.3.6.1.4.1.12619.1.2.2.1.4")){
                        aux.setText("Récupération Service Memory Used Info");
                    }else if(myLastRequestReceived.contains("1.3.6.1.4.1.12619.1.3.1")){
                        aux.setText("Récupération Battery Status Status Info");
                    }else if(myLastRequestReceived.contains("1.3.6.1.4.1.12619.1.3.2")){
                        aux.setText("Récupération Battery Level Info");
                    }else if(myLastRequestReceived.contains("1.3.6.1.4.1.12619.1.3.3")){
                        aux.setText("Récupération GPS Status Info");
                    }else if(myLastRequestReceived.contains("1.3.6.1.4.1.12619.1.3.4")){
                        aux.setText("Récupération Bluetooth Info");
                    }else if(myLastRequestReceived.contains("1.3.6.1.4.1.12619.1.3.5")){
                        aux.setText("Récupération Network Status Info");
                    }else if(myLastRequestReceived.contains("1.3.6.1.4.1.12619.1.3.6")){
                        aux.setText("Récupération Camera Status Info");
                    }else if(myLastRequestReceived.contains("1.3.6.1.4.1.12619.1.4.1")){
                        aux.setText("Récupération Manager Message Info");
                    }else if(myLastRequestReceived.contains("1.3.6.1.4.1.12619.9.1")){
                        aux.setText("Récupération Memory Info");
                    }else if(myLastRequestReceived.contains("1.3.6.1.4.1.12619.9.2")){
                        aux.setText("Récupération Disk Info");
                    }else if(myLastRequestReceived.contains("1.3.6.1.4.1.12619.9.3")){
                        aux.setText("END OF TRANSMISSION");
                    }*/

                    aux.setText(AgentService.lastRequestReceived);
                    messagesReceivedScrollView.addView(aux);

                    break;

                case AgentService.MSG_MANAGER_MESSAGE_RECEIVED:
                    MIBtree miBtree = MIBtree.getInstance();
                    String message = miBtree.getNext(MIBtree.MNG_MANAGER_MESSAGE_OID).getVariable().toString();
                    messagesReceivedAdapter.add(message);
                    messagesReceivedAdapter.notifyDataSetChanged();
                    break;

                default:
                    super.handleMessage(msg);
            }
        }
    }

    private void sendMessageToAgentService(Message msg){
        try {
            mService.send(msg);
        } catch (RemoteException e) {

        }
    }

    /**
     * Target we publish for clients to send messages to IncomingHandler.
     */
    final Messenger mMessenger = new Messenger(new IncomingHandler());

    /**
     * Class for interacting with the main interface of the service.
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {

            mService = new Messenger(service);

            // We want to monitor the service for as long as we are
            // connected to it.
            try {
                Message msg = Message.obtain(null,
                        AgentService.MSG_REGISTER_CLIENT);
                msg.replyTo = mMessenger;
                mService.send(msg);

                // Give it some value as an example.
                msg = Message.obtain(null,
                        AgentService.MSG_SET_VALUE, this.hashCode(), 0);
                mService.send(msg);
            } catch (RemoteException e) {

            }

        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            mService = null;
        }
    };

    void doBindAgentService() {
        // Establish a connection with the service.  We use an explicit
        // class name because there is no reason to be able to let other
        // applications replace our component.
        bindService(new Intent(this, AgentService.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindAgentService() {
        if (mIsBound) {
            // If we have received the service, and hence registered with
            // it, then now is the time to unregister.
            if (mService != null) {
                try {
                    Message msg = Message.obtain(null,
                            AgentService.MSG_UNREGISTER_CLIENT);
                    msg.replyTo = mMessenger;
                    mService.send(msg);
                } catch (RemoteException e) {

                }
            }

            // Detach our existing connection.
            unbindService(mConnection);
            mIsBound = false;
        }
    }

    @Override
    protected void onDestroy() {
        doUnbindAgentService();
        super.onDestroy();
    }
}
