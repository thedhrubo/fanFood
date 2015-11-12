package axiz.four.androap.fanfood.utilities;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by emran on 10/29/15.
 */
public class Application extends android.app.Application {
    public Application() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this,"1Xl9S0MM1ENnN2gv2PKIfcqu6Ax6cFKqXQ8AeccX","OAwgnfrxI58NTIZeis4YlIeplP8wrPcEgsRzbWuC");
        //PushService.Se
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
