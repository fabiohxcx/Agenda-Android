package es.esy.fabiohideki.agenda;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

import es.esy.fabiohideki.agenda.dao.AlunoDao;

/**
 * Created by Fabio on 28/01/2016.
 */
public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        Object messages[] = (Object[]) bundle.get("pdus");
        SmsMessage smsMessage[] = new SmsMessage[messages.length];

        SmsMessage[] msgs = new SmsMessage[messages.length];

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            msgs = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        }

        for (int n = 0; n < messages.length; n++) {
            if (Build.VERSION.SDK_INT >= 19) { //KITKAT
                smsMessage[n] = msgs[n];
            } else {
                smsMessage[n] = SmsMessage.createFromPdu((byte[]) messages[n]);
            }

            AlunoDao dao = new AlunoDao(context);



            if (dao.isAlunoTelefone(smsMessage[n].getDisplayOriginatingAddress())) {
                Toast.makeText(context, "SMS do contato: " + smsMessage[n].getMessageBody(), Toast.LENGTH_LONG).show();

                MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
                mp.start();
            }
        }


    }
}
