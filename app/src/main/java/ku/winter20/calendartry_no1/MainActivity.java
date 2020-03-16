package ku.winter20.calendartry_no1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    String month[] = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
    int weekBlockId[] = { R.id.main_week0, R.id.main_week1, R.id.main_week2, R.id.main_week3, R.id.main_week4 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar cal = Calendar.getInstance();

        TextView calendarBar = (TextView) findViewById(R.id.main_calendarBar);
        calendarBar.setText(cal.get(Calendar.YEAR) + " " + month[ cal.get(Calendar.MONTH)] );

        cal.set(Calendar.DATE, 1);
        int firstDayInMonth = cal.get(Calendar.DAY_OF_WEEK) - 1;

        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DATE, daysInMonth);
        int lastWeekInMonth = cal.get(Calendar.WEEK_OF_MONTH) - 1;
        int lastDayInMonth = cal.get(Calendar.DAY_OF_WEEK) - 1;

        Log.d("CalendarInfo", "firstDayInmonth: " + firstDayInMonth + "\t daysInMonth:" + daysInMonth + "\t lastWeekInMonth:" + lastWeekInMonth + "\t lastDayInMonth:" + lastDayInMonth);

        int calDay = 0;
        for (int week=0; week<5; week++) {
            LinearLayout curWeekBlock = (LinearLayout) findViewById(weekBlockId[week]);

            for (int day=0; day<7; day++) {
                LinearLayout calBlock = new LinearLayout(getApplicationContext());
                calBlock.setId(week*7 + day + 1);
                calBlock.setLayoutParams( new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        1) );
                calBlock.setOrientation(LinearLayout.VERTICAL);

                if (week == 0 && day < firstDayInMonth) {
                    calBlock.setBackgroundColor(Color.parseColor("#AAAAAA"));
                    curWeekBlock.addView(calBlock);
                }
                else if (week == lastWeekInMonth && day > lastDayInMonth) {
                    calBlock.setBackgroundColor(Color.parseColor("#AAAAAA"));
                    curWeekBlock.addView(calBlock);
                }
                else if (week > lastWeekInMonth) {
                    calBlock.setBackgroundColor(Color.parseColor("#AAAAAA"));
                    curWeekBlock.addView(calBlock);
                }
                else {
                    TextView dayblock = new TextView(getApplicationContext());
                    dayblock.setLayoutParams( new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT) );
                    dayblock.setGravity(Gravity.CENTER);
                    dayblock.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                    dayblock.setTextColor(Color.parseColor("#000000"));

                    dayblock.setText( String.format("%d", ++calDay) );

                    calBlock.addView(dayblock);
                    curWeekBlock.addView(calBlock);
                }
            }
        }

        Button createEventBtn = (Button) findViewById(R.id.main_createEventBtn);
        createEventBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView eventBlock = new TextView(getApplicationContext());
                eventBlock.setLayoutParams( new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT) );
                eventBlock.setGravity(Gravity.CENTER);
                eventBlock.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
                eventBlock.setTextColor(Color.parseColor("#555555"));

                eventBlock.setText( String.format("Event%d", i++));
                eventBlock.setBackgroundColor( getRandomColor() );
                eventBlock.setSingleLine( true );

                Calendar cal = Calendar.getInstance();
                int randomDayNum = getRandomDayNum( cal.getActualMaximum(Calendar.DAY_OF_MONTH) );
                cal.set(Calendar.DATE, randomDayNum);

                Log.d("CreateRandomEvent", String.format("Random Event Day: %d", randomDayNum));
                LinearLayout calBlock = (LinearLayout) findViewById( (cal.get(Calendar.WEEK_OF_MONTH)-1) * 7 + (cal.get(Calendar.DAY_OF_WEEK)-1) + 1);
                calBlock.addView(eventBlock);
            }
        });
    }
    static int i=0;
    int getRandomDayNum(int daysInMonth) {
        return (int) (Math.random() * daysInMonth) + 1;
    }
    int getRandomColor() {
        int red = (int) (Math.random() * 256);
        String redStr = String.format("%02X", red).toUpperCase();
        int green = (int) (Math.random() * 256);
        String greenStr = String.format("%02X", green).toUpperCase();
        int blue = (int) (Math.random() * 256);
        String blueStr = String.format("%02X", blue).toUpperCase();

        Log.d("GetRandomColor", redStr + greenStr + blueStr);

        return Color.parseColor("#" + redStr + greenStr + blueStr);
    }
}
