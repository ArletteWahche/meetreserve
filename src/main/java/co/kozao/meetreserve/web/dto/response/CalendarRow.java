package co.kozao.meetreserve.web.dto.response;

import java.util.List;

public class CalendarRow {
    private final String label;
    private final List<ReservationResponse> cells;

    public CalendarRow(String label, List<ReservationResponse> cells) {
        this.label = label;
        this.cells = cells;
    }

    public String getLabel() { return label; }
    public List<ReservationResponse> getCells() { return cells; }
}