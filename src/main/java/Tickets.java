import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Data
public class Tickets {
    List<Ticket> tickets = new ArrayList<>();

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy hh:mm");

    public String avgDuration() {
        AtomicLong avgDuration = new AtomicLong();
        tickets.forEach(ticket -> {
            String departure = ticket.getDeparture_date() + " " + ticket.getDeparture_time();
            String arrival = ticket.getArrival_date() + " " + ticket.getArrival_time();

            try {
                Date departureTime = dateFormat.parse(departure);
                Date arrivalTime = dateFormat.parse(arrival);
                long flight = (arrivalTime.getTime() + 25_200_000) - departureTime.getTime();
                avgDuration.addAndGet(flight);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        long avg = avgDuration.longValue() / tickets.size();

        return String.format("%02d:%02d:%02d", avg / 3600000, avg / 60000 % 60, avg / 1000 % 60);
    }

    public String percentile () {
        List<Long> ticketsByTime = new ArrayList<>();

        tickets.forEach(ticket -> {
            String departure = ticket.getDeparture_date() + " " + ticket.getDeparture_time();
            String arrival = ticket.getArrival_date() + " " + ticket.getArrival_time();

            try {
                Date departureTime = dateFormat.parse(departure);
                Date arrivalTime = dateFormat.parse(arrival);
                long flight = (arrivalTime.getTime() + 25_200_000) - departureTime.getTime();
                ticketsByTime.add(flight);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        Collections.sort(ticketsByTime);
        double coefficient = 0.90 * ticketsByTime.size();
        int index = (int) Math.ceil(coefficient) - 1;

        return String.format("%02d:%02d:%02d", ticketsByTime.get(index) / 3600000, ticketsByTime.get(index) / 60000 % 60, ticketsByTime.get(index) / 1000 % 60);
    }
}
