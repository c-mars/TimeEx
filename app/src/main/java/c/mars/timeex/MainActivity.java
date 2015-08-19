package c.mars.timeex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.t)
    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Long[] dates = {
                1439952600L,
                1439952900L,
                1439952900L,
                1439953200L
        };

        final String formatString = "MM/dd/yyyy hh:mm:ss";
//        final SimpleDateFormat dateFormat=new SimpleDateFormat(formatString);
        final DateTimeFormatter formatter=DateTimeFormat.forPattern(formatString);

        final DateTime date=new DateTime(DateTimeZone.getDefault());
//        final Date date = new Date();
        t.setText(formatter.print(date));

        Observable.from(dates).map(new Func1<Long, DateTime>() {
            @Override
            public DateTime call(Long aLong) {
                return new DateTime(aLong*1000, DateTimeZone.getDefault());//forOffsetHours(4) "America/New_York"
            }
        }).forEach(new Action1<DateTime>() {
            @Override
            public void call(DateTime dateTime) {
                t.append("\n" + formatter.print(dateTime));
            }
        });

        t.setMovementMethod(ScrollingMovementMethod.getInstance());

        rx.Observable.from(DateTimeZone.getAvailableIDs()).forEach(new Action1<String>() {
            @Override
            public void call(String s) {
                t.append("\n"+s);
            }
        });
    }

}
